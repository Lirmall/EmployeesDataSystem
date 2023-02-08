package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.dto.SalarybyEmployeeOnPeriodDTO;
import ru.klokov.employeesdatasystem.entities.*;
import ru.klokov.employeesdatasystem.exceptions.MoreThatOneResultException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SalaryService {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final EmployeeService employeeService;

    private final EmplPosRangeService emplPosRangeService;

    private final SalaryPeriodService salaryPeriodService;

    @Transactional(readOnly = true)
    public SalaryEntity getAverageSalaryByWorktypeId(WorktypeEntity worktypeEntity) {
        String query = "SELECT avg(salary) FROM EMPLOYEES where dismissed=false and worktype_id=:id";

        String salaryName = "Salary by \"" + worktypeEntity.getName() + "\" worktype";
        SalaryEntity salaryEntity = new SalaryEntity();
        Long id = worktypeEntity.getId();

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

        Double salary = namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, Double.class);

        salaryEntity.setNameOfSalary(salaryName);
        salaryEntity.setSalary(salary);

        return salaryEntity;
    }

    @Transactional(readOnly = true)
    public SalaryEntity getMonthSalaryByEmployeeId(EmployeeEntity employee) {
        String query = "SELECT salary FROM EMPLOYEES where dismissed=false and id=:id";

        String salaryName = "Month salary of an employee " + returnEmployeeFullName(employee);
        SalaryEntity salaryEntity = new SalaryEntity();
        Long id = employee.getId();

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id);

        Double salary = namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, Double.class);

        salaryEntity.setNameOfSalary(salaryName);
        salaryEntity.setSalary(salary);

        return salaryEntity;
    }

    @Transactional(readOnly = true)
    public SalaryEntity getSalaryOnPeriodByEmployee1(SalarybyEmployeeOnPeriodDTO dto) {
        SalaryEntity salaryEntity = new SalaryEntity();
        EmployeeEntity employee;
        List<EmployeeEntity> employeeEntities = findEmployee(dto);

        if (employeeEntities.size() == 1) {
            employee = employeeEntities.get(0);
        } else {
            throw new MoreThatOneResultException("More than one employee was founded");
        }

        List<EmployeePositionRangeEntity> allEPRList =
                emplPosRangeService.findEmployeePositionRangeEntitiesByEmployeeId(employee.getId());

        LocalDate periodStart = dto.getPeriodStart();
        LocalDate periodEnd = dto.getPeriodEnd();

        boolean periodStartsAtWorkstartDate = false;

        if (periodStart.isBefore(employee.getWorkstartDate())) {
            periodStart = employee.getWorkstartDate();
            periodStartsAtWorkstartDate = true;
        }

        if (employee.getDismissed().equals(true) && periodEnd.isAfter(employee.getDismissedDate())) {
            periodEnd = employee.getDismissedDate();
        }

        String salaryName = "Salary of an employee " + returnEmployeeFullName(employee) + " on period " + dto.getPeriodStart() + " - " + dto.getPeriodEnd();

        List<SalaryEntity> salaryEntities;

        if (periodStartsAtWorkstartDate) {
            salaryEntities = returnSalariesOnPeriodBySalaryWorktypeWhenStartDateIsWorkstartDate(periodStart, allEPRList, periodEnd, allEPRList.get(0));
        } else {
            salaryEntities = returnSalariesOnPeriodV2(periodStart, allEPRList, periodEnd, null);
//            salaryEntities = returnSalariesOnPeriod(periodStart, allEPRList, periodEnd, null);
        }
        double allSalary = 0.0;

        for (SalaryEntity s : salaryEntities) {
            allSalary = allSalary + s.getSalary();
        }

        salaryEntity.setNameOfSalary(salaryName);
        salaryEntity.setSalary(allSalary);

        return salaryEntity;
    }

    private List<SalaryEntity> returnSalariesOnPeriodV2(LocalDate periodStart,
                                                      List<EmployeePositionRangeEntity> eprList,
                                                      LocalDate periodEnd, EmployeePositionRangeEntity startEpr) {
        List<SalaryEntity> salaries = new ArrayList<>();

        EmployeePositionRangeEntity eprAtStart = startEpr;
        if(startEpr == null) {
            eprAtStart = getEmpPosRangeAtPeriodStart(eprList.get(0).getEmployeeId(), periodStart);
        }

        List<EmployeePositionRangeEntity> actualEPRs = actualizeEPRList(periodStart, periodEnd, eprList);
        actualEPRs.sort(Comparator.comparing(EmployeePositionRangeEntity::getPositionChangeDate));

        switch (actualEPRs.size()) {
            case 1:
                salaries = returnSalaries(salaryPeriodService.returnMonthPeriods(periodStart, periodEnd), actualEPRs.get(0));
                break;
            case 2:
                salaries.addAll(returnSalaries(salaryPeriodService.returnMonthPeriods(periodStart, actualEPRs.get(1).getPositionChangeDate()), actualEPRs.get(0)));
                salaries.addAll(returnSalaries(salaryPeriodService.returnMonthPeriods(actualEPRs.get(1).getPositionChangeDate(), periodEnd), actualEPRs.get(1)));
                break;
            default:
                for (int i = 0; i < actualEPRs.size() - 1; i++) {
                    EmployeePositionRangeEntity epr = actualEPRs.get(0);
                    if (i == 0) {
                        salaries = returnSalaries(salaryPeriodService.returnMonthPeriods(periodStart, actualEPRs.get(1).getPositionChangeDate()), actualEPRs.get(0));
                    }
                    else if (i == actualEPRs.size() - 1) {
                        salaries.addAll(returnSalaries(salaryPeriodService.returnMonthPeriods(actualEPRs.get(i).getPositionChangeDate(), periodEnd), actualEPRs.get(i)));
                    }
                    else {
                        salaries.addAll(returnSalaries(salaryPeriodService.returnMonthPeriods(actualEPRs.get(i-1).getPositionChangeDate(), actualEPRs.get(i).getPositionChangeDate()), actualEPRs.get(i-1)));
                    }
                }
                break;
        }

        return salaries;
    }

    private List<SalaryEntity> returnSalariesOnPeriodBySalaryWorktypeWhenStartDateIsWorkstartDate(LocalDate periodStart,
                                                                                                  List<EmployeePositionRangeEntity> eprList,
                                                                                                  LocalDate periodEnd, EmployeePositionRangeEntity startEpr) {

        List<SalaryPeriodEntity> periodsWithPosition = new ArrayList<>();
        Double salary;
        List<SalaryEntity> salaries = new ArrayList<>();

        List<EmployeePositionRangeEntity> listEPRBeforePerStart = new ArrayList<>(eprList);
        listEPRBeforePerStart.removeIf(employeePositionRangeEntity -> employeePositionRangeEntity.getPositionChangeDate().isAfter(periodStart));
        listEPRBeforePerStart.sort(Comparator.comparing(EmployeePositionRangeEntity::getPositionChangeDate));

        List<EmployeePositionRangeEntity> listEPRAfterPerStart = new ArrayList<>(eprList);
        listEPRAfterPerStart.removeIf(employeePositionRangeEntity -> employeePositionRangeEntity.getPositionChangeDate().isBefore(periodStart));
        listEPRAfterPerStart.sort(Comparator.comparing(EmployeePositionRangeEntity::getPositionChangeDate));
        EmployeePositionRangeEntity eprAtStart = startEpr;

        if (startEpr == null) {
            eprAtStart = getEmpPosRangeAtPeriodStart(eprList.get(0).getEmployeeId(), periodStart);
        }

        if (eprList.size() == 1) {
            periodsWithPosition = salaryPeriodService.returnMonthPeriods(periodStart, periodEnd);
            EmployeePositionRangeEntity epr = eprList.get(0);

            salaries.addAll(returnSalaries(periodsWithPosition, epr));
//            for (SalaryPeriodEntity salaryPeriodEntity : periodsWithPosition) {
//                Double salaryOnPeriod = epr.getPosition().getSalary() * epr.getRange().getBonus() * salaryPeriodEntity.getMultiplier();
//                String name = "Salary on period " + periodStart + " - " + periodEnd;
//                salaries.add(new SalaryEntity(name, salaryOnPeriod));
//            }

            for (SalaryPeriodEntity e : periodsWithPosition) {
                System.out.println(e.getPeriodStart() + " - " + e.getPeriodEnd() + " " + e.getMultiplier());
            }
            System.out.println("--------->");

            for (SalaryEntity s : salaries) {
                System.out.println(s.getNameOfSalary() + " - " + s.getSalary());
            }
            System.out.println("--------->");

            return salaries;
        }

        if (listEPRAfterPerStart.size() == 1) {
            List<SalaryPeriodEntity> startPeriod =
                    salaryPeriodService.returnMonthPeriods(eprAtStart.getPositionChangeDate(),
                            listEPRAfterPerStart.get(0).getPositionChangeDate());

            for (SalaryPeriodEntity salaryPeriodEntity : startPeriod) {
                Double salaryOnPeriod = eprAtStart.getPosition().getSalary() * eprAtStart.getRange().getBonus() * salaryPeriodEntity.getMultiplier();
                String name = "Salary on period " + salaryPeriodEntity.getPeriodStart() + " - " + salaryPeriodEntity.getPeriodEnd();
                salaries.add(new SalaryEntity(name, salaryOnPeriod));
            }

            List<SalaryPeriodEntity> endPeriod = salaryPeriodService.returnMonthPeriods(listEPRAfterPerStart.get(0).getPositionChangeDate(), periodEnd);

            for (SalaryPeriodEntity salaryPeriodEntity : endPeriod) {
                Double salaryOnPeriod = listEPRAfterPerStart.get(0).getPosition().getSalary() * listEPRAfterPerStart.get(0).getRange().getBonus() * salaryPeriodEntity.getMultiplier();
                String name = "Salary on period " + salaryPeriodEntity.getPeriodStart() + " - " + salaryPeriodEntity.getPeriodEnd();
                salaries.add(new SalaryEntity(name, salaryOnPeriod));
            }

            for (SalaryEntity s : salaries) {
                System.out.println(s.getNameOfSalary() + " - " + s.getSalary());
            }
            System.out.println("--------->");

            return salaries;
        }


        eprList.sort(Comparator.comparing(EmployeePositionRangeEntity::getPositionChangeDate));


        if (eprList.size() == 2) {
            LocalDate newPeriodEndDate = eprList.get(1).getPositionChangeDate();
            if (newPeriodEndDate.isAfter(periodEnd)) {
                newPeriodEndDate = periodEnd;
            }
            List<SalaryPeriodEntity> bukhgalterPeriodsWithPosition1 =
                    salaryPeriodService.returnMonthPeriods(periodStart, newPeriodEndDate);
            for (SalaryPeriodEntity s : bukhgalterPeriodsWithPosition1) {
                Double salaryOnPeriod = eprList.get(0).getPosition().getSalary() * eprList.get(0).getRange().getBonus() * s.getMultiplier();
                String name = "Salary on period " + s.getPeriodStart() + " - " + s.getPeriodEnd();
                salaries.add(new SalaryEntity(name, salaryOnPeriod));
            }

            if (eprList.get(1).getPositionChangeDate().isBefore(periodEnd)) {
                List<SalaryPeriodEntity> bukhgalterPeriodsWithPosition3 =
                        salaryPeriodService.returnMonthPeriods(eprList.get(1).getPositionChangeDate(), periodEnd);
                for (SalaryPeriodEntity s : bukhgalterPeriodsWithPosition3) {
                    Double salaryOnPeriod = eprList.get(1).getPosition().getSalary() * eprList.get(1).getRange().getBonus() * s.getMultiplier();
                    String name = "Salary on period " + s.getPeriodStart() + " - " + s.getPeriodEnd();
                    salaries.add(new SalaryEntity(name, salaryOnPeriod));
                }
            }

            for (SalaryEntity s : salaries) {
                System.out.println(s.getNameOfSalary() + " - " + s.getSalary());
            }
            System.out.println("--------->");

            return salaries;
        }


        for (int i = 0; i < eprList.size(); i++) {
            if (i == 0) {
                List<SalaryPeriodEntity> bukhgalterPeriodsWithPosition =
                        salaryPeriodService.returnMonthPeriods(periodStart, listEPRAfterPerStart.get(1).getPositionChangeDate());
                for (SalaryPeriodEntity s : bukhgalterPeriodsWithPosition) {
                    Double salaryOnPeriod = listEPRAfterPerStart.get(0).getPosition().getSalary() * listEPRAfterPerStart.get(0).getRange().getBonus() * s.getMultiplier();
                    String name = "Salary on period " + s.getPeriodStart() + " - " + s.getPeriodEnd();
                    salaries.add(new SalaryEntity(name, salaryOnPeriod));
                }
            }

            if (i >= 1 && i < eprList.size() - 1) {
                List<SalaryPeriodEntity> bukhgalterPeriodsWithPosition =
                        salaryPeriodService.returnMonthPeriods(listEPRBeforePerStart.get(i - 1).getPositionChangeDate(), listEPRAfterPerStart.get(i).getPositionChangeDate());
                for (SalaryPeriodEntity s : bukhgalterPeriodsWithPosition) {
                    Double salaryOnPeriod = listEPRAfterPerStart.get(i - 1).getPosition().getSalary() * listEPRAfterPerStart.get(i - 1).getRange().getBonus() * s.getMultiplier();
                    String name = "Salary on period " + s.getPeriodStart() + " - " + s.getPeriodEnd();
                    salaries.add(new SalaryEntity(name, salaryOnPeriod));
                }
            }

            if (i == eprList.size() - 1) {
                List<SalaryPeriodEntity> bukhgalterPeriodsWithPosition =
                        salaryPeriodService.returnMonthPeriods(listEPRBeforePerStart.get(i - 1).getPositionChangeDate(), periodEnd);
                for (SalaryPeriodEntity s : bukhgalterPeriodsWithPosition) {
                    Double salaryOnPeriod = listEPRAfterPerStart.get(i - 1).getPosition().getSalary() * listEPRAfterPerStart.get(i - 1).getRange().getBonus() * s.getMultiplier();
                    String name = "Salary on period " + s.getPeriodStart() + " - " + s.getPeriodEnd();
                    salaries.add(new SalaryEntity(name, salaryOnPeriod));
                }
            }
        }

        for (SalaryPeriodEntity e : periodsWithPosition) {
            System.out.println(e.getPeriodStart() + " - " + e.getPeriodEnd() + " " + e.getMultiplier());
        }
        System.out.println("--------->");

        for (SalaryEntity s : salaries) {
            System.out.println(s.getNameOfSalary() + " - " + s.getSalary());
        }
        System.out.println("--------->");


        return salaries;
    }
//    @Deprecated
//    @Transactional(readOnly = true)
//    public SalaryEntity getSalaryOnPeriodByEmployeeOnPeriodDTOver1(SalarybyEmployeeOnPeriodDTO dto) {
//        SalaryEntity salaryEntity = new SalaryEntity();
//        EmployeeEntity employee;
//        List<EmployeeEntity> employeeEntities = findEmployee(dto);
//
//        if (employeeEntities.size() == 1) {
//            employee = employeeEntities.get(0);
//        } else {
//            throw new AlreadyCreatedException("More than one or no one employee was founded");
//        }
//
//        Double salary = (ChronoUnit.DAYS.between(dto.getPeriodStart(), dto.getPeriodEnd()) / 30.0) * employee.getSalary();
//
//        salaryEntity.setNameOfSalary("Test");
//        salaryEntity.setSalary(salary);
//
//
//        return salaryEntity;
//    }

    private List<EmployeeEntity> findEmployee(SalarybyEmployeeOnPeriodDTO dto) {
        List<EmployeeEntity> findEntities = employeeService.findEmployeeEntityByBirthday(dto.getBirthdayDate());
        findEntities.removeIf(employee -> !Objects.equals(employee.getFirstName(), dto.getFirstName()));
        findEntities.removeIf(employee -> !Objects.equals(employee.getThirdName(), dto.getThirdName()));
        findEntities.removeIf(employee -> !Objects.equals(employee.getSecondName(), dto.getSecondName()));
        return findEntities;
    }

    private PositionEntity returnStartPosition(List<EmployeePositionRangeEntity> eprList, LocalDate periodStart) {
        PositionEntity position;
        if (eprList.size() == 1) {
            position = eprList.get(0).getPosition();
        } else {
            position = getPositionAtPeriodStart(eprList, periodStart);
        }
        return position;
    }

    private PositionEntity getPositionAtPeriodStart(List<EmployeePositionRangeEntity> eprList, LocalDate periodStart) {
        List<EmployeePositionRangeEntity> list = new ArrayList<>(eprList);
        list.removeIf(employeePositionRangeEntity -> employeePositionRangeEntity.getPositionChangeDate().isAfter(periodStart));

        Long maxId = eprList.get(0).getId();

        for (EmployeePositionRangeEntity entity : eprList) {
            if (maxId < entity.getId()) {
                maxId = entity.getId();
            }
        }

        Long finalMaxId = maxId;
        eprList.removeIf(employeePositionRangeEntity -> !Objects.equals(employeePositionRangeEntity.getId(), finalMaxId));

        return eprList.get(0).getPosition();
    }

    private EmployeePositionRangeEntity getEmpPosRangeAtPeriodStart(Long id, LocalDate periodStart) {
        List<EmployeePositionRangeEntity> eprList = emplPosRangeService.findByEmployeeIdWherePositionChangeDateLessThan(id, periodStart);

        if (eprList.isEmpty()) {
            eprList = emplPosRangeService.findByEmployeeIdWherePositionChangeDateLessThan(id, periodStart.plusDays(1));
        }

        Long maxId = eprList.get(0).getId();

        for (EmployeePositionRangeEntity entity : eprList) {
            if (maxId < entity.getId()) {
                maxId = entity.getId();
            }
        }

        Long finalMaxId = maxId;
        eprList.removeIf(employeePositionRangeEntity -> !Objects.equals(employeePositionRangeEntity.getId(), finalMaxId));

        return eprList.get(0);
    }

    private List<SalaryEntity> returnSalaries(List<SalaryPeriodEntity> periodsWithPosition, EmployeePositionRangeEntity epr) {
        List<SalaryEntity> salaries = new ArrayList<>();

        Double salary = epr.getPosition().getSalary();
        Double rangeBonus;
        Double workedHours = 1.0;

        boolean salaryWorktype = epr.getPosition().getWorktypeId().equals(1L);

        if(salaryWorktype) {
            rangeBonus = 1.0;
        } else {
            rangeBonus = epr.getRange().getBonus();
        }

        for (SalaryPeriodEntity salaryPeriodEntity : periodsWithPosition) {
            if(!salaryWorktype) {
                workedHours = ChronoUnit.DAYS.between(salaryPeriodEntity.getPeriodStart(), salaryPeriodEntity.getPeriodEnd()) * 12.0;
            }

            Double salaryOnPeriod = salary * workedHours * rangeBonus * salaryPeriodEntity.getMultiplier();

            String name = "Salary on period " + salaryPeriodEntity.getPeriodStart() + " - " + salaryPeriodEntity.getPeriodEnd();
            salaries.add(new SalaryEntity(name, salaryOnPeriod));
        }

        return salaries;
    }

    private List<EmployeePositionRangeEntity> returnListEPRBeforePerStart (LocalDate periodStart, List<EmployeePositionRangeEntity> eprList) {
    List<EmployeePositionRangeEntity> listEPRBeforePerStart = new ArrayList<>(eprList);
        listEPRBeforePerStart.removeIf(employeePositionRangeEntity -> employeePositionRangeEntity.getPositionChangeDate().isAfter(periodStart));
        listEPRBeforePerStart.sort(Comparator.comparing(EmployeePositionRangeEntity::getPositionChangeDate));

        return listEPRBeforePerStart;
    }

    private List<EmployeePositionRangeEntity> returnListEPRAfterPerStart(LocalDate periodStart, List<EmployeePositionRangeEntity> eprList) {
        List<EmployeePositionRangeEntity> listEPRAfterPerStart = new ArrayList<>(eprList);
        listEPRAfterPerStart.removeIf(employeePositionRangeEntity -> employeePositionRangeEntity.getPositionChangeDate().isBefore(periodStart));
        listEPRAfterPerStart.sort(Comparator.comparing(EmployeePositionRangeEntity::getPositionChangeDate));

        if(listEPRAfterPerStart.isEmpty()) {
            EmployeePositionRangeEntity e = eprList.get(eprList.size()-1);
            listEPRAfterPerStart.add(e);
        }

        return listEPRAfterPerStart;
    }

    private double returnDoubleCountDaysOfMonth(LocalDate date) {
        boolean leapYear = date.getYear() % 4 == 0;
        return date.getMonth().length(leapYear);
    }

    private int returnIntCountDaysOfMonth(LocalDate date) {
        boolean leapYear = date.getYear() % 4 == 0;
        return date.getMonth().length(leapYear);
    }

    private String returnEmployeeFullName(EmployeeEntity employee) {
        return " " + employee.getSecondName() + " " + employee.getFirstName() + " " + employee.getThirdName();
    }

    private List<EmployeePositionRangeEntity> deleteEPRAfterPeriodEnd(List<EmployeePositionRangeEntity> eprList, LocalDate periodEnd) {
        List<EmployeePositionRangeEntity> editedList = new ArrayList<>(eprList);
        editedList.removeIf(employeePositionRangeEntity -> employeePositionRangeEntity.getPositionChangeDate().isAfter(periodEnd));
        return editedList;
    }

    private List<EmployeePositionRangeEntity> deleteEPREarlierStartEPR(List<EmployeePositionRangeEntity> eprList, LocalDate periodStart) {
        EmployeePositionRangeEntity epr = getEmpPosRangeAtPeriodStart(eprList.get(0).getEmployeeId(), periodStart);
        List<EmployeePositionRangeEntity> editedList = new ArrayList<>(eprList);
        editedList.removeIf(employeePositionRangeEntity -> employeePositionRangeEntity.getPositionChangeDate().isBefore(epr.getPositionChangeDate()));
        return editedList;
    }

    private List<EmployeePositionRangeEntity> actualizeEPRList(LocalDate periodStart, LocalDate periodEnd, List<EmployeePositionRangeEntity> eprList) {
        List<EmployeePositionRangeEntity> startList = new ArrayList<>(eprList);
        List<EmployeePositionRangeEntity> firstEdit = deleteEPREarlierStartEPR(startList, periodStart);

        return deleteEPRAfterPeriodEnd(firstEdit, periodEnd);
    }
}
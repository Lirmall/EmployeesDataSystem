package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.dto.SalarybyEmployeeOnPeriodDTO;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.SalaryEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.repositories.EmployeeRepository;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;
import ru.klokov.employeesdatasystem.repositories.RangeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, EmployeeRepository.class,
        RangeRepository.class, PositionRepository.class,
        EmployeeRangeService.class, PositionEmployeeService.class, EmployeeGenderService.class,
        WorktypeEmployeeService.class, EmplPosRangeService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class SalaryServiceTest {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private WorktypeService worktypeService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
//            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
//            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
//            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getAverageSalaryByWorktypeId() {
        WorktypeEntity worktype = worktypeService.findById(2L);

        SalaryEntity salaryEntity = salaryService.getAverageSalaryByWorktypeId(worktype);

        assertEquals("Salary by \"Hourly\" worktype per working day", salaryEntity.getNameOfSalary());
        assertEquals(120.0, salaryEntity.getSalary());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
//            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
//            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
//            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getAverageSalaryByEmployeeId() {
        EmployeeEntity employee = employeeService.findById(5L);

        SalaryEntity salaryEntity = salaryService.getMonthSalaryByEmployeeId(employee);

        assertEquals("Month salary of an employee  Minaeva Elena Anatolyevna per month", salaryEntity.getNameOfSalary());
        assertEquals(20000.0, salaryEntity.getSalary());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getSalaryOnPeriodByEmployee1() {
        EmployeeEntity employee = employeeService.findById(5L);

        SalarybyEmployeeOnPeriodDTO dto = new SalarybyEmployeeOnPeriodDTO();
        dto.setSecondName(employee.getSecondName());
        dto.setFirstName(employee.getFirstName());
        dto.setThirdName(employee.getThirdName());
        dto.setBirthdayDate(employee.getBirthday());
        dto.setPeriodStart(LocalDate.of(2022, 6, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        SalaryEntity salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#1
        assertEquals("Salary of an employee  Minaeva Elena Anatolyevna on period 2022-06-01 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #1");
        assertEquals(60000.0, salaryEntity.getSalary(), "Trouble with salary at #1");

        dto.setPeriodStart(LocalDate.of(2022, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#2
        assertEquals("Salary of an employee  Minaeva Elena Anatolyevna on period 2022-06-12 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #2");
        assertEquals(52666.666666666664, salaryEntity.getSalary(), "Trouble with salary at #2");

        dto.setPeriodStart(LocalDate.of(2022, 6, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#3
        assertEquals("Salary of an employee  Minaeva Elena Anatolyevna on period 2022-06-01 - 2022-08-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #3");
        assertEquals(50322.58064516129, salaryEntity.getSalary(), "Trouble with salary at #3");

        dto.setPeriodStart(LocalDate.of(2022, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#4
        assertEquals("Salary of an employee  Minaeva Elena Anatolyevna on period 2022-06-12 - 2022-08-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #4");
        assertEquals(42322.58064516129, salaryEntity.getSalary(), "Trouble with salary at #4");
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getSalaryOnPeriodByEmployee1_test2() {
        EmployeeEntity employee = employeeService.findById(1L);

        SalarybyEmployeeOnPeriodDTO dto = new SalarybyEmployeeOnPeriodDTO();
        dto.setSecondName(employee.getSecondName());
        dto.setFirstName(employee.getFirstName());
        dto.setThirdName(employee.getThirdName());
        dto.setBirthdayDate(employee.getBirthday());
        dto.setPeriodStart(LocalDate.of(2022, 6, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        SalaryEntity salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#1
        assertEquals("Salary of an employee  Ivanov Ivan Ivanovich on period 2022-06-01 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #1");
        assertEquals(128160.0, salaryEntity.getSalary(), "Trouble with salary at #1");

        dto.setPeriodStart(LocalDate.of(2022, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#2
        assertEquals("Salary of an employee  Ivanov Ivan Ivanovich on period 2022-06-12 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #2");
        assertEquals(102816.0, salaryEntity.getSalary(), "Trouble with salary at #2");

        dto.setPeriodStart(LocalDate.of(2022, 6, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#3
        assertEquals("Salary of an employee  Ivanov Ivan Ivanovich on period 2022-06-01 - 2022-08-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #3");
        assertEquals(96108.3870967742, salaryEntity.getSalary(), "Trouble with salary at #3");

        dto.setPeriodStart(LocalDate.of(2022, 3, 20));
        dto.setPeriodEnd(LocalDate.of(2022, 4, 20));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#4
        assertEquals("Salary of an employee  Ivanov Ivan Ivanovich on period 2022-03-20 - 2022-04-20",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #4");
        assertEquals(23860.645161290322, salaryEntity.getSalary(), "Trouble with salary at #4");
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getSalaryOnPeriodByEmployee1withPositionChanges() {
        EmployeeEntity employee = employeeService.findById(8L);

        SalarybyEmployeeOnPeriodDTO dto = new SalarybyEmployeeOnPeriodDTO();
        dto.setSecondName(employee.getSecondName());
        dto.setFirstName(employee.getFirstName());
        dto.setThirdName(employee.getThirdName());
        dto.setBirthdayDate(employee.getBirthday());
        dto.setPeriodStart(LocalDate.of(2022, 6, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        SalaryEntity salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#1
        assertEquals("Salary of an employee  Sazhin Alexander Vladimirovich on period 2022-06-01 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #1");
        assertEquals(80483.87096774194, salaryEntity.getSalary(), "Trouble with salary at #1");

        dto.setPeriodStart(LocalDate.of(2022, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#2
        assertEquals("Salary of an employee  Sazhin Alexander Vladimirovich on period 2022-06-12 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #2");
        assertEquals(70483.87096774194, salaryEntity.getSalary(), "Trouble with salary at #2");

        dto.setPeriodStart(LocalDate.of(2022, 6, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#3
        assertEquals("Salary of an employee  Sazhin Alexander Vladimirovich on period 2022-06-01 - 2022-08-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #3");
        assertEquals(65967.74193548386, salaryEntity.getSalary(), "Trouble with salary at #3");

        dto.setPeriodStart(LocalDate.of(2022, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#4
        assertEquals("Salary of an employee  Sazhin Alexander Vladimirovich on period 2022-06-12 - 2022-08-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #4");
        assertEquals(55967.741935483864, salaryEntity.getSalary(), "Trouble with salary at #4");

        dto.setPeriodStart(LocalDate.of(2022, 2, 20));
        dto.setPeriodEnd(LocalDate.of(2022, 3, 20));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#5
        assertEquals("Salary of an employee  Sazhin Alexander Vladimirovich on period 2022-02-20 - 2022-03-20",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #5");
        assertEquals(23271.889400921656, salaryEntity.getSalary(), "Trouble with salary at #5");
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getSalaryOnPeriodByEmployee1withMoreOnePositionChanges() {
        EmployeeEntity employee = employeeService.findById(7L);

        SalarybyEmployeeOnPeriodDTO dto = new SalarybyEmployeeOnPeriodDTO();
        dto.setSecondName(employee.getSecondName());
        dto.setFirstName(employee.getFirstName());
        dto.setThirdName(employee.getThirdName());
        dto.setBirthdayDate(employee.getBirthday());
        dto.setPeriodStart(LocalDate.of(2020, 11, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 9, 30));

        SalaryEntity salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#1
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2020-11-01 - 2022-09-30",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #1");
        assertEquals(452666.6666666666, salaryEntity.getSalary(), "Trouble with salary at #1");

        dto.setPeriodStart(LocalDate.of(2022, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#2
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2022-06-12 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #2");
        assertEquals(52666.666666666664, salaryEntity.getSalary(), "Trouble with salary at #2");

        dto.setPeriodStart(LocalDate.of(2020, 11, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 9, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#3
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2020-11-01 - 2022-09-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #3");
        assertEquals(441000.0, salaryEntity.getSalary(), "Trouble with salary at #3");

        dto.setPeriodStart(LocalDate.of(2020, 11, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 9, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#4
        //Results are same because the employee was hired after period start. Maybe need change period start
        // to work start date
        //TODO fix it? Today - 8 feb 2023 - i didn't come to a decision
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2020-11-12 - 2022-09-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #4");
        assertEquals(441000.0, salaryEntity.getSalary(), "Trouble with salary at #4");
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getSalaryOnPeriodWherePeriodStartBeforeWorkstartDate() {
        EmployeeEntity employee = employeeService.findById(7L);

        SalarybyEmployeeOnPeriodDTO dto = new SalarybyEmployeeOnPeriodDTO();
        dto.setSecondName(employee.getSecondName());
        dto.setFirstName(employee.getFirstName());
        dto.setThirdName(employee.getThirdName());
        dto.setBirthdayDate(employee.getBirthday());
        dto.setPeriodStart(LocalDate.of(2020, 11, 20));
        dto.setPeriodEnd(LocalDate.of(2021, 1, 31));

        SalaryEntity salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#1
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2020-11-20 - 2021-01-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #1");
        assertEquals(47333.33333333333, salaryEntity.getSalary(), "Trouble with salary at #1");
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getSalaryOnPeriodByEmployee1withMoreOnePositionChangesSalaryServiceVer2() {
        EmployeeEntity employee = employeeService.findById(7L);

        SalarybyEmployeeOnPeriodDTO dto = new SalarybyEmployeeOnPeriodDTO();
        dto.setSecondName(employee.getSecondName());
        dto.setFirstName(employee.getFirstName());
        dto.setThirdName(employee.getThirdName());
        dto.setBirthdayDate(employee.getBirthday());
        dto.setPeriodStart(LocalDate.of(2020, 11, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 9, 30));

        SalaryEntity salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#1
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2020-11-01 - 2022-09-30",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #1");
        assertEquals(452666.6666666666, salaryEntity.getSalary(), "Trouble with salary at #1");

        dto.setPeriodStart(LocalDate.of(2020, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#2
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2020-06-12 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #2");
        assertEquals(427333.3333333333, salaryEntity.getSalary(), "Trouble with salary at #2");

        dto.setPeriodStart(LocalDate.of(2020, 11, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 9, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#3
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2020-11-01 - 2022-09-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #3");
        assertEquals(441000.0, salaryEntity.getSalary(), "Trouble with salary at #3");

        dto.setPeriodStart(LocalDate.of(2020, 11, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 9, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#4
        assertEquals("Salary of an employee  Fomin Alexey Petrovich on period 2020-11-12 - 2022-09-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #4");
        assertEquals(441000.0, salaryEntity.getSalary(), "Trouble with salary at #4");
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA
    })
    void getSalaryOnPeriodByEmployee1withPositionChangesHourlyToSalary() {
        EmployeeEntity employee = employeeService.findById(6L);

        SalarybyEmployeeOnPeriodDTO dto = new SalarybyEmployeeOnPeriodDTO();
        dto.setSecondName(employee.getSecondName());
        dto.setFirstName(employee.getFirstName());
        dto.setThirdName(employee.getThirdName());
        dto.setBirthdayDate(employee.getBirthday());
        dto.setPeriodStart(LocalDate.of(2022, 6, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        SalaryEntity salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#1
        assertEquals("Salary of an employee  Popov Sergey Gennadievch on period 2022-06-01 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #1");
        assertEquals(108669.67741935483, salaryEntity.getSalary(), "Trouble with salary at #1");

        dto.setPeriodStart(LocalDate.of(2022, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 31));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#2
        assertEquals("Salary of an employee  Popov Sergey Gennadievch on period 2022-06-12 - 2022-08-31",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #2");
        assertEquals(80277.67741935483, salaryEntity.getSalary(), "Trouble with salary at #2");

        dto.setPeriodStart(LocalDate.of(2022, 6, 1));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#3
        assertEquals("Salary of an employee  Popov Sergey Gennadievch on period 2022-06-01 - 2022-08-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #3");
        assertEquals(99960.0, salaryEntity.getSalary(), "Trouble with salary at #3");

        dto.setPeriodStart(LocalDate.of(2022, 6, 12));
        dto.setPeriodEnd(LocalDate.of(2022, 8, 16));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#4
        assertEquals("Salary of an employee  Popov Sergey Gennadievch on period 2022-06-12 - 2022-08-16",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #4");
        assertEquals(71568.0, salaryEntity.getSalary(), "Trouble with salary at #4");

        dto.setPeriodStart(LocalDate.of(2022, 2, 20));
        dto.setPeriodEnd(LocalDate.of(2022, 3, 20));

        salaryEntity = salaryService.getSalaryOnPeriodByEmployee(dto);

        //#5
        assertEquals("Salary of an employee  Popov Sergey Gennadievch on period 2022-02-20 - 2022-03-20",
                salaryEntity.getNameOfSalary(), "Trouble with name of salary at #5");
        assertEquals(22688.294930875574, salaryEntity.getSalary(), "Trouble with salary at #5");
    }

}
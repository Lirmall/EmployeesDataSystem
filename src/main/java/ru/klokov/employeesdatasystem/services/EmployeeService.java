package ru.klokov.employeesdatasystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klokov.employeesdatasystem.dto.GenderDTO;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.*;
import ru.klokov.employeesdatasystem.exceptions.AlreadyCreatedException;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.repositories.EmployeeRepository;
import ru.klokov.employeesdatasystem.specifications.employeeSpecification.worktypesSpecification.EmployeeSearchModel;
import ru.klokov.employeesdatasystem.specifications.employeeSpecification.worktypesSpecification.EmployeeSpecification;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeRangeService employeeRangeService;
    private final PositionEmployeeService positionEmployeeService;
    private final EmployeeGenderService employeeGenderService;
    private final WorktypeEmployeeService worktypeEmployeeService;
    private final SortColumnChecker sortColumnChecker;
    private final EmplPosRangeService emplPosRangeService;

    @Transactional
    public EmployeeEntity create2(CreateEmployeeEntity entity) {
        EmployeeEntity employeeToCreate = new EmployeeEntity();
        PositionEntity position = positionEmployeeService.findPositionByName(entity.getPosition().getName());

        GenderEntity genderEntity = entity.getGender();
        RangeEntity range = employeeRangeService.rangeCheck(entity.getRange());

        employeeToCreate.setSecondName(entity.getSecondName());
        employeeToCreate.setFirstName(entity.getFirstName());
        employeeToCreate.setThirdName(entity.getThirdName());
        employeeToCreate.setBirthday(entity.getBirthdayDate());

        GenderEntity gender = employeeGenderService.findGenderByName(genderEntity.getName());

        employeeToCreate.setGender(gender);

        WorktypeEntity worktype = positionEmployeeService.findPositionByName(position.getName()).getWorktype();

        employeeToCreate.setWorktype(worktype);

        employeeToCreate.setDismissed(false);
        employeeToCreate.setDismissedDate(null);

        employeeToCreate.setWorkstartDate(entity.getWorkstartDate());

        employeeToCreate.setSalary(position.getSalary() * range.getBonus());

        employeeRepository.save(employeeToCreate);

        emplPosRangeService.addEmployeePositionRangeEntity(employeeToCreate, position, range);

        List<EmployeeEntity> employeeEntities = findEmployeeEntityByEmployee(employeeToCreate);

        if(employeeEntities.size() == 1) {
            return employeeEntities.get(0);
        } else {
            throw new AlreadyCreatedException("Employee wit this parameters was already created");
        }
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public EmployeeEntity findById(Long id) {
        Optional<EmployeeEntity> foundRange = employeeRepository.findById(id);

        if (foundRange.isPresent()) {
            return foundRange.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("Worktype wit id =  " + id + " not found in database");
        }
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findEmployeeBySecondName(String name) {
        return employeeRepository.findEmployeeEntityBySecondName(name);
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findEmployeeByFirstName(String name) {
        return employeeRepository.findEmployeeEntityByFirstName(name);
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findEmployeeByThirdName(String name) {
        return employeeRepository.findEmployeeEntityByThirdName(name);
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findEmployeeByGender(GenderDTO genderDTO) {
        GenderEntity genderEntity = employeeGenderService.findById(genderDTO.getId());

        if(genderEntity == null) {
            genderEntity = employeeGenderService.findGenderByName(genderDTO.getName());
        }

        if(genderEntity == null) {
            throw new NoMatchingEntryInDatabaseException("Gender not found in database");
        }

        return employeeRepository.findEmployeeEntityByGender(genderEntity);
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findEmployeeEntityByWorktype(WorktypeDTO worktypeDTO) {
        WorktypeEntity worktypeEntity = worktypeEmployeeService.findById(worktypeDTO.getId());

        if(worktypeEntity == null) {
            worktypeEntity = worktypeEmployeeService.findByName(worktypeDTO.getName());
        }

        if(worktypeEntity == null) {
            throw new NoMatchingEntryInDatabaseException("Gender not found in database");
        }

        return employeeRepository.findEmployeeEntityByWorktype(worktypeEntity);
    }

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findEmployeeEntityByBirthday(LocalDate date) {
        return employeeRepository.findEmployeeEntityByBirthday(date);
    }


    @Transactional(readOnly = true)
    public Page<EmployeeEntity> findByFilter(EmployeeSearchModel request) {
        Sort sort = sortColumnChecker.sortColumnCheck(request);

        int page = request.getPages() != null ? request.getPages() : 0;
        int size = (request.getLimit() != null && request.getLimit() != 0) ? request.getLimit() : 5;

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(new EmployeeSpecification(request), pageable);
    }

    private List<EmployeeEntity> findEmployeeEntityByEmployee(EmployeeEntity employeeEntity) {
        List<EmployeeEntity> findEntities = employeeRepository.findEmployeeEntityByBirthday(employeeEntity.getBirthday());
        findEntities.removeIf(employee -> !Objects.equals(employee.getSecondName(), employeeEntity.getSecondName()));
        findEntities.removeIf(employee -> !Objects.equals(employee.getFirstName(), employeeEntity.getFirstName()));
        findEntities.removeIf(employee -> !Objects.equals(employee.getThirdName(), employeeEntity.getThirdName()));
        findEntities.removeIf(employee -> !Objects.equals(employee.getBirthday(), employeeEntity.getBirthday()));
        findEntities.removeIf(employee -> !Objects.equals(employee.getWorkstartDate(), employeeEntity.getWorkstartDate()));

        return findEntities;
    }

    public long getCountOfTotalItems() {
        return employeeRepository.count();
    }
}

package ru.klokov.employeesdatasystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.GenderEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
    List<EmployeeEntity> findEmployeeEntityByBirthday(LocalDate date);
    List<EmployeeEntity> findEmployeeEntityByBirthdayBefore(LocalDate date);
    List<EmployeeEntity> findEmployeeEntityByBirthdayAfter(LocalDate date);
    List<EmployeeEntity> findEmployeeEntityByBirthdayBetween(LocalDate minDate, LocalDate maxDate);
    List<EmployeeEntity> findEmployeeEntityByFirstName(String firstName);
    List<EmployeeEntity> findEmployeeEntityBySecondName(String secondName);
    List<EmployeeEntity> findEmployeeEntityByThirdName(String thirdName);
    List<EmployeeEntity> findEmployeeEntityByGender(GenderEntity gender);
    List<EmployeeEntity> findEmployeeEntityByWorktype(WorktypeEntity worktypeEntity);
    List<EmployeeEntity> findEmployeeEntityBySalary(Double salary);
    List<EmployeeEntity> findEmployeeEntityBySalaryIsBetween(Double minSalary, Double maxSalary);
    List<EmployeeEntity> findEmployeeEntityBySalaryGreaterThan(Double salary);
    List<EmployeeEntity> findEmployeeEntityBySalaryLessThan(Double salary);
    List<EmployeeEntity> findEmployeeEntityByWorkstartDate(LocalDate date);
    List<EmployeeEntity> findEmployeeEntityByDismissed(Boolean dismissed);
    List<EmployeeEntity> findEmployeeEntityByDismissedDateAfter(LocalDate date);
    List<EmployeeEntity> findEmployeeEntityByDismissedDateBefore(LocalDate date);
    List<EmployeeEntity> findEmployeeEntityByDismissedFalse();
    List<EmployeeEntity> findEmployeeEntityByDismissedTrue();
}

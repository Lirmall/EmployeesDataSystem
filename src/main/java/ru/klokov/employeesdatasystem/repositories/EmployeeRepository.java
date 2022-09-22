package ru.klokov.employeesdatasystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.GenderEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;

import java.time.LocalDate;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
    EmployeeEntity findEmployeeEntityByBirthday(LocalDate date);
    EmployeeEntity findEmployeeEntityByFirstName(String firstName);
    EmployeeEntity findEmployeeEntityBySecondName(String secondName);
    EmployeeEntity findEmployeeEntityByThirdName(String thirdName);
    EmployeeEntity findEmployeeEntityByGender(GenderEntity gender);
    EmployeeEntity findEmployeeEntityByWorktype(WorktypeEntity worktypeEntity);
}

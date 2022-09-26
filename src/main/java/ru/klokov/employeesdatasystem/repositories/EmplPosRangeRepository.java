package ru.klokov.employeesdatasystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.klokov.employeesdatasystem.entities.EmployeePositionRangeEntity;

import java.util.Set;

public interface EmplPosRangeRepository extends JpaRepository<EmployeePositionRangeEntity, Long>, JpaSpecificationExecutor<EmployeePositionRangeEntity> {

    @Query(value = "SELECT epr.* FROM EMPLOYEE_POSITION_RANGE as epr " +
            "inner join" +
            "            (select employee_id, max(position_change_date) as max_pcd " +
            "             from EMPLOYEE_POSITION_RANGE " +
            "             group by employee_id) as pcdepr  " +
            "on epr.employee_id=pcdepr.employee_id " +
            "and epr.position_change_date=pcdepr.max_pcd " +
            "inner join " +
            "           (select id, second_name, dismissed from employees " +
            "            ) as e " +
            "on pcdepr.employee_id=e.id " +
            "where e.dismissed=false and epr.position_id=:id " +
            "order by epr.employee_id", nativeQuery = true)
    Set<EmployeePositionRangeEntity> findActualEmployeeWithPosition(Long id);
}

package ru.klokov.employeesdatasystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.klokov.employeesdatasystem.entities.RangeEntity;

public interface RangeRepository extends JpaRepository<RangeEntity, Long>, JpaSpecificationExecutor<RangeEntity> {
    RangeEntity findRangeEntityByName(String name);
}

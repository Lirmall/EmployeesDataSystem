package ru.klokov.employeesdatasystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.klokov.employeesdatasystem.entities.PositionEntity;

public interface PositionRepository extends JpaRepository<PositionEntity, Long>, JpaSpecificationExecutor<PositionEntity> {
    PositionEntity findPositionEntityByName(String name);
}

package ru.klokov.employeesdatasystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;

import java.util.Optional;

public interface WorktypeRepository extends JpaRepository<WorktypeEntity, Long>, JpaSpecificationExecutor<WorktypeEntity> {
    Optional<WorktypeEntity> findWorktypeEntityByName(String name);
}

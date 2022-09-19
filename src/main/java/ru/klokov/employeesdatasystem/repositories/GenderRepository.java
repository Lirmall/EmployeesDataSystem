package ru.klokov.employeesdatasystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.klokov.employeesdatasystem.entities.GenderEntity;

import java.util.Optional;

public interface GenderRepository extends JpaRepository<GenderEntity, Long>, JpaSpecificationExecutor<GenderEntity> {
    Optional<GenderEntity> findGenderEntityByName(String name);
}

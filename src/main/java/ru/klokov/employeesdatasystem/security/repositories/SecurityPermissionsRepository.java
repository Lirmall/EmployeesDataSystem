package ru.klokov.employeesdatasystem.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klokov.employeesdatasystem.security.SecurityPermission;

public interface SecurityPermissionsRepository extends JpaRepository<SecurityPermission, Long> {
}

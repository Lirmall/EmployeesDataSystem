package ru.klokov.employeesdatasystem.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klokov.employeesdatasystem.security.SecurityRole;

public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long> {
}

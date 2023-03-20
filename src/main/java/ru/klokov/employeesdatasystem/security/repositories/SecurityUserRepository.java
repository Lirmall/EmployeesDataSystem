package ru.klokov.employeesdatasystem.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klokov.employeesdatasystem.security.SecurityUser;

import java.util.Optional;

public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {
    SecurityUser findByUsername(String username);
    Optional<SecurityUser> findById(Long id);
}

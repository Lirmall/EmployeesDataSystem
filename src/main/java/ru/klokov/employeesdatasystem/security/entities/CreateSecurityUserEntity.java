package ru.klokov.employeesdatasystem.security.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.klokov.employeesdatasystem.security.SecurityRole;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CreateSecurityUserEntity {
    private String username;
    private String password;
    private Set<SecurityRole> roles;
}

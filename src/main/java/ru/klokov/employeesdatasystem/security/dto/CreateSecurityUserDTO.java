package ru.klokov.employeesdatasystem.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSecurityUserDTO {
    private String username;
    private String password;
    private Set<SecurityRoleDTO> roles;
}

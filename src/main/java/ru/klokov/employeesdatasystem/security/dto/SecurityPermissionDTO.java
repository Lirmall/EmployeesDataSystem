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
public class SecurityPermissionDTO {
    private Long id;
    private String name;
    private Set<SecurityRoleDTO> securityRoleSet;
}

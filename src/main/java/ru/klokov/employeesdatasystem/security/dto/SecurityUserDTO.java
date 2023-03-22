package ru.klokov.employeesdatasystem.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.klokov.employeesdatasystem.security.SecurityRole;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUserDTO {
    private Long id;
    private String username;
    private String password;
    private Boolean nonExpired;
    private Boolean nonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private Set<SecurityRole> roles;
}

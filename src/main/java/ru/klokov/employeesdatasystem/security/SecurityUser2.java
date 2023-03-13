package ru.klokov.employeesdatasystem.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

//@Entity
@NoArgsConstructor
public class SecurityUser2 implements UserDetails {

    @Id
    private Long id;

    private String username;

    private String password;

    private Set<SecurityRole> roles;

    private Set<EmployeeEntity> entities;

    @Override
    public Set<SecurityPermission> getAuthorities() {
        Set<SecurityPermission> permissions = new HashSet<>();

        for (SecurityRole role: getRoles()) {
            permissions.addAll(role.getAuthorities());
        }

        return permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Set<SecurityRole> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }
}


package ru.klokov.employeesdatasystem.security;

import org.springframework.security.core.GrantedAuthority;

public class SecurityPermission implements GrantedAuthority {

    String name;

    public SecurityPermission(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}

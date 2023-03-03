package ru.klokov.employeesdatasystem.security;

import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {

    public SecurityUser(String username, String password, SecurityRole role) {
        super(username, password, role.getAuthorities());
    }
}

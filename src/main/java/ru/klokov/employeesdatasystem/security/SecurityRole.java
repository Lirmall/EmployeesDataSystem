package ru.klokov.employeesdatasystem.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SecurityRole {
    private String name;
    private Collection<? extends GrantedAuthority> authorities;

    public SecurityRole() {
    }

    public SecurityRole(String name, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}

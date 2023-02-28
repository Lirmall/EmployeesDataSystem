package ru.klokov.employeesdatasystem.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class SecurityUserDetailsManager implements UserDetailsManager {
    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO реализовать логику вычитывания пользователя из БД
        //TODO реализовать логику шифрования паролей

        if(!username.equals("user")) {
            return null;
        }

        Set<SecurityPermission> permissions = new HashSet<>();
        permissions.add(new SecurityPermission("genders.read"));

        return User.withDefaultPasswordEncoder()
                .username("user")
                .password("123")
                .authorities(permissions)
                .build();
    }
}

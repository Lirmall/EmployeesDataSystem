package ru.klokov.employeesdatasystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class SecurityUserDetailsManager implements UserDetailsManager {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityUserDetailsManager(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //TODO реализовать логику вычитывания пользователя из БД
//        //TODO реализовать логику шифрования паролей
//
//        if(!username.equals("user")) {
//            return null;
//        }
//
//        Set<SecurityPermission> permissions = new HashSet<>();
//        permissions.add(new SecurityPermission(Permissions.GENDERS_READ));
//
//        SecurityRole role = new SecurityRole("USER", permissions);
//
//        String encodedPassword = bCryptPasswordEncoder.encode("123");
//
//        return new SecurityUser("user", encodedPassword, role);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO реализовать логику вычитывания пользователя из БД
        //TODO реализовать логику шифрования паролей

        if(!username.equals("user")) {
            return null;
        }

        Set<SecurityPermission> permissions = new HashSet<>();
        permissions.add(new SecurityPermission(Permissions.GENDERS_READ));
        permissions.add(new SecurityPermission(Permissions.WORKTYPES_READ));

        SecurityRole role = new SecurityRole("USER", permissions);

        Set<SecurityRole> securityRoles = new HashSet<>();
        securityRoles.add(role);

        SecurityUser2 user = new SecurityUser2();

        String encodedPassword = bCryptPasswordEncoder.encode("123");
        user.setUsername("user");
        user.setPassword(encodedPassword);
        user.setRoles(securityRoles);

        return user;
    }
}

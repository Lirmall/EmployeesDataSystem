package ru.klokov.employeesdatasystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.security.repositories.SecurityUserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsManager implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityUserRepository securityUserRepository;

    public void createUser(UserDetails user) {

    }

    public void updateUser(UserDetails user) {

    }

    public void deleteUser(String username) {

    }

    public void changePassword(String oldPassword, String newPassword) {

    }

    public boolean userExists(String username) {
        return false;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //TODO реализовать логику шифрования паролей
//
//        if(!username.equals("user")) {
//            return null;
//        }
//
//        Set<SecurityPermission> permissions = new HashSet<>();
//        permissions.add(new SecurityPermission(Permissions.GENDERS_READ));
//        permissions.add(new SecurityPermission(Permissions.WORKTYPES_READ));
//
//        SecurityRole role = new SecurityRole("USER", permissions);
//
//        Set<SecurityRole> securityRoles = new HashSet<>();
//        securityRoles.add(role);
//
//        SecurityUser user = new SecurityUser();
//
//        String encodedPassword = bCryptPasswordEncoder.encode("123");
//        user.setUsername("user");
//        user.setPassword(encodedPassword);
//        user.setRoles(securityRoles);
//
//        return user;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = securityUserRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User with name \"" + username + "\" not found");
        }

        return user;
    }
}

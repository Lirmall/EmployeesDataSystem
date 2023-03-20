package ru.klokov.employeesdatasystem.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.security.SecurityRole;
import ru.klokov.employeesdatasystem.security.SecurityUser;
import ru.klokov.employeesdatasystem.security.entities.CreateSecurityUserEntity;
import ru.klokov.employeesdatasystem.security.repositories.SecurityRoleRepository;
import ru.klokov.employeesdatasystem.security.repositories.SecurityUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsManager implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;
    private final SecurityUserRepository securityUserRepository;
    private final SecurityRoleRepository roleRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public boolean createUser(CreateSecurityUserEntity user) {
        //TODO Исправить ошибку сохранения в БД

        String encodedPassword = encoder.encode(user.getPassword());

        SecurityUser securityUser = new SecurityUser(user.getUsername(), encodedPassword, user.getRoles());

        SecurityUser savedUser = securityUserRepository.save(securityUser);

        return securityUserRepository.findById(savedUser.getId()).isPresent();
    }

    public void updateUser(SecurityUser user) {

    }

    public void deleteUserByUsername(String username) {

    }

    public boolean deleteUserByID(Long id) {
        Optional<SecurityUser> user = securityUserRepository.findById(id);
        if(user.isPresent()) {
            securityUserRepository.delete(user.get());
        } else {
            throw new NoMatchingEntryInDatabaseException("User wit id =  " + id + " not found in database");
        }
        return securityUserRepository.findById(id).isPresent();
    }

    public void changePassword(String oldPassword, String newPassword) {

    }

    public SecurityUser findById(Long id) {
        Optional<SecurityUser> foundUser = securityUserRepository.findById(id);

        if(foundUser.isPresent()) {
            return foundUser.get();
        } else {
            throw new NoMatchingEntryInDatabaseException("User wit id =  " + id + " not found in database");
        }
    }

    public List<SecurityUser> findAll() {
        return securityUserRepository.findAll();
    }

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = securityUserRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User with name \"" + username + "\" not found");
        }

        return user;
    }

}

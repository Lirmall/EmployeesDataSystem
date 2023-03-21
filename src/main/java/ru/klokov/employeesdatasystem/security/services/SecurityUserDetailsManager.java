package ru.klokov.employeesdatasystem.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.exceptions.AlreadyCreatedException;
import ru.klokov.employeesdatasystem.exceptions.CannotCreateUserException;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.OldPasswordNotMatchesException;
import ru.klokov.employeesdatasystem.security.SecurityUser;
import ru.klokov.employeesdatasystem.security.entities.CreateSecurityUserEntity;
import ru.klokov.employeesdatasystem.security.repositories.SecurityRoleRepository;
import ru.klokov.employeesdatasystem.security.repositories.SecurityUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsManager implements UserDetailsService {

    private final BCryptPasswordEncoder encoder;
    private final SecurityUserRepository securityUserRepository;
    private final SecurityRoleRepository roleRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public SecurityUser createUser(CreateSecurityUserEntity user) {
        SecurityUser userFromDB = securityUserRepository.findByUsername(user.getUsername());

        if(userFromDB != null) {
            throw new AlreadyCreatedException("User with name \"" + user.getUsername() + "\" already created");
        }

        String encodedPassword = encoder.encode(user.getPassword());

        SecurityUser securityUser = new SecurityUser(user.getUsername(), encodedPassword, user.getRoles());

        SecurityUser savedUser = securityUserRepository.save(securityUser);

        Optional<SecurityUser> result = securityUserRepository.findById(savedUser.getId());

        if(result.isPresent()) {
            return result.get();
        } else {
            throw new CannotCreateUserException("Cannot create user");
        }
    }

    public SecurityUser updateUser(SecurityUser user) {
        SecurityUser userFromDB = findById(user.getId());

        if(userFromDB == null) {
            throw new NoMatchingEntryInDatabaseException("User not found");
        }

        userFromDB.setPassword(encoder.encode(user.getPassword()));
        userFromDB.setNonExpired(user.getNonExpired());
        userFromDB.setNonLocked(user.getNonLocked());
        userFromDB.setCredentialsNonExpired(user.getCredentialsNonExpired());
        userFromDB.setEnabled(user.getEnabled());
        userFromDB.setRoles(user.getRoles());

        return securityUserRepository.save(userFromDB);
    }

    public void deleteUserByUsername(String username) {
        SecurityUser userToDelete = loadUserByUsername(username);
        deleteUserByID(userToDelete.getId());
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

    public void changePassword(String username, String oldPassword, String newPassword) {
        SecurityUser userFromDB = loadUserByUsername(username);

        if(!encoder.matches(oldPassword, userFromDB.getPassword())) {
            throw new OldPasswordNotMatchesException("Wrong old password");
        }

        userFromDB.setPassword(encoder.encode(newPassword));

        securityUserRepository.save(userFromDB);
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

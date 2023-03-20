package ru.klokov.employeesdatasystem.security.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.security.SecurityRole;
import ru.klokov.employeesdatasystem.security.SecurityUser;
import ru.klokov.employeesdatasystem.security.entities.CreateSecurityUserEntity;
import ru.klokov.employeesdatasystem.security.repositories.SecurityRoleRepository;
import ru.klokov.employeesdatasystem.security.services.SecurityUserDetailsManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class SecurityUserDetailsManagerTest {

    @Autowired
    private SecurityUserDetailsManager securityUserDetailsManager;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private SecurityRoleRepository roleRepository;

    @Test
    void createUser() {
        CreateSecurityUserEntity createSecurityUserEntity =
                new CreateSecurityUserEntity();

        Optional<SecurityRole> securityRole = roleRepository.findById(1L);
        assertTrue(securityRole.isPresent());

        Set<SecurityRole> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).get());

        createSecurityUserEntity.setUsername("testUser");
        createSecurityUserEntity.setPassword("123");
        createSecurityUserEntity.setRoles(roleSet);

        assertTrue(securityUserDetailsManager.createUser(createSecurityUserEntity));
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserByUsername() {
    }

    @Test
    void deleteUserByID() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void findById() {
        SecurityUser user = securityUserDetailsManager.findById(1L);

        assertTrue(encoder.matches("123", user.getPassword()));
        assertTrue(user.getNonExpired());
        assertTrue(user.getNonLocked());
        assertTrue(user.getCredentialsNonExpired());
        assertTrue(user.getEnabled());
    }

    @Test
    void findAll() {
    }

    @Test
    void loadUserByUsername() {
        SecurityUser user = securityUserDetailsManager.loadUserByUsername("user");

        assertTrue(encoder.matches("123", user.getPassword()));
        assertTrue(user.getNonExpired());
        assertTrue(user.getNonLocked());
        assertTrue(user.getCredentialsNonExpired());
        assertTrue(user.getEnabled());
    }
}
package ru.klokov.employeesdatasystem.security.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.security.SecurityRole;
import ru.klokov.employeesdatasystem.security.SecurityUser;
import ru.klokov.employeesdatasystem.security.entities.CreateSecurityUserEntity;
import ru.klokov.employeesdatasystem.security.repositories.SecurityRoleRepository;

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
    void createUserTest() {
        CreateSecurityUserEntity createSecurityUserEntity =
                new CreateSecurityUserEntity();

        Optional<SecurityRole> securityRole = roleRepository.findById(1L);
        assertTrue(securityRole.isPresent());

        Set<SecurityRole> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).get());

        createSecurityUserEntity.setUsername("testUser");
        createSecurityUserEntity.setPassword("123");
        createSecurityUserEntity.setRoles(roleSet);

        SecurityUser result = securityUserDetailsManager.createUser(createSecurityUserEntity);

        assertEquals("testUser", result.getUsername());
        assertTrue(encoder.matches("123", result.getPassword()));
        assertTrue(result.getNonExpired());
        assertTrue(result.getNonLocked());
        assertTrue(result.getCredentialsNonExpired());
        assertTrue(result.getEnabled());
    }

    @Test
    void updateUserTest() {
        CreateSecurityUserEntity createSecurityUserEntity =
                new CreateSecurityUserEntity();

        Optional<SecurityRole> securityRole = roleRepository.findById(1L);
        assertTrue(securityRole.isPresent());

        Set<SecurityRole> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).get());

        createSecurityUserEntity.setUsername("testUser");
        createSecurityUserEntity.setPassword("123");
        createSecurityUserEntity.setRoles(roleSet);

        SecurityUser result = securityUserDetailsManager.createUser(createSecurityUserEntity);

        SecurityUser userFromDB = securityUserDetailsManager.findById(result.getId());

        userFromDB.setPassword("1234");
        userFromDB.setEnabled(false);
        userFromDB.setCredentialsNonExpired(false);
        userFromDB.setNonLocked(false);

        SecurityUser updatedUser = securityUserDetailsManager.updateUser(userFromDB);

        assertTrue(encoder.matches("1234", updatedUser.getPassword()));
        assertFalse(updatedUser.getEnabled());
        assertFalse(updatedUser.getCredentialsNonExpired());
        assertFalse(updatedUser.getNonLocked());
    }

    @Test
    void deleteUserByUsernameTest() {
        CreateSecurityUserEntity createSecurityUserEntity =
                new CreateSecurityUserEntity();

        Optional<SecurityRole> securityRole = roleRepository.findById(1L);
        assertTrue(securityRole.isPresent());

        Set<SecurityRole> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).get());

        String username = "testUser";

        createSecurityUserEntity.setUsername(username);
        createSecurityUserEntity.setPassword("123");
        createSecurityUserEntity.setRoles(roleSet);

        SecurityUser result = securityUserDetailsManager.createUser(createSecurityUserEntity);

        assertNotNull(result);

        securityUserDetailsManager.deleteUserByUsername(username);

        assertThrows(UsernameNotFoundException.class, () -> securityUserDetailsManager.loadUserByUsername(username));
    }

    @Test
    void deleteUserByIDTest() {
        CreateSecurityUserEntity createSecurityUserEntity =
                new CreateSecurityUserEntity();

        Optional<SecurityRole> securityRole = roleRepository.findById(1L);
        assertTrue(securityRole.isPresent());

        Set<SecurityRole> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).get());

        createSecurityUserEntity.setUsername("testUser");
        createSecurityUserEntity.setPassword("123");
        createSecurityUserEntity.setRoles(roleSet);

        SecurityUser result = securityUserDetailsManager.createUser(createSecurityUserEntity);

        Long id = result.getId();

        securityUserDetailsManager.deleteUserByID(id);

        assertThrows(NoMatchingEntryInDatabaseException.class, () -> securityUserDetailsManager.findById(id));
    }

    @Test
    void changePasswordTest() {
        CreateSecurityUserEntity createSecurityUserEntity =
                new CreateSecurityUserEntity();

        Optional<SecurityRole> securityRole = roleRepository.findById(1L);
        assertTrue(securityRole.isPresent());

        Set<SecurityRole> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).get());

        createSecurityUserEntity.setUsername("testUser");
        createSecurityUserEntity.setPassword("123");
        createSecurityUserEntity.setRoles(roleSet);

        SecurityUser result = securityUserDetailsManager.createUser(createSecurityUserEntity);

        SecurityUser userFromDB = securityUserDetailsManager.findById(result.getId());

        securityUserDetailsManager.changePassword("testUser", "123", "1234");

        SecurityUser updatedUser = securityUserDetailsManager.findById(result.getId());

        assertTrue(encoder.matches("1234", updatedUser.getPassword()));
    }

    @Test
    void findByIdTest() {
        SecurityUser user = securityUserDetailsManager.findById(1L);

        assertTrue(encoder.matches("123", user.getPassword()));
        assertTrue(user.getNonExpired());
        assertTrue(user.getNonLocked());
        assertTrue(user.getCredentialsNonExpired());
        assertTrue(user.getEnabled());
    }

    @Test
    void findAllTest() {
        int allUsersCount = securityUserDetailsManager.findAll().size();

        CreateSecurityUserEntity createSecurityUserEntity =
                new CreateSecurityUserEntity();

        Optional<SecurityRole> securityRole = roleRepository.findById(1L);
        assertTrue(securityRole.isPresent());

        Set<SecurityRole> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).get());

        createSecurityUserEntity.setUsername("testUser");
        createSecurityUserEntity.setPassword("123");
        createSecurityUserEntity.setRoles(roleSet);

        securityUserDetailsManager.createUser(createSecurityUserEntity);

        assertEquals((allUsersCount + 1), securityUserDetailsManager.findAll().size());
    }

    @Test
    void loadUserByUsernameTest() {
        SecurityUser user = securityUserDetailsManager.loadUserByUsername("user");

        assertTrue(encoder.matches("123", user.getPassword()));
        assertTrue(user.getNonExpired());
        assertTrue(user.getNonLocked());
        assertTrue(user.getCredentialsNonExpired());
        assertTrue(user.getEnabled());
    }
}
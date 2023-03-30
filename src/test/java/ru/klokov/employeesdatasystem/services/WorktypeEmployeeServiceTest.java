package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, WorktypeRepository.class, WorktypeService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class WorktypeEmployeeServiceTest {

    @Autowired
    private WorktypeEmployeeService worktypeEmployeeService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void findByIdTest() {
        WorktypeEntity worktypeEntity = worktypeEmployeeService.findById(1L);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void findByNameTest() {
        WorktypeEntity worktypeEntity = worktypeEmployeeService.findByName("Salary");

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());
    }
}
package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, PositionRepository.class, WorktypeRepository.class, PositionWorktypeService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class PositionWorktypeServiceTest {

    @Autowired
    private PositionWorktypeService positionWorktypeService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void findWorktypeByNameTest() {
        String name = "Hourly";
        WorktypeEntity worktype = positionWorktypeService.findWorktypeByName(name);

        assertEquals(2L, worktype.getId());

        assertThrows(NullOrEmptyArgumentexception.class, () ->  positionWorktypeService.findWorktypeByName(""));
        assertThrows(NullOrEmptyArgumentexception.class, () ->  positionWorktypeService.findWorktypeByName(null));
        assertThrows(NoMatchingEntryInDatabaseException.class, () ->  positionWorktypeService.findWorktypeByName("Test"));
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void findWorktypeByIdTest() {
        WorktypeEntity worktypeEntity = positionWorktypeService.findWorktypeById(1L);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());

        assertThrows(NullOrEmptyArgumentexception.class, () -> positionWorktypeService.findWorktypeById(null));
        assertThrows(NoMatchingEntryInDatabaseException.class, () -> positionWorktypeService.findWorktypeById(25L));
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA,
            StaticSqlSchemaClasspathes.POSITIONS_DATA})
    void findPositionByNameTest() {
        PositionEntity position = positionWorktypeService.findPositionByName("Engineer");

        assertEquals(3L, position.getId());
        assertEquals(1L, position.getWorktype().getId());
        assertEquals("Salary", position.getWorktype().getName());
        assertEquals(20000.0, position.getSalary());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void worktypeCheckTest() {
        WorktypeDTO worktypeDTO = new WorktypeDTO(1L, "Salary");

        WorktypeEntity worktypeEntity = positionWorktypeService.worktypeCheck(worktypeDTO);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());
    }
}
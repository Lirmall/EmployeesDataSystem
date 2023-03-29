package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.WorktypeSearchModel;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, WorktypeRepository.class, WorktypeService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class WorktypeServiceTest {

    @Autowired
    private WorktypeService worktypeService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void findAllTest() {

        List<WorktypeEntity> entities = worktypeService.findAll();
        assertEquals(2, entities.size());

        WorktypeEntity worktypeEntity = entities.get(0);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());

        worktypeEntity = entities.get(1);

        assertEquals(2L, worktypeEntity.getId());
        assertEquals("Hourly", worktypeEntity.getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void findByIdTest() {
        WorktypeEntity worktypeEntity = worktypeService.findById(1L);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void findByFilterTest() {
        WorktypeSearchModel worktypeSearchModel = new WorktypeSearchModel();

        worktypeSearchModel.setIds(Arrays.asList(1L, 2L));
        worktypeSearchModel.setSortColumn("-id");

        Page<WorktypeEntity> entities = worktypeService.findByFilter(worktypeSearchModel);

        assertEquals(2, entities.getTotalElements());

        assertEquals(2L, entities.getContent().get(0).getId());

        worktypeSearchModel.setIds(Collections.emptyList());
        worktypeSearchModel.setNames(Collections.emptyList());

        entities = worktypeService.findByFilter(worktypeSearchModel);

        assertEquals(Page.empty(), entities);
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA,
            StaticSqlSchemaClasspathes.WORKTYPESS_DATA})
    void getCountOfTotalItemsTest() {
        assertEquals(2L, worktypeService.getCountOfTotalItems());
    }
}
package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.specifications.worktypesSpecification.WorktypeSearchModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class WorktypeServiceTest {

    @Autowired
    private WorktypeService worktypeService;

    @Test
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
    void findByIdTest() {
        WorktypeEntity worktypeEntity = worktypeService.findById(1L);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());
    }

    @Test
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
    void getCountOfTotalItemsTest() {
        assertEquals(2L, worktypeService.getCountOfTotalItems());
    }
}
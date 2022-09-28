package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class WorktypeEmployeeServiceTest {

    @Autowired
    private WorktypeEmployeeService worktypeEmployeeService;

    @Test
    void findByIdTest() {
        WorktypeEntity worktypeEntity = worktypeEmployeeService.findById(1L);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());
    }

    @Test
    void findByNameTest() {
        WorktypeEntity worktypeEntity = worktypeEmployeeService.findByName("Salary");

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());
    }
}
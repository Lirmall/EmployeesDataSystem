package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.entities.WorktypeEntity;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.exceptions.NullOrEmptyArgumentexception;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class PositionWorktypeServiceTest {

    @Autowired
    private PositionWorktypeService positionWorktypeService;

    @Test
    void findWorktypeByNameTest() {
        String name = "Hourly";
        WorktypeEntity worktype = positionWorktypeService.findWorktypeByName(name);

        assertEquals(2L, worktype.getId());

        assertThrows(NullOrEmptyArgumentexception.class, () ->  positionWorktypeService.findWorktypeByName(""));
        assertThrows(NullOrEmptyArgumentexception.class, () ->  positionWorktypeService.findWorktypeByName(null));
        assertThrows(NoMatchingEntryInDatabaseException.class, () ->  positionWorktypeService.findWorktypeByName("Test"));
    }

    @Test
    void findWorktypeByIdTest() {
        WorktypeEntity worktypeEntity = positionWorktypeService.findWorktypeById(1L);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());

        assertThrows(NullOrEmptyArgumentexception.class, () -> positionWorktypeService.findWorktypeById(null));
        assertThrows(NoMatchingEntryInDatabaseException.class, () -> positionWorktypeService.findWorktypeById(25L));
    }

    @Test
    void findPositionByNameTest() {
        PositionEntity position = positionWorktypeService.findPositionByName("Engineer");

        assertEquals(3L, position.getId());
        assertEquals(1L, position.getWorktype().getId());
        assertEquals("Salary", position.getWorktype().getName());
        assertEquals(20000.0, position.getSalary());
    }

    @Test
    void worktypeCheckTest() {
        WorktypeDTO worktypeDTO = new WorktypeDTO(1L, "Salary");

        WorktypeEntity worktypeEntity = positionWorktypeService.worktypeCheck(worktypeDTO);

        assertEquals(1L, worktypeEntity.getId());
        assertEquals("Salary", worktypeEntity.getName());
    }
}
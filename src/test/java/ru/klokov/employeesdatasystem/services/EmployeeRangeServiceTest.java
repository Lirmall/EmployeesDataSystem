package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.entities.RangeEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class EmployeeRangeServiceTest {

    @Autowired
    private EmployeeRangeService employeeRangeService;

    @Test
    void rangeCheckTest() {
        RangeEntity rangeToCheck = new RangeEntity();
        rangeToCheck.setName("3 range");
        rangeToCheck.setId(3L);

        assertDoesNotThrow(() -> employeeRangeService.rangeCheck(rangeToCheck));
    }
}
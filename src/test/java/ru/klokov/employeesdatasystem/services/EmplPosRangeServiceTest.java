package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.entities.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class EmplPosRangeServiceTest {

    @Autowired
    private EmplPosRangeService emplPosRangeService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RangeService rangeService;

    @Test
    void findActualEmployeeWithPosition() {
        Set<EmployeePositionRangeEntity> entitySet =  emplPosRangeService.findActualEmployeeWithPosition(1L);

        assertEquals(3, entitySet.size());
    }

    @Test
    void addEmployeePositionRangeEntity() {
        assertEquals(17, emplPosRangeService.getCountOfTotalItems());

        EmployeeEntity employee = employeeService.findById(2L);

        PositionEntity position = positionService.findPositionByName("Engineer");

        RangeEntity range = rangeService.findById(4L);

        emplPosRangeService.addEmployeePositionRangeEntity(employee, position, range);

        assertEquals(18, emplPosRangeService.getCountOfTotalItems());
    }
}
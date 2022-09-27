package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.entities.*;

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

    @Autowired
    private

    @Test
    void findActualEmployeeWithPosition() {
    }

    @Test
    void addEmployeePositionRangeEntity() {
        System.out.println("count1 = " + emplPosRangeService.getCountOfTotalItems());

        EmployeeEntity employee = employeeService.findById(2L);

        PositionEntity position = positionService.findPositionByName("Engineer");

        RangeEntity range = rangeService.findById(4L);

        emplPosRangeService.addEmployeePositionRangeEntity(employee, position, range);

        System.out.println("count2 = " + emplPosRangeService.getCountOfTotalItems());
    }
}
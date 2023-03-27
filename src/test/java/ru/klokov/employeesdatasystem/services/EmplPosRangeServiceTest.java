package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.entities.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:application-springBootTest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("springBootTest")
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

    @Test
    void findByEmployeeIdWherePositionChangeDateLessThan() {
        Long id = 8L;
        LocalDate date = LocalDate.of(2022, 8, 3);

        List<EmployeePositionRangeEntity> entityList = emplPosRangeService.findByEmployeeIdWherePositionChangeDateLessThan(id, date);

        for (EmployeePositionRangeEntity epr : entityList) {
            System.out.println(epr.getPositionChangeDate());
        }
    }

}
package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.klokov.employeesdatasystem.entities.CreateEmployeeEntity;
import ru.klokov.employeesdatasystem.entities.CreateEmployeeEntityTestData;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void createTest() {
    }

    @Test
    void create2Test() {

        CreateEmployeeEntity entity = CreateEmployeeEntityTestData.returnCreateEmployeeEntity();

        employeeService.create2(entity);

    }

    @Test
    void findAllTest() {
    }

    @Test
    void findByIdTest() {
    }

    @Test
    void findEmployeeBySecondNameTest() {
    }

    @Test
    void findEmployeeByFirstNameTest() {
    }

    @Test
    void findEmployeeByThirdNameTest() {
    }

    @Test
    void findEmployeeByGenderTest() {
    }

    @Test
    void findEmployeeEntityByWorktypeTest() {
    }

    @Test
    void findEmployeeEntityByBirthdayTest() {
    }

    @Test
    void findByFilterTest() {
    }

    @Test
    void getCountOfTotalItemsTest() {
    }
}
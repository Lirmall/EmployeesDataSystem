package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.dto.GenderDTO;
import ru.klokov.employeesdatasystem.dto.WorktypeDTO;
import ru.klokov.employeesdatasystem.entities.*;
import ru.klokov.employeesdatasystem.exceptions.NoMatchingEntryInDatabaseException;
import ru.klokov.employeesdatasystem.repositories.EmployeeRepository;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;
import ru.klokov.employeesdatasystem.repositories.RangeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.specifications.employeeSpecification.EmployeeSearchModel;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, EmployeeRepository.class,
        RangeRepository.class, PositionRepository.class,
        EmployeeRangeService.class, PositionEmployeeService.class, EmployeeGenderService.class,
        WorktypeEmployeeService.class, EmplPosRangeService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private RangeService rangeService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void createTest() {
        long count = employeeService.getCountOfTotalItems();

        CreateEmployeeEntity entity = CreateEmployeeEntityTestData.returnCreateEmployeeEntity();

        EmployeeEntity employee = employeeService.create(entity);

        assertEquals(count + 1L, employeeService.getCountOfTotalItems());

        assertEquals(10L, employee.getId());
        assertEquals("Testov", employee.getSecondName());
        assertEquals("Test", employee.getFirstName());
        assertEquals("Testovich", employee.getThirdName());
        assertEquals(LocalDate.of(2020, 5, 15), employee.getBirthday());
        assertEquals(20000.0, employee.getSalary());
        assertEquals(LocalDate.of(2022, 9, 28), employee.getWorkstartDate());
        assertEquals(false, employee.getDismissed());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findAllTest() {
        List<EmployeeEntity> entities = employeeService.findAll();

        assertEquals(9, entities.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findByIdTest() {
        EmployeeEntity employee = employeeService.findById(3L);

        assertEquals(3L, employee.getId());
        assertEquals("Sidorov", employee.getSecondName());
        assertEquals("Alexander", employee.getFirstName());
        assertEquals("Sidorovich", employee.getThirdName());
        assertEquals(1L, employee.getGenderId());
        assertEquals(LocalDate.of(1982, 3, 17), employee.getBirthday());
        assertEquals(2L, employee.getWorktypeId());
        assertEquals(130.0, employee.getSalary());
        assertEquals(LocalDate.of(2001, 4, 16), employee.getWorkstartDate());
        assertEquals(false, employee.getDismissed());

        assertThrows(NoMatchingEntryInDatabaseException.class, () -> employeeService.findById(999999999L));
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findEmployeeBySecondNameTest() {
        List<EmployeeEntity> entities = employeeService.findEmployeeBySecondName("Ivanov");

        assertEquals(1, entities.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findEmployeeByFirstNameTest() {
        List<EmployeeEntity> entities = employeeService.findEmployeeByFirstName("Alexander");

        assertEquals(2, entities.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findEmployeeByThirdNameTest() {
        List<EmployeeEntity> entities = employeeService.findEmployeeByThirdName("Ivanovich");

        assertEquals(2, entities.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findEmployeeByGenderTest() {
        GenderDTO genderDTO = new GenderDTO(1L, "Male");

        List<EmployeeEntity> entities = employeeService.findEmployeeByGender(genderDTO);

        assertEquals(7, entities.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findEmployeeEntityByWorktypeTest() {
        WorktypeDTO worktypeDTO = new WorktypeDTO(2L, "Hourly");

        List<EmployeeEntity> entities = employeeService.findEmployeeEntityByWorktype(worktypeDTO);

        assertEquals(3, entities.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findEmployeeEntityByBirthdayTest() {
        LocalDate birthdayDate = LocalDate.of(1980, 1, 15);

        List<EmployeeEntity> entities = employeeService.findEmployeeEntityByBirthday(birthdayDate);

        assertEquals(1, entities.size());

        EmployeeEntity employee = entities.get(0);

        assertEquals(1, employee.getId());
        assertEquals("Ivanov", employee.getSecondName());
        assertEquals("Ivan", employee.getFirstName());
        assertEquals("Ivanovich", employee.getThirdName());
        assertEquals(1, employee.getGenderId());
        assertEquals(LocalDate.of(1980, 1, 15), employee.getBirthday());
        assertEquals(2, employee.getWorktypeId());
        assertEquals(120.0, employee.getSalary());
        assertEquals(LocalDate.of(2000, 5, 14), employee.getWorkstartDate());
        assertEquals(false, employee.getDismissed());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findByFilterTest() {
        EmployeeSearchModel employeeSearchModel = new EmployeeSearchModel();
        employeeSearchModel.setIds(Arrays.asList(1L, 2L, 8L, 4L, 5L, 6L, 7L, 3L));
        employeeSearchModel.setSortColumn("-id");

        Page<EmployeeEntity> entities = employeeService.findByFilter(employeeSearchModel);

        EmployeeEntity employee = entities.getContent().get(0);

        assertEquals(8L, employee.getId());
        assertEquals("Sazhin", employee.getSecondName());
        assertEquals("Alexander", employee.getFirstName());
        assertEquals("Vladimirovich", employee.getThirdName());
        assertEquals(1L, employee.getGenderId());
        assertEquals(LocalDate.of(1965, 6, 25), employee.getBirthday());
        assertEquals(1L, employee.getWorktypeId());
        assertEquals(30000.0, employee.getSalary());
        assertEquals(LocalDate.of(1980, 6, 8), employee.getWorkstartDate());
        assertEquals(false, employee.getDismissed());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void getCountOfTotalItemsTest() {
        assertEquals(9, employeeService.getCountOfTotalItems());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void dismissEmployeeTest() {
        EmployeeEntity employee = employeeService.findById(1L);

        DismissEmployeeEntity dismissEmployeeEntity = new DismissEmployeeEntity();

        LocalDate dismissDate = LocalDate.of(2022, 8, 30);

        dismissEmployeeEntity.setSecondName(employee.getSecondName());
        dismissEmployeeEntity.setFirstName(employee.getFirstName());
        dismissEmployeeEntity.setThirdName(employee.getThirdName());
        dismissEmployeeEntity.setWorkstartDate(employee.getWorkstartDate());
        dismissEmployeeEntity.setBirthdayDate(employee.getBirthday());
        dismissEmployeeEntity.setDismissDate(dismissDate);

        employeeService.dismissEmployee(dismissEmployeeEntity);

        EmployeeEntity dismissedEmployee = employeeService.findById(1L);

        assertEquals(true, dismissedEmployee.getDismissed());
        assertEquals(dismissDate, dismissedEmployee.getDismissedDate());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void updateEmployeeTest() {
        EmployeeEntity employee = employeeService.findById(1L);

        assertEquals(2L, employee.getWorktypeId());

        assertEquals(120.0, employee.getSalary());

        PositionEntity position = positionService.findPositionByName("Engineer");

        RangeEntity range = rangeService.findById(1L);

        UpdateEmployeeEntity updateEmployee = new UpdateEmployeeEntity();

        LocalDate date = LocalDate.of(2022, 8, 30);

        updateEmployee.setSecondName(employee.getSecondName());
        updateEmployee.setFirstName(employee.getFirstName());
        updateEmployee.setThirdName(employee.getThirdName());
        updateEmployee.setBirthdayDate(employee.getBirthday());
        updateEmployee.setUpdateDate(date);
        updateEmployee.setPosition(position);
        updateEmployee.setRange(range);

        employeeService.updateEmployee(updateEmployee);

        EmployeeEntity updatedEmployee = employeeService.findById(1L);

        System.out.println("id - " + updatedEmployee.getId());
        System.out.println("salary - " + updatedEmployee.getSalary());
        System.out.println("worktype id - " + updatedEmployee.getWorktypeId());

        assertEquals(20000.0, updatedEmployee.getSalary());

        assertEquals(1L, updatedEmployee.getWorktypeId());
    }
}
package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.entities.*;
import ru.klokov.employeesdatasystem.repositories.EmplPosRangeRepository;
import ru.klokov.employeesdatasystem.repositories.PositionRepository;
import ru.klokov.employeesdatasystem.repositories.RangeRepository;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, EmplPosRangeRepository.class, RangeRepository.class,
        RangeService.class, PositionRepository.class, WorktypeRepository.class,
        EmplPosRangeRepository.class, PositionWorktypeService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
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
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findActualEmployeeWithPosition() {
        Set<EmployeePositionRangeEntity> entitySet = emplPosRangeService.findActualEmployeeWithPosition(1L);

        assertEquals(3, entitySet.size());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void addEmployeePositionRangeEntity() {
        assertEquals(17, emplPosRangeService.getCountOfTotalItems());

        EmployeeEntity employee = employeeService.findById(2L);

        PositionEntity position = positionService.findPositionByName("Engineer");

        RangeEntity range = rangeService.findById(4L);

        emplPosRangeService.addEmployeePositionRangeEntity(employee, position, range);

        assertEquals(18, emplPosRangeService.getCountOfTotalItems());
    }

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void findByEmployeeIdWherePositionChangeDateLessThan() {
        Long id = 8L;
        LocalDate date = LocalDate.of(2022, 8, 3);

        List<EmployeePositionRangeEntity> entityList = emplPosRangeService.findByEmployeeIdWherePositionChangeDateLessThan(id, date);

        for (EmployeePositionRangeEntity epr : entityList) {
            System.out.println(epr.getPositionChangeDate());
        }
    }
}
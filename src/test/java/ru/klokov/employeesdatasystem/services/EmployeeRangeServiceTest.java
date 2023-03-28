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
import ru.klokov.employeesdatasystem.entities.RangeEntity;
import ru.klokov.employeesdatasystem.repositories.RangeRepository;
import ru.klokov.employeesdatasystem.repositories.WorktypeRepository;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {SortColumnChecker.class, RangeRepository.class, EmployeeRangeService.class,
        SecurityConfig.class, DefaultPermissionEvaluator.class})
@ActiveProfiles("dataJpaTest")
class EmployeeRangeServiceTest {

    @Autowired
    private EmployeeRangeService employeeRangeService;

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA})
    void rangeCheckTest() {
        RangeEntity rangeToCheck = new RangeEntity();
        rangeToCheck.setName("3 range");
        rangeToCheck.setId(3L);

        assertDoesNotThrow(() -> employeeRangeService.rangeCheck(rangeToCheck));
    }
}
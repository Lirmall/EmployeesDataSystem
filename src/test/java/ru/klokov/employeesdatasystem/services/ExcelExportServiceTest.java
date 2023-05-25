package ru.klokov.employeesdatasystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.repositories.*;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {ExcelExportService.class})
@ActiveProfiles("dataJpaTest")
class ExcelExportServiceTest {

    @Autowired
    private ExcelExportService excelExportService;

    private static final String FILENAME = "excelFileName";

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void generateEmployeesToExcel() {

    }
}
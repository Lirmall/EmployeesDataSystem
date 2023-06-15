package ru.klokov.employeesdatasystem.services;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.klokov.employeesdatasystem.StaticSqlSchemaClasspathes;
import ru.klokov.employeesdatasystem.config.SecurityConfig;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.EmployeeEntityTestData;
import ru.klokov.employeesdatasystem.headers.EmployeeHeader;
import ru.klokov.employeesdatasystem.security.DefaultPermissionEvaluator;
import ru.klokov.employeesdatasystem.utils.SortColumnChecker;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@PropertySource("classpath:application-dataJpaTest.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackageClasses = {ExcelExportService.class, SecurityConfig.class, DefaultPermissionEvaluator.class,
EmplPosRangeService.class, RangeService.class, SortColumnChecker.class})
@ActiveProfiles("dataJpaTest")
@ExtendWith(MockitoExtension.class)
class ExcelExportServiceTest {

    @Autowired
    private ExcelExportService excelExportService;

    private static final String FILENAME = "src\\test\\java\\ru\\klokov\\employeesdatasystem\\testData\\excelFileName";

    @Test
    @Sql(scripts = {StaticSqlSchemaClasspathes.CLEAN_DB,
            StaticSqlSchemaClasspathes.GENDERS_SCHEMA, StaticSqlSchemaClasspathes.GENDERS_DATA,
            StaticSqlSchemaClasspathes.WORKTYPESS_SCHEMA, StaticSqlSchemaClasspathes.WORKTYPESS_DATA,
            StaticSqlSchemaClasspathes.RANGES_SCHEMA, StaticSqlSchemaClasspathes.RANGES_DATA,
            StaticSqlSchemaClasspathes.POSITIONS_SCHEMA, StaticSqlSchemaClasspathes.POSITIONS_DATA,
            StaticSqlSchemaClasspathes.EMPLOYEES_SCHEMA, StaticSqlSchemaClasspathes.EMPLOYEES_DATA,
            StaticSqlSchemaClasspathes.EMPL_POS_RANGE_SCHEMA, StaticSqlSchemaClasspathes.EMPL_POS_RANGE_DATA})
    void generateEmployeesToExcel1() throws IOException {
        List<EmployeeHeader> headers = Arrays.asList(EmployeeHeader.values());

        List<EmployeeEntity> entities = new ArrayList<>();
        entities.add(EmployeeEntityTestData.returnEmployeeEntity());
        entities.add(EmployeeEntityTestData.returnEmployeeEntity2());

        excelExportService.generateEmployeesToExcel(headers, entities, FILENAME);

        File file = new File(FILENAME + ".xlsx");

        assertTrue(file.exists());

        byte[] result;

        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            result = IOUtils.toByteArray(fileInputStream);
        }

        Workbook workbook;

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result)) {
            workbook = new XSSFWorkbook(byteArrayInputStream);
        }

        Sheet sheet = workbook.getSheetAt(0);

        System.out.println();

        for (int i = 0; i < 9; i++) {
            int row = 0;
            CellType cellType = getCellType(sheet, row, i);
            if(cellType == CellType.STRING) {
                System.out.println("assertEquals(\"" + getCellDataAsString(sheet, row, i) + "\", getCellDataAsString(sheet, rowNum, " + i + "));");
            } else if (cellType == CellType.NUMERIC) {
                System.out.println("assertEquals(" + getCellDataAsNumeric(sheet, row, i) + ", getCellDataAsNumeric(sheet, rowNum, " + i + "));");
            } else if (cellType == CellType.BOOLEAN) {
                System.out.println("assertEquals(" + getCellDataAsBoolean(sheet, row, i) + ", getCellDataAsBoolean(sheet, rowNum, " + i + "));");
            }else {
                System.out.println("assertEquals(\"" + getCellDataAsString(sheet, row, i) + "\", getCellDataAsString(sheet, rowNum, " + i + "));");
            }
        }

        System.out.println();

        for (int i = 0; i < 9; i++) {
            int row = 1;
            CellType cellType = getCellType(sheet, row, i);
            if(cellType == CellType.STRING) {
                System.out.println("assertEquals(\"" + getCellDataAsString(sheet, row, i) + "\", getCellDataAsString(sheet, rowNum, " + i + "));");
            } else if (cellType == CellType.NUMERIC) {
                System.out.println("assertEquals(" + getCellDataAsNumeric(sheet, row, i) + ", getCellDataAsNumeric(sheet, rowNum, " + i + "));");
            } else if (cellType == CellType.BOOLEAN) {
                System.out.println("assertEquals(" + getCellDataAsBoolean(sheet, row, i) + ", getCellDataAsBoolean(sheet, rowNum, " + i + "));");
            }else {
                System.out.println("assertEquals(\"" + getCellDataAsString(sheet, row, i) + "\", getCellDataAsString(sheet, rowNum, " + i + "));");
            }
        }

        System.out.println();

        for (int i = 0; i < 9; i++) {
            int row = 2;
            CellType cellType = getCellType(sheet, row, i);
            if(cellType == CellType.STRING) {
                System.out.println("assertEquals(\"" + getCellDataAsString(sheet, row, i) + "\", getCellDataAsString(sheet, rowNum, " + i + "));");
            } else if (cellType == CellType.NUMERIC) {
                System.out.println("assertEquals(" + getCellDataAsNumeric(sheet, row, i) + ", getCellDataAsNumeric(sheet, rowNum, " + i + "));");
            } else if (cellType == CellType.BOOLEAN) {
                System.out.println("assertEquals(" + getCellDataAsBoolean(sheet, row, i) + ", getCellDataAsBoolean(sheet, rowNum, " + i + "));");
            }else {
                System.out.println("assertEquals(\"" + getCellDataAsString(sheet, row, i) + "\", getCellDataAsString(sheet, rowNum, " + i + "));");
            }
        }

        System.out.println();

        int rowNum = 0;

        assertEquals("Фамилия", getCellDataAsString(sheet, rowNum, 0));
        assertEquals("Имя", getCellDataAsString(sheet, rowNum, 1));
        assertEquals("Отчество", getCellDataAsString(sheet, rowNum, 2));
        assertEquals("Пол", getCellDataAsString(sheet, rowNum, 3));
        assertEquals("Дата рождения", getCellDataAsString(sheet, rowNum, 4));
        assertEquals("Текущая зарплата", getCellDataAsString(sheet, rowNum, 5));
        assertEquals("Дата принятия на работу", getCellDataAsString(sheet, rowNum, 6));
        assertEquals("Сотрудник уволен", getCellDataAsString(sheet, rowNum, 7));
        assertEquals("Дата увольнения", getCellDataAsString(sheet, rowNum, 8));

        rowNum = 1;

        assertEquals("Testov", getCellDataAsString(sheet, rowNum, 0));
        assertEquals("Test", getCellDataAsString(sheet, rowNum, 1));
        assertEquals("Testovich", getCellDataAsString(sheet, rowNum, 2));
        assertEquals("Male", getCellDataAsString(sheet, rowNum, 3));
        assertEquals("2020-10-12", getCellDataAsString(sheet, rowNum, 4));
        assertEquals(1000.0, getCellDataAsNumeric(sheet, rowNum, 5));
        assertEquals("2020-10-12", getCellDataAsString(sheet, rowNum, 6));
        assertEquals(false, getCellDataAsBoolean(sheet, rowNum, 7));
        assertEquals("", getCellDataAsString(sheet, rowNum, 8));

        rowNum = 2;

        assertEquals("Testov2", getCellDataAsString(sheet, rowNum, 0));
        assertEquals("Test2", getCellDataAsString(sheet, rowNum, 1));
        assertEquals("Testovich2", getCellDataAsString(sheet, rowNum, 2));
        assertEquals("Male", getCellDataAsString(sheet, rowNum, 3));
        assertEquals("2020-11-14", getCellDataAsString(sheet, rowNum, 4));
        assertEquals(1002.0, getCellDataAsNumeric(sheet, rowNum, 5));
        assertEquals("2020-11-14", getCellDataAsString(sheet, rowNum, 6));
        assertEquals(false, getCellDataAsBoolean(sheet, rowNum, 7));
        assertEquals("", getCellDataAsString(sheet, rowNum, 8));

        if(file.exists()) {
            assertTrue(file.delete());
        }
    }

    private String getCellDataAsString(Sheet sheet, int rowNum, int cellNum) {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(cellNum);
        return cell.getStringCellValue();
    }

    private Double getCellDataAsNumeric(Sheet sheet, int rowNum, int cellNum) {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(cellNum);
        return cell.getNumericCellValue();
    }

    private Boolean getCellDataAsBoolean(Sheet sheet, int rowNum, int cellNum) {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(cellNum);
        return cell.getBooleanCellValue();
    }

    private CellType getCellType(Sheet sheet, int rowNum, int cellNum) {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(cellNum);
        return cell.getCellType();
    }






}
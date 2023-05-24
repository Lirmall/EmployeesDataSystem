package ru.klokov.employeesdatasystem.services;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelGeneratorService {
    private static final String EMPLOYEE_SHEET_NAME ="Employees";
    private static final String POSITION_SHEET_NAME ="Positions";
    private static final String SALARY_SHEET_NAME ="Salaries";

    public void generateEmployeesToExcel() {
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(EMPLOYEE_SHEET_NAME);
    }
 }

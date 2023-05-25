package ru.klokov.employeesdatasystem.services;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.headers.IHeader;

import java.util.List;

@Service
public class ExcelExportService {
    private static final String EMPLOYEE_SHEET_NAME ="Employees";
    private static final String POSITION_SHEET_NAME ="Positions";
    private static final String SALARY_SHEET_NAME ="Salaries";

    public void generateEmployeesToExcel(List<? extends IHeader> headers, String excelFileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(EMPLOYEE_SHEET_NAME);
        createHeaderRow(headers, sheet);

    }

    private void createHeaderRow(List<? extends IHeader> headers, Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            headerRow.createCell(i).setCellValue(headers.get(i).getName());
            sheet.setColumnWidth(i, headers.get(i).getWidth() * 256);
        }
    }
 }

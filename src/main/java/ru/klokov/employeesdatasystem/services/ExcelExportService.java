package ru.klokov.employeesdatasystem.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.entities.EmployeeEntity;
import ru.klokov.employeesdatasystem.entities.PositionEntity;
import ru.klokov.employeesdatasystem.headers.IHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {
    private static final String EMPLOYEE_SHEET_NAME ="Employees";
    private static final String POSITION_SHEET_NAME ="Positions";
    private static final String SALARY_SHEET_NAME ="Salaries";

    public void generateEmployeesToExcel(List<? extends IHeader> headers, List<EmployeeEntity> entities, String excelFileName) throws IOException {
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(EMPLOYEE_SHEET_NAME);
        createHeaderRow(headers, sheet);
        fillEmployees(sheet, entities);
        writeBytesToFile(workbook, excelFileName);
    }

    public void generatePositionsToExcel(List<? extends IHeader> headers, List<PositionEntity> entities, String excelFileName) throws IOException {
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(POSITION_SHEET_NAME);
        createHeaderRow(headers, sheet);
        fillPositions(sheet, entities);
        writeBytesToFile(workbook, excelFileName);
    }

    private void createHeaderRow(List<? extends IHeader> headers, Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            headerRow.createCell(i).setCellValue(headers.get(i).getName());
            sheet.setColumnWidth(i, headers.get(i).getWidth() * 256);
        }
    }

    private void fillEmployees(Sheet sheet, List<EmployeeEntity> entities){

        int rowNum = 0;
        for (EmployeeEntity e: entities) {
            Row row = sheet.createRow(++rowNum);

            Cell cell = row.createCell(0);
            cell.setCellValue(e.getSecondName());

            cell = row.createCell(1);
            cell.setCellValue(e.getFirstName());

            cell = row.createCell(2);
            cell.setCellValue(e.getThirdName());

            cell = row.createCell(3);
            cell.setCellValue(e.getGender().getName());

            cell = row.createCell(4);
            cell.setCellValue(String.valueOf(e.getBirthday()));

            cell = row.createCell(5);
            cell.setCellValue(e.getSalary());

            cell = row.createCell(6);
            cell.setCellValue(String.valueOf(e.getBirthday()));

            cell = row.createCell(7);
            cell.setCellValue(e.getDismissed());

            cell = row.createCell(8);
            if(!e.getDismissed()) {
                cell.setCellValue("");
            } else {
                cell.setCellValue(String.valueOf(e.getBirthday()));
            }
        }
    }

    private void fillPositions(Sheet sheet, List<PositionEntity> entities){

        int rowNum = 0;
        for (PositionEntity e: entities) {
            Row row = sheet.createRow(++rowNum);

            Cell cell = row.createCell(0);
            cell.setCellValue(e.getName());

            cell = row.createCell(1);
            cell.setCellValue(e.getWorktype().getName());

            cell = row.createCell(2);
            cell.setCellValue(e.getSalary());
        }
    }

    private void writeBytesToFile(Workbook workbook, String fileName) throws IOException {

        File file = new File(fileName + ".xlsx");

        try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }
 }

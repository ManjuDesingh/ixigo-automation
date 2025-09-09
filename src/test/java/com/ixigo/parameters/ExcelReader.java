package com.ixigo.parameters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ExcelReader - utility class to read data from Excel files
 */
public class ExcelReader {

    private String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    // Get cell data by sheet name, row number, and column number
    public String getCellData(String sheetName, int rowNum, int colNum) {
        String cellData = "";
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            cellData = cell.toString();
            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cellData;
    }

    // Get number of rows in a sheet
    public int getRowCount(String sheetName) {
        int rowCount = 0;
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            rowCount = sheet.getPhysicalNumberOfRows();
            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowCount;
    }
}

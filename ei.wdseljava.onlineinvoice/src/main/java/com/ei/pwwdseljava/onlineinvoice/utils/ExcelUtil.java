package com.ei.pwwdseljava.onlineinvoice.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtil {

    public static String readCellData(String filePath, String sheetName, int rowNum, int colNum) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            workbook.close();
            throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the file " + filePath);
        }
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            workbook.close();
            return ""; // Return empty string if row does not exist
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            workbook.close();
            return ""; // Return empty string if cell does not exist
        }
        String cellValue = cell.toString();
        workbook.close();
        return cellValue;
    }

    public static void writeData(String filePath, String sheetName, int rowNum, int colNum, String data) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.createCell(colNum);
        cell.setCellValue(data);
        FileOutputStream outFile = new FileOutputStream(new File(filePath));
        workbook.write(outFile);
        outFile.close();
    }
}
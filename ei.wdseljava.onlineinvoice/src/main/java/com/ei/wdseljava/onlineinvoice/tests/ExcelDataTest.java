package com.ei.wdseljava.onlineinvoice.tests;

import org.testng.annotations.Test;

import com.ei.wdseljava.onlineinvoice.utils.ExcelUtil;

import static org.testng.Assert.assertEquals;

public class ExcelDataTest {

    @Test
    public void testExcelData() {
        try {
            String filePath = "src/test/resources/test-data/excel-data.xlsx";
            String sheetName = "Sheet1";

            String data = ExcelUtil.readCellData(filePath, sheetName, 0, 0);
            assertEquals(data, "expected_value", "Excel data does not match expected value.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

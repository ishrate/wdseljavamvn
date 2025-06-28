package com.ei.pwwdseljava.onlineinvoice.tests;

import com.ei.pwwdseljava.onlineinvoice.utils.ExcelUtil;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

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
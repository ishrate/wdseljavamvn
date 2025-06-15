package com.ei.wdseljava.onlineinvoice.tests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.ei.wdseljava.onlineinvoice.objects.TestDataXML;
import com.ei.wdseljava.onlineinvoice.utils.XMLUtil;

public class XMLDataTest {

	@Test
    public void testXMLData() {
        try {
            // Parse XML and get the data
            String filePath = "src/test/resources/test-data/search-query.xml";
            TestDataXML testData = XMLUtil.parseXML(filePath);

            // Assert each field exists
            assertTrue(testData.getDatafield1() != null, "Datafield1 is missing!");
            assertTrue(testData.getDatafield2() != null, "Datafield2 is missing!");
            assertTrue(testData.getDatafield3() != null, "Datafield3 is missing!");
            assertTrue(testData.getDatafield4() != null, "Datafield4 is missing!");
            assertTrue(testData.getDatafield5() != null, "Datafield5 is missing!");
            assertTrue(testData.getDatafield6() != null, "Datafield6 is missing!");
            assertTrue(testData.getDatafield7() != null, "Datafield7 is missing!");
            assertTrue(testData.getDatafield8() != null, "Datafield8 is missing!");
            assertTrue(testData.getDatafield9() != null, "Datafield9 is missing!");
            assertTrue(testData.getDatafield10() != null, "Datafield10 is missing!");

            // Optionally, print each field
            System.out.println("Datafield1: " + testData.getDatafield1());
            System.out.println("Datafield2: " + testData.getDatafield2());
            System.out.println("Datafield3: " + testData.getDatafield3());
            System.out.println("Datafield4: " + testData.getDatafield4());
            // Add more fields as needed...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
   

    


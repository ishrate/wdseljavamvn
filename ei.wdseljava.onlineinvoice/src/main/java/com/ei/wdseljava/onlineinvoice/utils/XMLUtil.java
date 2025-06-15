package com.ei.wdseljava.onlineinvoice.utils;

import com.ei.wdseljava.onlineinvoice.objects.TestDataXML;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XMLUtil {

	public static TestDataXML parseXML(String filePath) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        TestDataXML testData = xmlMapper.readValue(new File(filePath), TestDataXML.class);

        // Dynamically set the datafields based on the number of datafields in the XML
        File xmlFile = new File(filePath);
        xmlMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        // Return populated TestDataXML object
        return testData;
    }
}

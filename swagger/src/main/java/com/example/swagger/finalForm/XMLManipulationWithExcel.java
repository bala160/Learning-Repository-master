package com.example.swagger.finalForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLManipulationWithExcel {
    public static void main(String[] args) {
        try {
            // Load base XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document baseXml = docBuilder.parse(new File("C:\\Users\\Balakrishnan\\Documents\\testxml\\initial.xml"));

            // Load fields XML
            Document fieldsXml = docBuilder.parse(new File("C:\\Users\\Balakrishnan\\Documents\\testxml\\demo.xml"));

            // Read Excel to get desired ControlType
            String desiredControlType = readControlTypeFromExcel("C:\\Users\\Balakrishnan\\Documents\\testxml\\input.xlsx");

            // Find corresponding control tag in fields XML
            NodeList controlNodes = fieldsXml.getElementsByTagName("Control");
            Element desiredControl = null;
            for (int i = 0; i < controlNodes.getLength(); i++) {
                Element control = (Element) controlNodes.item(i);
                String controlType = control.getAttribute("ControlType");
                System.out.println(controlType+" ------");
                if (controlType.equals(desiredControlType)) {
                    desiredControl = control;
                    break;
                }
            }

            if (desiredControl != null) {
                // Clone and import the desired control into the base XML
                Node importedControl = baseXml.importNode(desiredControl, true);

                // Find the headerframe tag in base XML
                Element headerFrame = (Element) baseXml.getElementsByTagName("HeaderFrame").item(0);

                // Append imported control to the headerframe tag
                headerFrame.appendChild(importedControl);

                // Write the updated base XML to a new file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(baseXml);
                StreamResult result = new StreamResult(new File("C:\\Users\\Balakrishnan\\Documents\\testxml\\output.xml"));
                transformer.transform(source, result);

                System.out.println("Control added successfully to base XML.");
            } else {
                System.out.println("Desired control type not found in fields XML.");
            }
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static String readControlTypeFromExcel(String excelFilePath) throws IOException {
        FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

        // Assuming ControlType is in the first column (column index 0) and in the first row (row index 0)
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(0);
        String controlType = cell.getStringCellValue();
        System.out.println(controlType);
        workbook.close();
        return controlType;
    }
}




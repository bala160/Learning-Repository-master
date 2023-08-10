package com.example.swagger.test;

import org.apache.poi.ss.usermodel.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XmlParserExample {
    public static void main(String[] args) {
        try {
            // Paths to input and output files
            String inputXmlPath = "C:\\Users\\Balakrishnan\\Documents\\test\\allFields.xml";
            String outputXmlPath = "C:\\Users\\Balakrishnan\\Documents\\test\\initial.xml";

            // Load the Excel file
            FileInputStream excelFile = new FileInputStream(new File("C:\\Users\\Balakrishnan\\Documents\\test\\inputexcel.xlsx"));
            Workbook workbook = WorkbookFactory.create(excelFile);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            // Read the searchControlType from the Excel file
            String searchControlType = null;
            String userProvidedControlId = null;
            String userProvidedControlLabel = null;
            String userProvidedDataType = null;
            String userProvidedGroupId = null;
            String userProvidedIdentifier = null;
            String userProvidedTitle = null;
            String userProvidedDataClassName = null;

            // Create a flag to keep track of DataClass creation
            boolean dataClassCreated = false;

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    searchControlType = row.getCell(0).getStringCellValue().trim();
                    System.out.println(searchControlType);
                    userProvidedControlId = row.getCell(1).getStringCellValue().trim();
                    userProvidedControlLabel = row.getCell(2).getStringCellValue().trim();

                    // Use numericCellValue() for numeric columns
                    userProvidedDataType = String.valueOf((int) row.getCell(3).getNumericCellValue());
                    userProvidedGroupId = String.valueOf((int) row.getCell(4).getNumericCellValue());

                    // Handle boolean cell for "Identifier"
                    Cell identifierCell = row.getCell(5);
                    if (identifierCell.getCellType() == CellType.BOOLEAN) {
                        userProvidedIdentifier = String.valueOf(identifierCell.getBooleanCellValue());
                    } else {
                        userProvidedIdentifier = identifierCell.getStringCellValue().trim();
                    }

                    userProvidedTitle = row.getCell(6).getStringCellValue().trim();
                    userProvidedDataClassName = row.getCell(7).getStringCellValue().trim(); // New cell for DataClass Name

                }
            }

            if (searchControlType == null) {
                System.out.println("No searchControlType found in the Excel file.");
                return;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(inputXmlPath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Control");

            // Load the existing content from the output XML file
            DocumentBuilderFactory newFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder newBuilder = newFactory.newDocumentBuilder();
            Document newDoc = newBuilder.parse(new File(outputXmlPath));
            newDoc.getDocumentElement().normalize();

            Element headerFrame = (Element) newDoc.getElementsByTagName("HeaderFrame").item(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    searchControlType = row.getCell(0).getStringCellValue().trim();
                    System.out.println(searchControlType);
                    userProvidedControlId = row.getCell(1).getStringCellValue().trim();
                    userProvidedControlLabel = row.getCell(2).getStringCellValue().trim();
                    // Use numericCellValue() for numeric columns
                    userProvidedDataType = String.valueOf((int) row.getCell(3).getNumericCellValue());
                    userProvidedGroupId = String.valueOf((int) row.getCell(4).getNumericCellValue());
                    // Handle boolean cell for "Identifier"
                    Cell identifierCell = row.getCell(5);
                    if (identifierCell.getCellType() == CellType.BOOLEAN) {
                        userProvidedIdentifier = String.valueOf(identifierCell.getBooleanCellValue());
                    } else {
                        userProvidedIdentifier = identifierCell.getStringCellValue().trim();
                    }
                    userProvidedTitle = row.getCell(6).getStringCellValue().trim();
                    userProvidedDataClassName = row.getCell(7).getStringCellValue().trim();

                    // Create a new Control element for each row of data
                    Element importedControl = newDoc.createElement("Control");
                    importedControl.setAttribute("ControlType", searchControlType);
                    importedControl.setAttribute("ControlId", userProvidedControlId);
                    importedControl.setAttribute("ControlLabel", userProvidedControlLabel);
                    importedControl.setAttribute("DataType", userProvidedDataType);
                    importedControl.setAttribute("GroupId", userProvidedGroupId);
                    importedControl.setAttribute("Identifier", userProvidedIdentifier);
                    importedControl.setAttribute("Title", userProvidedTitle);

                    // Create and append the DataClass element with the provided name
                    Element dataClassElement = newDoc.createElement("DataClass");
                    dataClassElement.setAttribute("Name", userProvidedDataClassName);

                    importedControl.appendChild(dataClassElement);
                    headerFrame.appendChild(importedControl);
                }
            }

            // Write the updated document back to the output file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(newDoc);
            FileWriter writer = new FileWriter(outputXmlPath);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);

            System.out.println("Control tag appended to the new XML file.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String elementToString(Element element) {
        try {
            javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "yes");
            java.io.StringWriter writer = new java.io.StringWriter();
            transformer.transform(new javax.xml.transform.dom.DOMSource(element), new javax.xml.transform.stream.StreamResult(writer));
            return writer.toString();
        } catch (javax.xml.transform.TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}




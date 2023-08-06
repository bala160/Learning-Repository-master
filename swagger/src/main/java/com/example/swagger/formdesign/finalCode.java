package com.example.swagger.formdesign;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class finalCode {
    public static void main(String[] args) {
        try {
            // Load the input from Excel file (ControlId and ControlType)
            Map<String, String> controlMap = readControlsFromExcel("C:\\Users\\Balakrishnan\\Documents\\input.xlsx");

            // Load the first XML file that contains the control details
            File xmlFile1 = new File("C:\\Users\\Balakrishnan\\Downloads\\allFields.xml");
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document xmlDoc1 = docBuilder.parse(xmlFile1);
            xmlDoc1.getDocumentElement().normalize();

            // Load the second XML file that will be updated
            File xmlFile2 = new File("C:\\Users\\Balakrishnan\\Downloads\\initial.xml");
            Document xmlDoc2 = docBuilder.parse(xmlFile2);
            xmlDoc2.getDocumentElement().normalize();

            // Search for controls with matching ControlType in the first XML file
            NodeList controlNodes1 = xmlDoc1.getElementsByTagName("Control");
            for (int i = 0; i < controlNodes1.getLength(); i++) {
                Node controlNode1 = controlNodes1.item(i);
                if (controlNode1.getNodeType() == Node.ELEMENT_NODE) {
                    Element controlElement1 = (Element) controlNode1;
                    String controlId = controlElement1.getAttribute("ControlId");
                    String controlType = controlElement1.getAttribute("ControlType");

                    if (controlMap.containsKey(controlType)) {
                        // Get the Control details from the first XML file
                        String controlDetails = controlMap.get(controlType);

                        // Append this control to the second XML document
                        Node importedNode = xmlDoc2.importNode(controlNode1, true);
                        Node eventsNode = getEventsNode(xmlDoc2);
                        eventsNode.appendChild(importedNode);

                        // Update the ControlId attribute in the newly appended control
                        Element appendedControlElement = (Element) eventsNode.getLastChild();
                        appendedControlElement.setAttribute("ControlId", controlId + "_appended");

                        // Update the ControlType attribute in the newly appended control
                        appendedControlElement.setAttribute("ControlType", controlDetails);
                    }
                }
            }

            // Write the updated XML document to a new XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDoc2);
            StreamResult result = new StreamResult(new File("C:\\Users\\Balakrishnan\\Documents\\Learning-Repository-master\\Learning-Repository-master\\swagger\\src\\main\\java\\com\\example\\swagger\\formdesign\\output.xml"));
            transformer.transform(source, result);

            System.out.println("XML updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> readControlsFromExcel(String filePath) throws Exception {
        Map<String, String> controlMap = new HashMap<>();

        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell cellControlId = row.getCell(0);
            Cell cellControlType = row.getCell(1);

            if (cellControlId != null && cellControlId.getCellType() == CellType.STRING &&
                    cellControlType != null && cellControlType.getCellType() == CellType.STRING) {

                String controlId = cellControlId.getStringCellValue().trim();
                String controlType = cellControlType.getStringCellValue().trim();
                controlMap.put(controlType, controlId);
            }
        }

        workbook.close();
        file.close();

        return controlMap;
    }

    private static Node getEventsNode(Document doc) {
        NodeList eventsNodes = doc.getElementsByTagName("HeaderFrame");
        if (eventsNodes.getLength() > 0) {
            return eventsNodes.item(0);
        }
        return null;
    }
}

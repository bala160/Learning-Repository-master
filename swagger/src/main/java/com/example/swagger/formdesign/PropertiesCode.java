package com.example.swagger.formdesign;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PropertiesCode {
    public static void main(String[] args) {
        try {
            // Load the input from Excel file (ControlType and ControlProperties in JSON format)
            Map<String, Map<String, String>> controlPropertiesMap = readControlPropertiesFromExcel("C:\\Users\\Balakrishnan\\Downloads\\inputfinal.xlsx");

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

                    if (controlPropertiesMap.containsKey(controlType)) {
                        // Get the Control properties in JSON format from the input Excel
                        Map<String,String> controlPropertiesJson = controlPropertiesMap.get(controlType);

                        System.out.println("Control Type: " + controlType);
                        System.out.println("Control Properties JSON: " + controlPropertiesJson);

                        // Convert JSON to a Map of properties
                        ObjectMapper mapper = new ObjectMapper();
                       // Map<String, String> controlProperties = mapper.readValue(controlPropertiesJson, Map.class);
                        Map<String, String> controlProperties = controlPropertiesJson;


                        // Apply the properties to the control in the first XML file
                        for (Map.Entry<String, String> entry : controlProperties.entrySet()) {
                            String propertyName = entry.getKey();
                            String propertyValue = entry.getValue();
                            controlElement1.setAttribute(propertyName, propertyValue);
                        }

                        // Check if the control has a <Style> tag
                        Element styleElement1 = getStyleElement(controlElement1);
                        if (styleElement1 != null) {
                            // Get the Style properties in JSON format from the input Excel
                            Map<String, String> stylePropertiesJson = controlPropertiesMap.get(controlType + "_style");

                            // Convert JSON to a Map of Style properties
                            //Map<String, String> styleProperties = mapper.readValue(stylePropertiesJson, Map.class);
                            //Map<String, String> styleProperties = mapper.readValue((JsonParser) stylePropertiesJson, new TypeReference<Map<String, String>>() {});
                            Map<String, String> styleProperties = stylePropertiesJson;

                            // Apply the Style properties to the control in the first XML file
                            for (Map.Entry<String, String> entry : styleProperties.entrySet()) {
                                String stylePropertyName = entry.getKey();
                                String stylePropertyValue = entry.getValue();
                                styleElement1.setAttribute(stylePropertyName, stylePropertyValue);
                            }
                        }

                        // Append this control to the second XML document
                        Node importedNode = xmlDoc2.importNode(controlNode1, true);
                        Node eventsNode = getEventsNode(xmlDoc2);
                        eventsNode.appendChild(importedNode);

                        // Update the ControlId attribute in the newly appended control
                        Element appendedControlElement = (Element) eventsNode.getLastChild();
                        appendedControlElement.setAttribute("ControlId", controlId + "_appended");
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

    private static Element getStyleElement(Element controlElement) {
        NodeList styleNodes = controlElement.getElementsByTagName("Style");
        if (styleNodes.getLength() > 0) {
            return (Element) styleNodes.item(0);
        }
        return null;
    }

    // ... (existing readControlPropertiesFromExcel and getEventsNode methods)



    private static Map<String, Map<String, String>> readControlPropertiesFromExcel(String filePath) throws Exception {
        Map<String, Map<String, String>> controlPropertiesMap = new HashMap<>();

        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell cellControlType = row.getCell(0);
            Cell cellControlProperties = row.getCell(1);

            if (cellControlType != null && cellControlType.getCellType() == CellType.STRING &&
                    cellControlProperties != null && cellControlProperties.getCellType() == CellType.STRING) {

                String controlType = cellControlType.getStringCellValue().trim();
                String controlPropertiesJson = cellControlProperties.getStringCellValue().trim();

                // Parse JSON string manually
                Map<String, String> controlProperties = parseJsonString(controlPropertiesJson);
                controlPropertiesMap.put(controlType, controlProperties);
            }
        }

        workbook.close();
        file.close();

        return controlPropertiesMap;
    }

    private static Map<String, String> parseJsonString(String jsonString) throws IOException {
        Map<String, String> controlProperties = new HashMap<>();
        jsonString = jsonString.trim();

        if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
            jsonString = jsonString.substring(1, jsonString.length() - 1);
            String[] keyValuePairs = jsonString.split(",");
            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replaceAll("\"", "");
                    String value = keyValue[1].trim().replaceAll("\"", "");
                    controlProperties.put(key, value);
                }
            }
        }

        return controlProperties;
    }

    // ... (existing getEventsNode method)
    private static Node getEventsNode(Document doc) {
        NodeList eventsNodes = doc.getElementsByTagName("HeaderFrame");
        if (eventsNodes.getLength() > 0) {
            return eventsNodes.item(0);
        }
        return null;
    }

}


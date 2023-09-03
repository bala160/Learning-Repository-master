package com.example.swagger.ibps;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class DoclistClass {
    public static Element handleDoclistControlElement(String controlId, String controlLabel, String
            dataType, String groupId, String identifier, String title) throws IOException {
        Element controlElement = base.emptyXmlDoc.createElement("Control");
        controlElement.setAttribute("ControlId", controlId);
        controlElement.setAttribute("ControlType", "CustomControl");
        controlElement.setAttribute("ControlLabel", controlLabel);
        controlElement.setAttribute("DataType", dataType);
        controlElement.setAttribute("GroupId", groupId);
        controlElement.setAttribute("Identifier", identifier);
        controlElement.setAttribute("Title", title);

// Create <Event> and <Events> elements
        Element eventElement = base.emptyXmlDoc.createElement("Event");
        Element eventsElement = base.emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);

// Append <Event> element to control
        controlElement.appendChild(eventElement);

// Create <Style> element
        Element styleElement = base.emptyXmlDoc.createElement("Style");
        controlElement.appendChild(styleElement);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        Sheet sheet = workbook.getSheet("doclist");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = base.createColumnIndexMap(sheet);
        //System.out.println(columnIndexMap+"--------------------------------------");
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String radioId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (radioId.equals(controlId)) {
                    controlIdExists = true;
                    break;
                }
            }
        }


        String[] styleAttributes = {
                "ControlIcon",
                "ControlName",
                "CustomControlType",
                "DocComment",
                "DocControlID",
                "DocIndex",
                "DocName",
                "DocSize",
                "DocType",
                "Latitude",
                "Longitude",
                "NoOfPageInDoc",
                "WaiverComments",
                "WaiverDoc"
        };

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "radio" sheet
            for (String attribute : styleAttributes) {
                styleElement.setAttribute(attribute, getDefaultAttributeValueFordoclist(attribute));
            }
        } else {
            // controlId exists in the "radio" sheet, apply user-defined or default style properties
            // ...
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    String cellValue = dataFormatter.formatCellValue(row.getCell(columnIndex)).trim();

                    // Check if the cellValue is empty or contains only whitespace characters
                    if (cellValue.isEmpty()) {
                        cellValue = getDefaultAttributeValueFordoclist(attribute);
                    }

                    styleElement.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueFordoclist(attribute));
                }
            }
// ...
        }

// Create <DocList> element
        Element docListElement = base.emptyXmlDoc.createElement("DocList");
        styleElement.appendChild(docListElement);

// Append <Style> element to control
        controlElement.appendChild(styleElement);

// Create <DataClass> element
        Element dataClassElement = base.emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", "");

// Append <DataClass> element to control
        controlElement.appendChild(dataClassElement);

        return controlElement;
    }

    private static String getDefaultAttributeValueFordoclist(String attributeName) {
        return switch (attributeName) {
            case "ControlIcon" -> "doclist.png";
            case "ControlName", "CustomControlType" -> "Doclist";
            default -> "";
        };
    }
}

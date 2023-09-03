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

public class TileClass {

    public static Element handleTileControlElement(String controlId, String controlLabel, String dataType, String
            groupId, String identifier, String title) throws IOException {
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

// Create <Style> element with static attributes
        Element styleElement = base.emptyXmlDoc.createElement("Style");
        controlElement.appendChild(styleElement);
        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        Sheet sheet = workbook.getSheet("tile");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = base.createColumnIndexMap(sheet);
        boolean ControlLabelExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String listboxId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (listboxId.equals(controlId)) {
                    ControlLabelExists = true;
                    break;
                }
            }
        }


        String[] styleAttributes = {
                "ControlName",
                "CustomControlType",
                "ControlIcon",
                "TileHeader",
                "TileButtonLabel",
                "TileAlignLabel",
                "VisibleOnMobile"
        };

        if (!ControlLabelExists) {
            // Set default style properties if controlId does not exist in the "label" sheet
            for (String attribute : styleAttributes) {
                styleElement.setAttribute(attribute, getDefaultAttributeValueFortile(attribute));
            }
        } else {
            // controlId exists in the "label" sheet, apply user-defined or default style properties
            // ...
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    String cellValue = dataFormatter.formatCellValue(row.getCell(columnIndex)).trim();

                    // Check if the cellValue is empty or contains only whitespace characters
                    if (cellValue.isEmpty()) {
                        cellValue = getDefaultAttributeValueFortile(attribute);
                    }

                    styleElement.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueFortile(attribute));
                }
            }
// ...
        }

// Create <DataClass> element
        Element dataClassElement = base.emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", "");

// Append <DataClass> element to control
        controlElement.appendChild(dataClassElement);

        return controlElement;
    }

    private static String getDefaultAttributeValueFortile(String attributeName) {
        return switch (attributeName) {
            case "ControlName" -> "Tile";
            case "CustomControlType" -> "Tile";
            case "ControlIcon" -> "tile.png";
            case "TileHeader" -> "";
            case "TilePoint" -> "";
            case "TileButtonLabel" -> "";
            case "TileAlignLabel" -> "left";
            case "VisibleOnMobile" -> "yes";
            default -> "";
        };
    }
}

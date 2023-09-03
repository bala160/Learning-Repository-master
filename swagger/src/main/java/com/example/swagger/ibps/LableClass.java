package com.example.swagger.ibps;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class LableClass {
    public static Element label(String controlId, String ControlType, String controlLabel, String dataType, String
            groupId, String identifier, String title, String saveValueType, String dataClassName) throws IOException {
        Element newControl = base.emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", ControlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        Element styleNode = base.emptyXmlDoc.createElement("Style"); // Create a Style element
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties

        Sheet sheet = workbook.getSheet("label");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = base.createColumnIndexMap(sheet);

        // Check if controlId exists in the "label" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String labelId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (labelId.equals(controlId)) {
                    controlIdExists = true;
                    break;
                }
            }
        }

        String[] styleAttributes = {
                "FontStyle",
                "FontWeight",
                "FontSize",
                "FontColor",
                "BackColor",
                "FontFamily",
                "Mandatory",
                "Visible",
                "ReadOnly",
                "Enable",
                "MergeSection",
                "Grouping",
                "TextAlignment",
                "LinkURL",
                "Link",
                "SubSection",
                "CustomId",
                "CombinedFontWeight"
        };

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "label" sheet
            for (String attribute : styleAttributes) {
                styleNode.setAttribute(attribute, getDefaultAttributeValueForlabel(attribute));
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
                        cellValue = getDefaultAttributeValueForlabel(attribute);
                    }

                    styleNode.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleNode.setAttribute(attribute, getDefaultAttributeValueForlabel(attribute));
                }
            }
// ...
        }

        Element dataClassElement = base.emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", dataClassName);
        newControl.appendChild(dataClassElement);
        return newControl;
    }


    private static String getDefaultAttributeValueForlabel(String attributeName) {
        return switch (attributeName) {
            case "Mandatory", "ReadOnly" -> "false";
            case "Visible", "Enable" -> "true";
            case "MergeSection", "Grouping", "Link" -> "1";
            case "SubSection" -> "N";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };
    }
}

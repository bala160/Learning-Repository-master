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

public class DatepickClass {
    public static Element datepick(String controlId, String ControlType, String controlLabel, String
            dataType, String groupId, String identifier, String title, String saveValueType, String dataClassName) throws IOException {
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

        Sheet sheet = workbook.getSheet("datepick");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = base.createColumnIndexMap(sheet);

        // Check if controlId exists in the "datepick" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String datepickId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (datepickId.equals(controlId)) {
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
                "ValidationMessage",
                "Visible",
                "ReadOnly",
                "Enable",
                "MinDateAsCurrent",
                "MaxDateAsCurrent",
                "BorderColor",
                "BorderWidth",
                "ControlStyle",
                "TextAlignment",
                "PickerType",
                "OpenPicker",
                "SelectPicker",
                "Grouping",
                "MergeSection",
                "MinDate",
                "MaxDate",
                "LabelFontSize",
                "LabelFontWeight",
                "LabelFontFamily",
                "LabelBackgoundColor",
                "LabelFontColor",
                "TimeZone",
                "ToolTip",
                "Summary",
                "LabelInputRatio",
                "LabelInputAlignment",
                "ReadOnlyStyle",
                "validateMandatoryDisable",
                "CustomId",
                "DefaultValue",
                "CombinedFontWeight",
                "DefaultHijriView"
        };

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "datepick" sheet
            for (String attribute : styleAttributes) {
                styleNode.setAttribute(attribute, getDefaultAttributeValueFordatepick(attribute));
            }
        } else {
            // controlId exists in the "datepick" sheet, apply user-defined or default style properties
            // ...
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    String cellValue = dataFormatter.formatCellValue(row.getCell(columnIndex)).trim();

                    // Check if the cellValue is empty or contains only whitespace characters
                    if (cellValue.isEmpty()) {
                        cellValue = getDefaultAttributeValueFordatepick(attribute);
                    }

                    styleNode.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleNode.setAttribute(attribute, getDefaultAttributeValueFordatepick(attribute));
                }
            }
// ...
        }

        Element eventElement = base.emptyXmlDoc.createElement("Event");
        Element eventsElement = base.emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);

        Element dataClassElement = base.emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", dataClassName);
        newControl.appendChild(dataClassElement);

        return newControl;
    }


    private static String getDefaultAttributeValueFordatepick(String attributeName) {

        return switch (attributeName) {
            case "Mandatory", "ReadOnly", "MinDateAsCurrent", "MaxDateAsCurrent", "TimeZone" -> "false";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "Visible", "Enable" -> "true";
            case "PickerType", "OpenPicker", "Grouping", "MergeSection", "DefaultHijriView" -> "1";
            case "LabelInputAlignment" -> "-1";
            case "ReadOnlyStyle", "validateMandatoryDisable" -> "N";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };

    }
}

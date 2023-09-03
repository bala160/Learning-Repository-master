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

public class TextBoxClass {


    public static Element textbox(String controlId, String ControlType, String controlLabel, String
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

        Element styleNode = base.emptyXmlDoc.createElement("Style");
        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        Sheet sheet = workbook.getSheet("textbox");

        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = base.createColumnIndexMap(sheet);
        // Check if controlId exists in the "textbox" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String textboxId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (textboxId.equals(controlId)) {
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
                "Grouping",
                "DisableMaxLength",
                "BorderColor",
                "BorderWidth",
                "ControlStyle",
                "MergeSection",
                "MaskingValue",
                "FormattedValue",
                "RangeMax",
                "RangeMin",
                "TypeOfValue",
                "Precision",
                "SaveEncrypted",
                "MaxLength",
                "Alphabets",
                "Spaces",
                "Numerals",
                "SpecialCharacters",
                "Pattern",
                "LabelFontSize",
                "LabelFontWeight",
                "LabelFontFamily",
                "LabelBackgoundColor",
                "LabelFontColor",
                "LabelInputRatio",
                "ToolTip",
                "Summary",
                "ReadOnlyStyle",
                "AllowSpecialChars",
                "validateMandatoryDisable",
                "CharacterSet",
                "AllowCopy",
                "AllowPaste",
                "LabelInputAlignment",
                "CustomId",
                "LabelAsLink",
                "TextAlignment",
                "MaxValue",
                "MinValue",
                "PlaceHolder",
                "CombinedFontWeight"
        };
        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "textbox" sheet

            for (String attribute : styleAttributes) {
                styleNode.setAttribute(attribute, getDefaultAttributeValueFortextbox(attribute));
            }
        } else {
            // controlId exists in the "textbox" sheet, apply user-defined or default style properties
            // ...
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    String cellValue = dataFormatter.formatCellValue(row.getCell(columnIndex)).trim();

                    // Check if the cellValue is empty or contains only whitespace characters
                    if (cellValue.isEmpty()) {
                        cellValue = getDefaultAttributeValueFortextbox(attribute);
                    }

                    styleNode.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleNode.setAttribute(attribute, getDefaultAttributeValueFortextbox(attribute));
                }
            }
// ...
        }

        newControl.appendChild(styleNode);

        Element eventElement = base.emptyXmlDoc.createElement("Event");
        Element eventsElement = base.emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);

        Element dataClassElement = base.emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", dataClassName);
        newControl.appendChild(dataClassElement);

        return newControl;
    }


    private static String getDefaultAttributeValueFortextbox(String attributeName) {
        return switch (attributeName) {
            case "Mandatory", "ReadOnly", "DisableMaxLength" -> "false";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "Visible", "Enable", "Alphabets", "Spaces", "Numerals" -> "true";
            case "Grouping", "MergeSection" -> "1";
            case "MaskingValue" -> "nom asking";
            case "FormattedValue", "SaveEncrypted", "ReadOnlyStyle", "validateMandatoryDisable", "LabelAsLink" -> "N";
            case "RangeMax", "RangeMin", "CharacterSet", "AllowCopy", "AllowPaste" -> "0";
            case "AllowSpecialChars" -> "Y";
            case "LabelInputAlignment" -> "-1";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };
    }

}

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
import java.util.List;
import java.util.Map;

public class TableClass {

    public static Element generateTableControlElement(String controlId, String controlLabel, String
            dataType, String groupId, String identifier, String title, String insertionOrderIdColumn, String
                                                              dataClassName, List<base.ColumnProperties> columns) throws IOException {
        Element tableControlElement = base.emptyXmlDoc.createElement("Control");
        tableControlElement.setAttribute("ControlId", controlId);
        tableControlElement.setAttribute("ControlType", "table");
        tableControlElement.setAttribute("ControlLabel", controlLabel);
        tableControlElement.setAttribute("DataType", dataType);
        tableControlElement.setAttribute("GroupId", groupId);
        tableControlElement.setAttribute("Identifier", identifier);
        tableControlElement.setAttribute("Title", title);
        tableControlElement.setAttribute("insertionOrderIdColumn", insertionOrderIdColumn);

        // Set other attributes for the table control...

        Element styleNode = base.emptyXmlDoc.createElement("Style"); // Create a Style element
        tableControlElement.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties

        Sheet sheet = workbook.getSheet("table");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = base.createColumnIndexMap(sheet);

        // Check if controlId exists in the "label" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String tableId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (tableId.equals(controlId)) {
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
                "HeaderFontFamily",
                "HeaderFontSize",
                "HeaderFontColor",
                "HeaderBackColor",
                "Visible",
                "Enable",
                "Height",
                "BorderColor",
                "BorderWidth",
                "MergeSection",
                "Grouping",
                "multiSelect",
                "Summary",
                "timeZone",
                "HideAdd",
                "HideDelete",
                "ShowCheckboxColumn",
                "isAdvancedListview",
                "ListBtnAlignment",
                "DuplicateRow",
                "Searching",
                "Sorting",
                "CustomId",
                "HidePrevNext",
                "CombinedFontWeight",
                "CombinedHeaderFontWeight"
        };

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "label" sheet
            for (String attribute : styleAttributes) {
                styleNode.setAttribute(attribute, getDefaultAttributeValueFortable(attribute));
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
                        cellValue = getDefaultAttributeValueFortable(attribute);
                    }

                    styleNode.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleNode.setAttribute(attribute, getDefaultAttributeValueFortable(attribute));
                }
            }
// ...
        }


        // Create and append <columns> tag before <event> tags
        Element columnsElement = base.emptyXmlDoc.createElement("columns");
        tableControlElement.appendChild(columnsElement);

        for (base.ColumnProperties column : columns) {
            Element columnElement = base.emptyXmlDoc.createElement("column");
            columnElement.setAttribute("columnName", column.columnName);
            columnElement.setAttribute("complexFieldName", column.complexFieldName);
            columnElement.setAttribute("complexFieldType", column.complexFieldType);

            // Set other attributes for the column...

            // Example attributes set with default values
            columnElement.setAttribute("HeaderFontSize", "4");
            columnElement.setAttribute("HeaderFontColor", "000000");
            columnElement.setAttribute("HeaderBackColor", "efefef");
            columnElement.setAttribute("widthInPx", "");
            columnElement.setAttribute("width", "10");
            columnElement.setAttribute("visibility", "True");
            columnElement.setAttribute("mappedControlField", "");
            columnElement.setAttribute("labelMethodStyle", "2");
            columnElement.setAttribute("labelCaption", "");
            columnElement.setAttribute("columnMappedField", "2");
            columnElement.setAttribute("TotalFontSize", "4");
            columnElement.setAttribute("TotalFontColor", "000");
            columnElement.setAttribute("TotalBackColor", "FFF");
            columnElement.setAttribute("TextAreaHeight", "");
            columnElement.setAttribute("TableImageBtnURL", "");
            columnElement.setAttribute("SpecialCharacters", "");
            columnElement.setAttribute("ShowTotal", "false");
            columnElement.setAttribute("Precision", "0");
            columnElement.setAttribute("PatternMasking", "nomasking");
            columnElement.setAttribute("Pattern", "");
            columnElement.setAttribute("MaxLength", "");
            columnElement.setAttribute("MasterQuery", "");
            columnElement.setAttribute("LabelAlignment", "-1");
            columnElement.setAttribute("IsIOID", "N");
            columnElement.setAttribute("ImageName", "");
            columnElement.setAttribute("EnableSorting", "true");
            columnElement.setAttribute("EnableSearching", "true");
            columnElement.setAttribute("DigitGrouping", "");
            columnElement.setAttribute("DateMasking", "1");
            columnElement.setAttribute("ColumnTooltip", "");
            columnElement.setAttribute("ColumnMethod", "");
            columnElement.setAttribute("ColumnMasking", "nomasking");
            columnElement.setAttribute("CharVisisbleOnOption", "");
            columnElement.setAttribute("CharLimit", "");
            columnElement.setAttribute("CellFontSize", "4");
            columnElement.setAttribute("CellFontColor", "000");
            columnElement.setAttribute("CellBackColor", "FFF");
            columnElement.setAttribute("AutoIncrement", "N");
            columnElement.setAttribute("AllowSpecialchars", "Y");
            columnElement.setAttribute("AllowSpaces", "Y");
            columnElement.setAttribute("AllowNumbers", "Y");
            columnElement.setAttribute("AllowNull", "true");
            columnElement.setAttribute("AllowDuplicate", "true");
            columnElement.setAttribute("AllowAlphabets", "Y");

            columnsElement.appendChild(columnElement);
        }

        // Create and append <Event> and <Events> tags
        Element eventElement = base.emptyXmlDoc.createElement("Event");
        Element eventsElement = base.emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        tableControlElement.appendChild(eventElement);

        // Set the DataClass element
        Element dataClassElement = base.emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", dataClassName);
        tableControlElement.appendChild(dataClassElement);

        return tableControlElement;
    }


    private static String getDefaultAttributeValueFortable(String attributeName) {
        return switch (attributeName) {
            case "HeaderBackColor" -> "ffffff";
            case "Visible", "Enable", "DuplicateRow", "Sorting", "Searching" -> "true";
            case "Height" -> "300";
            case "MergeSection", "Grouping", "multiSelect", "timeZone" -> "1";
            case "HideAdd", "HideDelete", "ShowCheckboxColumn", "isAdvancedListview" -> "false";
            case "ListBtnAlignment" -> "0";
            case "HidePrevNext" -> "N";
            case "CombinedFontWeight", "CombinedHeaderFontWeight" -> "Bold";
            default -> "";
        };
    }
}

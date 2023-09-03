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

public class ListBoxClass {
    public static Element handleListBoxControl(String controlId, String ControlType, String controlLabel, String
            dataType, String groupId, String identifier, String title, String saveValueType, String dbQuery, String caching, String dataClassName) throws
            IOException {
        System.out.println(controlId);
        Element newControl = base.emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", ControlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        if (!dbQuery.isEmpty() && !caching.isEmpty()) {
            NodeList fetchFromDBNodes = newControl.getElementsByTagName("FetchFromDB");
            if (fetchFromDBNodes.getLength() > 0) {
                Element fetchFromDBElement = (Element) fetchFromDBNodes.item(0);
                fetchFromDBElement.setAttribute("DBQuery", dbQuery);
                fetchFromDBElement.setAttribute("Caching", caching);
                fetchFromDBElement.setTextContent("true"); // Update the text content
            }
        } else if (dbQuery.isEmpty() && caching.isEmpty()) {
            // Remove existing FetchFromDB element if it exists
            NodeList fetchFromDBNodes = newControl.getElementsByTagName("FetchFromDB");
            if (fetchFromDBNodes.getLength() > 0) {
                Element fetchFromDBElement = (Element) fetchFromDBNodes.item(0);
                newControl.removeChild(fetchFromDBElement);
            }
            Element optionsElement = base.emptyXmlDoc.createElement("Options");
            newControl.appendChild(optionsElement);
            newControl.insertBefore(optionsElement, newControl.getElementsByTagName("Style").item(0));
        }

        Element styleNode = base.emptyXmlDoc.createElement("Style"); // Create a Style element
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties

        Sheet sheet = workbook.getSheet("listbox");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = base.createColumnIndexMap(sheet);

        // Check if controlId exists in the "label" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String listboxId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (listboxId.equals(controlId)) {
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
                "ValidationMessage",
                "SortingOrder",
                "ComboType",
                "CharVisibleOnOption",
                "ListSize",
                "BorderColor",
                "BorderWidth",
                "ControlStyle",
                "Grouping",
                "MergeSection",
                "LabelFontSize",
                "LabelFontWeight",
                "LabelFontFamily",
                "LabelBackgoundColor",
                "LabelFontColor",
                "ToolTip",
                "Summary",
                "LabelInputRatio",
                "LabelInputAlignment",
                "ReadOnlyStyle",
                "validateMandatoryDisable",
                "CustomId",
                "CombinedFontWeight"
        };

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "label" sheet
            for (String attribute : styleAttributes) {
                styleNode.setAttribute(attribute, getDefaultAttributeValueForlistbox(attribute));
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
                        cellValue = getDefaultAttributeValueForlistbox(attribute);
                    }

                    styleNode.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleNode.setAttribute(attribute, getDefaultAttributeValueForlistbox(attribute));
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


    private static String getDefaultAttributeValueForlistbox(String attributeName) {
        return switch (attributeName) {
            case "Mandatory", "ReadOnly" -> "false";
            case "Visible", "Enable" -> "true";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "SortingOrder" -> "0";
            case "ComboType" -> "listbox";
            case "Grouping", "MergeSection" -> "1";
            case "LabelInputAlignment" -> "-1";
            case "ReadOnlyStyle", "validateMandatoryDisable" -> "N";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };
    }
}

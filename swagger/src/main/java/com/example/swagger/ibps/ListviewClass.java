package com.example.swagger.ibps;

import com.example.swagger.formdesign.All_Combo_ListBox_Table;
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

public class ListviewClass {

    public static Element listview(String controlId, String controlType, String controlLabel, String
            dataType, String groupId, String identifier, String title, String insertionOrderIdColumn, String
                                           dataClassName, List<base.ColumnProperties> columns, String saveValueType, List<base.frameControlNames> controlNames, String dbQuery, String caching) throws
            IOException {

        Element tableControlElement = base.emptyXmlDoc.createElement("Control");
        tableControlElement.setAttribute("ControlId", controlId);
        tableControlElement.setAttribute("ControlType", controlType);
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

        Sheet sheet = workbook.getSheet("listview");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = base.createColumnIndexMap(sheet);

        // Check if controlId exists in the "label" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String listviewId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (listviewId.equals(controlId)) {
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
                styleNode.setAttribute(attribute, getDefaultAttributeValueForlistview(attribute));
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
                        cellValue = getDefaultAttributeValueForlistview(attribute);
                    }

                    styleNode.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleNode.setAttribute(attribute, getDefaultAttributeValueForlistview(attribute));
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

        // Create the ListViewFrameLayoutXML and Frame elements
        Element ListViewFrameLayoutXML = base.emptyXmlDoc.createElement("ListViewFrameLayoutXML");
        Element Frame = base.emptyXmlDoc.createElement("Frame");
        tableControlElement.appendChild(Frame);
        sheet = workbook.getSheet("frame");
        columnIndexMap = base.createColumnIndexMap(sheet);
        boolean frameIdExists = false;
        row = null;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String listboxId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (listboxId.equals(controlId)) {
                    frameIdExists = true;
                    break;
                }
            }
        }

        styleAttributes = new String[]{
                "FrameId",
                "SectionTheme",
                "ControlType",
                "Caption",
                "FontStyle",
                "FontWeight",
                "FontSize",
                "FontColor",
                "BackColor",
                "SectionBackColor",
                "FontFamily",
                "Enable",
                "DataOnDemand",
                "ColumnLayout",
                "BorderColor",
                "BorderWidth",
                "Grouping",
                "MergeSection",
                "ReadOnlyStyle",
                "Summary",
                "CustomId",
                "CombinedFontWeight",
                "FrameState",
                "FrameVisible",
                "GridLayout",
                "GridLayoutInputLabel",
                "GridLayoutBorderColor"
        };

        if (!frameIdExists) {
            // Set default style properties if controlId does not exist in the "label" sheet
            for (String attribute : styleAttributes) {
                Frame.setAttribute(attribute, getDefaultAttributeValueForframe(attribute));
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
                        cellValue = getDefaultAttributeValueForframe(attribute);
                    }

                    Frame.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    Frame.setAttribute(attribute, getDefaultAttributeValueForframe(attribute));
                }
            }
// ...
        }


        for (int i = 0; i < controlNames.size(); i++) {
            base.frameControlNames control = controlNames.get(i);
            String names = control.controlName;
            System.out.println(controlNames.size() + " Names-------------");
            if (names.equalsIgnoreCase("textarea")) {
                Frame.appendChild(TextAreaClass.textarea(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("combo")) {
                Frame.appendChild(ComboBoxClass.handleComboControl(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching, dataClassName));
            } else if (names.equalsIgnoreCase("textbox")) {
                Frame.appendChild(TextBoxClass.textbox(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("checkbox")) {
                Frame.appendChild(CheckBoxClass.checkbox(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("datepick")) {
                Frame.appendChild(DatepickClass.datepick(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            } else if (names.equalsIgnoreCase("label")) {
                Frame.appendChild(LableClass.label(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName));
            }  else if (names.equalsIgnoreCase("frameend")) {
                break;
            }
        }

        Element eventElement = base.emptyXmlDoc.createElement("Event");
        Element eventsElement = base.emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        Frame.appendChild(eventElement);

        // Append the Frame element to the ListViewFrameLayoutXML element
        ListViewFrameLayoutXML.appendChild(Frame);

        // Append the ListViewFrameLayoutXML to the tableControlElement
        tableControlElement.appendChild(ListViewFrameLayoutXML);

        Element eventElement1 = base.emptyXmlDoc.createElement("Event");
        Element eventsElement1 = base.emptyXmlDoc.createElement("Events");
        eventElement1.appendChild(eventsElement1);
        tableControlElement.appendChild(eventElement1);

        // Set the DataClass element
        Element dataClassElement = base.emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", dataClassName);
        tableControlElement.appendChild(dataClassElement);

        return tableControlElement;
    }


    private static String getDefaultAttributeValueForlistview(String attributeName) {
        return switch (attributeName) {
            case "HeaderBackColor" -> "ffffff";
            case "Visible", "Enable", "DuplicateRow", "Searching", "Sorting" -> "true";
            case "Height" -> "300";
            case "MergeSection", "multiSelect", "Grouping", "timeZone" -> "1";
            case "HideAdd", "HideDelete", "ShowCheckboxColumn", "isAdvancedListview" -> "false";
            case "ListBtnAlignment" -> "0";
            case "HidePrevNext" -> "N";
            case "CombinedFontWeight", "CombinedHeaderFontWeight" -> "Bold";
            default -> "";
        };
    }

    private static String getDefaultAttributeValueForframe(String attributeName) {
        return switch (attributeName) {
            case "FrameId" -> "columnFrameLayout";
            case "SectionTheme" -> "Select";
            case "ControlType" -> "frame";
            case "Caption" -> "ListView";
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "SectionBackColor" -> "";
            case "FontFamily" -> "";
            case "Enable" -> "true";
            case "DataOnDemand" -> "N";
            case "ColumnLayout" -> "3";
            case "BorderColor" -> "";
            case "BorderWidth" -> "1";
            case "Grouping" -> "1";
            case "MergeSection" -> "1";
            case "ReadOnlyStyle" -> "N";
            case "Summary" -> "";
            case "CustomId" -> "";
            case "CombinedFontWeight" -> "Regular";
            case "FrameState" -> "false";
            case "FrameVisible" -> "false";
            case "GridLayout" -> "false";
            case "GridLayoutInputLabel" -> "FFFFFF";
            case "GridLayoutBorderColor" -> "FFFFFF";
            default -> "";
        };
    }
}

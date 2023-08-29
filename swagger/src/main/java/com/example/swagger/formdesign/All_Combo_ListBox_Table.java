package com.example.swagger.formdesign;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class All_Combo_ListBox_Table {
    private static Document emptyXmlDoc;
    private static Document allFieldXmlDoc;

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            emptyXmlDoc = docBuilder.parse(new File("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Empty_form.xml"));
            allFieldXmlDoc = docBuilder.parse(new File("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\allFields.xml"));

            FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Element emptyHeaderFrame = (Element) emptyXmlDoc.getElementsByTagName("HeaderFrame").item(0);

            DataFormatter dataFormatter = new DataFormatter();
            boolean tabControlGenerated = false;
            boolean tableControlGenerated = false;
            boolean listviewControlGenerated = false;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    String controlId = dataFormatter.formatCellValue(row.getCell(0));
                    String controlTypeToSearch = dataFormatter.formatCellValue(row.getCell(1));
                    String controlLabel = dataFormatter.formatCellValue(row.getCell(2));
                    String dataType = dataFormatter.formatCellValue(row.getCell(3));
                    String groupId = dataFormatter.formatCellValue(row.getCell(5));
                    String identifier = dataFormatter.formatCellValue(row.getCell(6));
                    String title = dataFormatter.formatCellValue(row.getCell(4));
                    String saveValueType = dataFormatter.formatCellValue(row.getCell(7));
                    String dataClassName = dataFormatter.formatCellValue(row.getCell(8));
                    String dbQuery = dataFormatter.formatCellValue(row.getCell(9));
                    String caching = dataFormatter.formatCellValue(row.getCell(10));
                    String insertionOrderIdColumn = dataFormatter.formatCellValue(row.getCell(11));

                    if (controlTypeToSearch.equalsIgnoreCase("tab")) {
                        if (!tabControlGenerated) {
                            Element tabControl = createTabElement(controlId, controlLabel, controlTypeToSearch);
                            emptyHeaderFrame.appendChild(tabControl);
                            tabControlGenerated = true;
                        }
                    } else if (controlTypeToSearch.equalsIgnoreCase("sheet")) {
                        if (tabControlGenerated) {
                            // Create the <sheet> element without a closing </Control> tag
                            Element sheetControl = emptyXmlDoc.createElement("Control");
                            sheetControl.setAttribute("ControlId", controlId);
                            sheetControl.setAttribute("ControlType", controlTypeToSearch);
                            //sheetControl.setAttribute("ControlLabel", controlLabel);
                            sheetControl.setAttribute("Caption", controlLabel);
                            sheetControl.setAttribute("FontStyle", "");
                            sheetControl.setAttribute("FontWeight", "");
                            sheetControl.setAttribute("FontSize", "");
                            sheetControl.setAttribute("FontColor", "");
                            sheetControl.setAttribute("BackColor", "");
                            sheetControl.setAttribute("FontFamily", "");
                            sheetControl.setAttribute("ColumnLayout", "1");
                            sheetControl.setAttribute("Visible", "true");

                            emptyHeaderFrame.getLastChild().appendChild(sheetControl);
                        }
                    } else {
                        if (tabControlGenerated) {
                            Element controlElement = findControlElementByType(allFieldXmlDoc, controlTypeToSearch);
                            if (controlElement != null) {
                                Element newControl = (Element) emptyXmlDoc.importNode(controlElement, true);
                                newControl.setAttribute("ControlId", controlId);
                                newControl.setAttribute("ControlLabel", controlLabel);
                                newControl.setAttribute("DataType", dataType);
                                newControl.setAttribute("GroupId", groupId);
                                newControl.setAttribute("Identifier", identifier);
                                newControl.setAttribute("Title", title);
                                newControl.setAttribute("SaveValueType", saveValueType);

                                if (controlTypeToSearch.equalsIgnoreCase("table")) {
                                    if (!tableControlGenerated) {
                                        List<ColumnProperties> columns = new ArrayList<>();

                                        for (int j = i + 1; j <= sheet.getLastRowNum(); j++) {
                                            Row columnRow = sheet.getRow(j);

                                            if (columnRow == null) {
                                                continue;
                                            }

                                            String elementName = dataFormatter.formatCellValue(columnRow.getCell(12));

                                            if (elementName.equalsIgnoreCase("tableend")) {
                                                i = j; // Move to the next control
                                                break;
                                            }

                                            String columnName = dataFormatter.formatCellValue(columnRow.getCell(13));
                                            String complexFieldName = dataFormatter.formatCellValue(columnRow.getCell(14));
                                            String complexFieldType = dataFormatter.formatCellValue(columnRow.getCell(15));
                                            columns.add(new ColumnProperties(columnName, complexFieldName, complexFieldType));
                                        }

                                        Element tableControlElement = generateTableControlElement(controlId, controlLabel, dataType, groupId, identifier, title, insertionOrderIdColumn, dataClassName, columns);
                                        emptyHeaderFrame.getLastChild().getLastChild().appendChild(tableControlElement);
                                        tableControlGenerated = true;
                                    }
                                    continue;
                                } else if (controlTypeToSearch.equalsIgnoreCase("combo")) {
                                    newControl = handleComboControl(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching);
                                } else if (controlTypeToSearch.equalsIgnoreCase("listbox")) {
                                    newControl = handleListBoxControl(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching);
                                } else if (controlTypeToSearch.equalsIgnoreCase("textarea")) {
                                    newControl = textarea(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType,dataClassName);

                                } else if (controlTypeToSearch.equalsIgnoreCase("textbox")) {
                                    newControl = textbox(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType);

                                } else if (controlTypeToSearch.equalsIgnoreCase("checkbox")) {
                                    newControl = checkbox(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType);

                                } else if (controlTypeToSearch.equalsIgnoreCase("datepick")) {
                                    newControl = datepick(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType);

                                } else if (controlTypeToSearch.equalsIgnoreCase("label")) {
                                    newControl = label(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType);

                                } else if (controlTypeToSearch.equalsIgnoreCase("radio")) {
                                    newControl = radio(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType);

                                } else if (controlTypeToSearch.equalsIgnoreCase("ListView")) {
                                    if (!listviewControlGenerated) {
                                        List<ColumnProperties> columns = new ArrayList<>();
                                        List<frameControlNames> frameControl = new ArrayList<>();

                                        for (int j = i + 1; j <= sheet.getLastRowNum(); j++) {
                                            Row columnRow = sheet.getRow(j);

                                            if (columnRow == null) {
                                                continue;
                                            }

                                            String elementName = dataFormatter.formatCellValue(columnRow.getCell(12));

                                            if (elementName.equalsIgnoreCase("columnend")) {
                                                i = j; // Move to the next control
                                                break;
                                            }

                                            String columnName = dataFormatter.formatCellValue(columnRow.getCell(13));
                                            String complexFieldName = dataFormatter.formatCellValue(columnRow.getCell(14));
                                            String complexFieldType = dataFormatter.formatCellValue(columnRow.getCell(15));
                                            columns.add(new ColumnProperties(columnName, complexFieldName, complexFieldType));
                                        }

                                        for (int j = i + 1; j <= sheet.getLastRowNum(); j++) {
                                            Row columnRow = sheet.getRow(j);

                                            if (columnRow == null) {
                                                continue;
                                            }

                                            String controlName = dataFormatter.formatCellValue(columnRow.getCell(16));

                                            if (controlName.equalsIgnoreCase("frameend")) {
                                                i = j; // Move to the next control
                                                break;
                                            }
                                            frameControl.add(new frameControlNames(controlName));
                                        }

                                        Element tableControlElement = listview(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, insertionOrderIdColumn, dataClassName, columns, saveValueType,frameControl);
                                        emptyHeaderFrame.getLastChild().getLastChild().appendChild(tableControlElement);
                                        tableControlGenerated = true;
                                    }
                                    continue;
                                }

                                NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
                                if (dataClassNodes.getLength() > 0) {
                                    Element dataClassElement = (Element) dataClassNodes.item(0);
                                    newControl.removeChild(dataClassElement);
                                }

                                Element dataClassElement = emptyXmlDoc.createElement("DataClass");
                                dataClassElement.setAttribute("Name", dataClassName);
                                newControl.appendChild(dataClassElement);

                                emptyHeaderFrame.getLastChild().getLastChild().appendChild(newControl);
                            }
                        }
                    }
                }
            }

            workbook.close();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource source = new DOMSource(emptyXmlDoc);
            StreamResult result = new StreamResult(new File("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\cat.xml"));
            transformer.transform(source, result);
            System.out.println("Merged XML generated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element createTabElement(String controlId, String controlLabel, String controlTypeToSearch) {
        Element tabControlElement = emptyXmlDoc.createElement("Control");
        tabControlElement.setAttribute("ControlId", controlId);
        tabControlElement.setAttribute("ControlType", controlTypeToSearch);
        tabControlElement.setAttribute("Caption", controlLabel); // You can set the Caption attribute to the control label
        tabControlElement.setAttribute("FontStyle", "");
        tabControlElement.setAttribute("FontWeight", "");
        tabControlElement.setAttribute("FontSize", "");
        tabControlElement.setAttribute("FontColor", "");
        tabControlElement.setAttribute("BackColor", "");
        tabControlElement.setAttribute("FontFamily", "");
        tabControlElement.setAttribute("DataOnDemand", "N");
        tabControlElement.setAttribute("FixTabHeader", "N");
        tabControlElement.setAttribute("CustomId", "");
        tabControlElement.setAttribute("Visible", "true");
        tabControlElement.setAttribute("iFontColor", "");
        tabControlElement.setAttribute("ControlStyle", "");
        tabControlElement.setAttribute("Grouping", "1");
        tabControlElement.setAttribute("MergeSection", "1");
        tabControlElement.setAttribute("Enable", "true");
        tabControlElement.setAttribute("ReadOnly", "false");
        tabControlElement.setAttribute("ReadOnlyStyle", "N");
        tabControlElement.setAttribute("Summary", "");
        tabControlElement.setAttribute("ShowSaveButtons", "N");
        tabControlElement.setAttribute("CombinedFontWeight", "Bold");
        return tabControlElement;
    }

    private static Element generateTableControlElement(String controlId, String controlLabel, String dataType, String groupId, String identifier, String title, String insertionOrderIdColumn, String dataClassName, List<ColumnProperties> columns) throws IOException {
        Element tableControlElement = emptyXmlDoc.createElement("Control");
        tableControlElement.setAttribute("ControlId", controlId);
        tableControlElement.setAttribute("ControlType", "table");
        tableControlElement.setAttribute("ControlLabel", controlLabel);
        tableControlElement.setAttribute("DataType", dataType);
        tableControlElement.setAttribute("GroupId", groupId);
        tableControlElement.setAttribute("Identifier", identifier);
        tableControlElement.setAttribute("Title", title);
        tableControlElement.setAttribute("insertionOrderIdColumn", insertionOrderIdColumn);

        // Set other attributes for the table control...

        Node styleNode = emptyXmlDoc.createElement("Style"); // Create a Style element
        tableControlElement.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("table");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

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
                styleElement.setAttribute(attribute, getDefaultAttributeValueFortable(attribute));
            }
        } else {
            // controlId exists in the "label" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueFortable(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueFortable(attribute));
                }
            }
        }


        // Create and append <columns> tag before <event> tags
        Element columnsElement = emptyXmlDoc.createElement("columns");
        tableControlElement.appendChild(columnsElement);

        for (ColumnProperties column : columns) {
            Element columnElement = emptyXmlDoc.createElement("column");
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
        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        tableControlElement.appendChild(eventElement);

        // Set the DataClass element
        Element dataClassElement = emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", dataClassName);
        tableControlElement.appendChild(dataClassElement);

        return tableControlElement;
    }


    private static String getDefaultAttributeValueFortable(String attributeName) {
        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "HeaderFontFamily" -> "";
            case "HeaderFontSize" -> "";
            case "HeaderFontColor" -> "";
            case "HeaderBackColor" -> "ffffff";
            case "Visible" -> "true";
            case "Enable" -> "true";
            case "Height" -> "300";
            case "BorderColor" -> "";
            case "BorderWidth" -> "";
            case "MergeSection" -> "1";
            case "Grouping" -> "1";
            case "multiSelect" -> "1";
            case "Summary" -> "";
            case "timeZone" -> "1";
            case "HideAdd" -> "false";
            case "HideDelete" -> "false";
            case "ShowCheckboxColumn" -> "false";
            case "isAdvancedListview" -> "false";
            case "ListBtnAlignment" -> "0";
            case "DuplicateRow" -> "true";
            case "Searching" -> "true";
            case "Sorting" -> "true";
            case "CustomId" -> "";
            case "HidePrevNext" -> "N";
            case "CombinedFontWeight" -> "Bold";
            case "CombinedHeaderFontWeight" -> "Bold";
            default -> "";
        };
    }

    private static Element listview(String controlId, String controlType, String controlLabel, String dataType, String groupId, String identifier, String title, String insertionOrderIdColumn, String dataClassName, List<ColumnProperties> columns, String saveValueType,List<frameControlNames> controlNames) throws IOException {

        List<frameControlNames> controlTypes = controlNames;

        Element tableControlElement = emptyXmlDoc.createElement("Control");
        tableControlElement.setAttribute("ControlId", controlId);
        tableControlElement.setAttribute("ControlType", controlType);
        tableControlElement.setAttribute("ControlLabel", controlLabel);
        tableControlElement.setAttribute("DataType", dataType);
        tableControlElement.setAttribute("GroupId", groupId);
        tableControlElement.setAttribute("Identifier", identifier);
        tableControlElement.setAttribute("Title", title);
        tableControlElement.setAttribute("insertionOrderIdColumn", insertionOrderIdColumn);

        // Set other attributes for the table control...

        Node styleNode = emptyXmlDoc.createElement("Style"); // Create a Style element
        tableControlElement.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("table");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

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
                styleElement.setAttribute(attribute, getDefaultAttributeValueForlistview(attribute));
            }
        } else {
            // controlId exists in the "label" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueForlistview(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueForlistview(attribute));
                }
            }
        }


        // Create and append <columns> tag before <event> tags
        Element columnsElement = emptyXmlDoc.createElement("columns");
        tableControlElement.appendChild(columnsElement);

        for (ColumnProperties column : columns) {
            Element columnElement = emptyXmlDoc.createElement("column");
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
        Element ListViewFrameLayoutXML = emptyXmlDoc.createElement("ListViewFrameLayoutXML");
        Element Frame = emptyXmlDoc.createElement("Frame");
        Frame.setAttribute("FrameId", "columnFrameLayout");
        Frame.setAttribute("SectionTheme", "Select");
        Frame.setAttribute("ControlType", "frame");
        Frame.setAttribute("Caption", "ListView");
        Frame.setAttribute("FontStyle", "");
        Frame.setAttribute("FontWeight", "");
        Frame.setAttribute("FontSize", "");
        Frame.setAttribute("FontColor", "");
        Frame.setAttribute("BackColor", "");
        Frame.setAttribute("SectionBackColor", "");
        Frame.setAttribute("FontFamily", "");
        Frame.setAttribute("Enable", "true");
        Frame.setAttribute("DataOnDemand", "N");
        Frame.setAttribute("ColumnLayout", "3");
        Frame.setAttribute("BorderColor", "");
        Frame.setAttribute("BorderWidth", "1");
        Frame.setAttribute("Grouping", "1");
        Frame.setAttribute("MergeSection", "1");
        Frame.setAttribute("ReadOnlyStyle", "N");
        Frame.setAttribute("Summary", "");
        Frame.setAttribute("CustomId", "");
        Frame.setAttribute("CombinedFontWeight", "Regular");
        Frame.setAttribute("FrameState", "false");
        Frame.setAttribute("FrameVisible", "false");
        Frame.setAttribute("GridLayout", "false");
        Frame.setAttribute("GridLayoutInputLabel", "FFFFFF");
        Frame.setAttribute("GridLayoutBorderColor", "FFFFFF");


        for (int i = 0; i < controlTypes.size(); i++) {
            frameControlNames control = controlTypes.get(i);
            String names = control.controlName; // Assuming "getName" is the method to get the name property
            System.out.println("names--------------------" + names);
            if (names.equalsIgnoreCase("textarea")) {
                // Create and append a textarea control
                System.out.println("Adding textarea control to Frame");
                Frame.appendChild(textarea(controlId, names, controlLabel, dataType, groupId, identifier, title, saveValueType,dataClassName));
            } else if (names.equalsIgnoreCase("frameend")) {
                System.out.println("Encountered 'frameend', breaking out of the loop");
                // If you encounter "frameend," you can break out of the loop
                break;
            }
        }
        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        Frame.appendChild(eventElement);

        // Append the Frame element to the ListViewFrameLayoutXML element
        ListViewFrameLayoutXML.appendChild(Frame);

        // Append the ListViewFrameLayoutXML to the tableControlElement
        tableControlElement.appendChild(ListViewFrameLayoutXML);

        Element eventElement1 = emptyXmlDoc.createElement("Event");
        Element eventsElement1 = emptyXmlDoc.createElement("Events");
        eventElement1.appendChild(eventsElement1);
        tableControlElement.appendChild(eventElement1);

        // Set the DataClass element
        Element dataClassElement = emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", dataClassName);
        tableControlElement.appendChild(dataClassElement);

        return tableControlElement;
    }


    private static String getDefaultAttributeValueForlistview(String attributeName) {
        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "HeaderFontFamily" -> "";
            case "HeaderFontSize" -> "";
            case "HeaderFontColor" -> "";
            case "HeaderBackColor" -> "ffffff";
            case "Visible" -> "true";
            case "Enable" -> "true";
            case "Height" -> "300";
            case "BorderColor" -> "";
            case "BorderWidth" -> "";
            case "MergeSection" -> "1";
            case "Grouping" -> "1";
            case "multiSelect" -> "1";
            case "Summary" -> "";
            case "timeZone" -> "1";
            case "HideAdd" -> "false";
            case "HideDelete" -> "false";
            case "ShowCheckboxColumn" -> "false";
            case "isAdvancedListview" -> "false";
            case "ListBtnAlignment" -> "0";
            case "DuplicateRow" -> "true";
            case "Searching" -> "true";
            case "Sorting" -> "true";
            case "CustomId" -> "";
            case "HidePrevNext" -> "N";
            case "CombinedFontWeight" -> "Bold";
            case "CombinedHeaderFontWeight" -> "Bold";
            default -> "";
        };
    }

    private static Element handleComboControl(String controlId, String controlType, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType, String dbQuery, String caching) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", controlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        if (!dbQuery.isEmpty() && !caching.isEmpty()) {
            Element fetchFromDB = emptyXmlDoc.createElement("FetchFromDB");
            fetchFromDB.setAttribute("DBQuery", dbQuery);
            fetchFromDB.setAttribute("Caching", caching);
            fetchFromDB.setTextContent("true"); // Add the text content
            newControl.appendChild(fetchFromDB);
            newControl.insertBefore(fetchFromDB, newControl.getElementsByTagName("Style").item(0));
        } else {
            Element optionsElement = emptyXmlDoc.createElement("Options");
            newControl.appendChild(optionsElement);
            newControl.insertBefore(optionsElement, newControl.getElementsByTagName("Style").item(0));
        }

        Node styleNode = emptyXmlDoc.createElement("Style"); // Create a Style element
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("combo");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

        // Check if controlId exists in the "label" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String comboId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (comboId.equals(controlId)) {
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
                styleElement.setAttribute(attribute, getDefaultAttributeValueForCombo(attribute));
            }
        } else {
            // controlId exists in the "label" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueForCombo(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueForCombo(attribute));
                }
            }
        }


        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);

        NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }

        return newControl;
    }


    // Define a method to get default attribute values based on the attribute name
    private static String getDefaultAttributeValueForCombo(String attributeName) {
        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "Mandatory" -> "false";
            case "Visible" -> "true";
            case "ReadOnly" -> "false";
            case "Enable" -> "true";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "SortingOrder" -> "0";
            case "ComboType" -> "";
            case "CharVisibleOnOption" -> "";
            case "ListSize" -> "";
            case "BorderColor" -> "";
            case "BorderWidth" -> "";
            case "ControlStyle" -> "";
            case "Grouping" -> "1";
            case "MergeSection" -> "1";
            case "LabelFontSize" -> "";
            case "LabelFontWeight" -> "";
            case "LabelFontFamily" -> "";
            case "LabelBackgoundColor" -> "";
            case "LabelFontColor" -> "";
            case "ToolTip" -> "";
            case "Summary" -> "";
            case "LabelInputRatio" -> "";
            case "LabelInputAlignment" -> "-1";
            case "ReadOnlyStyle" -> "N";
            case "validateMandatoryDisable" -> "N";
            case "CustomId" -> "";
            case "CombinedFontWeight" -> "Bold";
            default -> "";

        };
    }

    public static Map<String, Integer> createColumnIndexMap(Sheet sheet) {
        Map<String, Integer> columnIndexMap = new HashMap<>();
        Row headerRow = sheet.getRow(0); // Assuming the header row is the first row

        // Create a mapping of column names to their respective column indexes
        for (Cell cell : headerRow) {
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String columnName = cell.getStringCellValue();
                columnIndexMap.put(columnName, cell.getColumnIndex());
            }
        }

        return columnIndexMap;
    }


    private static Element handleListBoxControl(String controlId, String ControlType, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType, String dbQuery, String caching) throws IOException {
        System.out.println(controlId);
        Element newControl = emptyXmlDoc.createElement("Control");
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
            Element optionsElement = emptyXmlDoc.createElement("Options");
            newControl.appendChild(optionsElement);
            newControl.insertBefore(optionsElement, newControl.getElementsByTagName("Style").item(0));
        }

        Node styleNode = emptyXmlDoc.createElement("Style"); // Create a Style element
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("listbox");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

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
                styleElement.setAttribute(attribute, getDefaultAttributeValueForlistbox(attribute));
            }
        } else {
            // controlId exists in the "label" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueForlistbox(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueForlistbox(attribute));
                }
            }
        }


        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);

        NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }

        return newControl;
    }


    private static String getDefaultAttributeValueForlistbox(String attributeName) {
        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "Mandatory" -> "false";
            case "Visible" -> "true";
            case "ReadOnly" -> "false";
            case "Enable" -> "true";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "SortingOrder" -> "0";
            case "ComboType" -> "listbox";
            case "CharVisibleOnOption" -> "";
            case "ListSize" -> "";
            case "BorderColor" -> "";
            case "BorderWidth" -> "";
            case "ControlStyle" -> "";
            case "Grouping" -> "1";
            case "MergeSection" -> "1";
            case "LabelFontSize" -> "";
            case "LabelFontWeight" -> "";
            case "LabelFontFamily" -> "";
            case "LabelBackgoundColor" -> "";
            case "LabelFontColor" -> "";
            case "ToolTip" -> "";
            case "Summary" -> "";
            case "LabelInputRatio" -> "";
            case "LabelInputAlignment" -> "-1";
            case "ReadOnlyStyle" -> "N";
            case "validateMandatoryDisable" -> "N";
            case "CustomId" -> "";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };
    }

    private static Element findControlElementByType(Document xmlDoc, String controlType) {
        NodeList controlNodes = xmlDoc.getElementsByTagName("Control");
        for (int i = 0; i < controlNodes.getLength(); i++) {
            Element controlElement = (Element) controlNodes.item(i);
            String type = controlElement.getAttribute("ControlType");
            if (type.equalsIgnoreCase(controlType)) {
                return controlElement;
            }
        }
        return null;
    }

    public static Element textarea(String controlId, String ControlType, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType, String dataClassName) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", ControlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        Node styleNode = emptyXmlDoc.createElement("Style"); // Create a Style element
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("textarea");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

        // Check if controlId exists in the "label" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String textareaId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (textareaId.equals(controlId)) {
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
                "TextAlignment",
                "Rows",
                "MaximumChar",
                "MaxLength",
                "BorderColor",
                "BorderWidth",
                "ControlStyle",
                "CombinedFontWeight",
                "Grouping",
                "RichText",
                "MergeSection",
                "TypeOfValue",
                "Precision",
                "DisableMaxLength",
                "LabelFontSize",
                "LabelFontWeight",
                "LabelFontFamily",
                "LabelBackgoundColor",
                "LabelFontColor",
                "ToolTip",
                "AllowSpecialChars",
                "Summary",
                "ReadOnlyStyle",
                "CharacterSet",
                "AllowCopy",
                "AllowPaste",
                "LabelInputRatio",
                "LabelInputAlignment",
                "validateMandatoryDisable",
                "CustomId",
                "Alphabets",
                "Spaces",
                "Numerals",
                "SpecialCharacters",
                "PlaceHolder"
        };

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "label" sheet
            for (String attribute : styleAttributes) {
                styleElement.setAttribute(attribute, getDefaultAttributeValueFortextarea(attribute));
            }
        } else {
            // controlId exists in the "label" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueFortextarea(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueFortextarea(attribute));
                }
            }
        }

        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);

        /*NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }*/

        Element dataClassElement = emptyXmlDoc.createElement("DataClass");
        dataClassElement.setAttribute("Name", dataClassName);
        newControl.appendChild(dataClassElement);

        return newControl;
    }


    private static String getDefaultAttributeValueFortextarea(String attributeName) {
        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "Mandatory" -> "false";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "Visible" -> "true";
            case "ReadOnly" -> "false";
            case "Enable" -> "true";
            case "TextAlignment" -> "";
            case "Rows" -> "3";
            case "MaximumChar" -> "100";
            case "MaxLength" -> "";
            case "BorderColor" -> "";
            case "BorderWidth" -> "";
            case "ControlStyle" -> "";
            case "CombinedFontWeight" -> "Bold";
            case "Grouping" -> "1";
            case "RichText" -> "1";
            case "MergeSection" -> "1";
            case "TypeOfValue" -> "";
            case "Precision" -> "";
            case "DisableMaxLength" -> "false";
            case "LabelFontSize" -> "";
            case "LabelFontWeight" -> "";
            case "LabelFontFamily" -> "";
            case "LabelBackgoundColor" -> "";
            case "LabelFontColor" -> "";
            case "ToolTip" -> "";
            case "AllowSpecialChars" -> "Y";
            case "Summary" -> "";
            case "ReadOnlyStyle" -> "N";
            case "CharacterSet" -> "0";
            case "AllowCopy" -> "0";
            case "AllowPaste" -> "0";
            case "LabelInputRatio" -> "";
            case "LabelInputAlignment" -> "-1";
            case "validateMandatoryDisable" -> "N";
            case "CustomId" -> "";
            case "Alphabets" -> "true";
            case "Spaces" -> "true";
            case "Numerals" -> "true";
            case "SpecialCharacters" -> "";
            case "PlaceHolder" -> "";
            default -> "";
        };
    }

    public static Element textbox(String controlId, String ControlType, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", ControlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        Element styleNode = emptyXmlDoc.createElement("Style");
        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        Sheet sheet = workbook.getSheet("textbox");

        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);
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

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "textbox" sheet
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

            for (String attribute : styleAttributes) {
                styleNode.setAttribute(attribute, getDefaultAttributeValueFortextbox(attribute));
            }
        } else {
            // controlId exists in the "textbox" sheet, apply user-defined or default style properties
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

            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    String cellValue = dataFormatter.formatCellValue(row.getCell(columnIndex));
                    // Check if the cell value is empty or null, if so, use the default value
                    if (cellValue == null || cellValue.isEmpty()) {
                        cellValue = getDefaultAttributeValueFortextbox(attribute);
                    }
                    styleNode.setAttribute(attribute, cellValue);
                } else {
                    // If the column index is not found, set the default value
                    styleNode.setAttribute(attribute, getDefaultAttributeValueFortextbox(attribute));
                }
            }
        }

        newControl.appendChild(styleNode);

        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);

        NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }

        return newControl;
    }


    private static String getDefaultAttributeValueFortextbox(String attributeName) {
        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "Mandatory" -> "false";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "Visible" -> "true";
            case "ReadOnly" -> "false";
            case "Enable" -> "true";
            case "Grouping" -> "1";
            case "DisableMaxLength" -> "false";
            case "BorderColor" -> "";
            case "BorderWidth" -> "";
            case "ControlStyle" -> "";
            case "MergeSection" -> "1";
            case "MaskingValue" -> "nomasking";
            case "FormattedValue" -> "N";
            case "RangeMax" -> "0";
            case "RangeMin" -> "0";
            case "TypeOfValue" -> "";
            case "Precision" -> "";
            case "SaveEncrypted" -> "N";
            case "MaxLength" -> "";
            case "Alphabets" -> "true";
            case "Spaces" -> "true";
            case "Numerals" -> "true";
            case "SpecialCharacters" -> "";
            case "Pattern" -> "";
            case "LabelFontSize" -> "";
            case "LabelFontWeight" -> "";
            case "LabelFontFamily" -> "";
            case "LabelBackgroundColor" -> "";
            case "LabelFontColor" -> "";
            case "LabelInputRatio" -> "";
            case "ToolTip" -> "";
            case "Summary" -> "";
            case "ReadOnlyStyle" -> "N";
            case "AllowSpecialChars" -> "Y";
            case "validateMandatoryDisable" -> "N";
            case "CharacterSet" -> "0";
            case "AllowCopy" -> "0";
            case "AllowPaste" -> "0";
            case "LabelInputAlignment" -> "-1";
            case "CustomId" -> "";
            case "LabelAsLink" -> "N";
            case "TextAlignment" -> "";
            case "MaxValue" -> "";
            case "MinValue" -> "";
            case "PlaceHolder" -> "";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };
    }

    public static Element checkbox(String controlId, String ControlType, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", ControlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        Node styleNode = emptyXmlDoc.createElement("Style"); // Create a Style element
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("checkbox");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

        // Check if controlId exists in the "label" sheet
        boolean controlIdExists = false;
        Row row = null; // Define row here

        // Assuming controlId is unique, you can set controlIdExists to true if a match is found.
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i); // Assign the row

            if (row != null) {
                String checkboxId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                if (checkboxId.equals(controlId)) {
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
                "BorderColor",
                "BorderWidth",
                "ControlStyle",
                "Grouping",
                "MergeSection",
                "VerticalAlignment",
                "ToolTip",
                "Summary",
                "CustomId",
                "validateMandatoryDisable",
                "ReadOnlyStyle",
                "CombinedFontWeight"
        };

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "label" sheet
            for (String attribute : styleAttributes) {
                styleElement.setAttribute(attribute, getDefaultAttributeValueForCheckbox(attribute));
            }
        } else {
            // controlId exists in the "label" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueForCheckbox(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueForCheckbox(attribute));
                }
            }
        }

        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);


        NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }

        return newControl;
    }


    private static String getDefaultAttributeValueForCheckbox(String attributeName) {
        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "Mandatory" -> "false";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "Visible" -> "true";
            case "ReadOnly" -> "false";
            case "Enable" -> "true";
            case "BorderColor" -> "";
            case "BorderWidth" -> "";
            case "ControlStyle" -> "";
            case "Grouping" -> "1";
            case "MergeSection" -> "1";
            case "VerticalAlignment" -> "";
            case "ToolTip" -> "";
            case "Summary" -> "";
            case "CustomId" -> "";
            case "validateMandatoryDisable" -> "N";
            case "ReadOnlyStyle" -> "N";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };


    }

    public static Element datepick(String controlId, String ControlType, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", ControlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        Node styleNode = emptyXmlDoc.createElement("Style"); // Create a Style element
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("datepick");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

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
                styleElement.setAttribute(attribute, getDefaultAttributeValueFordatepick(attribute));
            }
        } else {
            // controlId exists in the "datepick" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueFordatepick(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueFordatepick(attribute));
                }
            }
        }

        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);

        NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }

        return newControl;
    }


    private static String getDefaultAttributeValueFordatepick(String attributeName) {

        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "Mandatory" -> "false";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "Visible" -> "true";
            case "ReadOnly" -> "false";
            case "Enable" -> "true";
            case "MinDateAsCurrent" -> "false";
            case "MaxDateAsCurrent" -> "false";
            case "BorderColor" -> "";
            case "BorderWidth" -> "";
            case "ControlStyle" -> "";
            case "TextAlignment" -> "";
            case "PickerType" -> "1";
            case "OpenPicker" -> "1";
            case "SelectPicker" -> "";
            case "Grouping" -> "1";
            case "MergeSection" -> "1";
            case "MinDate" -> "";
            case "MaxDate" -> "";
            case "LabelFontSize" -> "";
            case "LabelFontWeight" -> "";
            case "LabelFontFamily" -> "";
            case "LabelBackgoundColor" -> "";
            case "LabelFontColor" -> "";
            case "TimeZone" -> "false";
            case "ToolTip" -> "";
            case "Summary" -> "";
            case "LabelInputRatio" -> "";
            case "LabelInputAlignment" -> "-1";
            case "ReadOnlyStyle" -> "N";
            case "validateMandatoryDisable" -> "N";
            case "CustomId" -> "";
            case "DefaultValue" -> "";
            case "CombinedFontWeight" -> "Bold";
            case "DefaultHijriView" -> "1";
            default -> "";
        };

    }

    public static Element label(String controlId, String ControlType, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", ControlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        Node styleNode = emptyXmlDoc.createElement("Style"); // Create a Style element
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("label");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

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
                styleElement.setAttribute(attribute, getDefaultAttributeValueForlabel(attribute));
            }
        } else {
            // controlId exists in the "label" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueForlabel(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueForlabel(attribute));
                }
            }
        }

        NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }

        return newControl;
    }


    private static String getDefaultAttributeValueForlabel(String attributeName) {
        return switch (attributeName) {
            case "FontStyle" -> "";
            case "FontWeight" -> "";
            case "FontSize" -> "";
            case "FontColor" -> "";
            case "BackColor" -> "";
            case "FontFamily" -> "";
            case "Mandatory" -> "false";
            case "Visible" -> "true";
            case "ReadOnly" -> "false";
            case "Enable" -> "true";
            case "MergeSection" -> "1";
            case "Grouping" -> "1";
            case "TextAlignment" -> "";
            case "LinkURL" -> "";
            case "Link" -> "1";
            case "SubSection" -> "N";
            case "CustomId" -> "";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };
    }

    public static Element radio(String controlId, String ControlType, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", ControlType);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);
        newControl.setAttribute("Title", title);
        newControl.setAttribute("SaveValueType", saveValueType);

        // Create and append the Options element
        Element optionsElement = emptyXmlDoc.createElement("Options");
        newControl.appendChild(optionsElement);
        newControl.insertBefore(optionsElement, newControl.getElementsByTagName("Style").item(0));

        // Create and append the Style element
        Node styleNode = emptyXmlDoc.createElement("Style");
        newControl.appendChild(styleNode);

        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);

        // Now, you can modify the style properties
        Element styleElement = (Element) styleNode;

        Sheet sheet = workbook.getSheet("radio");
        DataFormatter dataFormatter = new DataFormatter();
        Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

        // Check if controlId exists in the "radio" sheet
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
                "BorderColor",
                "BorderWidth",
                "ControlStyle",
                "Allignment",
                "Grouping",
                "MergeSection",
                "LabelFontSize",
                "LabelFontWeight",
                "LabelFontFamily",
                "LabelBackgoundColor",
                "LabelFontColor",
                "LabelInputRatio",
                "LabelInputAlignment",
                "VerticalAlignment",
                "ToolTip",
                "Summary",
                "ReadOnlyStyle",
                "validateMandatoryDisable",
                "CustomId",
                "CombinedFontWeight"
        };

        if (!controlIdExists) {
            // Set default style properties if controlId does not exist in the "radio" sheet
            for (String attribute : styleAttributes) {
                styleElement.setAttribute(attribute, getDefaultAttributeValueForradio(attribute));
            }
        } else {
            // controlId exists in the "radio" sheet, apply user-defined or default style properties
            for (String attribute : styleAttributes) {
                Integer columnIndex = columnIndexMap.get(attribute);

                if (columnIndex != null) {
                    styleElement.setAttribute(attribute,
                            dataFormatter.formatCellValue(row.getCell(columnIndex)) != null ?
                                    dataFormatter.formatCellValue(row.getCell(columnIndex)) :
                                    getDefaultAttributeValueForradio(attribute)
                    );
                } else {
                    // If the column index is not found, set the default value
                    styleElement.setAttribute(attribute, getDefaultAttributeValueForradio(attribute));
                }
            }
        }

        Element eventElement = emptyXmlDoc.createElement("Event");
        Element eventsElement = emptyXmlDoc.createElement("Events");
        eventElement.appendChild(eventsElement);
        newControl.appendChild(eventElement);

        NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }

        return newControl;
    }


    private static String getDefaultAttributeValueForradio(String attributeName) {
        return switch (attributeName) {
            case "Mandatory", "ReadOnly" -> "false";
            case "ValidationMessage" -> "Missing or Invalid Value";
            case "Visible", "Enable" -> "true";
            case "Allignment", "Grouping", "MergeSection" -> "1";
            case "LabelInputAlignment" -> "-1";
            case "ReadOnlyStyle", "validateMandatoryDisable" -> "N";
            case "CombinedFontWeight" -> "Bold";
            default -> "";
        };
    }

    static class ColumnProperties {
        String columnName;
        String complexFieldName;
        String complexFieldType;

        ColumnProperties(String columnName, String complexFieldName, String complexFieldType) {
            this.columnName = columnName;
            this.complexFieldName = complexFieldName;
            this.complexFieldType = complexFieldType;
        }
    }

    static class frameControlNames {
        String controlName;

        frameControlNames(String controlName) {
            this.controlName = controlName;
        }
    }
}

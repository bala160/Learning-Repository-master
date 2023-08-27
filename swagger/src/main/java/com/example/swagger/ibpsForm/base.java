package com.example.swagger.ibpsForm;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class base {
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
                    }  else if (controlTypeToSearch.equalsIgnoreCase("sheet")) {
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
                                    newControl = handleComboControl(controlId, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching);
                                } else if (controlTypeToSearch.equalsIgnoreCase("listbox")) {
                                    newControl = handleListBoxControl(controlId, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching);
                                } else if (controlTypeToSearch.equalsIgnoreCase("textarea")) {
                                    newControl = textarea(controlId,controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType);

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

    // Define functions to create tab, sheet, and control elements as needed
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

    private static Element generateTableControlElement(String controlId, String controlLabel, String dataType, String groupId, String identifier, String title, String insertionOrderIdColumn, String dataClassName, List<ColumnProperties> columns) {
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

        // Import style elements from allFieldXmlDoc
        Node styleNode = emptyXmlDoc.importNode(allFieldXmlDoc.getElementsByTagName("Style").item(0), true);
        tableControlElement.appendChild(styleNode);

        // Create and append <columns> tag before <event> tags
        Element columnsElement = emptyXmlDoc.createElement("columns");
        tableControlElement.appendChild(columnsElement);

        for (ColumnProperties column : columns) {
            Element columnElement = emptyXmlDoc.createElement("column");
            columnElement.setAttribute("columnName", column.columnName);
            columnElement.setAttribute("complexFieldName", column.complexFieldName);
            columnElement.setAttribute("complexFieldType", column.complexFieldType);
            // Set other attributes for the column...
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


    private static Element handleComboControl(String controlId, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType, String dbQuery, String caching) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", "combo");
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

        // Import style elements from allFieldXmlDoc
        Node styleNode = emptyXmlDoc.importNode(allFieldXmlDoc.getElementsByTagName("Style").item(0), true);
        newControl.appendChild(styleNode);
        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);
        // Now, you can modify the style properties
        if (styleNode.getNodeType() == Node.ELEMENT_NODE) {
            Element styleElement = (Element) styleNode;
            Sheet sheet = workbook.getSheetAt(1);
            DataFormatter dataFormatter = new DataFormatter();
            Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                //String addGradient = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("AddGradient")));

                if (row != null) {

                    String comboId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));

                    if (comboId.equals(controlId)) {
                        String[] styleAttributes = {
                                "AddGradient",
                                "AllowAutoScroll",
                                "BGActiveColor",
                                "BGInactiveColor",
                                "BackColor",
                                "BackColorBoundary",
                                "BoderSize",
                                "CombinedFontWeight",
                                "EnableSliderArrows",
                                "FitToContentHeight",
                                "FixHeader",
                                "FontColor",
                                "FontFamily",
                                "FontSize",
                                "FontStyle",
                                "FontWeight",
                                "FormAllignments",
                                "FormBackgroundColor",
                                "FormBorderColor",
                                "FormBorderWidth",
                                "FormHorizontal",
                                "FormMaxWidth",
                                "FormMinWidth",
                                "FormVertical",
                                "FormWidth",
                                "FormWidthType",
                                "GradientAngles",
                                "GradientColor1",
                                "GradientColor2",
                                "GradientPercent1",
                                "GradientPercent2",
                                "HeaderAllignment",
                                "ImageAllignment",
                                "ImageFormUrl",
                                "InputStyle",
                                "LeftPercent",
                                "MenuActiveColor",
                                "MenuInactiveColor",
                                "RightPercent",
                                "ScreenBackgroundColor",
                                "ScreenBackgroundImage",
                                "ScreenBackgroundRadio",
                                "ScreenBgImgWidth",
                                "SelectedCarouselImages",
                                "ShowHeader",
                                "TopPercent"
                        };

                        for (String attribute : styleAttributes) {
                            styleElement.setAttribute(attribute,
                                    dataFormatter.formatCellValue(row.getCell(columnIndexMap.get(attribute))) != null ?
                                            dataFormatter.formatCellValue(row.getCell(columnIndexMap.get(attribute))) :
                                            combo.getDefaultAttributeValueForCombo(attribute)
                            );
                        }
                    }
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

    public static Map<String, Integer> createColumnIndexMap(Sheet sheet) {
        Map<String, Integer> columnIndexMap = new HashMap<>();
        Row headerRow = sheet.getRow(0);
        for (Cell cell : headerRow) {
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String columnName = cell.getStringCellValue();
                columnIndexMap.put(columnName, cell.getColumnIndex());
            }
        }

        return columnIndexMap;
    }

    private static Element handleListBoxControl(String controlId, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType, String dbQuery, String caching) {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", "listbox");
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

            // Create and append the Options element
            Element optionsElement = emptyXmlDoc.createElement("Options");
            newControl.appendChild(optionsElement);
            newControl.insertBefore(optionsElement, newControl.getElementsByTagName("Style").item(0));
        }


        NodeList dataClassNodes = newControl.getElementsByTagName("DataClass");
        if (dataClassNodes.getLength() > 0) {
            Element dataClassElement = (Element) dataClassNodes.item(0);
            newControl.removeChild(dataClassElement);
        }


        return newControl;
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

    public static Element textarea(String controlId,String controlTypeToSearch, String controlLabel, String dataType, String groupId, String identifier, String title, String saveValueType) throws IOException {
        Element newControl = emptyXmlDoc.createElement("Control");
        newControl.setAttribute("ControlId", controlId);
        newControl.setAttribute("ControlType", controlTypeToSearch);
        newControl.setAttribute("ControlLabel", controlLabel);
        newControl.setAttribute("DataType", dataType);
        newControl.setAttribute("GroupId", groupId);
        newControl.setAttribute("Identifier", identifier);

        Node styleNode = emptyXmlDoc.importNode(allFieldXmlDoc.getElementsByTagName("Style").item(0), true);
        newControl.appendChild(styleNode);
        FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
        Workbook workbook = new XSSFWorkbook(excelFile);
        // Now, you can modify the style properties
        if (styleNode.getNodeType() == Node.ELEMENT_NODE) {
            Element styleElement = (Element) styleNode;
            Sheet sheet = workbook.getSheetAt(2);
            DataFormatter dataFormatter = new DataFormatter();
            Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    String comboId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));

                    if (comboId.equals(controlId)) {
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

                        for (String attribute : styleAttributes) {
                            Integer columnIndex = columnIndexMap.get(attribute);

                            if (columnIndex != null) {
                                Cell valueCell = row.getCell(columnIndex);
                                String cellValue = dataFormatter.formatCellValue(valueCell);

                                styleElement.setAttribute(attribute,
                                        cellValue != null ? cellValue : combo.getDefaultAttributeValueFortextarea(attribute)
                                );
                            } else {
                                // Handle the case where columnIndex is null, possibly by setting a default value
                                styleElement.setAttribute(attribute, combo.getDefaultAttributeValueFortextarea(attribute));
                            }
                        }


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
        }
        return  newControl;
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
}

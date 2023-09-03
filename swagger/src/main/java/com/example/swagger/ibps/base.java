package com.example.swagger.ibps;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class base {
    public static Document emptyXmlDoc;
    public static Document allFieldXmlDoc;

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            emptyXmlDoc = docBuilder.parse(new File("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Empty_form.xml"));
            allFieldXmlDoc = docBuilder.parse(new File("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\allFields.xml"));

            FileInputStream excelFile = new FileInputStream("C:\\Users\\Balakrishnan\\Documents\\test\\23-08\\Checkinput11.xlsx");
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheet("controlProperties");
            Map<String, Integer> columnIndexMap = createColumnIndexMap(sheet);
            Element emptyHeaderFrame = (Element) emptyXmlDoc.getElementsByTagName("HeaderFrame").item(0);

            DataFormatter dataFormatter = new DataFormatter();
            boolean tabControlGenerated = false;
            boolean tableControlGenerated = false;
            boolean listviewControlGenerated = false;


            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String controlId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlId")));
                String controlTypeToSearch = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("ControlType")));
                String controlLabel = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("controlLabel")));
                String dataType = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("DataType")));
                String groupId = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("GroupId")));
                String identifier = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("Identifier")));
                String title = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("Title")));
                String saveValueType = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("SaveValueType")));
                String dataClassName = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("DataClass Name")));
                String dbQuery = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("DBQuery")));
                String caching = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("Caching")));
                String insertionOrderIdColumn = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("InsertionOrderIdColumn")));
                String caption = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("Sectioncaption")));
                String columnLayout = dataFormatter.formatCellValue(row.getCell(columnIndexMap.get("SectioncolumnLayout")));

                if (controlTypeToSearch.equalsIgnoreCase("section")) {
                    System.out.println("section Calling");
                    Element sectionControl;
                    List<frameControlNames> frameControl = new ArrayList<>();

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

                    sectionControl = SectionClass.section(controlId, controlLabel, dataType, groupId, identifier, title, saveValueType, frameControl, dataClassName, caption, columnLayout, dbQuery, caching);


                    // Insert the section control after the </HeaderFrame> element
                    emptyHeaderFrame.getParentNode().insertBefore(sectionControl, emptyHeaderFrame.getNextSibling());
                    continue;
                } else if (controlTypeToSearch.equalsIgnoreCase("tab")) {
                    if (!tabControlGenerated) {
                        Element tabControl = TabClass.createTabElement(controlId, controlLabel, controlTypeToSearch);
                        emptyHeaderFrame.appendChild(tabControl);
                        tabControlGenerated = true;
                    }
                } else if (controlTypeToSearch.equalsIgnoreCase("tabend")) {
                    if (tabControlGenerated) {
                        tabControlGenerated = false;
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

                                    Element tableControlElement = TableClass.generateTableControlElement(controlId, controlLabel, dataType, groupId, identifier, title, insertionOrderIdColumn, dataClassName, columns);
                                    emptyHeaderFrame.getLastChild().getLastChild().appendChild(tableControlElement);
                                    tableControlGenerated = true;
                                }
                                continue;
                            } else if (controlTypeToSearch.equalsIgnoreCase("combo")) {
                                newControl = ComboBoxClass.handleComboControl(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching, dataClassName);
                            } else if (controlTypeToSearch.equalsIgnoreCase("listbox")) {
                                newControl = ListBoxClass.handleListBoxControl(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching, dataClassName);
                            } else if (controlTypeToSearch.equalsIgnoreCase("textarea")) {
                                newControl = TextAreaClass.textarea(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                            } else if (controlTypeToSearch.equalsIgnoreCase("textbox")) {
                                newControl = TextBoxClass.textbox(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                            } else if (controlTypeToSearch.equalsIgnoreCase("checkbox")) {
                                newControl = CheckBoxClass.checkbox(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                            } else if (controlTypeToSearch.equalsIgnoreCase("datepick")) {
                                newControl = DatepickClass.datepick(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                            } else if (controlTypeToSearch.equalsIgnoreCase("label")) {
                                newControl = LableClass.label(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                            } else if (controlTypeToSearch.equalsIgnoreCase("radio")) {
                                newControl = RadioClass.radio(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                            } else if (controlTypeToSearch.equalsIgnoreCase("EmptyCell") && controlTypeToSearch.equalsIgnoreCase("EmptyRow")) {
                                newControl = EmptyCellRowClass.handleEmptyCellControl(controlId, controlLabel, dataType, groupId, identifier, title);
                            } else if (controlId.startsWith("Slider")) {
                                newControl = SliderClass.handleSliderControlElement(controlId, controlLabel, dataType, groupId, identifier, title);
                            } else if (controlId.startsWith("Doclist")) {
                                newControl = DoclistClass.handleDoclistControlElement(controlId, controlLabel, dataType, groupId, identifier, title);
                            } else if (controlId.startsWith("Toggle")) {
                                newControl = ToggleClass.handleToggleControlElement(controlId, controlLabel, dataType, groupId, identifier, title);
                            } else if (controlId.startsWith("Tile")) {
                                newControl = TileClass.handleTileControlElement(controlId, controlLabel, dataType, groupId, identifier, title);
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

                                    Element tableControlElement = ListviewClass.listview(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, insertionOrderIdColumn, dataClassName, columns, saveValueType, frameControl, dbQuery, caching);
                                    emptyHeaderFrame.getLastChild().getLastChild().appendChild(tableControlElement);
                                    tableControlGenerated = true;
                                }
                                continue;
                            }

                            assert newControl != null;
                            NodeList dataClassNodes = Objects.requireNonNull(newControl).getElementsByTagName("DataClass");
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
                if (!tabControlGenerated) {
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

                                Element tableControlElement = TableClass.generateTableControlElement(controlId, controlLabel, dataType, groupId, identifier, title, insertionOrderIdColumn, dataClassName, columns);
                                emptyHeaderFrame.getLastChild().getLastChild().appendChild(tableControlElement);
                                tableControlGenerated = true;
                            }
                            continue;
                        } else if (controlTypeToSearch.equalsIgnoreCase("combo")) {
                            newControl = ComboBoxClass.handleComboControl(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching, dataClassName);
                        } else if (controlTypeToSearch.equalsIgnoreCase("listbox")) {
                            newControl = ListBoxClass.handleListBoxControl(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dbQuery, caching, dataClassName);
                        } else if (controlTypeToSearch.equalsIgnoreCase("textarea")) {
                            newControl = TextAreaClass.textarea(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                        } else if (controlTypeToSearch.equalsIgnoreCase("textbox")) {
                            newControl = TextBoxClass.textbox(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                        } else if (controlTypeToSearch.equalsIgnoreCase("checkbox")) {
                            newControl = CheckBoxClass.checkbox(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                        } else if (controlTypeToSearch.equalsIgnoreCase("datepick")) {
                            newControl = DatepickClass.datepick(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                        } else if (controlTypeToSearch.equalsIgnoreCase("label")) {
                            newControl = LableClass.label(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                        } else if (controlTypeToSearch.equalsIgnoreCase("radio")) {
                            newControl = RadioClass.radio(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, saveValueType, dataClassName);

                        } else if (controlTypeToSearch.equalsIgnoreCase("EmptyCell") && controlTypeToSearch.equalsIgnoreCase("EmptyRow")) {
                            newControl = EmptyCellRowClass.handleEmptyCellControl(controlId, controlLabel, dataType, groupId, identifier, title);
                        } else if (controlId.startsWith("Slider")) {
                            newControl = SliderClass.handleSliderControlElement(controlId, controlLabel, dataType, groupId, identifier, title);
                        } else if (controlId.startsWith("Doclist")) {
                            newControl = DoclistClass.handleDoclistControlElement(controlId, controlLabel, dataType, groupId, identifier, title);
                        } else if (controlId.startsWith("Toggle")) {
                            newControl = ToggleClass.handleToggleControlElement(controlId, controlLabel, dataType, groupId, identifier, title);
                        } else if (controlId.startsWith("Tile")) {
                            newControl = TileClass.handleTileControlElement(controlId, controlLabel, dataType, groupId, identifier, title);
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

                                Element tableControlElement = ListviewClass.listview(controlId, controlTypeToSearch, controlLabel, dataType, groupId, identifier, title, insertionOrderIdColumn, dataClassName, columns, saveValueType, frameControl, dbQuery, caching);
                                emptyHeaderFrame.getLastChild().getLastChild().appendChild(tableControlElement);
                                tableControlGenerated = true;
                            }
                            continue;
                        }

                        assert newControl != null;
                        NodeList dataClassNodes = Objects.requireNonNull(newControl).getElementsByTagName("DataClass");
                        if (dataClassNodes.getLength() > 0) {
                            Element dataClassElement = (Element) dataClassNodes.item(0);
                            newControl.removeChild(dataClassElement);
                        }

                        Element dataClassElement = emptyXmlDoc.createElement("DataClass");
                        dataClassElement.setAttribute("Name", dataClassName);
                        newControl.appendChild(dataClassElement);

                        emptyHeaderFrame.appendChild(newControl);
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




    public static Map<String, Integer> createColumnIndexMap(Sheet sheet) {
        Map<String, Integer> columnIndexMap = new HashMap<>();
        Row headerRow = sheet.getRow(0); // Assuming the header row is the first row

        // Create a mapping of column names to their respective column indexes
        for (Cell cell : headerRow) {
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String columnName = cell.getStringCellValue().trim();
                columnIndexMap.put(columnName, cell.getColumnIndex());
            }
        }

        return columnIndexMap;
    }

    public static Element findControlElementByType(Document xmlDoc, String controlType) {
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


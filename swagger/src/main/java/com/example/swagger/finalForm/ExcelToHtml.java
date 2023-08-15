package com.example.swagger.finalForm;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;

public class ExcelToHtml {

    private static StringBuilder htmlBuilder;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\Balakrishnan\\Downloads\\DocumentGenerationAutomation\\DocumentGenerationAutomation\\input.xlsx";
        String outputHtmlFilePath = "C:\\Users\\Balakrishnan\\Downloads\\DocumentGenerationAutomation\\DocumentGenerationAutomation\\output.html";
        generateDynamicHtmlFromExcel(excelFilePath, outputHtmlFilePath);
    }

    public static void generateDynamicHtmlFromExcel(String excelFilePath, String outputHtmlFilePath) {
        htmlBuilder = new StringBuilder();
        initializeHtml();

        try (InputStream inputStream = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            for (Sheet sheet : workbook) {
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Cell keywordCell = row.getCell(0);
                    Cell contentCell = row.getCell(1);
                    Cell styleCell = row.getCell(10);
                    Cell sizeCell = row.getCell(11);
                    Cell underlineCell = row.getCell(12);

                    if (keywordCell != null && keywordCell.getCellType() == CellType.STRING) {
                        String keyword = keywordCell.getStringCellValue().trim().toLowerCase();
                        switch (keyword) {
                            case "paragraph":
                                boolean underline = (underlineCell != null && underlineCell.getCellType() == CellType.BOOLEAN)
                                        ? underlineCell.getBooleanCellValue()
                                        : false;
                                generateParagraph(contentCell, styleCell, sizeCell, underline);
                                break;
                            case "space":
                                generateLineBreak();
                                break;
                            case "table":
                                int numColumns = (int) row.getCell(1).getNumericCellValue();
                                generateTable(rowIterator, styleCell, sizeCell, numColumns);
                                break;
                            case "table-cell":
                                generateTableCell(contentCell, styleCell, sizeCell);
                                break;
                            case "table-end":
                                generateTableEnd();
                                break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        finalizeHtml();
        saveHtmlToFile(outputHtmlFilePath);
    }

    private static void initializeHtml() {
        htmlBuilder.append("<!DOCTYPE html>\n");
        htmlBuilder.append("<html>\n<head>\n<title>Generated HTML</title>\n<style>\n");
        htmlBuilder.append("table {\nborder-collapse: collapse;\nwidth: 100%;\n}\n");
        htmlBuilder.append("th, td {\nborder: 1px solid black;\npadding: 8px;\ntext-align: left;\n}\n");
        htmlBuilder.append("</style>\n</head>\n<body>\n");
    }

    private static void generateParagraph(Cell contentCell, Cell styleCell, Cell sizeCell, boolean underline) {
        if (contentCell != null && contentCell.getCellType() == CellType.STRING) {
            String paragraphContent = contentCell.getStringCellValue();
            String fontStyle = getFontStyle(styleCell);
            String fontSize = getFontSize(sizeCell);
            String textDecoration = underline ? "text-decoration: underline;" : "";
            htmlBuilder.append("<p style=\"").append(fontStyle).append("; font-size: ").append(fontSize)
                    .append("; ").append(textDecoration).append("\">")
                    .append(paragraphContent).append("</p>\n");
        }
    }

    private static void generateLineBreak() {
        htmlBuilder.append("<br>\n");
    }

    private static void generateTableCell(Cell contentCell, Cell styleCell, Cell sizeCell) {
        if (contentCell != null && contentCell.getCellType() == CellType.STRING) {
            String cellContent = contentCell.getStringCellValue();
            String fontStyle = getFontStyle(styleCell);
            String fontSize = getFontSize(sizeCell);
            htmlBuilder.append("<td style=\"").append(fontStyle).append("; font-size: ").append(fontSize).append("\">")
                    .append(cellContent).append("</td>");
        }
    }

    private static void generateTableEnd() {
        htmlBuilder.append("</tr>\n");
    }

    private static String getFontStyle(Cell styleCell) {
        if (styleCell != null && styleCell.getCellType() == CellType.STRING) {
            return "font-family: " + styleCell.getStringCellValue();
        }
        return "";
    }

    private static String getFontSize(Cell sizeCell) {
        if (sizeCell != null && sizeCell.getCellType() == CellType.NUMERIC) {
            return sizeCell.getNumericCellValue() + "px";
        }
        return "15px"; // Default font size if not specified
    }

    private static void generateTable(Iterator<Row> rowIterator, Cell styleCell, Cell sizeCell, int numColumns) {
        htmlBuilder.append("<table style=\"border-collapse: collapse; width: 100%;\">\n");
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell tableCell = row.getCell(1);
            if (tableCell != null && tableCell.getCellType() == CellType.STRING) {
                String tableContent = tableCell.getStringCellValue();
                if (tableContent.equalsIgnoreCase("end")) {
                    break;
                }
                htmlBuilder.append("<tr>");
                for (int i = 0; i < numColumns; i++) {
                    Cell cell = row.getCell(i + 1);
                    String cellContent = (cell != null && cell.getCellType() == CellType.STRING)
                            ? cell.getStringCellValue()
                            : "";
                    String fontStyle = getFontStyle(styleCell);
                    String fontSize = getFontSize(sizeCell);
                    htmlBuilder.append("<td style=\"").append(fontStyle).append("; font-size: ").append(fontSize).append("\">")
                            .append(cellContent).append("</td>");
                }
                htmlBuilder.append("</tr>\n");
            }
        }
        htmlBuilder.append("</table>\n");
    }

    private static void finalizeHtml() {
        htmlBuilder.append("</body>\n</html>");
    }

    private static void saveHtmlToFile(String filePath) {
        try {
            Path outputPath = Paths.get(filePath);
            Files.write(outputPath, htmlBuilder.toString().getBytes());
            System.out.println("HTML content saved to: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

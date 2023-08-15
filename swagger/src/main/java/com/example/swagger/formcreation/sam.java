package com.example.swagger.formcreation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class sam {
    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\Balakrishnan\\Downloads\\DocumentGenerationAutomation\\DocumentGenerationAutomation\\input.xlsx";
        String outputHtml = generateHtmlFromExcel(excelFilePath);
        System.out.println(outputHtml);
    }

    public static String generateHtmlFromExcel(String excelFilePath) {
        StringBuilder output = new StringBuilder();

        output.append("<!DOCTYPE html>\n");
        output.append("<html>\n<head>\n");
        output.append("<title>Generated HTML</title>\n");
        output.append("<style>\n");
        output.append("table {\nborder-collapse: collapse;\nwidth: 100%;\n}\n");
        output.append("th, td {\nborder: 1px solid black;\npadding: 8px;\ntext-align: left;\n}\n");
        output.append("</style>\n</head>\n<body>\n");

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int tableDepth = 0;
            boolean inNestedTable = false;

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell == null) {
                    continue;
                }

                String cellValue = cell.getStringCellValue().trim();
                if (cellValue.equals("space")) {
                    output.append("<br>\n");
                } else if (cellValue.startsWith("paragraph")) {
                    String[] parts = cellValue.split("\t", 2);
                    if (parts.length >= 2) {
                        String content = parts[1];
                        output.append("<p>").append(content).append("</p>\n");
                    }
                } else if (cellValue.startsWith("table")) {
                    String[] parts = cellValue.split("\t", 2);
                    if (parts.length >= 2) {
                        String content = parts[1];
                        if (content.matches("\\d+")) {
                            if (!inNestedTable) {
                                tableDepth++;
                                output.append("<table style=\"border: 1px solid black;\">\n");
                            }
                        }
                    }
                } else if (cellValue.equals("nestedtable")) {
                    inNestedTable = true;
                    output.append("<tr><td><table style=\"border: 1px solid black;\">\n");
                } else if (cellValue.equals("endnestedtable")) {
                    inNestedTable = false;
                    output.append("</table></td></tr>\n");
                } else if (cellValue.equals("end")) {
                    if (!inNestedTable && tableDepth > 0) {
                        tableDepth--;
                        output.append("</table>\n");
                    }
                } else {
                    if (!inNestedTable && tableDepth > 0) {
                        output.append("<tr>\n");
                        String[] cells = cellValue.split("\t");
                        for (String cellContent : cells) {
                            output.append("<td>").append(cellContent).append("</td>\n");
                        }
                        output.append("</tr>\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        output.append("</body>\n</html>");

        return output.toString();
    }
}

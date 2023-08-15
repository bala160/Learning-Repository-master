package com.example.swagger.formcreation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelToHTMLGenerator {
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\Balakrishnan\\Documents\\data.xlsx";
        String outputFile = "C:\\Users\\Balakrishnan\\Documents\\output.html";

        try {
            FileInputStream fis = new FileInputStream(inputFile);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);

            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html><body>");

            for (Row row : sheet) {
                Cell instructionCell = row.getCell(0); // First column for user instructions
                Cell contentCell = row.getCell(1);      // Second column for user content
                Cell widthCell = row.getCell(2);        // Third column for table width
                Cell heightCell = row.getCell(3);       // Fourth column for table height

                if (instructionCell != null && contentCell != null) {
                    String instruction = instructionCell.getStringCellValue().trim();
                    String content = contentCell.getStringCellValue().trim();
                    String tableWidth = widthCell != null ? getCellValue(widthCell) : "100%";
                    String tableHeight = heightCell != null ? getCellValue(heightCell) : "";

                    if (instruction.equalsIgnoreCase("Paragraph")) {
                        htmlBuilder.append("<p>").append(content).append("</p>");
                    } else if (instruction.equalsIgnoreCase("Table")) {
                        htmlBuilder.append("<table border=\"1\" width=\"").append(tableWidth).append("\" height=\"").append(tableHeight).append("\">")
                                .append("<tr><td>").append(content).append("</td></tr>")
                                .append("</table>");
                    } else if (instruction.equalsIgnoreCase("Nested Table")) {
                        // Similar logic for nested table
                        htmlBuilder.append("<table border=\"1\" width=\"").append(tableWidth).append("\" height=\"").append(tableHeight).append("\">")
                                .append("<tr><td>").append(content).append("</td></tr>")
                                .append("</table>");
                    }
                    // Add more conditions for other instructions as needed
                }
            }

            htmlBuilder.append("</body></html>");

            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(htmlBuilder.toString().getBytes());
            fos.close();

            System.out.println("HTML generated successfully!");

            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }
}

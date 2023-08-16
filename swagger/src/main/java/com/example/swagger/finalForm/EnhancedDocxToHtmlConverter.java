package com.example.swagger.finalForm;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class EnhancedDocxToHtmlConverter {

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\Balakrishnan\\Documents\\test\\input.docx";
        String outputFilePath = "C:\\Users\\Balakrishnan\\Documents\\test\\output.html";

        try {
            String htmlContent = convertDocxToHTML(inputFilePath);
            writeHTMLToFile(htmlContent, outputFilePath);
            System.out.println("Conversion completed successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String convertDocxToHTML(String docxFilePath) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(docxFilePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            htmlContent.append("<!DOCTYPE html>");
            htmlContent.append("<html>");
            htmlContent.append("<head>");
            // Add necessary CSS styles
            htmlContent.append("<style>");
            htmlContent.append("table { border-collapse: collapse; width: 100%; }");
            htmlContent.append("td, th { border: 1px solid black; padding: 8px; }");
            // Add more CSS styles for alignment, fonts, etc.
            htmlContent.append("</style>");
            htmlContent.append("</head>");
            htmlContent.append("<body>");

            for (IBodyElement element : document.getBodyElements()) {
                if (element instanceof XWPFParagraph) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    // Apply paragraph styles (alignment, font, etc.) as needed
                    String alignment = paragraph.getAlignment().toString().toLowerCase();
                    htmlContent.append("<p style=\"text-align: ").append(alignment).append(";\">").append(paragraph.getText()).append("</p>");
                } else if (element instanceof XWPFTable) {
                    XWPFTable table = (XWPFTable) element;
                    convertTableToHTML(table, htmlContent);
                }
            }

            htmlContent.append("</body>");
            htmlContent.append("</html>");
        }
        return htmlContent.toString();
    }

    private static void convertTableToHTML(XWPFTable table, StringBuilder htmlContent) {
        htmlContent.append("<table>");
        for (XWPFTableRow row : table.getRows()) {
            htmlContent.append("<tr>");
            for (XWPFTableCell cell : row.getTableCells()) {
                // Apply cell styles (borders, padding, etc.) as needed
                String alignment = cell.getParagraphs().get(0).getAlignment().toString().toLowerCase();
                htmlContent.append("<td style=\"text-align: ").append(alignment).append(";\">").append(convertCellContentToHTML(cell)).append("</td>");
            }
            htmlContent.append("</tr>");
        }
        htmlContent.append("</table>");
    }

    private static String convertCellContentToHTML(XWPFTableCell cell) {
        StringBuilder cellContent = new StringBuilder();
        for (IBodyElement element : cell.getBodyElements()) {
            if (element instanceof XWPFParagraph) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                // Apply paragraph styles within cell as needed
                cellContent.append("<p>").append(paragraph.getText()).append("</p>");
            } else if (element instanceof XWPFTable) {
                XWPFTable nestedTable = (XWPFTable) element;
                convertTableToHTML(nestedTable, cellContent);
            }
            // Handle images, hyperlinks, lists, etc. within the cell if needed
        }
        return cellContent.toString();
    }

    private static void writeHTMLToFile(String htmlContent, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            writer.write(htmlContent);
        }
    }
}

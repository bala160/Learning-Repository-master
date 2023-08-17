package com.example.swagger.finalForm;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    // ... (your existing imports and class definition)

    private static String convertDocxToHTML(String docxFilePath) throws IOException {
        StringBuilder htmlContent = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(docxFilePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            htmlContent.append("<!DOCTYPE html>");
            htmlContent.append("<html>");
            htmlContent.append("<head>");
            htmlContent.append("<style>");
            htmlContent.append("table { border-collapse: collapse; width: 100%; }");
            htmlContent.append("td, th { border: 1px solid black; padding: 8px; }");
            htmlContent.append("</style>");
            htmlContent.append("</head>");
            htmlContent.append("<body>");

            for (IBodyElement element : document.getBodyElements()) {
                if (element instanceof XWPFParagraph) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    String alignment = getParagraphAlignment(paragraph);
                    String style = getParagraphStyles(paragraph);
                    htmlContent.append("<p style=\"text-align: ").append(alignment).append("; ").append(style).append("\">")
                            .append(paragraph.getText()).append("</p>");
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
    // ... (your existing imports and class definition)

    private static String convertCellContentToHTML(XWPFTableCell cell) {
        StringBuilder cellContent = new StringBuilder();
        for (IBodyElement element : cell.getBodyElements()) {
            if (element instanceof XWPFParagraph) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                String paragraphAlignment = getParagraphAlignment(paragraph);

                // Apply paragraph alignment to the enclosing div tag
                cellContent.append("<div style=\"text-align: ").append(paragraphAlignment).append("; ").append(getParagraphStyles(paragraph)).append("\">");

                for (XWPFRun run : paragraph.getRuns()) {
                    String runStyles = getRunStyles(run);
                    cellContent.append("<span style=\"").append(runStyles).append("font-size: ").append(run.getFontSize()).append("pt;");

                    // Explicitly set underline style
                    if (run.getUnderline() != null && run.getUnderline() != UnderlinePatterns.NONE) {
                        cellContent.append("text-decoration: underline;");
                    }

                    cellContent.append("\">");

                    cellContent.append(run.getText(0));

                    cellContent.append("</span>");
                }

                cellContent.append("</div>");
            } else if (element instanceof XWPFTable) {
                XWPFTable nestedTable = (XWPFTable) element;
                convertTableToHTML(nestedTable, cellContent);
            }
            // Handle images, hyperlinks, lists, etc. within the cell if needed
        }
        return cellContent.toString();
    }

// ... (rest of your code remains unchanged)



    private static String getRunStyles(XWPFRun run) {
        StringBuilder styles = new StringBuilder();

        if (run.isBold()) {
            styles.append("font-weight: bold; ");
        }

        if (run.isItalic()) {
            styles.append("font-style: italic; ");
        }

        if (run.isStrike()) {
            styles.append("text-decoration: line-through; ");
        }

        UnderlinePatterns underline = run.getUnderline();
        if (underline != null && underline != UnderlinePatterns.NONE) {
            styles.append("text-decoration: underline; ");
            System.out.println("Underline pattern: " + underline.toString());
        }

        if (run.getColor() != null && !"000000".equals(run.getColor())) {
            styles.append("color: #").append(run.getColor()).append("; ");
        }

        if (run.getFontSize() > 0) {
            styles.append("font-size: ").append(run.getFontSize()).append("pt; ");
        }

        // Add more styles as needed

        return styles.toString();
    }




    private static void convertTableToHTML(XWPFTable table, StringBuilder htmlContent) {
        htmlContent.append("<table>");

        List<XWPFTableRow> rows = table.getRows();
        int numRows = rows.size();

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            XWPFTableRow row = rows.get(rowIndex);
            List<XWPFTableCell> cells = row.getTableCells();
            int numCells = cells.size();

            htmlContent.append("<tr>");

            for (int cellIndex = 0; cellIndex < numCells; cellIndex++) {
                XWPFTableCell cell = cells.get(cellIndex);
                int colSpan = getColSpan(cell);
                int rowSpan = getRowSpan(rowIndex, cellIndex, table);
                String alignment = getCellAlignment(cell);
                String cellStyle = getCellStyles(cell);

                htmlContent.append("<td rowspan=\"").append(rowSpan).append("\" colspan=\"").append(colSpan)
                        .append("\" style=\"text-align: ").append(alignment).append("; ").append(cellStyle).append("\">")
                        .append(convertCellContentToHTML(cell)).append("</td>");
            }

            htmlContent.append("</tr>");
        }

        htmlContent.append("</table>");
    }

    private static String getCellAlignment(XWPFTableCell cell) {
        if (cell.getParagraphs() != null && !cell.getParagraphs().isEmpty()) {
            XWPFParagraph paragraph = cell.getParagraphs().get(0);
            if (paragraph.getAlignment() != null) {
                return paragraph.getAlignment().toString().toLowerCase();
            }
        }
        return "left"; // Default to left alignment if not specified
    }
    private static String getParagraphAlignment(XWPFParagraph paragraph) {
        if (paragraph.getAlignment() != null) {
            return paragraph.getAlignment().toString().toLowerCase();
        }
        return "left"; // Default to left alignment if not specified
    }

    private static int getColSpan(XWPFTableCell cell) {
        CTTcPr cellProperties = cell.getCTTc().getTcPr();
        if (cellProperties.isSetGridSpan()) {
            return cellProperties.getGridSpan().getVal().intValue();
        }
        return 1;
    }

    private static int getRowSpan(int rowIndex, int cellIndex, XWPFTable table) {
        XWPFTableCell cell = table.getRow(rowIndex).getCell(cellIndex);
        CTTcPr cellProperties = cell.getCTTc().getTcPr();
        if (cellProperties.isSetVMerge()) {
            CTVMerge merge = cellProperties.getVMerge();
            if (merge.getVal() != STMerge.RESTART) {
                int span = 0;
                for (int r = rowIndex; r < table.getRows().size(); r++) {
                    XWPFTableCell nextCell = table.getRow(r).getCell(cellIndex);
                    CTVMerge nextMerge = nextCell.getCTTc().getTcPr().getVMerge();
                    if (nextMerge == null || nextMerge.getVal() == STMerge.RESTART) {
                        break;
                    }
                    span++;
                }
                return span;
            }
        }
        return 1;
    }

// ... (rest of your code remains unchanged)


    private static String getParagraphStyles(XWPFParagraph paragraph) {
        StringBuilder styles = new StringBuilder();

        if (paragraph.getRuns().isEmpty()) {
            return styles.toString();
        }

        XWPFRun firstRun = paragraph.getRuns().get(0);

        if (firstRun.isBold()) {
            styles.append("font-weight: bold; ");
        }

        if (firstRun.getColor() != null && !"000000".equals(firstRun.getColor())) {
            styles.append("color: #").append(firstRun.getColor()).append("; ");
        }

        return styles.toString();
    }


    /*private static void convertTableToHTML(XWPFTable table, StringBuilder htmlContent) {
        htmlContent.append("<table>");
        for (XWPFTableRow row : table.getRows()) {
            htmlContent.append("<tr>");
            for (XWPFTableCell cell : row.getTableCells()) {
                String alignment = cell.getParagraphs().get(0).getAlignment().toString().toLowerCase();
                String cellStyle = getCellStyles(cell);
                htmlContent.append("<td style=\"text-align: ").append(alignment).append("; ").append(cellStyle).append("\">")
                        .append(convertCellContentToHTML(cell)).append("</td>");
            }
            htmlContent.append("</tr>");
        }
        htmlContent.append("</table>");
    }*/

    private static String getCellStyles(XWPFTableCell cell) {
        StringBuilder styles = new StringBuilder();

        // Check for cell background color
        if (cell.getColor() != null && !"000000".equals(cell.getColor())) {
            styles.append("background-color: #").append(cell.getColor()).append("; ");
        }

        // Check for other cell styles and add them here

        return styles.toString();
    }

    /*private static String convertCellContentToHTML(XWPFTableCell cell) {
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
    }*/

    private static void writeHTMLToFile(String htmlContent, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            writer.write(htmlContent);
        }
    }
}

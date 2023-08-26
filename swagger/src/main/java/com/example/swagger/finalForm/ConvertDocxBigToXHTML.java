package com.example.swagger.finalForm;

import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ConvertDocxBigToXHTML {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {
            // 1) Load docx from the local file system
            File inputFile = new File("C:\\Users\\Balakrishnan\\Documents\\test\\input.docx");
            FileInputStream inputStream = new FileInputStream(inputFile);
            XWPFDocument document = new XWPFDocument(inputStream);

            // 2) Convert POI XWPFDocument to XHTML
            File outFile = new File("C:\\Users\\Balakrishnan\\Documents\\test\\output.html");
            outFile.getParentFile().mkdirs();

            OutputStream out = new FileOutputStream(outFile);

            // Use custom CSS within the HTML output
            /*String customCss = "<style type=\"text/css\">\n" +
                    "table {border-collapse: collapse; width: 100%;}\n" +
                    "table, th, td {border: 1px solid black;}\n" +
                    "p {text-align: justify;}\n" + // Set paragraph alignment to justify
                    "h1, h2, h3, h4, h5, h6 {text-align: center;}\n" + // Center align headings
                    "</style>\n";*/
            StringBuilder customCss = new StringBuilder("<style type=\"text/css\">\n");
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String alignment = paragraph.getAlignment().toString().toLowerCase();
                customCss.append("p.align-").append(alignment).append(" {text-align: ").append(alignment).append(";}\n");

            }
            customCss.append("table {border-collapse: collapse; width: 100%;}\n");
            customCss.append("table, th, td {border: 1px solid black;}\n");
            customCss.append("</style>\n");
            customCss.append("</style>\n");
            // Write the custom CSS to the output stream before the XHTML content
            out.write(customCss.toString().getBytes(StandardCharsets.UTF_8));


            XHTMLConverter.getInstance().convert(document, out, null);
            out.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        System.out.println("Generate DocxBig.htm with " + (System.currentTimeMillis() - startTime) + " ms.");
    }
}

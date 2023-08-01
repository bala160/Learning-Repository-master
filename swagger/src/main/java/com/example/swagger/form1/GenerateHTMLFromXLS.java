package com.example.swagger.form1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenerateHTMLFromXLS {

    public static void main(String[] args) {
        String inputXLSFile = "C:\\Users\\Balakrishnan\\Documents\\Learning-Repository-master\\Learning-Repository-master\\swagger\\src\\main\\java\\com\\example\\swagger\\form1\\data.xlsx";
        String outputHTMLFile = "C:\\Users\\Balakrishnan\\Documents\\Learning-Repository-master\\Learning-Repository-master\\swagger\\src\\main\\java\\com\\example\\swagger\\form1\\output.txt";

        try {
            String generatedHTML = generateHTMLFromXLS(inputXLSFile);
            writeHTMLToFile(outputHTMLFile, generatedHTML);
            System.out.println("HTML page generated and saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateHTMLFromXLS(String xlsFilePath) throws IOException {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>\n<head>\n</head>\n<body>\n<table>\n<tr><th>Name</th><th>Age</th></tr>\n");

        FileInputStream fileInputStream = new FileInputStream(xlsFilePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell nameCell = row.getCell(1);
            Cell ageCell = row.getCell(2);
            //Cell address = row.getCell(3);

            if (nameCell != null && ageCell != null) {
                String name = nameCell.getStringCellValue();
                double age = ageCell.getNumericCellValue();
                htmlBuilder.append("<tr><td>").append(name).append("</td><td>").append(age).append("</td></tr>\n");
            }
        }

        workbook.close();
        fileInputStream.close();

        htmlBuilder.append("</table>\n</body>\n</html>");
        return htmlBuilder.toString();
    }

    public static void writeHTMLToFile(String outputHTMLFile, String generatedHTML) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputHTMLFile)) {
            fos.write(generatedHTML.getBytes());
        }
    }
}

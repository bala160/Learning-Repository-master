package com.example.swagger.finalForm;

import java.io.IOException;

public class DocxToHtmlConverterWithPandoc {

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\Balakrishnan\\Documents\\test\\input.docx";
        String outputFilePath = "C:\\Users\\Balakrishnan\\Documents\\test\\output.html";

        try {
            // Convert DOCX to HTML using Pandoc
            convertDocxToHTMLWithPandoc(inputFilePath, outputFilePath);
            System.out.println("Conversion completed successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void convertDocxToHTMLWithPandoc(String inputFilePath, String outputFilePath) throws IOException {
        // Run the Pandoc command to convert DOCX to HTML
        String pandoc = "C:\\Users\\Balakrishnan\\Downloads\\pandoc-3.1.6.1-windows-x86_64\\pandoc-3.1.6.1\\pandoc.exe";
        Process process = new ProcessBuilder(pandoc, inputFilePath, "-s", "-o", outputFilePath).start();

        // Wait for the conversion process to complete
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Pandoc conversion process exited with non-zero status: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Pandoc conversion process was interrupted.", e);
        }
    }
}

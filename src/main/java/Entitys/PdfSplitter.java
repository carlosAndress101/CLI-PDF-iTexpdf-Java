package Entitys;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PdfSplitter {
    public static void splitPdf(String inputPdfPath, String outputFolder) {
        try {
            // Check if the output folder exists, if not, create it
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Open the input PDF file
            PdfReader pdfReader = new PdfReader(inputPdfPath);

            // Get the total number of pages in the PDF
            int totalPages = pdfReader.getNumberOfPages();
            System.out.println("Total number of pages in the PDF: " + totalPages);

            // Iterate through all pages of the PDF
            for (int pageNumber = 1; pageNumber <= totalPages; pageNumber++) {
                // Create a new PDF document with a single page
                Document document = new Document(PageSize.A4);
                String outputPdfPath = outputFolder + "/page_" + pageNumber + ".pdf";
                FileOutputStream outputStream = new FileOutputStream(outputPdfPath);
                PdfCopy pdfCopy = new PdfCopy(document, outputStream);
                document.open();

                // Import the current page from the input PDF
                PdfImportedPage importedPage = pdfCopy.getImportedPage(pdfReader, pageNumber);
                pdfCopy.addPage(importedPage);

                // Close the new PDF document
                document.close();
                outputStream.close();
            }

            // Create a formatted message
            String message = "PDF pages divided successfully into individual files\n" +
                    "Total pages in the PDF: " + totalPages + "\n" +
                    "Output folder: " + outputFolder;

            // Print the message with proper formatting
            System.out.println("\n" + "------------------------------------------------------");
            System.out.println(message);
            System.out.println("------------------------------------------------------\n");

        } catch (IOException | BadPdfFormatException e) {
            String errorMessage = "Error splitting the PDF: " + e.getMessage();
            System.out.println("\n" + "======================================================");
            System.out.println(errorMessage);
            System.out.println("======================================================\n");
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}


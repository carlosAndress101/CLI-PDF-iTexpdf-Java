import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import Entitys.PdfTextExtractor;
import Entitys.extractImagesPdf;
import Entitys.PdfMetadataExtractor;
import Entitys.PdfSplitter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Select an option:");
            System.out.println("1. Extract text from a PDF file and save it to a Word file");
            System.out.println("2. Extract image from PDF file");
            System.out.println("3. Extracting metadata from a PDF file");
            System.out.println("4. Split a PDF");
            System.out.println("5. Merge PDFS in a folder");
            System.out.println("6. Quit");
            System.out.print("Option: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Example of a route -> src/main/java/pdfs/test_file.pdf \n");
                    System.out.print("Enter the PDF file path: ");
                    String pdfFilePath = scanner.nextLine();

                    File archivo = new File(pdfFilePath);
                    try {
                        String textoExtraido = PdfTextExtractor.extraerTextoDePDF(archivo.getAbsolutePath());
                        System.out.println("Text extracted from PDF:");
                        System.out.println(textoExtraido);

                        // Guardar el texto extraído en un archivo Word en la misma ubicación
                        String docxFilePath = archivo.getParentFile().getPath() + File.separator + "TextExtracted.docx";
                        PdfTextExtractor.crearDocumentoWord(docxFilePath, textoExtraido);
                        System.out.println("Extracted text saved in a Word file in the same location.");
                    } catch (IOException e) {
                        System.err.println("Error processing PDF file: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Example of a route -> src/main/java/pdfs/test_file.pdf \n");
                    System.out.println("Enter the PDF file path:");
                    String pdfPath = scanner.nextLine();
                    System.out.print("Example of a route -> src/main/java/pdfs/ \n");
                    System.out.println("Enter the destination folder to save the images:");
                    String outputPath = scanner.nextLine();
                    File image = new File(pdfPath);
                    extractImagesPdf.extractImagesFromPDF(image.getAbsolutePath(), outputPath);
                    break;
                case 3:
                    try {
                        System.out.print("Example of a route -> src/main/java/pdfs/test_file.pdf \n");
                        System.out.println("Enter the PDF file path:");
                        String pdfFilePathMeta = scanner.nextLine();
                        File meta = new File(pdfFilePathMeta);
                        PdfMetadataExtractor.extractMetadata(meta.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.print("Example of a route -> src/main/java/pdfs/test_file.pdf \n");
                    System.out.println("Enter the PDF file path:");
                    String pdfPathsp = scanner.nextLine();
                    System.out.print("Example of a route -> src/main/java/pdfs/splits \n");
                    System.out.println("Enter the destination folder to save to:");
                    String outputPathsp = scanner.nextLine();
                    File splitter = new File(pdfPathsp);
                    File splitterOut = new File(outputPathsp);
                    PdfSplitter.splitPdf(splitter.getAbsolutePath(), splitterOut.getAbsolutePath());
                    break;
                case 5:
                    System.out.print("Example of a route -> src/main/java/pdfs/splits \n");
                    System.out.println("Enter the path to the folder containing the PDF files to be merged:");
                    String folderPath = scanner.nextLine();

                    File folder = new File(folderPath);

                    if (!folder.exists() || !folder.isDirectory()) {
                        System.out.println("The specified path is not a valid folder.");
                        return;
                    }

                    File[] pdfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

                    if (pdfFiles == null || pdfFiles.length == 0) {
                        System.out.println("The folder does not contain PDF files.");
                        return;
                    }

                    String outputFilePath = "merged.pdf"; // Nombre del archivo PDF fusionado

                    try {
                        Document document = new Document();
                        PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputFilePath));
                        document.open();

                        for (File pdfFile : pdfFiles) {
                            PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());
                            copy.addDocument(reader);
                            reader.close();
                        }

                        document.close();
                        System.out.println("The PDF files have been successfully merged in " + outputFilePath);
                    } catch (IOException | DocumentException e) {
                        e.printStackTrace();
                    }
                case 6:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        } while (opcion != 6);

        scanner.close();
    }
}



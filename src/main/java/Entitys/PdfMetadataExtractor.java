package Entitys;

import com.itextpdf.text.pdf.PdfReader;
import java.io.IOException;

public class PdfMetadataExtractor {
    public static void extractMetadata(String pdfFilePath) throws IOException {
        PdfReader reader = new PdfReader(pdfFilePath);

        // Extraer metadatos del documento
        String title = reader.getInfo().get("Title");
        String author = reader.getInfo().get("Author");
        String subject = reader.getInfo().get("Subject");
        String keywords = reader.getInfo().get("Keywords");
        String creator = reader.getInfo().get("Creator");
        String producer = reader.getInfo().get("Producer");

        System.out.println("Título: " + title);
        System.out.println("Autor: " + author);
        System.out.println("Asunto: " + subject);
        System.out.println("Palabras clave: " + keywords);
        System.out.println("Creador: " + creator);
        System.out.println("Productor: " + producer);

        reader.close();
    }
}

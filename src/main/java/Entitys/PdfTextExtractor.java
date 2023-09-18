package Entitys;

import com.itextpdf.text.pdf.PdfReader;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;

public class PdfTextExtractor {

    public static String extraerTextoDePDF(String pdfFilePath) throws IOException {
        PdfReader reader = new PdfReader(pdfFilePath);
        StringBuilder texto = new StringBuilder();

        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            String pageText = com.itextpdf.text.pdf.parser.PdfTextExtractor.getTextFromPage(reader, i);
            texto.append(pageText);
        }

        reader.close();

        return texto.toString();
    }

    public static void crearDocumentoWord(String docxFilePath, String texto) throws IOException {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(texto);

        try (FileOutputStream fos = new FileOutputStream(docxFilePath)) {
            document.write(fos);
        }
    }
}


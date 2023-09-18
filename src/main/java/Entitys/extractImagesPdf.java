package Entitys;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;

import java.io.FileOutputStream;
import java.io.IOException;


public class extractImagesPdf {

    public static void extractImagesFromPDF(String pdfPath, String outputPath) {

        try {
            PdfReader reader = new PdfReader(pdfPath);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            for (int pageNum = 1; pageNum <= reader.getNumberOfPages(); pageNum++) {
                ImageRenderListener listener = new ImageRenderListener();
                parser.processContent(pageNum, listener);

                for (PdfImageObject image : listener.getImages()) {
                    byte[] imageData = image.getImageAsBytes();

                    // Generar un nombre único para el archivo de imagen
                    String imageName = "image_" + pageNum + "_" + System.currentTimeMillis() + ".png";
                    String imagePath = outputPath + "/" + imageName;

                    // Guardar la imagen en la ubicación especificada
                    try (FileOutputStream fos = new FileOutputStream(imagePath)) {
                        fos.write(imageData);
                        System.out.println("Imagen guardada en: " + imagePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ImageRenderListener implements RenderListener {
        private final java.util.List<PdfImageObject> images = new java.util.ArrayList<>();

        @Override
        public void beginTextBlock() {
        }

        @Override
        public void renderText(TextRenderInfo renderInfo) {
        }

        @Override
        public void endTextBlock() {
        }


        @Override
        public void renderImage(ImageRenderInfo renderInfo) {
            try {
                PdfImageObject image = renderInfo.getImage();
                if (image != null) {
                    images.add(image);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public java.util.List<PdfImageObject> getImages() {
            return images;
        }
    }
}


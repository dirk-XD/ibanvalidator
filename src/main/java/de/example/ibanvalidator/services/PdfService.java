package de.example.ibanvalidator.services;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A service for extracting text content from PDF files.
 */
@Service
public class PdfService {

    /**
     * Extracts text content from a PDF file accessible via the specified URL.
     *
     * @param url the URL pointing to the PDF file
     * @return the extracted text content of the PDF file
     * @throws IOException if an error occurs while reading the stream or extracting text
     */
    public String getTextFromPdf(URL url) throws IOException {

        String text;
        try (InputStream inputStream = url.openStream()) {
            RandomAccessReadBuffer randomAccessReadBuffer = new RandomAccessReadBuffer(inputStream);
            PDDocument document = Loader.loadPDF(randomAccessReadBuffer);
            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(document);
            document.close();
        }
        return text;
    }

    /**
     * Extracts text content from a PDF file located at the specified file path in the project's resources directory.
     * Mainly for test purposes.
     *
     * @param localFilePath the file path of the PDF file within the application's classpath
     * @return the extracted text content of the PDF file
     * @throws IOException if an error occurs while reading the file or extracting text
     */
    public String getTextFromPdf(String localFilePath) throws IOException {

        ClassPathResource resource = new ClassPathResource(localFilePath);
        File file = resource.getFile();
        PDDocument document = Loader.loadPDF(file);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }
}

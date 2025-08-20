package de.example.ibanvalidator.services;

import de.example.ibanvalidator.common.Constants;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class PdfServiceTest {

    private final PdfService pdfService = new PdfService();


    @Test
    void testGetTextFromPdfWithUrl() throws IOException {

        URL url = Constants.REMOTE_TEST_FILE_URL;
        String extractedText = pdfService.getTextFromPdf(url);
        assertTrue(extractedText.contains(Constants.REMOTE_TEST_FILE_TEXT));
    }

    @Test
    void testGetTextFromPdfWithInvalidUrl() throws MalformedURLException {

        URL invalidUrl = URI.create("http://example.com/myfile").toURL();
        assertThrows(IOException.class, () -> pdfService.getTextFromPdf(invalidUrl));
    }

    @Test
    void testGetTextFromPdfWithInvalidLocalFilePath() {

        String invalidFilePath = "invalid.pdf";
        assertThrows(IOException.class, () -> pdfService.getTextFromPdf(invalidFilePath));
    }
}
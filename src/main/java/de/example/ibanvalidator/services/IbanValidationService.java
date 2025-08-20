package de.example.ibanvalidator.services;

import de.example.ibanvalidator.common.Constants;
import de.example.ibanvalidator.errorhandling.IbanValidationException;
import de.example.ibanvalidator.validation.IbanValidationError;
import de.example.ibanvalidator.validation.IbanValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * Service class for validating International Bank Account Numbers (IBANs).
 * Validators must implement the {@link IbanValidator} interface.
 */
@Service
public class IbanValidationService {

    private final PdfService pdfService;

    public IbanValidationService(PdfService pdfService) {

        this.pdfService = pdfService;
    }

    /**
     * Validates all IBANs found in a PDF file accessible via the provided path.
     * The method extracts text from the PDF and validates the IBANs contained in the text.
     *
     * @param localFilePath the path of the PDF file containing IBANs to be validated
     * @throws IOException if an error occurs while reading the PDF file
     * @throws IbanValidationException if any invalid IBAN was detected
     */
    public void validateIbansFromPdf(String localFilePath) throws IOException, IbanValidationException {

        String text = this.pdfService.getTextFromPdf(localFilePath);
        validateIbansFromText(text);
    }

    /**
     * Validates all IBANs found in a PDF file accessible via the provided URL.
     * The method extracts text from the PDF and validates the IBANs contained in the text.
     *
     * @param url the URL of the PDF file containing IBANs to be validated
     * @throws IOException if an error occurs while reading the PDF file
     * @throws IbanValidationException if any invalid IBAN was detected
     */
    public void validateIbansFromPdf(URL url) throws IOException, IbanValidationException {

        String text = this.pdfService.getTextFromPdf(url);
        validateIbansFromText(text);
    }

    /**
     * Validates all International Bank Account Numbers (IBANs) found in the provided text.
     * The method extracts IBANs from the input text, applies all available validators,
     * and throws an exception with all collected violations if any IBAN is found invalid.
     *
     * @param text the input text containing IBANs to be validated
     * @throws IbanValidationException if one or more IBANs are invalid
     */
    public void validateIbansFromText(String text) throws IbanValidationException {

        Set<String> ibans = extractIbansFromText(text);

        List<IbanValidationError> validationErrors =
                IbanValidator.getAllValidators().parallelStream()
                .flatMap(validator ->
                        ibans.stream()
                                .filter(iban -> !validator.isValid(iban))
                                .map(invalidIban -> new IbanValidationError(validator.getViolationMessage(), invalidIban))
                )
                .toList();

        if(!validationErrors.isEmpty()) {
            throw new IbanValidationException(Constants.IBAN_ERROR_MESSAGE, validationErrors);
        }
    }

    /**
     * Extracts a list of International Bank Account Numbers (IBANs) from the provided text.
     * It identifies IBANs based on a predefined pattern, cleans any whitespace, and returns
     * the sanitized list of IBANs.
     *
     * @param text the input text from which IBANs are to be extracted
     * @return a set of cleaned IBAN strings extracted from the input text
     */
    public Set<String> extractIbansFromText(String text) {

        Set<String> ibans = new HashSet<>();
        Matcher ibanMatcher = Constants.IBAN_PATTERN.matcher(text);

        while (ibanMatcher.find()) {

            // grabs the text matching the regEx part in the first parenthesis
            String rawIban = ibanMatcher.group(1);
            // remove all kinds of whitespace characters
            String cleanedIban = rawIban.replaceAll("\\s+", "");
            ibans.add(cleanedIban);
        }
        return ibans;
    }
}

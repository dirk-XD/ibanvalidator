package de.example.ibanvalidator.controllers;

import de.example.ibanvalidator.services.IbanValidationService;
import de.example.ibanvalidator.validation.IbanValidationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/ibanvalidation")
public class IbanValidationController {

    private final IbanValidationService ibanValidationService;

    public IbanValidationController(IbanValidationService ibanValidationService) {
        this.ibanValidationService = ibanValidationService;
    }

    /**
     * Validates IBANs extracted from the input string.
     * The validation executes all necessary checks and processes errors using the application's global exception handler.
     *
     * @return if no problems (or no IBAN) found, a ResponseEntity containing an empty {@link IbanValidationResponseDto} and HTTP 200
     * @throws IOException if an error occurs while reading the PDF file
     */
    @GetMapping("/validate-string")
    public ResponseEntity<IbanValidationResponseDto> validateIbansFromString(@RequestParam String input) throws IOException {

        if (!input.startsWith("IBAN")) {
            input = "IBAN" + input;
        }
        this.ibanValidationService.validateIbansFromText(input);
        return new ResponseEntity<>(IbanValidationResponseDto.EMPTY_RESPONSE, HttpStatus.OK);
    }

    /**
     * Could be a POST if no caching wanted.
     * Validates IBANs extracted from a remote PDF file from the url.
     * The validation executes all necessary checks and processes errors using the application's global exception handler.
     *
     * @param url the URL of the PDF file from which IBANs should be extracted and validated
     * @return if no problems (or no IBAN) found, a ResponseEntity containing an empty {@link IbanValidationResponseDto} and HTTP 200
     * @throws IOException if an error occurs while accessing or reading the PDF file
     */
    @GetMapping("/validate-remote")
    public ResponseEntity<IbanValidationResponseDto> validateIbansFromPdfByUrl(@RequestParam String url) throws IOException {

        this.ibanValidationService.validateIbansFromPdf(URI.create(url).toURL());
        return new ResponseEntity<>(IbanValidationResponseDto.EMPTY_RESPONSE, HttpStatus.OK);
    }
}

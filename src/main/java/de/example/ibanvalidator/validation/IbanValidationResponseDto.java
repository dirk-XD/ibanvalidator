package de.example.ibanvalidator.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The response DTO for IBAN validation. If both the processingError and the validationErrors list are null,
 * all IBANs in the document(s) were valid or no IBAN was found.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IbanValidationResponseDto {

    public static final IbanValidationResponseDto EMPTY_RESPONSE = new IbanValidationResponseDto();

    /**
     * A message describing a processing error like network- or scanning issues
     */
    private String processingError;

    /**
     * A list of validation errors that occurred during the IBAN validation process.
     * Each entry in the list represents a specific validation error encountered for an IBAN.
     * The same IBAN could appear multiple times.
     */
    private List<IbanValidationError> validationErrors;
}

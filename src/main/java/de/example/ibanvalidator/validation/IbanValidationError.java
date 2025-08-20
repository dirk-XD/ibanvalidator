package de.example.ibanvalidator.validation;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Error class to identify a single IBAN validation failure
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IbanValidationError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * A detailed error message for one specific validation failure.
     */
    private String errorMessage;

    /**
     * The International Bank Account Number (IBAN)
     */
    private String iban;
}

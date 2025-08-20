package de.example.ibanvalidator.errorhandling;

import de.example.ibanvalidator.validation.IbanValidationError;
import lombok.Getter;

import java.io.Serial;
import java.util.List;

@Getter
public class IbanValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<IbanValidationError> ibanValidationErrors;


    public IbanValidationException(String message, List<IbanValidationError> ibanValidationErrors) {
        super(message);

        if (ibanValidationErrors == null) {
            throw new IllegalArgumentException("ibanValidationErrors cannot be null");
        }

        this.ibanValidationErrors = ibanValidationErrors;
    }
}

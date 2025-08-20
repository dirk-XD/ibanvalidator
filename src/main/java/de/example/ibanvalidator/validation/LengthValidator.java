package de.example.ibanvalidator.validation;

import de.example.ibanvalidator.common.Constants;

/**
 * Simple additional validator to show a "multiple validators" approach.
 * Validator that checks if the given IBAN's length conforms to the expected length.
 * Implements the IbanValidator interface.
 * For a real application, a map for the IBAN's length per countryCode should be used.
 */
public class LengthValidator implements IbanValidator {

    @Override
    public boolean isValid(String iban) {

        return iban != null
                && (iban.startsWith("DE")
                    ? iban.length() == Constants.IBAN_LENGTH_DE
                    : iban.length() <= Constants.IBAN_MAX_LENGTH);
    }

    @Override
    public String getViolationMessage() {

        return Constants.LENGTH_ERROR_MESSAGE;
    }
}

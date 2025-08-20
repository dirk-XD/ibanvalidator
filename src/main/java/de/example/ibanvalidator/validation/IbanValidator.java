package de.example.ibanvalidator.validation;

import java.util.List;

/**
 * Interface representing a validator for International Bank Account Numbers (IBANs).
 */
public interface IbanValidator {

    /**
     * Validates whether the provided IBAN (International Bank Account Number) is valid,
     * according to the implemented validation logic.
     *
     * @param iban the IBAN to be validated; must not be null
     * @return true if the provided IBAN is valid; false otherwise
     */
    boolean isValid(String iban);

    /**
     * A validation specific violation description
     *
     * @return a message describing the validation failure
     */
    String getViolationMessage();

    /**
     * Put all implementing classes here to let them all validate automatically.
     * Another way of more automatism would be a bean scanner, which grabs a list of all classes in one package, for example.
     * @return The list of IbanValidator's implementations
     */
    static List<IbanValidator> getAllValidators() {

        return List.of(new LengthValidator(), new BlacklistValidator());
    }
}

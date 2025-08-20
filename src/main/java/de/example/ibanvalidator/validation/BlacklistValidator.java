package de.example.ibanvalidator.validation;

import de.example.ibanvalidator.common.BlackList;
import de.example.ibanvalidator.common.Constants;

/**
 * Validator that checks whether a given IBAN (International Bank Account Number) is blacklisted.
 * Implements the IbanValidator interface.
 */
public class BlacklistValidator implements IbanValidator {

    @Override
    public boolean isValid(String iban) {
        // in a real service, the blacklisted IBANs would certainly come from a database or microservice
        return iban != null && !BlackList.BLACK_LIST.contains(iban);
    }

    @Override
    public String getViolationMessage() {

        return Constants.BLACKLIST_ERROR_MESSAGE;
    }
}

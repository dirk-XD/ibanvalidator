package de.example.ibanvalidator.services;

import de.example.ibanvalidator.common.BlackList;
import de.example.ibanvalidator.common.Constants;
import de.example.ibanvalidator.errorhandling.IbanValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IbanValidationServiceTest {

    private final IbanValidationService ibanValidationService = new IbanValidationService(new PdfService());

    @Test
    void testExtractIbansFromTextWithValidIbans() {

        String iban = "DE60 0123 4567 8901 8765 12";
        String text = "1234ab jh kl IBAN " + iban + "  ";

        Assertions.assertEquals(iban.replaceAll("\\s+", ""), ibanValidationService.extractIbansFromText(text).stream().findFirst().orElseThrow());
    }

    @Test
    void testExtractIbansFromPdfWithInvalidIban() {

        String text = "1234ab jh kl IBAN DE60 0123   ";

        Assertions.assertTrue(ibanValidationService.extractIbansFromText(text).isEmpty());
    }

    @Test
    void testValidateIbansFromTextWithShortIban() {

        String text = "1234ab jh kl IBAN DE60 0123 4444 4444";

        IbanValidationException validationException =
                assertThrows(IbanValidationException.class, () -> ibanValidationService.validateIbansFromText(text));

        Assertions.assertEquals(1, validationException.getIbanValidationErrors().size());
        Assertions.assertEquals(Constants.IBAN_ERROR_MESSAGE, validationException.getMessage());

        var ibanValidationError = validationException.getIbanValidationErrors().getFirst();
        Assertions.assertEquals("DE60012344444444", ibanValidationError.getIban());
        Assertions.assertEquals(Constants.LENGTH_ERROR_MESSAGE, ibanValidationError.getErrorMessage());
    }

    @Test
    void testValidateIbansFromTextWithBlacklistedIban() {

        String text = "ihiub87d IBAN IBAN " + BlackList.BLACKLISTED1;

        IbanValidationException validationException =
                assertThrows(IbanValidationException.class, () -> ibanValidationService.validateIbansFromText(text));

        Assertions.assertEquals(1, validationException.getIbanValidationErrors().size());
        Assertions.assertEquals(Constants.IBAN_ERROR_MESSAGE, validationException.getMessage());

        var ibanValidationError = validationException.getIbanValidationErrors().getFirst();
        Assertions.assertEquals(BlackList.BLACKLISTED1.replaceAll("\\s+", ""), ibanValidationError.getIban());
        Assertions.assertEquals(Constants.BLACKLIST_ERROR_MESSAGE, ibanValidationError.getErrorMessage());
    }
}

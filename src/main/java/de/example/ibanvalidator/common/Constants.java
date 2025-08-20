package de.example.ibanvalidator.common;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Collection of all constant application values and objects. Could be put in several classes for different topics.
 */
@Slf4j
public final class Constants {

    private Constants() {
        throw new AssertionError("Utility class");
    }

    // online dummy pdf for testing
    public static final String REMOTE_TEST_FILE_URL_STRING = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";

    public static final URL REMOTE_TEST_FILE_URL;
    static {
        URL testFileUrl;
        try {
            testFileUrl = URI.create(REMOTE_TEST_FILE_URL_STRING).toURL();
        } catch (MalformedURLException e) {
            log.error("Error while creating remote test file URL", e);
            testFileUrl = null;
        }
        REMOTE_TEST_FILE_URL = testFileUrl;
    }

    public static final String REMOTE_TEST_FILE_TEXT = "Dummy PDF file";

    public static final String STANDARD_IBAN_REGEX = "^[A-Z]{2}[0-9]{2}[0-9A-Za-z]{1,30}$";

    // Matches better our test file's IBAN entries.
    // IBAN followed by optional colon, followed by any number of spaces, only one or none space in the BBAN part
    private static final String IBAN_REGEX = "(?i)\\bIBAN:?\\s*([A-Z]{2}\\s?\\d{2}(?:\\s?[0-9A-Z]){11,30})";

    public static final Pattern IBAN_PATTERN = Pattern.compile(IBAN_REGEX);

    /** IBAN LENGTH IN GERMANY */
    public static final int IBAN_LENGTH_DE = 22;

    /** GENERAL MAX IBAN LENGTH */
    public static final int IBAN_MAX_LENGTH = 34;

    public static final String IBAN_ERROR_MESSAGE = "Invalid IBANs detected in the document(s).";
    public static final String LENGTH_ERROR_MESSAGE = "The IBAN length is invalid.";
    public static final String BLACKLIST_ERROR_MESSAGE = "The IBAN is blacklisted.";
}

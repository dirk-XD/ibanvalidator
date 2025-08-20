package de.example.ibanvalidator.common;

import java.util.Set;

/**
 * A test blacklist with invented IBANs. Replace with IBANs provided by a database or microservice.
 */
public final class BlackList {

    private BlackList() {
        throw new AssertionError("Utility class");
    }

    public static final String BLACKLISTED1 = "DE99 1234 1234 1234 1234 12";
    public static final String BLACKLISTED2 = "DE66 1234 1234 1234 1234 12";

    public static final Set<String> BLACK_LIST = Set.of(BLACKLISTED1.replaceAll("\\s+", ""), BLACKLISTED2.replaceAll("\\s+", ""));
}

package com.gscien.testing.Gescience_testing;

import net.datafaker.Faker;
import org.springframework.stereotype.Component;

/**
 * Utility component for generating standardized identification strings.
 * This class is managed by the Spring container and provides methods to
 * transform personal names into corporate-compliant identifiers.
 */
@Component
public class GenerateIds {

    private final Faker faker = new Faker();

    /**
     * Generates a corporate-standard CDSID (Core Directory Service ID) based on a person's name.
     * <p>
     * The generation rules are:
     * <ul>
     * <li>Normalizes input by removing non-alphabetic characters and converting to lowercase.</li>
     * <li>Takes up to the first 3 characters of the Last Name.</li>
     * <li>Takes up to the first 2 characters of the First Name.</li>
     * <li>Pads the remaining length with random digits to reach exactly 8 characters.</li>
     * </ul>
     * * Example: {@code generateCdsid("Gerrit", "Gerrits")} might return {@code "gerge123"}.
     *
     * @param firstName the person's first name (e.g., "Gerrit")
     * @param lastName  the person's last name (e.g., "Gerrits")
     * @return a unique, 8-character string representing the user's corporate ID.
     * @throws IllegalArgumentException if the resulting prefix is empty (rare edge case).
     */
    public String cdsid(String firstName, String lastName) {
        // 1. Clean and normalize inputs
        String first = firstName.replaceAll("[^a-zA-Z]", "").toLowerCase();
        String last = lastName.replaceAll("[^a-zA-Z]", "").toLowerCase();

        // 2. Take up to 3 from last name and 2 from first name
        String partLast = last.substring(0, Math.min(last.length(), 6));
        String partFirst = first.substring(0, Math.min(first.length(), 1));

        String prefix = partFirst + partLast;

        // 3. Use Faker to fill the remaining length with digits
        int remaining = 8 - prefix.length();
        return prefix + faker.number().digits(remaining);
    }

}

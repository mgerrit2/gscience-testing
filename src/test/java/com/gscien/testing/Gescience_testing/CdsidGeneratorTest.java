package com.gscien.testing.Gescience_testing;

import net.datafaker.Faker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CdsidGeneratorTest {

    private final Faker faker = new Faker();

    @Autowired
    private GenerateIds generateIds;


    /**
     * Test the CDSID generation logic with various name combinations.
     */
    @ParameterizedTest(name = "Names [{0}, {1}] should produce 8-char ID starting with {2}")
    @MethodSource("provideNamingScenarios")
    void shouldGenerateValidCdsid(String firstName, String lastName, String expectedPrefix) {
        // Act
        String result = generateIds.cdsid(firstName, lastName);

        // Assert
        assertThat(result)
                .hasSize(8)                             // Must be exactly 8 characters
                .containsPattern("^[a-z0-9]+$")        // Only lowercase letters and numbers
                .startsWith(expectedPrefix);            // Matches the name logic
    }

    private static Stream<Arguments> provideNamingScenarios() {
        return Stream.of(
                // Normal case: 3 from last, 2 from first
                Arguments.of("Marc", "Gerrits", "mgerrit"),
                // Short names: takes what is available
                Arguments.of("Jo", "Li", "jli"),
                // Special characters: should be stripped
                Arguments.of("Jean-Luc", "O'Neill", "joneill"),
                // Mixed case: should be lowercased
                Arguments.of("ALICE", "SMITH", "asmith")
        );
    }

}

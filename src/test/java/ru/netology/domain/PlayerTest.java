package ru.netology.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private Player player = new Player(1, "Name Surname", 5);

    @ParameterizedTest
    @CsvSource({
            "Camel case, Name Surname, true",
            "Lower case, name surname, true",
            "Only Name, Name, false",
            "Another Player, Someone Else, false",
            "Empty String,, false"
    })
    public void shouldMatchesFlights(String test, String name, boolean expected) {
        boolean actual = player.matches(name);
        assertEquals(actual, expected);
    }

    @ParameterizedTest
    @CsvSource({
            "Lower Strength, 4, 1",
            "Higher Strength, 6, -1",
            "Equal Strength, 5, 0"
    })
    public void shouldCompareTo(String testName, int strength, int expected) {
        Player testPlayer = new Player(0, "Test Player", strength);
        int actual = player.compareTo(testPlayer);
        assertEquals(actual, expected);
    }
}
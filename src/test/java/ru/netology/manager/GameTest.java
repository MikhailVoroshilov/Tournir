package ru.netology.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.domain.Player;
import ru.netology.exception.AlreadyRegisteredException;
import ru.netology.exception.NotRegisteredException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Player player1 = new Player(1, "Player 1", 1);
    private Player player2 = new Player(2, "Player 2", 2);
    private Player player3 = new Player(3, "Player 3", 3);
    private Player newPlayer = new Player(4, "Player 4", 2);
    private Player newPlayerWithSameId = new Player(1, "Player 5", 1);
    private Collection<Player> players = new ArrayList<>(List.of(player1, player2, player3));
    private Game game = new Game(players);

    @Test
    void getAll() {
        assertTrue(game.getAll().containsAll(players));
    }

    @Test
    void shouldFindByName() {
        Player expected = player1;
        Player actual = game.findBy("player 1");
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByName() {
        assertNull(game.findBy("player"));
    }

    @Test
    void shouldFindById() {
        Player expected = player1;
        Player actual = game.findBy(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindById() {
        assertNull(game.findBy(5));
    }

    @Test
    void shouldRegister() {
        game.register(newPlayer);
        assertTrue(game.getAll().contains(newPlayer));
    }

    @Test
    void shouldTrowAlreadyRegisteredException() {
        Assertions.assertThrows(AlreadyRegisteredException.class, () -> game.register(player1));
    }

    @Test
    void shouldTrowAlreadyRegisteredExceptionWithSameId() {
        Assertions.assertThrows(AlreadyRegisteredException.class, () -> game.register(newPlayerWithSameId));
    }

    @Test
    void shouldTrowNotRegisteredExceptionIfFirstPlayerIsNotRegistered() {
        game.removeAll(List.of(player1));
        Assertions.assertThrows(NotRegisteredException.class, () -> game.round("Player 1", "Player 2"));
    }

    @Test
    void shouldTrowNotRegisteredExceptionIfSecondPlayerIsNotRegistered() {
        game.removeAll(List.of(player2));
        Assertions.assertThrows(NotRegisteredException.class, () -> game.round("Player 1", "Player 2"));
    }

    @Test
    void shouldTrowNotRegisteredExceptionIfBothPlayersAreNotRegistered() {
        game.removeAll(List.of(player1, player2));
        Assertions.assertThrows(NotRegisteredException.class, () -> game.round("Player 1", "Player 2"));
    }

    @ParameterizedTest
    @CsvSource({
            "First Player Wins, player 2, player 1, 1",
            "Second Player Wins, player 2, player 3, 2",
            "Draw, player 2, player 4, 0",
    })
    public void shouldRound(String testName, String playerName1, String playerName2, int expected) {
        game.register(newPlayer);
        int actual = game.round(playerName1, playerName2);
        assertEquals(expected, actual);
    }
}
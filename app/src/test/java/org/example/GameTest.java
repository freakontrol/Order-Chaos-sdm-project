package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game);
        assertNotNull(game.getGameBoard());
        assertEquals(Role.ORDER, game.getCurrentPlayer().getRole());
        assertEquals("ORDER", game.getCurrentPlayer().getName());
    }

    @Test
    public void testSwitchPlayer() {
        Player initialPlayer = game.getCurrentPlayer();
        game.switchPlayer();
        assertNotEquals(initialPlayer, game.getCurrentPlayer());
        assertEquals(Role.CHAOS, game.getCurrentPlayer().getRole());
        assertEquals("CHAOS", game.getCurrentPlayer().getName());

        game.switchPlayer();
        assertNotEquals(game.getCurrentPlayer(), initialPlayer);
        assertEquals(Role.ORDER, game.getCurrentPlayer().getRole());
        assertEquals("ORDER", game.getCurrentPlayer().getName());
    }

    @Test
    public void testIsDraw() {
        for (int i = 0; i < 36; i++) { // Fill the board with moves
            Position position = new Position(i / 6, i % 6);
            Type type = new Type("X");
            Mark mark = new Mark(position, type);
            Move move = new Move(mark, game.getCurrentPlayer());
            game.getGameBoard().addMove(move);
        }
        assertTrue(game.isDraw());
    }

    @Test
    public void testStartGame() {
        // this test will fail because startGame is not implemented yet
        game.startGame();
    }
}
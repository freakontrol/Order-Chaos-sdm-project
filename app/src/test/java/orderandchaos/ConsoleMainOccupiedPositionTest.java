package orderandchaos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleMainOccupiedPositionTest {

    private ConsoleMain game;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        game = new ConsoleMain();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testGameSimulation() {
        // Simulate input for player names and moves
        String simulatedInput = "Alice\nThomas\n0 0\nX\n0 0\nO\n1 1\nX\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Start the game
        game.initializeGame();
        game.startGame();
        game.exitGame();

        // Reset the system input and output
        System.setIn(originalIn);
        System.setOut(originalOut);

        // Verify the output
        String expectedOutput = """
                Enter name for ORDER player: Enter name for CHAOS player:
                Current Board:
                . . . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                Alice, enter your move (row column): Alice, enter your mark (X or O):
                Current Board:
                X . . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                Thomas, enter your move (row column): Position is occupied. Choose another.
                Thomas, enter your move (row column): Thomas, enter your mark (X or O):
                Current Board:
                X . . . . .
                . . X . . .
                . . . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
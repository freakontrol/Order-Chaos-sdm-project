package orderandchaos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleMainTest {

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
        // Simulate input for player names
        String simulatedInput = "Alice\nThomas\n0 0\nX\n1 1\nO\n2 2\nX\n3 3\nO\n4 4\nX\n5 5\nO\n";
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
                Thomas, enter your move (row column): Thomas, enter your mark (X or O):
                Current Board:
                X . . . . .
                . O . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                . . . . . .
                Alice, enter your move (row column): Alice, enter your mark (X or O):
                Current Board:
                X . . . . .
                . O . . . .
                . . X . . .
                . . . . . .
                . . . . . .
                . . . . . .
                Thomas, enter your move (row column): Thomas, enter your mark (X or O):
                Current Board:
                X . . . . .
                . O . . . .
                . . X . . .
                . . O . . .
                . . . . . .
                . . . . . .
                Alice, enter your move (row column): Alice, enter your mark (X or O):
                Current Board:
                X . . . . .
                . O . . . .
                . . X . . .
                . . O . . .
                . . X . . .
                . . . . . .
                Thomas, enter your move (row column): Thomas, enter your mark (X or O):
                Current Board:
                X . . . . .
                . O . . . .
                . . X . . .
                . . O . . .
                . . X . . .
                . . O . . .
                Alice, enter your move (row column): Alice, enter your mark (X or O):
                Current Board:
                X . . . . .
                . O . . . .
                . . X . . .
                . . O . . .
                . . X . . .
                . . O . . .
                Thomas wins! Game Over.
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
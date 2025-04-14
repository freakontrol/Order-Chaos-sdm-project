package orderandchaos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

import orderandchaos.ui.console.*;


class ConsoleMainTest {

    private ConsoleMain game;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        InputHandlerConsole inputHandler = new InputHandlerConsole();
        OutputHandlerConsole outputHandler = new OutputHandlerConsole(); // Initialize with null, will be set later
        game = new ConsoleMain(inputHandler, outputHandler);
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testGameSimulation() {
        // Simulate input for player names
        String simulatedInput = "Alice\nThomas\n0 0\nX\n1 1\nO\n2 2\nX\n3 3\nO\n4 4\nX\n5 5\nO\nno\n";
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
                Do you want to play a new game? (yes/no):
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testOccupiedPosition() {
        // Simulate input for player names and moves
        String simulatedInput = "Alice\nThomas\n0 0\nX\n0 0\nO\n1 1\nX\nno\n";
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
                Do you want to play a new game? (yes/no):
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testFullBoard() {
        // Simulate input for player names and moves to fill the board
        StringBuilder simulatedInput = new StringBuilder();
        simulatedInput.append("Alice\nThomas\n");

        // Generate input to fill the entire board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                simulatedInput.append(i).append(" ").append(j).append("\n");
                simulatedInput.append((i + j) % 2 == 0 ? "X\n" : "O\n");
            }
        }
        simulatedInput.append("no\n");

        System.setIn(new ByteArrayInputStream(simulatedInput.toString().getBytes()));

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
                """;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                expectedOutput += "Alice, enter your move (row column): Alice, enter your mark (X or O):\n";
                expectedOutput += "Current Board:\n";
                for (int k = 0; k < 6; k++) {
                    for (int l = 0; l < 6; l++) {
                        if (k == i && l == j) {
                            expectedOutput += "X ";
                        } else {
                            expectedOutput += (k * 6 + l < i * 6 + j) ? ((k * 6 + l) % 2 == 0 ? "X " : "O ") : ". ";
                        }
                    }
                    expectedOutput += "\n";
                }
                expectedOutput += "Thomas, enter your move (row column): Thomas, enter your mark (X or O):\n";
                expectedOutput += "Current Board:\n";
                for (int k = 0; k < 6; k++) {
                    for (int l = 0; l < 6; l++) {
                        if (k == i && l == j) {
                            expectedOutput += "O ";
                        } else {
                            expectedOutput += (k * 6 + l < i * 6 + j) ? ((k * 6 + l) % 2 == 0 ? "X " : "O ") : ". ";
                        }
                    }
                    expectedOutput += "\n";
                }
            }
        }

        expectedOutput += "Player Chaos wins! Game Over.\nDo you want to play a new game? (yes/no):";

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testDuplicatePlayerName() {
        // Simulate input for player names where Chaos tries to use the same name as Order
        String simulatedInput = "Alice\nAlice\nThomas\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        game.initializeGame();

        // Reset the system input and output
        System.setIn(originalIn);
        System.setOut(originalOut);

        // Verify the output
        String expectedOutput = """
                Enter name for ORDER player: Enter name for CHAOS player: Name is already taken. Choose another.
                Enter name for CHAOS player:
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testSwitchRoles() {
        // Simulate input for player names and switching roles
        String simulatedInput = "Alice\nThomas\n0 0\nX\n1 1\nO\nno\nyes\n";
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
                Do you want to play a new game? (yes/no): Do you want to switch roles between ORDER and CHAOS? (yes/no):
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void testInvalidInput() {
        // Simulate input for player names and invalid moves
        String simulatedInput = "Alice\nThomas\ninvalid\n0 0\nX\n1 1\nO\nno\n";
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
                Alice, enter your move (row column): Please provide exactly two values separated by space: row and column.
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
                Do you want to play a new game? (yes/no):
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
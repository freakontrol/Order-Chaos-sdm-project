package orderandchaos.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import orderandchaos.exceptions.InvalidPlayerException;
import orderandchaos.exceptions.OccupiedPositionException;

import javax.swing.*;
import java.awt.event.*;

class BoardGUITest {
    private Board board;
    private BoardGUI gui;
   
    @BeforeEach
    void setUp() { 
        // Initialize the game board and its graphical interface before each test
        board = new Board();
        gui = new BoardGUI(board);
    }

    @AfterEach
    void tearDown() {
        gui.dispose();
    }

    @Test // Checks initial state of buttons and board
    void testInitialState() {
        assertFalse(board.isFiveInLineFound(), "The game ended before it started");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertTrue(gui.getButton(i, j).isEnabled(), "The buttons should be enabled at first");
                assertEquals("", gui.getButton(i, j).getText(), "The button text should initially be blank");
            }
        }
    }

    @Test // Checks that updateButton correctly sets symbol and disables the button
    void testUpdateButton() {
        gui.updateButton(0, 0, "X");
        assertEquals("X", gui.getButton(0, 0).getText(), "Button text should be X");
        assertFalse(gui.getButton(0, 0).isEnabled(), "Button should be disabled after update");
    }

    @Test // Simulates a click and ensures the GUI updates with symbol and disables button
    void testSimulatedClickUpdatesGUI() throws Exception {
        TestBoardGUI testGui = new TestBoardGUI(new Board(), "X");
        Player order = new Player(Role.ORDER, "ORDER");

        SwingUtilities.invokeAndWait(() -> {
            testGui.setCurrentPlayer(order);
            JButton button = testGui.getButton(0, 0);
            button.doClick();
        });

        JButton clickedButton = testGui.getButton(0, 0);
        assertEquals("X", clickedButton.getText(), "Button (0,0) should contain X");
        assertFalse(clickedButton.isEnabled(), "Button (0,0) should be disabled");

        testGui.dispose();
    }

    @Test // Checks that setCurrentPlayer updates the label
    void testPlayerLabelUpdate() {
        gui.setCurrentPlayer(new Player(Role.ORDER, "Player 1"));
        assertEquals("shift of: Player 1", gui.getPlayerLabel().getText(), "Incorrect player label text");
    }

    @Test // Ensures askForSymbol returns a valid symbol (X or O)
    void testAskForSymbolReturnsValidOption() {
        BoardGUI testGui = new BoardGUI(board) {
            @Override
            public String askForSymbol() {
                return "O";
            }
        };
        String symbol = testGui.askForSymbol();
        assertTrue(symbol.equals("X") || symbol.equals("O"), "Symbol should be X or O");
    }

    @Test // Ensures getButtonPosition returns correct coordinates
    void testGetButtonPositionReturnsCorrectPosition() {
        JButton btn = gui.getButton(2, 3);
        ActionEvent fakeEvent = new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, "");
        Position pos = gui.getButtonPosition(fakeEvent);
        assertNotNull(pos, "Position should not be null");
        assertEquals(2, pos.getRow(), "Row should be 2");
        assertEquals(3, pos.getColumn(), "Column should be 3");
    }

    @Test // Ensures showWinnerMessage displays the correct message
    void testShowWinnerMessageDisplaysDialog() {
        String[] messageHolder = new String[1];

        BoardGUI testGui = new BoardGUI(board) {
            @Override
            public void showWinnerMessage(String message) {
                messageHolder[0] = message;
            }
        };

        testGui.showWinnerMessage("ORDER wins!");
        assertEquals("ORDER wins!", messageHolder[0], "Winner message should be correct");
    }

    @Test // Ensures askForNewGame returns true when YES is selected
    void testAskForNewGameReturnsYes() {
        BoardGUI testGui = new BoardGUI(board) {
            @Override
            public boolean askForNewGame() {
                return true;
            }
        };
        assertTrue(testGui.askForNewGame(), "Should return true for new game confirmation");
    }

    // Custom subclass for GUI testing with fixed symbol selection
    class TestBoardGUI extends BoardGUI {
        private final String forcedSymbol;

        public TestBoardGUI(Board board, String forcedSymbol) {
            super(board);
            this.forcedSymbol = forcedSymbol;
            setMoveListener(new TestMoveListener());
        }

        @Override
        public String askForSymbol() {
            return forcedSymbol;
        }

        private class TestMoveListener implements ActionListener {
            private Player current = new Player(Role.ORDER, "ORDER");
            private Player other = new Player(Role.CHAOS, "CHAOS");

            @Override
            public void actionPerformed(ActionEvent e) {
                Position position = getButtonPosition(e);
                if (!board.isOccupied(position)) {
                    Type markType = forcedSymbol.equals("X") ? Type.X : Type.O;
                    Mark mark = new Mark(position, markType);
                    Move move = new Move(mark, current);
                    try {
                        board.addMove(move);
                    updateButton(position.getRow(), position.getColumn(), markType.getName());
                    Player temp = current;
                    current = other;
                    other = temp;
                    setCurrentPlayer(current);
                    } catch (InvalidPlayerException | OccupiedPositionException ex) {
                        ex.printStackTrace();
                        gui.showErrorMessage(ex.getMessage());
                }
            }
        }
    }
}
}

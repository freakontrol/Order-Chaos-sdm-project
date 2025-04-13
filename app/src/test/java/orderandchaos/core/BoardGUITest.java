package orderandchaos.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import orderandchaos.exceptions.InvalidPlayerException;
import orderandchaos.exceptions.OccupiedPositionException;
import orderandchaos.exceptions.OutOfBoundsException;
import orderandchaos.testutils.DisabledIfHeadless;

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

    @Test
    @DisabledIfHeadless
    void testFrameInitialization() {
        JFrame frame = gui.getFrame();
        assertNotNull(frame);
        assertEquals("Order & Chaos Game", frame.getTitle());
        assertTrue(frame.isVisible());
    }

    @Test
    @DisabledIfHeadless
    void testGridButtonInitialization() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                JButton button = gui.getButton(i, j);
                assertNotNull(button);
                assertEquals("", button.getText());
                assertTrue(button.isEnabled());
            }
        }
    }

    @Test
    @DisabledIfHeadless
    void testInvalidButtonAccessThrows() {
        assertThrows(OutOfBoundsException.class, () -> gui.getButton(-1, 5));
        assertThrows(OutOfBoundsException.class, () -> gui.getButton(6, 0));
        assertThrows(OutOfBoundsException.class, () -> gui.getButton(3, 6));
    }

    @Test
    @DisabledIfHeadless
    void testPlayerLabelInitialization() {
        JLabel label = gui.getPlayerLabel();
        assertNotNull(label);
        assertEquals("Current turn: ", label.getText());
    }

    @Test
    @DisabledIfHeadless
    void testSetCurrentPlayerUpdatesLabel() {
        Player player = new Player(Role.ORDER, "Alice");
        gui.setCurrentPlayer(player);
        assertEquals("Current turn: Alice", gui.getPlayerLabel().getText());
    }

    @Test
    @DisabledIfHeadless
    void testUpdateButtonSetsSymbolAndDisables() {
        gui.updateButton(1, 1, "O");
        JButton btn = gui.getButton(1, 1);
        assertEquals("O", btn.getText());
        assertFalse(btn.isEnabled());
    }

    @Test
    @DisabledIfHeadless
    void testGetButtonPositionReturnsCorrectPos() {
        JButton btn = gui.getButton(2, 4);
        ActionEvent event = new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, "");
        Position pos = gui.getButtonPosition(event);
        assertNotNull(pos);
        assertEquals(2, pos.getRow());
        assertEquals(4, pos.getColumn());
    }

    @Test
    @DisabledIfHeadless
    void testAskForSymbolReturnsValidValue() {
        BoardGUI testGui = new BoardGUI(board) {
            @Override
            public String askForSymbol() {
                return "X"; // Simulated user choice
            }
        };
        String symbol = testGui.askForSymbol();
        assertTrue(symbol.equals("X") || symbol.equals("O"));
    }

    @Test
    @DisabledIfHeadless
    void testShowWinnerMessageMocked() {
        final String[] receivedMessage = new String[1];
        BoardGUI testGui = new BoardGUI(board) {
            @Override
            public void showWinnerMessage(String message) {
                receivedMessage[0] = message;
            }
        };
        testGui.showWinnerMessage("Chaos wins!");
        assertEquals("Chaos wins!", receivedMessage[0]);
    }

    @Test
    @DisabledIfHeadless
    void testAskForNewGameReturnsTrueMocked() {
        BoardGUI testGui = new BoardGUI(board) {
            @Override
            public boolean askForNewGame() {
                return true;
            }
        };
        assertTrue(testGui.askForNewGame());
    }

    @Test
    @DisabledIfHeadless
    void testSimulatedClickPlacesMark() throws Exception {
        TestBoardGUI testGui = new TestBoardGUI(new Board(), "X");
        Player order = new Player(Role.ORDER, "ORDER");

        SwingUtilities.invokeAndWait(() -> {
            testGui.setCurrentPlayer(order);
            testGui.getButton(0, 0).doClick();
        });

        JButton clicked = testGui.getButton(0, 0);
        assertEquals("X", clicked.getText());
        assertFalse(clicked.isEnabled());
    }

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
                    Type type = forcedSymbol.equals("X") ? Type.X : Type.O;
                    try {
                        board.addMove(new Move(new Mark(position, type), current));
                        updateButton(position.getRow(), position.getColumn(), type.getName());
                        Player tmp = current;
                        current = other;
                        other = tmp;
                        setCurrentPlayer(current);
                    } catch (InvalidPlayerException | OccupiedPositionException ex) {
                        showErrorMessage(ex.getMessage());
                    }
                }
            }
        }
    }
}

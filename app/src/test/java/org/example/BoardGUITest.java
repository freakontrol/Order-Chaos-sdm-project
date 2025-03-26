package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

public class BoardGUITest {
    private Board board;
    private BoardGUI gui;

    @BeforeEach
    public void setUp() {
        // Initialize the game board and its graphical interface before each test
        board = new Board();
        gui = new BoardGUI(board);
    }

    @Test
    public void testInitialState() {
        // Check that the game has not ended at the start
        assertFalse(board.isFiveInLineFound(), "The game ended before it started");

        // Verify that all buttons are initially enabled and their text is blank
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertTrue(gui.getButton(i, j).isEnabled(), "The buttons should be enabled at first");
                assertEquals("", gui.getButton(i, j).getText(), "The button text should initially be blank");
            }
        }
    }

    @Test
    public void testButtonPlacement() {
        // Test the behavior of clicking a button
        JButton button = gui.getButton(0, 0);

        // Check that the button is not null and has no initial text
        assertNotNull(button, "The button should not be null");
        assertEquals("", button.getText(), "The button text should initially be empty.");

        // Simulate a button click and verify it becomes disabled
        button.doClick();
        assertFalse(button.isEnabled(), "The button should be disabled after the click.");
    }

    @Test
    public void testGameOverOnFullBoard() {
        // Simulate filling the entire board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                JButton button = gui.getButton(i, j);
                button.doClick();
            }
        }

        // Check if all 36 moves are registered and there is no Order victory
        assertEquals(36, board.getMoves().size(), "There should be 36 moves on the full board.");
        assertFalse(board.isFiveInLineFound(), "There should be no Order victory.");
    }

    @Test
    public void testOrderWinCondition() {
        // Simulate a winning condition for Order (5 consecutive marks)
        for (int i = 0; i < 5; i++) {
            JButton button = gui.getButton(0, i);
            button.doClick();
        }

        // Check if the win condition for Order is met
        assertTrue(board.isFiveInLineFound(), "There should be an Order win with 5 consecutive marks.");
    }

    @Test
    public void testChaosWinCondition() {
        // Simulate filling the entire board without Order winning
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                JButton button = gui.getButton(i, j);
                button.doClick();
            }
        }

        // Verify that the board is full and no Order victory is declared
        assertEquals(36, board.getMoves().size(), "The board should be full with 36 moves.");
        assertFalse(board.isFiveInLineFound(), "There should be no Order victory.");
    }

    @Test
    public void testPlayerLabelUpdate() {
        // Set the current player and verify the label is updated correctly
        gui.setCurrentPlayer(new Player(Role.ORDER, "Player 1"));
        assertEquals("shift of: Player 1", gui.getPlayerLabel().getText(), "The label should correctly show the current player.");
    }
}

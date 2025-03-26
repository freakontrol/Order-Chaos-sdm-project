package org.example;

import javax.swing.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.event.*;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;

// import java.awt.*;
// import java.util.HashMap;
// import java.util.Map;

public class Main {
    private static Board board;
    private static BoardGUI gui;
    private static Player playerOrder;
    private static Player playerChaos;
    private static Player currentPlayer;

    public static void main(String[] args) {
       
        // Initialize the game when the application starts
        initializeGame();
    }

    private static void initializeGame() {
        
        // Set the graphical look and feel of the GUI
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Initialize the game board and GUI
        board = new Board();
        gui = new BoardGUI(board);

        // Create players for ORDER and CHAOS roles
        playerOrder = new Player(Role.ORDER, "ORDER");
        playerChaos = new Player(Role.CHAOS, "CHAOS");

        // Ask the user which player starts the game
        Object[] options = {"ORDER", "CHAOS"};
        int choice = JOptionPane.showOptionDialog(null, "Who starts?", "Initial Choice",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // If the dialog is closed, exit the program
        if (choice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        // Set the current player based on the chosen option
        currentPlayer = (choice == 0) ? playerOrder : playerChaos;
        gui.setCurrentPlayer(currentPlayer);

        // Set up a listener to handle player moves
        gui.setMoveListener(new MoveListener());
    }

    // Inner class to handle player moves
    private static class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the button that was clicked
            JButton button = (JButton) e.getSource();
            int row = (int) button.getClientProperty("row");
            int col = (int) button.getClientProperty("col");

            // Check if the selected position is not already occupied
            if (!board.isOccupied(new Position(row, col))) {
                // Prompt the player to choose between X and O
                String[] options = {"X", "O"};
                String choice = (String) JOptionPane.showInputDialog(gui.getFrame(),
                        "Do you place X or O? :", "Move",
                        JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                // If a valid choice is made, place the mark on the board
                if (choice != null) {
                    Type markType = choice.equals("X") ? Type.X : Type.O;
                    Mark mark = new Mark(new Position(row, col), markType);
                    Move move = new Move(mark, currentPlayer);
                    board.addMove(move);
                    gui.updateButton(row, col, markType.getName());

                    // Check if the ORDER player has won
                    if (board.isFiveInLineFound()) {
                        JOptionPane.showMessageDialog(gui.getFrame(), "Player ORDER wins!");
                        askForNewGame(); // Ask if the player wants to start a new game
                        return;
                    }

                    // Check if the board is full, resulting in a win for CHAOS
                    if (board.getMoves().size() == 36) {
                        JOptionPane.showMessageDialog(gui.getFrame(), "Player CHAOS wins!");
                        askForNewGame(); // Ask if the player wants to start a new game
                        return;
                    }

                    // Switch to the other player for the next move
                    currentPlayer = (currentPlayer.getRole() == Role.ORDER) ? playerChaos : playerOrder;
                    gui.setCurrentPlayer(currentPlayer);
                }
            }
        }

        // Method to prompt the user to start a new game or exit
        private void askForNewGame() {
            int restart = JOptionPane.showConfirmDialog(gui.getFrame(), "Do you want to play a new game?", "New Game",
                    JOptionPane.YES_NO_OPTION);

            if (restart == JOptionPane.YES_OPTION) {
                // Restart the game if the player chooses "Yes"
                gui.dispose();
                initializeGame();
            } else {
                // Exit the application if the player chooses "No"
                gui.dispose();
                System.exit(0);
            }
        }
    }
}

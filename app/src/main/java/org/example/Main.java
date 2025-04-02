package org.example;

// import javax.swing.*;
import java.awt.event.*;


import javax.swing.UIManager;

// import javax.swing.*;
// import java.awt.event.*;

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

        // Initialize the game board and GUI
        board = new Board();
        gui = new BoardGUI(board);

        // Create players for ORDER and CHAOS roles
        playerOrder = new Player(Role.ORDER, "ORDER");
        playerChaos = new Player(Role.CHAOS, "CHAOS");

        // Ask the user which player starts the game
        //bject[] options = {"ORDER", "CHAOS"};
        //int choice = JOptionPane.showOptionDialog(null, "Who starts?", "Initial Choice",
                //JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // If the dialog is closed, exit the program
        //if (choice == JOptionPane.CLOSED_OPTION) {
            //System.exit(0);
       // }

        
       
        // Set the current player based on the chosen option
        //currentPlayer = (choice == 0) ? playerOrder : playerChaos;
        currentPlayer = playerOrder;
        gui.setCurrentPlayer(currentPlayer);

        // Set up a listener to handle player moves
        gui.setMoveListener(new MoveListener());
    }

    // Inner class to handle player moves
    private static class MoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Get the button that was clicked
            Position position = gui.getButtonPosition(e);

            // Check if the selected position is not already occupied
            if (!board.isOccupied(position)) {
                String choice = gui.askForSymbol();

                if (choice != null) {
                    Type markType = choice.equals("X") ? Type.X : Type.O;
                    Mark mark = new Mark(position, markType);
                    Move move = new Move(mark, currentPlayer);
                    board.addMove(move);
                    gui.updateButton(position.getRow(), position.getColumn(), markType.getName());

                    // Check if the ORDER player has won
                    if (board.isFiveInLineFound()) {
                        gui.showWinnerMessage("Player ORDER wins!");
                        if (gui.askForNewGame()) {// Ask if the player wants to start a new game
                            restartGame();
                        } else {
                            gui.dispose();
                            System.exit(0);
                        }
                        return; 
                    }

                    // Check if the board is full, resulting in a win for CHAOS
                    if (board.getMoves().size() == 36) {
                        gui.showWinnerMessage("Player CHAOS wins!");
                        if (gui.askForNewGame()) {// Ask if the player wants to start a new game
                            restartGame();
                        } else {
                            gui.dispose();
                            System.exit(0);
                        }
                        return; 
                    }

                    // Switch to the other player for the next move
                    currentPlayer = (currentPlayer.getRole() == Role.ORDER) ? playerChaos : playerOrder;
                    gui.setCurrentPlayer(currentPlayer);
                }
            }
        }

        // Method to prompt the user to start a new game or exit
        private static void restartGame() {
            gui.dispose();
            initializeGame();
        }
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        Player playerOrder = new Player(Role.ORDER, "Player Order");
        Player playerChaos = new Player(Role.CHAOS, "Player Chaos");

        Board board = new Board();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean isGameOver = false;
        Player currentPlayer = playerOrder; // Start with Order's turn

        while (!isGameOver) {
            System.out.println("\nCurrent Board:");
            board.printBoard();

            Position position = null;
            Type markType = null;
            do {
                try {
                    System.out.print(currentPlayer.getName() + ", enter your move (row column mark): ");

                    String inputLine = reader.readLine();
                    if (inputLine == null) {
                        // Exit gracefully if input stream is closed
                        System.out.println("Input stream closed unexpectedly. Exiting.");
                        return;
                    }

                    String[] parts = inputLine.split(" ");

                    if (parts.length != 3) {
                        System.out.println("Please provide exactly three values separated by space: row, column, and mark type.");
                        continue;
                    }

                    int row, col;

                    try {
                        row = Integer.parseInt(parts[0]);
                        col = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Row and column must be integers. Try again.");
                        continue;
                    }

                    position = new Position(row, col);

                    // Validate coordinates within 0-5
                    if (row < 0 || row >=6 || col <0 || col >=6) {
                        System.out.println("Row and column must be between 0 and 5. Try again.");
                        continue;
                    }

                    // Check if the position is already occupied
                    if (board.isOccupied(position)) {
                        System.out.println("Position " + position + " is taken. Choose another spot.");
                        continue;
                    }

                    // Validate mark type
                    String markInput = parts[2].toUpperCase();
                    switch (markInput) {
                        case "X":
                            markType = Type.X;
                            break;
                        case "O":
                            markType = Type.O;
                            break;
                        default:
                            System.out.println("Invalid mark type. Please enter X or O.");
                            continue;
                    }

                    // Create mark based on user input
                    Mark mark = new Mark(position, markType);
                    Move move = new Move(mark, currentPlayer);

                    board.addMove(move); // Add to the board

                    break; // Valid move processed successfully
                } catch (IOException e) {
                    System.out.println("Error reading input: " + e.getMessage());
                    return; // Exit on any other I/O error
                }
            } while(true);

            // Check win condition after move is added
            if (board.isFiveInLineFound()) {
                isGameOver = true;
                System.out.println("\n" + currentPlayer.getName() + " wins with five in a row! Game Over.");
            } else if (board.isBoardFull()){
                isGameOver = true;
                System.out.println("\n" + "Player Chaos wins! Game Over.");
            }

            // Switch to other player for next turn
            currentPlayer = (currentPlayer == playerOrder) ? playerChaos : playerOrder;
        }

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Error closing input stream: " + e.getMessage());
        }
    }
}
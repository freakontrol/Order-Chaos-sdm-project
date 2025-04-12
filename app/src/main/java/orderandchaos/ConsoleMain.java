package orderandchaos;

import orderandchaos.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleMain {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Player playerOrder = initializePlayer(reader, Role.ORDER);
        Player playerChaos = initializePlayer(reader, Role.CHAOS);
        Board board = new Board();

        boolean isGameOver = false;
        Player currentPlayer = playerOrder; // Start with Order's turn

        while (!isGameOver) {
            printBoard(board);
            Position position = null;
            Type markType = null;

            do {
                try {
                    position = getPlayerMove(reader, currentPlayer);
                    markType = getMarkType(reader, currentPlayer);
                    Mark mark = new Mark(position, markType);
                    Move move = new Move(mark, currentPlayer);
                    board.addMove(move); // Add to the board
                    break; // Valid move processed successfully
                } catch (IOException e) {
                    System.out.println("Error reading input: " + e.getMessage());
                    return; // Exit on any other I/O error
                }
            } while (true);

            // Check win condition after move is added
            isGameOver = checkWinCondition(board, currentPlayer);

            // Switch to other player for next turn
            currentPlayer = (currentPlayer == playerOrder) ? playerChaos : playerOrder;
        }

        closeReader(reader);
    }

    private static Player initializePlayer(BufferedReader reader, Role role) {
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter name for " + role + " player: ");
            try {
                name = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
                System.exit(0);
            }
        }
        return new Player(role, name);
    }

    private static void printBoard(Board board) {
        System.out.println("\nCurrent Board:");
        board.printBoard();
    }

    private static Position getPlayerMove(BufferedReader reader, Player currentPlayer) throws IOException {
        Position position = null;
        while (position == null) {
            System.out.print(currentPlayer.getName() + ", enter your move (row column): ");
            String inputLine = reader.readLine();

            if (inputLine == null) {
                // Exit gracefully if input stream is closed
                System.out.println("Input stream closed unexpectedly. Exiting.");
                System.exit(0);
            }

            String[] parts = inputLine.split(" ");

            if (parts.length != 2) {
                System.out.println(
                        "Please provide exactly two values separated by space: row and column.");
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
            if (row < 0 || row >= 6 || col < 0 || col >= 6) {
                System.out.println("Row and column must be between 0 and 5. Try again.");
                position = null;
            }
        }
        return position;
    }

    private static Type getMarkType(BufferedReader reader, Player currentPlayer) throws IOException {
        Type markType = null;
        while (markType == null) {
            System.out.print(currentPlayer.getName() + ", enter your mark (X or O): ");
            String inputLine = reader.readLine();

            if (inputLine == null) {
                // Exit gracefully if input stream is closed
                System.out.println("Input stream closed unexpectedly. Exiting.");
                System.exit(0);
            }

            String markInput = inputLine.toUpperCase();

            switch (markInput) {
                case "X":
                    markType = Type.X;
                    break;
                case "O":
                    markType = Type.O;
                    break;
                default:
                    System.out.println("Invalid mark type. Please enter X or O.");
            }
        }
        return markType;
    }

    private static boolean checkWinCondition(Board board, Player currentPlayer) {
        boolean isGameOver = false;
        if (board.isFiveInLineFound()) {
            isGameOver = true;
            System.out.println("\n" + currentPlayer.getName() + " wins with five in a row! Game Over.");
        } else if (board.isBoardFull()) {
            isGameOver = true;
            System.out.println("\nPlayer Chaos wins! Game Over.");
        }
        return isGameOver;
    }

    private static void closeReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Error closing input stream: " + e.getMessage());
        }
    }
}
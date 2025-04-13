package orderandchaos;

import orderandchaos.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleMain extends OrderAndChaos {

    private BufferedReader reader;

    public static void main(String[] args) {
        ConsoleMain game = new ConsoleMain();
        game.initializePlayers();
        game.initializeGame();
        game.startGame();
        game.closeReader();
    }

    public void initializePlayers() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        playerOrder = initializePlayer(Role.ORDER);
        playerChaos = initializePlayer(Role.CHAOS);
    }

    @Override
    public void startGame() {
        while (!isGameOver) {
            printBoard();
            Position position = null;
            Type markType = null;

            do {
                try {
                    position = getPlayerMove(currentPlayer);
                    markType = getMarkType(currentPlayer);
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
            isGameOver = checkWinCondition(currentPlayer);

            // Switch to other player for next turn
            currentPlayer = (currentPlayer == playerOrder) ? playerChaos : playerOrder;
        }
    }

    @Override
    protected Player initializePlayer(Role role) {
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

    @Override
    protected void printBoard() {
        System.out.println("\nCurrent Board:");
        board.printBoard();
    }

    @Override
    protected Position getPlayerMove(Player currentPlayer) throws IOException {
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

    @Override
    protected Type getMarkType(Player currentPlayer) throws IOException {
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

    @Override
    protected boolean checkWinCondition(Player currentPlayer) {
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

    public void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Error closing input stream: " + e.getMessage());
        }
    }
}
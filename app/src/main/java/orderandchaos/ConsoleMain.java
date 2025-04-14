package orderandchaos;

import orderandchaos.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleMain extends OrderAndChaos {

    private BufferedReader reader;

    public ConsoleMain() {
        super();
    }

    public static void main(String[] args) {
        ConsoleMain game = new ConsoleMain();
        game.initializeGame();
        game.startGame();
        game.exitGame();
    }

    @Override
    protected void preInitializeGame() {
        gameWelcome();

        reader = new BufferedReader(new InputStreamReader(System.in));

        String orderPlayerName = initializePlayer(Role.ORDER);
        String chaosPlayerName;

        do {
            chaosPlayerName = initializePlayer(Role.CHAOS);

            if (orderPlayerName.equals(chaosPlayerName)) {
                System.out.println("Name is already taken. Choose another.");
            } else {
                this.playerOrder = new Player(Role.ORDER, orderPlayerName);
                this.playerChaos = new Player(Role.CHAOS, chaosPlayerName);
                break;
            }
        } while (true);
    }

    @Override
    protected void gameWelcome(){
        System.out.println("\nWelcome to Order and Chaos.\nGet ready for an exciting battle of strategy and wit on a 6x6 board.\nColumns and rows are numbered from 1 to 6, making it easy to plan your moves.");
        System.out.println("Choose your player name and let the game begin!\nIf you need to brush up on the rules, you can find them " + createHyperlink("here", "https://en.wikipedia.org/wiki/Order_and_Chaos") + ".");
        System.out.println("Have fun and may the best player win!\n");
    }

    //Use hyperlink to keep the link hidden
    private String createHyperlink(String text, String url) {
        return "\033]8;;" + url + "\033\\" + text + "\033]8;;\033\\";
    }

    private String initializePlayer(Role role) {
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter name for " + role + " player: ");
            try {
                name = reader.readLine();
                name = name.trim();

                if (name.isEmpty()) {
                    System.out.println("The " + role + " name can't be empty.");
                    continue;
                }

            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
                System.exit(0);
            }
        }
        return name;
    }

    @Override
    public void startGame() {
        while (true) {
            while (!isGameOver) {
                printBoard();
                Position position = null;
                Type markType = null;

                do {
                    try {
                        position = getPlayerMove();
                        markType = getMarkType();
                        addMove(position, markType);
                        break; // Valid move processed successfully
                    } catch (IOException e) {
                        System.out.println("Error reading input: " + e.getMessage());
                        return; // Exit on any other I/O error
                    }
                } while (true);

                // Check win condition after move is added
                isGameOver = checkWinCondition();

                // Switch to other player for next turn
                currentPlayer = (currentPlayer == playerOrder) ? playerChaos : playerOrder;
            }

            // Ask if players want to play a new game
            if (!askToPlayNewGame()) {
                break;
            }

            // Ask if players want to switch roles and restart the game
            if (askToSwitchRoles()) {
                restartGame();
            } else {
                resetGame();
            }
        }
    }

    @Override
    protected Position getPlayerMove() throws IOException {
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

            row -= 1;
            col -=1;

            // Validate coordinates within 0-5
            if (row < 0 || row >= 6 || col < 0 || col >= 6) {
                System.out.println("Row and column must be between 1 and 6. Try again.");
                position = null;
                continue;
            }

            position = new Position(row, col);

            if (!checkFreePosition(position)) {
                System.out.println("Position is occupied. Choose another.");
                position = null;
            }
        }
        return position;
    }

    @Override
    protected Type getMarkType() throws IOException {
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
    protected boolean checkWinCondition() {
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

    @Override
    public void exitGame() {
        closeReader();
    }

    protected void printBoard() {
        System.out.println("\nCurrent Board:");
        board.printBoard();
    }

    public void closeReader() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing input stream: " + e.getMessage());
        }
    }

    private boolean askToSwitchRoles() {
        while (true) {
            System.out.print("Do you want to switch roles between ORDER and CHAOS? (yes/no): ");
            try {
                String input = reader.readLine();
                if (input.equalsIgnoreCase("yes")) {
                    return true;
                } else if (input.equalsIgnoreCase("no")) {
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
        }
    }

    private boolean askToPlayNewGame() {
        while (true) {
            System.out.print("Do you want to play a new game? (yes/no): ");
            try {
                String input = reader.readLine();
                if (input.equalsIgnoreCase("yes")) {
                    return true;
                } else if (input.equalsIgnoreCase("no")) {
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                }
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
        }
    }

    private void restartGame() {
        Player temp = playerOrder;
        playerOrder = new Player(Role.ORDER, playerChaos.getName());
        playerChaos = new Player(Role.CHAOS, temp.getName());
        resetGame();
    }

    private void resetGame() {
        board.clearBoard();
        isGameOver = false;
        currentPlayer = playerOrder;
    }
}
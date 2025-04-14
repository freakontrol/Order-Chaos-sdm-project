package orderandchaos;

import orderandchaos.core.*;

import java.io.IOException;

public class ConsoleMain extends OrderAndChaos<InputHandlerConsole, OutputHandlerConsole> {
    public ConsoleMain(InputHandlerConsole inputHandler, OutputHandlerConsole outputHandler) {
        super(inputHandler, outputHandler);
    }

    public static void main(String[] args) {
        InputHandlerConsole inputHandler = new InputHandlerConsole();
        OutputHandlerConsole outputHandler = new OutputHandlerConsole(); // Initialize with null, will be set later
        ConsoleMain game = new ConsoleMain(inputHandler, outputHandler);
        game.initializeGame();
        game.startGame();
        game.exitGame();
    }

    @Override
    protected void preInitializeGame() {
        String orderPlayerName = initializePlayer(Role.ORDER);
        String chaosPlayerName;

        do {
            chaosPlayerName = initializePlayer(Role.CHAOS);

            if (orderPlayerName.equals(chaosPlayerName)) {
                outputHandler.showWinnerMessage("Name is already taken. Choose another.");
            } else {
                this.playerOrder = new Player(Role.ORDER, orderPlayerName);
                this.playerChaos = new Player(Role.CHAOS, chaosPlayerName);
                break;
            }
        } while (true);
    }

    private String initializePlayer(Role role) {
        String name = "";
        while (name.isEmpty()) {
            try {
                name = inputHandler.askForPlayerName(role);
            } catch (IOException e) {
                outputHandler.showWinnerMessage("Error reading input: " + e.getMessage());
                System.exit(0);
            }
        }
        return name;
    }

    @Override
    public void startGame() {
        while (!isGameOver) {
            outputHandler.printBoard(board); // Call the printBoard method from OutputHandler
            Position position = null;
            Type markType = null;

            do {
                try {
                    position = inputHandler.getPlayerMove(currentPlayer);
                    markType = inputHandler.getMarkType(currentPlayer);
                    addMove(position, markType);
                    break; // Valid move processed successfully
                } catch (IOException e) {
                    outputHandler.showWinnerMessage("Error reading input: " + e.getMessage());
                    return; // Exit on any other I/O error
                }
            } while (true);

            // Check win condition after move is added
            isGameOver = checkWinCondition();

            // Switch to other player for next turn
            currentPlayer = (currentPlayer == playerOrder) ? playerChaos : playerOrder;
            //outputHandler.updatePlayerLabel(currentPlayer); // Update the player label
        }
    }

    @Override
    protected boolean checkWinCondition() {
        boolean isGameOver = false;
        if (board.isFiveInLineFound()) {
            isGameOver = true;
            outputHandler.showWinnerMessage("\n" + currentPlayer.getName() + " wins with five in a row! Game Over.");
        } else if (board.isBoardFull()) {
            isGameOver = true;
            outputHandler.showWinnerMessage("\nPlayer Chaos wins! Game Over.");
        }
        return isGameOver;
    }

    @Override
    public void exitGame() {
    }
}
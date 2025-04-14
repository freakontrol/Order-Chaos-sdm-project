package orderandchaos;

import orderandchaos.core.*;
import orderandchaos.ui.console.*;


import java.io.IOException;

public class ConsoleMain extends OrderAndChaos<InputHandlerConsole, OutputHandlerConsole> {

    public ConsoleMain(InputHandlerConsole inputHandler, OutputHandlerConsole outputHandler) {
        super(inputHandler, outputHandler);
    }

    public static void main(String[] args) {
        InputHandlerConsole inputHandler = new InputHandlerConsole();
        OutputHandlerConsole outputHandler = new OutputHandlerConsole();
        ConsoleMain game = new ConsoleMain(inputHandler, outputHandler);
        game.initializeGame();
        game.startGame();
    }

    @Override
    protected void preInitializeGame() {
        outputHandler.gameWelcome();

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
        while (true) {
            while (checkWinCondition() == GameOutcome.GAME_NOT_OVER) {
                outputHandler.printBoard(board);
                Position position = null;
                Type markType = null;

            do {
                try {
                    while (position == null){
                        position = inputHandler.getPlayerMove(currentPlayer);
                        if (!checkFreePosition(position)) {
                            System.out.println("Position is occupied. Choose another.");
                            position = null;
                        }
                    }
                    markType = inputHandler.getMarkType(currentPlayer);
                    addMove(position, markType);
                    break; // Valid move processed successfully
                } catch (IOException e) {
                    outputHandler.showWinnerMessage("Error reading input: " + e.getMessage());
                    return; // Exit on any other I/O error
                }
            } while (true);


                // Switch to other player for next turn
                currentPlayer = (currentPlayer == playerOrder) ? playerChaos : playerOrder;
            }

            if (checkWinCondition() == GameOutcome.FIVE_IN_A_ROW) {
                outputHandler.showWinnerMessage("\n" + currentPlayer.getName() + " wins with five in a row! Game Over.");
            } else if (checkWinCondition() == GameOutcome.BOARD_FULL) {
                outputHandler.showWinnerMessage("\nPlayer Chaos wins! Game Over.");
            }

            // Ask if players want to play a new game
            if (!outputHandler.askForNewGame()) {
                break;
            }

            // Ask if players want to switch roles and restart the game
            if (inputHandler.askToSwitchRoles()) {
                restartGame();
            } else {
                resetGame();
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
        currentPlayer = playerOrder;
    }
}
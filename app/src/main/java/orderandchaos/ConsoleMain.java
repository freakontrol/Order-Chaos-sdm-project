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
        gameWelcome();

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
            while (!isGameOver) {
                outputHandler.printBoard(board); // Call the printBoard method from OutputHandler
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

                // Check win condition after move is added
                isGameOver = checkWinCondition();

                // Switch to other player for next turn
                currentPlayer = (currentPlayer == playerOrder) ? playerChaos : playerOrder;
            }

            // Ask if players want to play a new game
            if (!inputHandler.askToPlayNewGame()) {
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
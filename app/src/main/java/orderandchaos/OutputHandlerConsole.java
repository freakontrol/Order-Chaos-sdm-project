package orderandchaos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import orderandchaos.core.OutputHandler;
import orderandchaos.core.Player;
import orderandchaos.core.Board;

public class OutputHandlerConsole implements OutputHandler {
    private BufferedReader reader;
    public OutputHandlerConsole() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void showWinnerMessage(String message) {
        System.out.println(message);
    }

    @Override
    public boolean askForNewGame() {
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

    @Override
    public void updatePlayerLabel(Player currentPlayer) {
        System.out.println("Current turn: " + currentPlayer.getName());
    }

    @Override
    public void resetBoardDisplay() {
        System.out.println("Board has been reset.");
    }

    public void printBoard(Board board) { // Implement the printBoard method
        System.out.println("\nCurrent Board:");
        board.printBoard();
    }

    @Override
    public void gameWelcome(){
        System.out.println("\nWelcome to Order and Chaos.\nGet ready for an exciting battle of strategy and wit on a 6x6 board.\nColumns and rows are numbered from 1 to 6, making it easy to plan your moves.");
        System.out.println("Choose your player name and let the game begin!\nIf you need to brush up on the rules, you can find them " + createHyperlink("here", "https://en.wikipedia.org/wiki/Order_and_Chaos") + ".");
        System.out.println("Have fun and may the best player win!\n");
    }

    private String createHyperlink(String text, String url) {
        return "\033]8;;" + url + "\033\\" + text + "\033]8;;\033\\";
    }

}
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
    public void updateButton(int row, int col, String symbol) {
        System.out.println("Updated button at (" + row + ", " + col + ") with symbol: " + symbol);
    }

    @Override
    public void showWinnerMessage(String message) {
        System.out.println(message);
    }

    @Override
    public boolean askForNewGame() {
        System.out.print("Do you want to play a new game? (yes/no): ");
        try {
            String response = reader.readLine();
            return response.equalsIgnoreCase("yes");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
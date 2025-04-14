package orderandchaos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import orderandchaos.core.OutputHandler;
import orderandchaos.core.Player;

public class OutputHandlerConsole implements OutputHandler {
    private BufferedReader reader;
    private Player currentPlayer;

    public OutputHandlerConsole(Player currentPlayer) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.currentPlayer = currentPlayer;
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
    public void updatePlayerLabel() {
        System.out.println("Current turn: " + currentPlayer.getName());
    }

    @Override
    public void resetBoardDisplay() {
        System.out.println("Board has been reset.");
    }
}
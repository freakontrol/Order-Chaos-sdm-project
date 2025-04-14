package orderandchaos.core;

public interface OutputHandler {
    void updateButton(int row, int col, String symbol);
    void showWinnerMessage(String message);
    boolean askForNewGame();
    void updatePlayerLabel(Player currentPlayer);
    void resetBoardDisplay();
    void gameWelcome();

}
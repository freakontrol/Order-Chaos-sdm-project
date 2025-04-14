package orderandchaos.core;

public interface OutputHandler {
    void showWinnerMessage(String message);
    boolean askForNewGame();
    void updatePlayerLabel(Player currentPlayer);
    void resetBoardDisplay();
    void gameWelcome();

}
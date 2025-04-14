package orderandchaos.ui;

import orderandchaos.core.Player;

public interface OutputHandler {
    void showWinnerMessage(String message);
    boolean askForNewGame();
    void updatePlayerLabel(Player currentPlayer);
    void resetBoardDisplay();
    void gameWelcome();
}
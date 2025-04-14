package orderandchaos.core;

import orderandchaos.core.Board;

public interface OutputHandler {
    void updateButton(int row, int col, String symbol);
    void showWinnerMessage(String message);
    boolean askForNewGame();
    void updatePlayerLabel(Player currentPlayer);
    void resetBoardDisplay();
    void printBoard(Board board);
}
package orderandchaos.core;

import java.io.IOException;

public abstract class OrderAndChaos {

    protected Player playerOrder;
    protected Player playerChaos;
    protected Board board;
    protected boolean isGameOver;
    protected Player currentPlayer;

    public void initializeGame() {
        board = new Board();
        isGameOver = false;
        currentPlayer = playerOrder; // Start with Order's turn
    }

    public abstract void startGame();

    protected abstract Player initializePlayer(Role role);
    protected abstract void printBoard();
    protected abstract Position getPlayerMove(Player currentPlayer) throws IOException;
    protected abstract Type getMarkType(Player currentPlayer) throws IOException;
    protected abstract boolean checkWinCondition(Player currentPlayer);
}

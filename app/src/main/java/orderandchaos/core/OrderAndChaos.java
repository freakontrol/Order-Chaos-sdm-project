package orderandchaos.core;

import java.io.IOException;

public abstract class OrderAndChaos {

    protected Player playerOrder;
    protected Player playerChaos;
    protected Board board;
    protected boolean isGameOver;
    protected Player currentPlayer;

    public void startGame() {
        board = new Board();
        isGameOver = false;
        currentPlayer = playerOrder; // Start with Order's turn

        while (!isGameOver) {
            printBoard();
            Position position = null;
            Type markType = null;

            do {
                try {
                    position = getPlayerMove(currentPlayer);
                    markType = getMarkType(currentPlayer);
                    Mark mark = new Mark(position, markType);
                    Move move = new Move(mark, currentPlayer);
                    board.addMove(move); // Add to the board
                    break; // Valid move processed successfully
                } catch (IOException e) {
                    System.out.println("Error reading input: " + e.getMessage());
                    return; // Exit on any other I/O error
                }
            } while (true);

            // Check win condition after move is added
            isGameOver = checkWinCondition(currentPlayer);

            // Switch to other player for next turn
            currentPlayer = (currentPlayer == playerOrder) ? playerChaos : playerOrder;
        }
    }

    protected abstract Player initializePlayer(Role role);
    protected abstract void printBoard();
    protected abstract Position getPlayerMove(Player currentPlayer) throws IOException;
    protected abstract Type getMarkType(Player currentPlayer) throws IOException;
    protected abstract boolean checkWinCondition(Player currentPlayer);
}
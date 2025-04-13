package orderandchaos.core;

import java.io.IOException;

import orderandchaos.exceptions.OrderAndChaosException;

public abstract class OrderAndChaos {

    protected Player playerOrder;
    protected Player playerChaos;
    protected Board board;
    protected boolean isGameOver;
    protected Player currentPlayer;
    protected OrderAndChaos() {
        board = new Board();
        isGameOver = false;
    }

    public void initializeGame(){
        preInitializeGame();
        this.currentPlayer = this.playerOrder;
    }

    public abstract void startGame() throws OrderAndChaosException;

    public abstract void exitGame();

    protected abstract void preInitializeGame();


    protected abstract Position getPlayerMove() throws IOException;

    protected abstract Type getMarkType() throws IOException;

    protected void addMove(Position position, Type markType){
        Mark mark = new Mark(position, markType);
        Move move = new Move(mark, currentPlayer);
        board.addMove(move); // Add to the board
    }
    protected boolean checkWinCondition() {
        if (board.isFiveInLineFound() || board.isBoardFull()) isGameOver = true;
        return isGameOver;
    }

    protected boolean checkFreePosition(Position position) {
        return board.checkFreePosition(position);
    }
}

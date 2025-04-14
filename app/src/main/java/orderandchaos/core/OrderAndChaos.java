package orderandchaos.core;

import orderandchaos.exceptions.OrderAndChaosException;

public abstract class OrderAndChaos<IH extends InputHandler, OH extends OutputHandler> {

    protected Player playerOrder;
    protected Player playerChaos;
    protected Board board;
    protected boolean isGameOver;
    protected Player currentPlayer;

    protected IH inputHandler;
    protected OH outputHandler;

    protected OrderAndChaos(IH inputHandler, OH outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.board = new Board();
        this.isGameOver = false;
    }

    public void initializeGame(){
        preInitializeGame();
        this.currentPlayer = this.playerOrder;
    }

    public abstract void startGame() throws OrderAndChaosException;

    public abstract void exitGame();

    protected abstract void preInitializeGame();

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
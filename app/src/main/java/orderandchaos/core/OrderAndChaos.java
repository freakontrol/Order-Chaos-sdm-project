package orderandchaos.core;

import orderandchaos.exceptions.OrderAndChaosException;

public abstract class OrderAndChaos<I extends InputHandler, O extends OutputHandler> {

    protected Player playerOrder;
    protected Player playerChaos;
    protected Board board;
    protected Player currentPlayer;

    protected I inputHandler;
    protected O outputHandler;

    protected OrderAndChaos(I inputHandler, O outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.board = new Board();
    }

    public void initializeGame(){
        preInitializeGame();
        this.currentPlayer = this.playerOrder;
    }

    public abstract void startGame() throws OrderAndChaosException;

    protected abstract void preInitializeGame();

    protected void addMove(Position position, Type markType){
        Mark mark = new Mark(position, markType);
        Move move = new Move(mark, currentPlayer);
        board.addMove(move); // Add to the board
    }

    public enum GameOutcome {
        FIVE_IN_A_ROW,
        BOARD_FULL,
        GAME_NOT_OVER
    }

    protected GameOutcome checkWinCondition() {
        if (board.isFiveInLineFound()) {
            return GameOutcome.FIVE_IN_A_ROW;
        }
        if (board.isBoardFull()) {
            return GameOutcome.BOARD_FULL;
        }
        return GameOutcome.GAME_NOT_OVER;
    }


    protected boolean checkFreePosition(Position position) {
        return board.checkFreePosition(position);
    }
}
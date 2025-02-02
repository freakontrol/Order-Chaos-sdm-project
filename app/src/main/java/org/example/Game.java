package org.example;

public class Game {
    private Board gameBoard;
    private Player currentPlayer;
    private GameStatus status;

    public Game() {
        this.gameBoard = new Board();
        this.currentPlayer = new Player(Role.ORDER, "ORDER");
        this.status = GameStatus.IN_PROGRESS;
    }

    // getter for gameStatus
    public GameStatus getGameStatus() {
        return status;
    }

    // getter for gameBoard
    public Board getGameBoard() {
        return gameBoard;
    }

    // getter for currentPlayer
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isDraw() {
        return gameBoard.getMoves().size() == 36; // 6x6 board
    }

    public void switchPlayer() {
        if (currentPlayer.getRole() == Role.ORDER) {
            currentPlayer = new Player(Role.CHAOS, "CHAOS");
        } else {
            currentPlayer = new Player(Role.ORDER, "ORDER");
        }
    }

    public void startGame() {
        this.gameBoard = new Board();
        this.currentPlayer = new Player(Role.ORDER, "ORDER");
        this.status = GameStatus.IN_PROGRESS;
    }

    public boolean makeMove(int row, int col, char symbol) {
        Position position = new Position(row, col);
        Type type = Type.valueOf(Character.toString(symbol).toUpperCase());
        Mark mark = new Mark(position, type);
        Move move = new Move(mark, currentPlayer);

        if (gameBoard.isOccupied(position)) {
            return false; // can't place a piece where one already exists
        }

        gameBoard.addMove(move);
        
        if (checkWin(row, col, symbol)) {
            status = GameStatus.ORDER_WINS;
        } else if (isDraw()) {
            status = GameStatus.CHAOS_WINS;
        } else {
            switchPlayer();
        }
        
        return true;
    }

    private boolean checkWin(int row, int col, char symbol) {
        return gameBoard.hasFiveInARow(row, col, symbol);
    }

}
package org.example;

public class Game {
    private Board gameBoard;
    private Player currentPlayer;

    public Game() {
        this.gameBoard = new Board();
        this.currentPlayer = new Player(Role.ORDER, "ORDER");
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
    }

}
package org.example;

public class GameLogic {

    public static boolean checkForWin(Board board, Player player) {
        char symbol = player.getRole().name().charAt(0);
        for (Move move : board.getMoves()) {
            Position position = move.getMark().getPosition();
            if (move.getPlayer().equals(player)) {
                int row = position.getRow();
                int col = position.getColumn();
                if (board.hasFiveInARow(row, col, symbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    
    public static boolean checkForDraw(Board board) {
        return board.getMoves().size() == 36; // 6x6 board
    }
}
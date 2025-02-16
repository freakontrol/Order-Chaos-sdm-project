package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    private char[][] grid;
    private List<Move> moves;
    private HashMap<Position, Move> positionMoves;


    public Board() {
        this.moves = new ArrayList<>();
        this.positionMoves = new HashMap<>();

        this.grid = new char[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                grid[i][j] = ' '; // empty board
            }
        }
    }

    public void addMove(Move move) {
        moves.add(move);
        Position position = move.getMark().getPosition(); // TODO: use getter
        if (!positionMoves.containsKey(position)) {
            positionMoves.put(position, move);
        }

        char symbol = move.getMark().getType().getName().charAt(0);
        grid[position.getRow()][position.getColumn()] = symbol;
    }

    public List<Move> getMoves() {
        return new ArrayList<>(moves);
    }

    public void clearBoard() {
        moves.clear();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    public boolean isOccupied(Position position) {
        return grid[position.getRow()][position.getColumn()] != ' ';
    }

    public boolean hasFiveInARow(int row, int col, char symbol) {
        return checkDirection(row, col, symbol, 1, 0) || // horizontal
               checkDirection(row, col, symbol, 0, 1) || // vertical
               checkDirection(row, col, symbol, 1, 1) || // diagonal /
               checkDirection(row, col, symbol, 1, -1);  // diagonal \
    }

    private boolean checkDirection(int row, int col, char symbol, int dRow, int dCol) {
        int count = 0;

        // forward
        for (int i = 1; i < 5; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < 6 && c >= 0 && c < 6 && grid[r][c] == symbol) {
                count++;
            } else {
                break;
            }
        }

        // backward
        for (int i = 1; i < 5; i++) {
            int r = row - i * dRow;
            int c = col - i * dCol;
            if (r >= 0 && r < 6 && c >= 0 && c < 6 && grid[r][c] == symbol) {
                count++;
            } else {
                break;
            }
        }

        return count + 1 == 5;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Position position = new Position(i, j);
                if (positionMoves.containsKey(position)) {
                    sb.append("X").append(" ");
                } else {
                    sb.append("_").append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
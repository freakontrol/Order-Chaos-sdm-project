package org.example;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Move> moves;

    public Board() {
        this.moves = new ArrayList<>();
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public List<Move> getMoves() {
        return new ArrayList<>(moves); 
    }

    public void clearBoard() {
        moves.clear();
    }

    public boolean isOccupied(Position position) {
        for (Move move : moves) {
            if (move.getMark().getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFiveInARow(int row, int col, char symbol) {
        // placeholder for win condition
        return false;
    }

    @Override
    public String toString() {
        return "Board{moves=" + moves.toString() +
                '}';
    }
}
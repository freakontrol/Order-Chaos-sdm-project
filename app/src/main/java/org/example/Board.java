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

    @Override
    public String toString() {
        return "Board{moves=" + moves.toString() +
                '}';
    }
}
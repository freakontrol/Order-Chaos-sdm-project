package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    private List<Move> moves;
    private HashMap<Position, Move> positionMoves;
    private boolean fiveInLineFound = false; // Internal variable to track if 5 in a line is found

    public Board() {
        this.moves = new ArrayList<>();
        this.positionMoves = new HashMap<>();
    }

    public void addMove(Move move) {
        moves.add(move);
        Position position = move.getMark().getPosition(); // TODO: move getters
        if (!positionMoves.containsKey(position)) {
            positionMoves.put(position, move);

            checkAdjacentMoves(position, move);
        } else {
            throw new IllegalArgumentException("Multiple moves to same position are not allowed");
        }
    }

    private void checkAdjacentMoves(Position position, Move move) {
        Position adjPosUpLeft = null;
        Position adjPosDownRight = null;

        if (!position.isOnLeftEdge() && !position.isOnTopEdge()) {
            adjPosUpLeft = position.getUpLeft();
        }
        if (!position.isOnRightEdge() && !position.isOnBottomEdge()) {
            adjPosDownRight = position.getDownRight();
        }

        int countUpLeft = 0;
        int countDownRight = 0;

        if (adjPosUpLeft != null) {
            Move adjMoveUpLeft = positionMoves.get(adjPosUpLeft);
            while (adjMoveUpLeft != null && adjMoveUpLeft.isMarkTypeEqual(move)) {
                countUpLeft++;
                Position nextPosition = adjMoveUpLeft.getMark().getPosition();
                if (!nextPosition.isOnLeftEdge() && !nextPosition.isOnTopEdge()) {
                    adjMoveUpLeft = positionMoves.get(nextPosition.getUpLeft());
                } else {
                    break;
                }
            }
        }

        if (adjPosDownRight != null) {
            Move adjMoveDownRight = positionMoves.get(adjPosDownRight);
            while (adjMoveDownRight != null && adjMoveDownRight.isMarkTypeEqual(move)) {
                countDownRight++;
                Position nextPosition = adjMoveDownRight.getMark().getPosition();
                if (!nextPosition.isOnRightEdge() && !nextPosition.isOnBottomEdge()) {
                    adjMoveDownRight = positionMoves.get(nextPosition.getDownRight());
                } else {
                    break;
                }
            }
        }

        // Check if the total count of consecutive same-mark moves is exactly 5
        if (countUpLeft + countDownRight == 4) { // 4 because we are counting from the current move position
            fiveInLineFound = true;
        }
    }

    public void clearBoard() {
        moves.clear();
        positionMoves.clear();
        fiveInLineFound = false; // Reset the internal variable
    }

    public boolean isOccupied(Position position) {
        return positionMoves.containsKey(position);
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

    public boolean isFiveInLineFound() {
        return fiveInLineFound;
    }
}
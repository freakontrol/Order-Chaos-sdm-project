package org.example;

import java.util.ArrayList;
import java.util.Collections;
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

    //Adds a move to the board and checks for winning conditions
    public void addMove(Move move) {
        Position position = move.getMark().getPosition();
        Player currentPlayer = move.getPlayer();

        if (!moves.isEmpty() && moves.get(moves.size() - 1).getPlayer().equals(currentPlayer)) {
            throw new IllegalArgumentException("Two subsequent moves from the same player are not allowed");
        }

        if (!positionMoves.containsKey(position)) {
            moves.add(move);
            positionMoves.put(position, move);

            // Check all directions for 5 consecutive moves
            boolean isFiveInRow = checkDirection(position, move, Position::getLeft, Position::getRight);
            boolean isFiveInColumn = checkDirection(position, move, Position::getUp, Position::getDown);
            boolean isFiveInDiag1 = checkDirection(position, move, Position::getUpLeft, Position::getDownRight);
            boolean isFiveInDiag2 = checkDirection(position, move, Position::getUpRight, Position::getDownLeft);

            fiveInLineFound = isFiveInRow || isFiveInColumn || isFiveInDiag1 || isFiveInDiag2;
        } else {
            throw new IllegalArgumentException("Multiple moves to same position are not allowed");
        }
    }

    //Checks a specific direction for 5 consecutive moves
    private boolean checkDirection(Position position, Move move,
                                   GetPosition getPrevious, GetPosition getNext) {
        int count = 0;

        // Check in the negative direction
        Position current = position;
        while (true) {
            try {
                current = getPrevious.get(current);
                if (positionMoves.containsKey(current)) {
                    Move adjMove = positionMoves.get(current);
                    if (adjMove.isMarkTypeEqual(move)) {
                        count++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                break;
            }
        }

        // Check in the positive direction
        current = position;
        while (true) {
            try {
                current = getNext.get(current);
                if (positionMoves.containsKey(current)) {
                    Move adjMove = positionMoves.get(current);
                    if (adjMove.isMarkTypeEqual(move)) {
                        count++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                break;
            }
        }

        return count >= 4; // 4 more means total of 5 including the current move
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
                    Move move = positionMoves.get(position);
                    sb.append(move.getMark().getType().getName()).append(" ");
                } else {
                    sb.append("_").append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //Checks if a winning condition has been found, returns True if winning condition is found, false otherwise
    public boolean isFiveInLineFound() {
        return fiveInLineFound;
    }

    //Returns an unmodifiable view of the list of moves made on the board.

    public List<Move> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    // public List<Move> getMoves() {
    //     return new ArrayList<>(moves);
    // }

    public void printBoard() {
        System.out.println(this.toString());
    }

    @FunctionalInterface
    private interface GetPosition {
        Position get(Position position) throws IllegalArgumentException;
    }
}
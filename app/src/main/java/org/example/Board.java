package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    private List<Move> moves;
    private HashMap<Position, Move> positionMoves;

    class movesInRow {
        private List<Move> upDiagMoves;
        private List<Move> DownDiagMoves;
        private List<Move> horizMoves;
        private List<Move> vertMoves;

        public movesInRow() {
            upDiagMoves = new ArrayList<>();
            DownDiagMoves = new ArrayList<>();
            horizMoves = new ArrayList<>();
            vertMoves = new ArrayList<>();
        }

        public void addUpDiagMove(Move move) {
            upDiagMoves.add(move);
        }
        public void addDownDiagMove(Move move) {
            DownDiagMoves.add(move);
        }
        public void addHorizMove(Move move) {
            horizMoves.add(move);
        }
        public void addVertMove(Move move) {
            vertMoves.add(move);
        }
        public void shareUpDiagMoves(movesInRow other) {
            if (other != null) {
                other.upDiagMoves = this.upDiagMoves;
            }
        }
        public void shareDownDiagMoves(movesInRow other) {
            if (other != null) {
                other.DownDiagMoves = this.DownDiagMoves;
            }
        }
        public void shareHorizMoves(movesInRow other) {
            if (other != null) {
                other.horizMoves = this.horizMoves;
            }
        }
        public void shareVertMoves(movesInRow other) {
            if (other != null) {
                other.vertMoves = this.vertMoves;
            }
        }
    }

    private HashMap<Position, movesInRow> adjMoves;


    public Board() {
        this.moves = new ArrayList<>();
        this.positionMoves = new HashMap<>();
    }

    public void addMove(Move move) {
        moves.add(move);
        Position position = move.getMark().getPosition(); // TODO: use getter
        if (!positionMoves.containsKey(position) && !adjMoves.containsKey(position)) {
            positionMoves.put(position, move);

            // check adjancent moves
            movesInRow adjMoveList = new movesInRow();

            Position adjPosUpLeft;
            Position adjPosDownRight;

            boolean isUpLeft = false;
            boolean isDownRight = false;

           
            if(!position.isOnLeftEdge() || !position.isOnTopEdge()) {
                adjPosUpLeft = position.getUpLeft();
                if (positionMoves.containsKey(adjPosUpLeft)) {
                    if (positionMoves.get(adjPosUpLeft).isMarkTypeEqual(move)) {
                        adjMoves.get(adjPosUpLeft).addDownDiagMove(move);
                        adjMoveList.shareDownDiagMoves(adjMoves.get(adjPosUpLeft));
                        isUpLeft = true;
                    }
                }
            }
            if(!position.isOnRightEdge() || !position.isOnBottomEdge()) {
                adjPosDownRight = position.getDownRight();
                if (positionMoves.containsKey(adjPosDownRight)) {
                    if (positionMoves.get(adjPosDownRight).isMarkTypeEqual(move)) {
                        adjMoves.get(adjPosDownRight).addDownDiagMove(move);
                        adjMoveList.shareDownDiagMoves(adjMoves.get(adjPosDownRight));
                        isDownRight = true;
                    }
                }
            }

            if (isUpLeft && isDownRight) {
                
            }

 
        } else {
            throw new IllegalArgumentException("Multiple moves to same position are not allowed");
        }

    }

    public void clearBoard() {
        moves.clear();
        positionMoves.clear();
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
}
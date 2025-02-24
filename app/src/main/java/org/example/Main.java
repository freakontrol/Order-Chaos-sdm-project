package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Player playerOrder = new Player(Role.ORDER, "John Doe");
        Player playerChaos = new Player(Role.CHAOS, "Jane Doe");

        System.out.println("Player Name: " + playerOrder.getName() + ", Role: " + playerOrder.getRole());
        System.out.println("Player Name: " + playerChaos.getName() + ", Role: " + playerChaos.getRole());

        Board board = new Board();

        // Define positions for ORDER and CHAOS to form diagonals
        Position[] orderPositions = {
            new Position(0, 2),
            new Position(1, 3),
            new Position(2, 4)
        };

        Position[] chaosPositions = {
            new Position(5, 0),
            new Position(4, 1),
            new Position(3, 2)
        };

        // ORDER's turn
        for (Position position : orderPositions) {
            Mark markX = new Mark(position, Type.X);
            Move moveX = new Move(markX, playerOrder);

            //System.out.println("Adding move: " + move.toString());

            board.addMove(moveX);

            // Check for winning condition after each move
            if (board.isFiveInLineFound()) {
                System.out.println("Winning condition found! Player " + playerOrder.getName() + " wins!");
                break;
            }

            // Print the board state after each move
            board.printBoard();
        }

        // CHAOS's turn
        for (Position position : chaosPositions) {
            Mark markO = new Mark(position, Type.O);
            Move moveO = new Move(markO, playerChaos);

            //System.out.println("Adding move: " + move.toString());

            board.addMove(moveO);

            // Check for winning condition after each move
            if (board.isFiveInLineFound()) {
                System.out.println("Winning condition found! Player " + playerChaos.getName() + " wins!");
                break;
            }

            // Print the board state after each move
            board.printBoard();
        }

        // Print all moves on the board
        List<Move> moves = board.getMoves();
        for (Move m : moves) {
            System.out.println(m.toString());
        }

        //Clear the board and print it to verify clearing
        board.clearBoard();

        System.out.println("Board after clearing: " + board);
    }
}
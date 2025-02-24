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
            new Position(2, 4),
            new Position(0, 0),
            new Position(5, 5)
        };

        Position[] chaosPositions = {
            new Position(5, 0),
            new Position(4, 1),
            new Position(3, 2),
            new Position(2, 3),
            new Position(1, 4)
        };

        // Alternate moves between ORDER and CHAOS
        for (int i = 0; i < orderPositions.length + chaosPositions.length; i++) {
            if (i % 2 == 0) { // ORDER's turn
                Mark markX = new Mark(orderPositions[i / 2], Type.X);
            Move moveX = new Move(markX, playerOrder);

            board.addMove(moveX);

            // Print the board state after each move
            board.printBoard();

            // Check for winning condition after each move
            if (board.isFiveInLineFound()) {
                System.out.println("Winning condition found! Player " + playerOrder.getName() + " wins!");
                break;
            }
            } else { // CHAOS's turn
                Mark markO = new Mark(chaosPositions[i / 2], Type.O);
            Move moveO = new Move(markO, playerChaos);

            board.addMove(moveO);

            // Print the board state after each move
            board.printBoard();

            // Check for winning condition after each move
            if (board.isFiveInLineFound()) {
                System.out.println("Winning condition found! Player " + playerChaos.getName() + " wins!");
                break;
            }
        }
        }

        // Print all moves on the board
        List<Move> moves = board.getMoves();
        for (Move m : moves) {
            System.out.println(m.toString());
        }

        // Clear the board and print it to verify clearing
        board.clearBoard();

        System.out.println("Board after clearing: " + board);
    }
}
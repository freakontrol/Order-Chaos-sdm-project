package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Role playerRole = Role.ORDER; 
        Player player = new Player(playerRole, "John Doe");

        System.out.println("Player Name: " + player.getName());
        System.out.println("Player Role: " + player.getRole()); 

        Position position = new Position(5, 3);
        Type type = Type.X;

        Mark mark = new Mark(position, type);

        Move move = new Move(mark, player);

        System.out.println(move.toString());

        Board board = new Board();
        board.addMove(move);

        List<Move> moves = board.getMoves();
        for (Move m : moves) {
            System.out.println(m.toString());
        }

        //board.clearBoard();

        System.out.println("Board: " + board);
    }
}
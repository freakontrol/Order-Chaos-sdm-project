package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class BoardTest {

    private Position position;
    private Type type;
    private Mark mark;
    private Role role;
    private Player player;
    private Move move;
    private Board board;

    @BeforeEach
    public void setUp() {
        position = new Position(10, 20);
        type = Type.X;
        mark = new Mark(position, type);

        role = Role.ORDER;
        player = new Player(role, "John Doe");

        move = new Move(mark, player);

        board = new Board();
    }

    // @Test
    // public void testAddMove() {
    //     board.addMove(move);
    //     assertEquals(1, board.getMoves().size());
    //     assertTrue(board.getMoves().contains(move));
    // }

    // @Test
    // public void testGetMoves() {
    //     board.addMove(move);
    //     List<Move> moves = board.getMoves();
    //     assertEquals(1, moves.size());
    //     assertTrue(moves.contains(move));
    // }

    // @Test
    // public void testClearBoard() {
    //     board.addMove(move);
    //     board.clearBoard();
    //     assertTrue(board.getMoves().isEmpty());
    // }

    // @Test
    // public void testToString() {
    //     String expected = "Board{moves=[]}";
    //     assertEquals(expected, board.toString());

    //     board.addMove(move);
    //     expected = "Board{moves=[Move{mark=Mark{position=Position{row=10, column=20}, type=Type{name='CROSS'}}, player=Player{role=ORDER, name='John Doe'}}]}";
    //     assertEquals(expected, board.toString());
    // }
}
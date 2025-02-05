package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BoardTest {

    private Board board;
    private Position position1;
    private Type typeX;
    private Mark markX;
    private Player playerOrder;
    private Move moveX;

    @BeforeEach
    public void setUp() {
        board = new Board();
        position1 = new Position(0, 1);
        typeX = Type.X;
        markX = new Mark(position1, typeX);
        playerOrder = new Player(Role.ORDER, "ORDER");
        moveX = new Move(markX, playerOrder);
    }
  
    @Test
    public void testAddMove() {
        board.addMove(moveX);
        assertEquals(1, board.getMoves().size());
        assertTrue(board.getMoves().contains(moveX));
    }

    @Test
    public void testGetMoves() {
        board.addMove(moveX);
        List<Move> moves = board.getMoves();
        assertEquals(1, moves.size());
        assertTrue(moves.contains(moveX));
    }

    @Test
    public void testClearBoard() {
        board.addMove(moveX);
        board.clearBoard();
        assertTrue(board.getMoves().isEmpty());
    }

    @Test
    public void testIsOccupied() {
        assertFalse(board.isOccupied(new Position(0, 1)));
        board.addMove(moveX);
        assertTrue(board.isOccupied(new Position(0, 1)));
    }

    @Test
    public void testHasFiveInARow() {
        //horizontal
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(0, i), Type.X), playerOrder));
        }
        assertTrue(board.hasFiveInARow(0, 4, 'X'));

        //vertical
        board.clearBoard();
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(i, 0), Type.X), playerOrder));
        }
        assertTrue(board.hasFiveInARow(4, 0, 'X'));

        //diagonal /
        board.clearBoard();
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(i, i), Type.X), playerOrder));
        }
        assertTrue(board.hasFiveInARow(4, 4, 'X'));

        //diagonal \
        board.clearBoard();
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(i, 4 - i), Type.X), playerOrder));
        }
        assertFalse(board.hasFiveInARow(4, 1, 'X'));

        //no five in a row
        board.clearBoard();
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(i, i % 2), Type.X), playerOrder));
        }
        assertFalse(board.hasFiveInARow(4, 1, 'X'));
    }

    public void testToString() {
        String expected = "   \n" +
                          "   \n" +
                          "   \n" +
                          "   \n" +
                          "   \n" +
                          "   \n";
        assertEquals(expected, board.toString());
        board.addMove(moveX);
        expected = "  X \n" +
                   "     \n" +
                   "     \n" +
                   "     \n" +
                   "     \n" +
                   "     \n";
        assertEquals(expected, board.toString());

        board.clearBoard();
        board.addMove(new Move(new Mark(new Position(0, 1), Type.X), playerOrder));
        board.addMove(new Move(new Mark(new Position(1, 2), Type.X), playerOrder));
        expected = "  X \n" +
                   "   X \n" +
                   "     \n" +
                   "     \n" +
                   "     \n" +
                   "     \n";
        assertEquals(expected, board.toString());
    }
   
}
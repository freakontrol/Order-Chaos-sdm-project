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
    public void testIsOccupied() {
        assertFalse(board.isOccupied(new Position(0, 1)));
        board.addMove(moveX);
        assertTrue(board.isOccupied(new Position(0, 1)));
    }

    @Test
    public void testToString() {
        String expected = "_ _ _ _ _ _ \n" +
                          "_ _ _ _ _ _ \n" +
                          "_ _ _ _ _ _ \n" +
                          "_ _ _ _ _ _ \n" +
                          "_ _ _ _ _ _ \n" +
                          "_ _ _ _ _ _ \n";
        assertEquals(expected, board.toString());

        board.addMove(moveX);
        expected = "_ X _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n";
        assertEquals(expected, board.toString());

        board.clearBoard();
        board.addMove(new Move(new Mark(new Position(0, 2), Type.X), playerOrder));
        board.addMove(new Move(new Mark(new Position(1, 3), Type.O), new Player(Role.CHAOS, "CHAOS")));
        expected = "_ _ X _ _ _ \n" +
                   "_ _ _ O _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n";
        assertEquals(expected, board.toString());
    }

    @Test
    public void testIsFiveInLineFound() {
        // Test horizontal line
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(i, 2), Type.X), playerOrder));
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test vertical line
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(2, i), Type.X), playerOrder));
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test diagonal line (top-left to bottom-right)
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(i, i), Type.X), playerOrder));
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test diagonal line (top-right to bottom-left)
        for (int i = 0; i < 5; i++) {
            board.addMove(new Move(new Mark(new Position(i, 4 - i), Type.X), playerOrder));
        }
        assertTrue(board.isFiveInLineFound());
    }

    @Test
    public void testClearBoard() {
        board.addMove(moveX);
        board.clearBoard();
        assertFalse(board.isOccupied(position1));
        assertEquals(0, board.getMoves().size());
    }

    @Test
    public void testGetMoves() {
        Move moveY = new Move(new Mark(new Position(1, 2), Type.O), playerOrder);
        board.addMove(moveX);
        board.addMove(moveY);

        List<Move> moves = board.getMoves();
        assertEquals(2, moves.size());
        assertTrue(moves.contains(moveX));
        assertTrue(moves.contains(moveY));
    }

    @Test
    public void testAddMove() {
        Move moveY = new Move(new Mark(new Position(1, 2), Type.O), playerOrder);
        board.addMove(moveX);
        board.addMove(moveY);

        assertThrows(IllegalArgumentException.class, () -> {
            board.addMove(moveX); // Trying to add the same move again
        });
    }
}
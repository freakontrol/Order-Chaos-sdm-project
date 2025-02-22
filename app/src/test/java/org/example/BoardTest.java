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
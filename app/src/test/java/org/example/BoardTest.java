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
    private Player playerChaos;
    private Move moveO;

    @BeforeEach
    public void setUp() {
        board = new Board();
        position1 = new Position(0, 2);
        typeX = Type.X;
        markX = new Mark(position1, typeX);
        playerOrder = new Player(Role.ORDER, "ORDER");
        moveX = new Move(markX, playerOrder);

        position1 = new Position(1, 3);
        markX = new Mark(position1, typeX);
        playerChaos = new Player(Role.CHAOS, "CHAOS");
        moveO = new Move(new Mark(new Position(1, 2), Type.O), playerChaos);
    }

    @Test
    public void testIsOccupied() {
        assertFalse(board.isOccupied(new Position(0, 2)));
        board.addMove(moveX);
        assertTrue(board.isOccupied(new Position(0, 2)));
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
        expected = "_ _ X _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n";
        assertEquals(expected, board.toString());

        board.clearBoard();
        board.addMove(new Move(new Mark(new Position(0, 2), Type.X), playerOrder));
        board.addMove(moveO);
        expected = "_ _ X _ _ _ \n" +
                   "_ _ O _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n" +
                   "_ _ _ _ _ _ \n";
        assertEquals(expected, board.toString());
    }

    @Test
    public void testIsFiveInLineFound() {
        // Test horizontal line with alternating players
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                board.addMove(new Move(new Mark(new Position(i, 2), Type.X), playerOrder));
            } else {
                board.addMove(new Move(new Mark(new Position(i, 2), Type.X), playerChaos));
            }
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test vertical line with alternating players
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                board.addMove(new Move(new Mark(new Position(2, i), Type.O), playerOrder));
            } else {
                board.addMove(new Move(new Mark(new Position(2, i), Type.O), playerChaos));
            }
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test diagonal line (top-left to bottom-right) with alternating players
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                board.addMove(new Move(new Mark(new Position(i, i), Type.X), playerOrder));
            } else {
                board.addMove(new Move(new Mark(new Position(i, i), Type.X), playerChaos));
            }
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test diagonal line (top-right to bottom-left) with alternating players
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                board.addMove(new Move(new Mark(new Position(i, 4 - i), Type.O), playerOrder));
            } else {
                board.addMove(new Move(new Mark(new Position(i, 4 - i), Type.O), playerChaos));
            }
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
        board.addMove(moveO);

        List<Move> moves = board.getMoves();
        assertEquals(2, moves.size());
        assertTrue(moves.contains(moveX));
        assertTrue(moves.contains(moveO));
    }

    @Test
    public void testAddMove() {
        Move moveY = new Move(new Mark(new Position(1, 2), Type.O), playerOrder);
        board.addMove(moveX);
        board.addMove(moveO);

        assertThrows(IllegalArgumentException.class, () -> {
            board.addMove(moveX); // Trying to add the same move again
        });

        assertThrows(IllegalArgumentException.class, () -> {
            board.addMove(new Move(new Mark(new Position(1, 2), Type.X), playerOrder)); // Trying to add a move from the same player subsequently
        });
    }

    @Test
    public void testAlternatePlayers() {
        board.addMove(moveX); // ORDER moves first

        assertThrows(IllegalArgumentException.class, () -> {
            board.addMove(new Move(new Mark(new Position(1, 3), Type.X), playerOrder)); // ORDER tries to move again
        });

        board.addMove(moveO); // CHAOS moves next

        assertThrows(IllegalArgumentException.class, () -> {
            board.addMove(new Move(new Mark(new Position(2, 4), Type.O), playerChaos)); // CHAOS tries to move again
        });
    }
        
    @Test
    public void testSixInLineNotWinning() {
        // Place six consecutive marks in a row with alternating players
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                board.addMove(new Move(new Mark(new Position(i, 2), Type.X), playerOrder));
            } else {
                board.addMove(new Move(new Mark(new Position(i, 2), Type.O), playerChaos));
            }
        }
        assertFalse(board.isFiveInLineFound());

        // Reset the board and place six consecutive marks in a column with alternating players
        board.clearBoard();
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                board.addMove(new Move(new Mark(new Position(2, i), Type.X), playerOrder));
            } else {
                board.addMove(new Move(new Mark(new Position(2, i), Type.O), playerChaos));
            }
        }
        assertFalse(board.isFiveInLineFound());

        // Reset the board and place six consecutive marks in a diagonal line (top-left to bottom-right) with alternating players
        board.clearBoard();
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                board.addMove(new Move(new Mark(new Position(i, i), Type.X), playerOrder));
            } else {
                board.addMove(new Move(new Mark(new Position(i, i), Type.O), playerChaos));
            }
        }
        assertFalse(board.isFiveInLineFound());

        // Reset the board and place six consecutive marks in a diagonal line (top-right to bottom-left) with alternating players
        board.clearBoard();
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                board.addMove(new Move(new Mark(new Position(i, 5 - i), Type.X), playerOrder));
            } else {
                board.addMove(new Move(new Mark(new Position(i, 5 - i), Type.O), playerChaos));
            }
        }
        assertFalse(board.isFiveInLineFound());
    }
}
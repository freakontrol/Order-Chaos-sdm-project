package orderandchaos.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import orderandchaos.exceptions.InvalidPlayerException;
import orderandchaos.exceptions.OccupiedPositionException;

class BoardTest {

    private Board board;
    private Position position1;
    private Type typeX;
    private Mark markX;
    private Player playerOrder;
    private Move moveX;
    private Player playerChaos;
    private Move moveO;

    @BeforeEach
    void setUp() {
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
    void testAddDuplicateMove() {
        try {
            board.addMove(moveX);
            board.addMove(moveO);
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        assertThrows(OccupiedPositionException.class, () -> {
            board.addMove(moveX); // Trying to add the same move again
        });
    }

    @Test
    void testAddSamePlayerMove() {
        try {
            board.addMove(moveX);
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        Move newMove = new Move(new Mark(new Position(2, 2), typeX), playerOrder);

        assertThrows(InvalidPlayerException.class, () -> {
            board.addMove(newMove); // Trying to add a move from the same player subsequently
        });
    }

    @Test
    void testAlternatePlayers() {
        try {
            board.addMove(moveX); // ORDER moves first

            Move newOrderMove = new Move(new Mark(new Position(1, 3), Type.X), playerOrder);

            assertThrows(InvalidPlayerException.class, () -> {
                board.addMove(newOrderMove); // ORDER tries to move again
            });

            board.addMove(moveO); // CHAOS moves next

            Move newChaosMove = new Move(new Mark(new Position(2, 4), Type.O), playerChaos);

            assertThrows(InvalidPlayerException.class, () -> {
                board.addMove(newChaosMove); // CHAOS tries to move again
            });
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testIsOccupied() {
        assertFalse(board.isOccupied(new Position(0, 2)));
        try {
            board.addMove(moveX);
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertTrue(board.isOccupied(new Position(0, 2)));
    }

    @Test
    void testToString() {
        String expected = """
            _ _ _ _ _ _
            _ _ _ _ _ _
            _ _ _ _ _ _
            _ _ _ _ _ _
            _ _ _ _ _ _
            _ _ _ _ _ _
            """;
        assertEquals(expected, board.toString());

        try {
            board.addMove(moveX);
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        expected = """
                          _ _ X _ _ _
                          _ _ _ _ _ _
                          _ _ _ _ _ _
                          _ _ _ _ _ _
                          _ _ _ _ _ _
                          _ _ _ _ _ _
                          """;
        assertEquals(expected, board.toString());

        board.clearBoard();
        try {
            board.addMove(new Move(new Mark(new Position(0, 2), Type.X), playerOrder));
            board.addMove(moveO);
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        expected = """
                          _ _ X _ _ _
                          _ _ O _ _ _
                          _ _ _ _ _ _
                          _ _ _ _ _ _
                          _ _ _ _ _ _
                          _ _ _ _ _ _
                          """;
        assertEquals(expected, board.toString());
    }

    @Test
    void testIsFiveInLineFound() {
        // Test horizontal line with alternating players
        for (int i = 0; i < 5; i++) {
            try {
                if (i % 2 == 0) {
                    board.addMove(new Move(new Mark(new Position(i, 2), Type.X), playerOrder));
                } else {
                    board.addMove(new Move(new Mark(new Position(i, 2), Type.X), playerChaos));
                }
            } catch (InvalidPlayerException | OccupiedPositionException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test vertical line with alternating players
        for (int i = 0; i < 5; i++) {
            try {
                if (i % 2 == 0) {
                    board.addMove(new Move(new Mark(new Position(2, i), Type.O), playerOrder));
                } else {
                    board.addMove(new Move(new Mark(new Position(2, i), Type.O), playerChaos));
                }
            } catch (InvalidPlayerException | OccupiedPositionException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test diagonal line (top-left to bottom-right) with alternating players
        for (int i = 0; i < 5; i++) {
            try {
                if (i % 2 == 0) {
                    board.addMove(new Move(new Mark(new Position(i, i), Type.X), playerOrder));
                } else {
                    board.addMove(new Move(new Mark(new Position(i, i), Type.X), playerChaos));
                }
            } catch (InvalidPlayerException | OccupiedPositionException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
        assertTrue(board.isFiveInLineFound());

        // Reset the board
        board.clearBoard();

        // Test diagonal line (top-right to bottom-left) with alternating players
        for (int i = 0; i < 5; i++) {
            try {
                if (i % 2 == 0) {
                    board.addMove(new Move(new Mark(new Position(i, 4 - i), Type.O), playerOrder));
                } else {
                    board.addMove(new Move(new Mark(new Position(i, 4 - i), Type.O), playerChaos));
                }
            } catch (InvalidPlayerException | OccupiedPositionException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
        assertTrue(board.isFiveInLineFound());
    }

    @Test
    void testClearBoard() {
        try {
            board.addMove(moveX);
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        board.clearBoard();
        assertFalse(board.isOccupied(position1));
        assertEquals(0, board.getMoves().size());
    }

    @Test
    void testGetMoves() {
        try {
            board.addMove(moveX);
            board.addMove(moveO);
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        List<Move> moves = board.getMoves();
        assertEquals(2, moves.size());
        assertTrue(moves.contains(moveX));
        assertTrue(moves.contains(moveO));
    }

    @Test
    void testSixInLineNotWinning() {
        // Place six consecutive marks in a row with alternating players
        for (int i = 0; i < 6; i++) {
            try {
                if (i % 2 == 0) {
                    board.addMove(new Move(new Mark(new Position(i, 2), Type.X), playerOrder));
                } else {
                    board.addMove(new Move(new Mark(new Position(i, 2), Type.O), playerChaos));
                }
            } catch (InvalidPlayerException | OccupiedPositionException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
        assertFalse(board.isFiveInLineFound());

        // Reset the board and place six consecutive marks in a column with alternating players
        board.clearBoard();
        for (int i = 0; i < 6; i++) {
            try {
                if (i % 2 == 0) {
                    board.addMove(new Move(new Mark(new Position(2, i), Type.X), playerOrder));
                } else {
                    board.addMove(new Move(new Mark(new Position(2, i), Type.O), playerChaos));
                }
            } catch (InvalidPlayerException | OccupiedPositionException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
        assertFalse(board.isFiveInLineFound());

        // Reset the board and place six consecutive marks in a diagonal line (top-left to bottom-right) with alternating players
        board.clearBoard();
        for (int i = 0; i < 6; i++) {
            try {
                if (i % 2 == 0) {
                    board.addMove(new Move(new Mark(new Position(i, i), Type.X), playerOrder));
                } else {
                    board.addMove(new Move(new Mark(new Position(i, i), Type.O), playerChaos));
                }
            } catch (InvalidPlayerException | OccupiedPositionException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
        assertFalse(board.isFiveInLineFound());

        // Reset the board and place six consecutive marks in a diagonal line (top-right to bottom-left) with alternating players
        board.clearBoard();
        for (int i = 0; i < 6; i++) {
            try {
                if (i % 2 == 0) {
                    board.addMove(new Move(new Mark(new Position(i, 5 - i), Type.X), playerOrder));
                } else {
                    board.addMove(new Move(new Mark(new Position(i, 5 - i), Type.O), playerChaos));
                }
            } catch (InvalidPlayerException | OccupiedPositionException e) {
                fail("Unexpected exception: " + e.getMessage());
            }
        }
        assertFalse(board.isFiveInLineFound());
    }

    @Test
    void testIsBoardFull() {
        // Create a list to hold all positions on the 6x6 board
        List<Position> allPositions = new ArrayList<>();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                allPositions.add(new Position(row, col));
            }
        }

        // Fill the board completely with alternating X and O marks
        try {
            for (int i = 0; i < allPositions.size(); i++) {
                Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
                Type markType = (i % 2 == 0) ? typeX : Type.O;
                Position position = allPositions.get(i);
                Mark mark = new Mark(position, markType);
                Move move = new Move(mark, currentPlayer);
                board.addMove(move);
            }
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception while adding moves: " + e.getMessage());
        }

        // Assert
        assertTrue(board.isBoardFull(),
            "isBoardFull() should return true after 36 moves");

        assertEquals(36, board.getMoves().size(),
            "Move list size should be exactly 36 when board is full");
    }
}

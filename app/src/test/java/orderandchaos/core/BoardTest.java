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

    private void safeAddMove(Move move) {
        try {
            board.addMove(move);
        } catch (InvalidPlayerException | OccupiedPositionException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testAddDuplicateMove() {
        safeAddMove(moveX);
        safeAddMove(moveO);
        assertThrows(OccupiedPositionException.class, () -> board.addMove(moveX));
    }

    @Test
    void testAddSamePlayerMove() {
        safeAddMove(moveX);
        Move newMove = new Move(new Mark(new Position(2, 2), typeX), playerOrder);
        assertThrows(InvalidPlayerException.class, () -> board.addMove(newMove));
    }

    @Test
    void testAlternatePlayers() {
        safeAddMove(moveX);
        Move newOrderMove = new Move(new Mark(new Position(1, 3), Type.X), playerOrder);
        assertThrows(InvalidPlayerException.class, () -> board.addMove(newOrderMove));

        safeAddMove(moveO);
        Move newChaosMove = new Move(new Mark(new Position(2, 4), Type.O), playerChaos);
        assertThrows(InvalidPlayerException.class, () -> board.addMove(newChaosMove));
    }

    @Test
    void testIsOccupied() {
        assertFalse(board.isOccupied(new Position(0, 2)));
        safeAddMove(moveX);
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

        safeAddMove(moveX);
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
        safeAddMove(new Move(new Mark(new Position(0, 2), Type.X), playerOrder));
        safeAddMove(moveO);

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
        for (int i = 0; i < 5; i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            safeAddMove(new Move(new Mark(new Position(i, 2), Type.X), currentPlayer));
        }
        assertTrue(board.isFiveInLineFound());

        board.clearBoard();

        for (int i = 0; i < 5; i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            safeAddMove(new Move(new Mark(new Position(2, i), Type.O), currentPlayer));
        }
        assertTrue(board.isFiveInLineFound());

        board.clearBoard();

        for (int i = 0; i < 5; i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            safeAddMove(new Move(new Mark(new Position(i, i), Type.X), currentPlayer));
        }
        assertTrue(board.isFiveInLineFound());

        board.clearBoard();

        for (int i = 0; i < 5; i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            safeAddMove(new Move(new Mark(new Position(i, 4 - i), Type.O), currentPlayer));
        }
        assertTrue(board.isFiveInLineFound());
    }

    @Test
    void testClearBoard() {
        safeAddMove(moveX);
        board.clearBoard();
        assertFalse(board.isOccupied(position1));
        assertEquals(0, board.getMoves().size());
    }

    @Test
    void testGetMoves() {
        safeAddMove(moveX);
        safeAddMove(moveO);

        List<Move> moves = board.getMoves();
        assertEquals(2, moves.size());
        assertTrue(moves.contains(moveX));
        assertTrue(moves.contains(moveO));
    }

    @Test
    void testSixInLineNotWinning() {
        for (int i = 0; i < 6; i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            Type type = (i % 2 == 0) ? Type.X : Type.O;
            safeAddMove(new Move(new Mark(new Position(i, 2), type), currentPlayer));
        }
        assertFalse(board.isFiveInLineFound());

        board.clearBoard();

        for (int i = 0; i < 6; i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            Type type = (i % 2 == 0) ? Type.X : Type.O;
            safeAddMove(new Move(new Mark(new Position(2, i), type), currentPlayer));
        }
        assertFalse(board.isFiveInLineFound());

        board.clearBoard();

        for (int i = 0; i < 6; i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            Type type = (i % 2 == 0) ? Type.X : Type.O;
            safeAddMove(new Move(new Mark(new Position(i, i), type), currentPlayer));
        }
        assertFalse(board.isFiveInLineFound());

        board.clearBoard();

        for (int i = 0; i < 6; i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            Type type = (i % 2 == 0) ? Type.X : Type.O;
            safeAddMove(new Move(new Mark(new Position(i, 5 - i), type), currentPlayer));
        }
        assertFalse(board.isFiveInLineFound());
    }

    @Test
    void testIsBoardFull() {
        List<Position> allPositions = new ArrayList<>();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                allPositions.add(new Position(row, col));
            }
        }

        for (int i = 0; i < allPositions.size(); i++) {
            Player currentPlayer = (i % 2 == 0) ? playerOrder : playerChaos;
            Type markType = (i % 2 == 0) ? typeX : Type.O;
            safeAddMove(new Move(new Mark(allPositions.get(i), markType), currentPlayer));
        }

        assertTrue(board.isBoardFull(), "isBoardFull() should return true after 36 moves");
        assertEquals(36, board.getMoves().size(), "Move list size should be exactly 36 when board is full");
    }
}
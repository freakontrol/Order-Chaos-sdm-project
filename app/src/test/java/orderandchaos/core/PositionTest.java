package orderandchaos.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import orderandchaos.exceptions.OutOfBoundsException;

class PositionTest {

    @Test
    void testConstructor() {
        Position position = new Position(1, 2);
        assertEquals(1, position.getRow());
        assertEquals(2, position.getColumn());
    }

    @Test
    void testConstructorWithNegativeRow() {
        assertThrows(OutOfBoundsException.class, () -> {
            new Position(-1, 2);
        });
    }

    @Test
    void testConstructorWithNegativeColumn() {
        assertThrows(OutOfBoundsException.class, () -> {
            new Position(1, -2);
        });
    }

    @Test
    void testSetRow() {
        Position position = new Position(0, 0);
        position.setRow(5);
        assertEquals(5, position.getRow());
    }

    @Test
    void testSetRowWithNegativeValue() {
        Position position = new Position(0, 0);
        assertThrows(OutOfBoundsException.class, () -> {
            position.setRow(-1);
        });
    }

    @Test
    void testSetColumn() {
        Position position = new Position(0, 0);
        position.setColumn(5);
        assertEquals(5, position.getColumn());
    }

    @Test
    void testSetColumnWithNegativeValue() {
        Position position = new Position(0, 0);
        assertThrows(OutOfBoundsException.class, () -> {
            position.setColumn(-1);
        });
    }

    @Test
    void testEquals() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);
        Position pos3 = new Position(2, 3);

        assertEquals(pos1, pos2);
        assertNotEquals(pos1, pos3);
        assertNotEquals(null, pos1);
    }

    @Test
    void testHashCode() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);
        Position pos3 = new Position(2, 3);

        assertEquals(pos1.hashCode(), pos2.hashCode());
        assertNotEquals(pos1.hashCode(), pos3.hashCode());
    }

    @Test
    void testGetUp() {
        Position position = new Position(5, 2);
        Position upPosition = position.getUp();
        assertEquals(4, upPosition.getRow());
        assertEquals(2, upPosition.getColumn());

        Position newPosition = new Position(0, 2);

        assertThrows(OutOfBoundsException.class, newPosition::getUp);
    }

    @Test
    void testGetDown() {
        Position position = new Position(5, 2);
        Position downPosition = position.getDown();
        assertEquals(6, downPosition.getRow());
        assertEquals(2, downPosition.getColumn());

        Position newPosition = new Position(6, 2);

        assertThrows(OutOfBoundsException.class, newPosition::getDown);
    }

    @Test
    void testGetLeft() {
        Position position = new Position(5, 2);
        Position leftPosition = position.getLeft();
        assertEquals(5, leftPosition.getRow());
        assertEquals(1, leftPosition.getColumn());

        Position newPosition = new Position(5, 0);

        assertThrows(OutOfBoundsException.class, newPosition::getLeft);
    }

    @Test
    void testGetRight() {
        Position position = new Position(5, 2);
        Position rightPosition = position.getRight();
        assertEquals(5, rightPosition.getRow());
        assertEquals(3, rightPosition.getColumn());

        Position newPosition = new Position(5, 6);

        assertThrows(OutOfBoundsException.class, newPosition::getRight);
    }

    @Test
    void testGetUpLeft() {
        Position position = new Position(5, 2);
        Position upLeftPosition = position.getUpLeft();
        assertEquals(4, upLeftPosition.getRow());
        assertEquals(1, upLeftPosition.getColumn());

        Position newPosition = new Position(0, 2);

        assertThrows(OutOfBoundsException.class, newPosition::getUpLeft);
    }

    @Test
    void testGetUpRight() {
        Position position = new Position(5, 2);
        Position upRightPosition = position.getUpRight();
        assertEquals(4, upRightPosition.getRow());
        assertEquals(3, upRightPosition.getColumn());

        Position newPosition = new Position(0, 6);

        assertThrows(OutOfBoundsException.class, newPosition::getUpRight);
    }

    @Test
    void testGetDownLeft() {
        Position position = new Position(5, 2);
        Position downLeftPosition = position.getDownLeft();
        assertEquals(6, downLeftPosition.getRow());
        assertEquals(1, downLeftPosition.getColumn());

        Position newPosition = new Position(6, 0);

        assertThrows(OutOfBoundsException.class, newPosition::getDownLeft);
    }

    @Test
    void testGetDownRight() {
        Position position = new Position(5, 2);
        Position downRightPosition = position.getDownRight();
        assertEquals(6, downRightPosition.getRow());
        assertEquals(3, downRightPosition.getColumn());

        Position newPosition = new Position(6, 6);

        assertThrows(OutOfBoundsException.class, newPosition::getDownRight);
    }

    @Test
    void testIsOnLeftEdge() {
        Position position = new Position(0, 1);
        assertTrue(position.isOnLeftEdge());
        position.setColumn(0);
        assertFalse(position.isOnLeftEdge());

        position.setRow(5);
        assertFalse(position.isOnLeftEdge());
    }

    @Test
    void testIsOnRightEdge() {
        Position position = new Position(5, 5);
        assertTrue(position.isOnRightEdge());
        position.setColumn(4);
        assertFalse(position.isOnRightEdge());
    }

    @Test
    void testIsOnTopEdge() {
        Position position = new Position(1, 0);
        assertTrue(position.isOnTopEdge());
        position.setColumn(1);
        assertFalse(position.isOnTopEdge());

        position.setRow(5);
        assertFalse(position.isOnTopEdge());
    }

    @Test
    void testIsOnBottomEdge() {
        Position position = new Position(5, 5);
        assertTrue(position.isOnBottomEdge());
        position.setRow(0);
        assertFalse(position.isOnBottomEdge());
    }
}
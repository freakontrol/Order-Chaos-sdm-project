package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void testConstructor() {
        Position position = new Position(1, 2);
        assertEquals(1, position.getRow());
        assertEquals(2, position.getColumn());
    }

    @Test
    public void testConstructorWithNegativeRow() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(-1, 2);
        });
    }

    @Test
    public void testConstructorWithNegativeColumn() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(1, -2);
        });
    }

    @Test
    public void testSetRow() {
        Position position = new Position(0, 0);
        position.setRow(5);
        assertEquals(5, position.getRow());
    }

    @Test
    public void testSetRowWithNegativeValue() {
        Position position = new Position(0, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            position.setRow(-1);
        });
    }

    @Test
    public void testSetColumn() {
        Position position = new Position(0, 0);
        position.setColumn(5);
        assertEquals(5, position.getColumn());
    }

    @Test
    public void testSetColumnWithNegativeValue() {
        Position position = new Position(0, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            position.setColumn(-1);
        });
    }

    @Test
    public void testEquals() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);
        Position pos3 = new Position(2, 3);

        assertTrue(pos1.equals(pos2));
        assertFalse(pos1.equals(pos3));
        assertFalse(pos1.equals(null));
    }

    @Test
    public void testHashCode() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(1, 2);
        Position pos3 = new Position(2, 3);

        assertEquals(pos1.hashCode(), pos2.hashCode());
        assertNotEquals(pos1.hashCode(), pos3.hashCode());
    }

    @Test
    public void testGetUp() {
        Position position = new Position(5, 2);
        Position upPosition = position.getUp();
        assertEquals(4, upPosition.getRow());
        assertEquals(2, upPosition.getColumn());

        assertThrows(IllegalArgumentException.class, () -> {
            new Position(0, 2).getUp();
        });
    }

    @Test
    public void testGetDown() {
        Position position = new Position(5, 2);
        Position downPosition = position.getDown();
        assertEquals(6, downPosition.getRow());
        assertEquals(2, downPosition.getColumn());

        assertThrows(IllegalArgumentException.class, () -> {
            new Position(6, 2).getDown();
        });
    }

    @Test
    public void testGetLeft() {
        Position position = new Position(5, 2);
        Position leftPosition = position.getLeft();
        assertEquals(5, leftPosition.getRow());
        assertEquals(1, leftPosition.getColumn());

        assertThrows(IllegalArgumentException.class, () -> {
            new Position(5, 0).getLeft();
        });
    }

    @Test
    public void testGetRight() {
        Position position = new Position(5, 2);
        Position rightPosition = position.getRight();
        assertEquals(5, rightPosition.getRow());
        assertEquals(3, rightPosition.getColumn());

        assertThrows(IllegalArgumentException.class, () -> {
            new Position(5, 6).getRight();
        });
    }

    @Test
    public void testGetUpLeft() {
        Position position = new Position(5, 2);
        Position upLeftPosition = position.getUpLeft();
        assertEquals(4, upLeftPosition.getRow());
        assertEquals(1, upLeftPosition.getColumn());

        assertThrows(IllegalArgumentException.class, () -> {
            new Position(0, 2).getUpLeft();
        });
    }

    @Test
    public void testGetUpRight() {
        Position position = new Position(5, 2);
        Position upRightPosition = position.getUpRight();
        assertEquals(4, upRightPosition.getRow());
        assertEquals(3, upRightPosition.getColumn());

        assertThrows(IllegalArgumentException.class, () -> {
            new Position(0, 6).getUpRight();
        });
    }

    @Test
    public void testGetDownLeft() {
        Position position = new Position(5, 2);
        Position downLeftPosition = position.getDownLeft();
        assertEquals(6, downLeftPosition.getRow());
        assertEquals(1, downLeftPosition.getColumn());

        assertThrows(IllegalArgumentException.class, () -> {
            new Position(6, 0).getDownLeft();
        });
    }

    @Test
    public void testGetDownRight() {
        Position position = new Position(5, 2);
        Position downRightPosition = position.getDownRight();
        assertEquals(6, downRightPosition.getRow());
        assertEquals(3, downRightPosition.getColumn());

        assertThrows(IllegalArgumentException.class, () -> {
            new Position(6, 6).getDownRight();
        });
    }
}
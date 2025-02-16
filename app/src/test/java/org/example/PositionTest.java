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
}
package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MarkTest {
    private Position position;
    private Type type;
    private Mark mark;

    @BeforeEach
    public void setUp() {
        position = new Position(1, 2);
        type = Type.CROSS;
        mark = new Mark(position, type);
    }

    @Test
    public void testConstructor() {
        assertNotNull(mark);
    }

    @Test
    public void testGetPosition() {
        assertEquals(position, mark.getPosition());
    }

    @Test
    public void testSetPosition() {
        Position newPosition = new Position(3, 4);
        mark.setPosition(newPosition);
        assertEquals(newPosition, mark.getPosition());
    }

    @Test
    public void testGetType() {
        assertEquals(type, mark.getType());
    }

    @Test
    public void testSetType() {
        Type newType = Type.CIRCLE;
        mark.setType(newType);
        assertEquals(newType, mark.getType());
    }
}

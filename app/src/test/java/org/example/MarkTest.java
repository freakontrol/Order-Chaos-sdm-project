package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarkTest {
    private Position position;
    private Type type;
    private Mark mark;

    @BeforeEach
    void setUp() {
        position = new Position(1, 2);
        type = Type.X;
        mark = new Mark(position, type);
    }

    @Test
    void testConstructor() {
        assertNotNull(mark);
    }

    @Test
    void testGetPosition() {
        assertEquals(position, mark.getPosition());
    }

    @Test
    void testSetPosition() {
        Position newPosition = new Position(3, 4);
        mark.setPosition(newPosition);
        assertEquals(newPosition, mark.getPosition());
    }

    @Test
    void testGetType() {
        assertEquals(type, mark.getType());
    }

    @Test
    void testSetType() {
        Type newType = Type.O;
        mark.setType(newType);
        assertEquals(newType, mark.getType());
    }
}
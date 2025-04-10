package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TypeTest {

    @Test
    void testEnumValues() {
        assertEquals("X", Type.X.getName());
        assertEquals("O", Type.O.getName());
    }

    @Test
    void testToString() {
        assertEquals("Type{name='X'}", Type.X.toString());
        assertEquals("Type{name='O'}", Type.O.toString());
    }
}
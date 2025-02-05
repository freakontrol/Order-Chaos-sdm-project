package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TypeTest {

    @Test
    public void testEnumValues() {
        assertEquals("X", Type.X.getName());
        assertEquals("O", Type.O.getName());
    }

    @Test
    public void testToString() {
        assertEquals("Type{name='X'}", Type.X.toString());
        assertEquals("Type{name='O'}", Type.O.toString());
    }
}

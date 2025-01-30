package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TypeTest {

    @Test
    public void testEnumValues() {
        assertEquals(Type.CROSS, Type.valueOf("CROSS"));
        assertEquals(Type.CIRCLE, Type.valueOf("CIRCLE"));
    }

    @Test
    public void testEnumOrdinal() {
        assertEquals(0, Type.CROSS.ordinal());
        assertEquals(1, Type.CIRCLE.ordinal());
    }
}

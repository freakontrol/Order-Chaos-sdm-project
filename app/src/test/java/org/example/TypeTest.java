package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TypeTest {

    @Test
    public void testConstructorAndGetName() {
        Type type = new Type("CROSS");
        assertEquals("CROSS", type.getName());
    }

    @Test
    public void testSetName() {
        Type type = new Type("CIRCLE");
        type.setName("CROSS");
        assertEquals("CROSS", type.getName());
    }

    @Test
    public void testToString() {
        Type type = new Type("CROSS");
        assertEquals("Type{name='CROSS'}", type.toString());

        Type type2 = new Type("CIRCLE");
        assertEquals("Type{name='CIRCLE'}", type2.toString());
    }
}

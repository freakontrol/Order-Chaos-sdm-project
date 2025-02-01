package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testPlayerRoleOrder() {
        Player player = new Player(Role.ORDER, "John Doe");
        assertEquals(Role.ORDER, player.getRole());
    }

    @Test
    public void testPlayerRoleChaos() {
        Player player = new Player(Role.CHAOS, "Jane Smith");
        assertEquals(Role.CHAOS, player.getRole());
    }

    @Test
    public void testPlayerNameOrder() {
        String name = "John Doe";
        Player player = new Player(Role.ORDER, name);
        assertEquals(name, player.getName());
    }

    @Test
    public void testPlayerNameChaos() {
        String name = "Jane Smith";
        Player player = new Player(Role.CHAOS, name);
        assertEquals(name, player.getName());
    }
}

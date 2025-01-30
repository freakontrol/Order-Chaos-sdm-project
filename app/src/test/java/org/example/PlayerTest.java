package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testPlayerRoleOrder() {
        Player player = new Player(Role.ORDER);
        assertEquals(Role.ORDER, player.getRole());
    }

    @Test
    public void testPlayerRoleChaos() {
        Player player = new Player(Role.CHAOS);
        assertEquals(Role.CHAOS, player.getRole());
    }
}

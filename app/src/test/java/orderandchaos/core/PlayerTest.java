package orderandchaos.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void testPlayerRoleOrder() {
        Player player = new Player(Role.ORDER, "John Doe");
        assertEquals(Role.ORDER, player.getRole());
    }

    @Test
    void testPlayerRoleChaos() {
        Player player = new Player(Role.CHAOS, "Jane Smith");
        assertEquals(Role.CHAOS, player.getRole());
    }

    @Test
    void testPlayerNameOrder() {
        String name = "John Doe";
        Player player = new Player(Role.ORDER, name);
        assertEquals(name, player.getName());
    }

    @Test
    void testPlayerNameChaos() {
        String name = "Jane Smith";
        Player player = new Player(Role.CHAOS, name);
        assertEquals(name, player.getName());
    }
}

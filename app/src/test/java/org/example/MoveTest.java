package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoveTest {

    private Position position1;
    private Type typeX;
    private Mark markX;
    private Role roleOrder;
    private Player playerOrder;
    private Move moveX;

    private Position position2;
    private Type typeO;
    private Mark markO;
    private Role roleChaos;
    private Player playerChaos;
    private Move moveO;

    @BeforeEach
    void setUp() {
        // Setup for the first move with X type
        position1 = new Position(10, 20);
        typeX = Type.X;
        markX = new Mark(position1, typeX);

        roleOrder = Role.ORDER;
        playerOrder = new Player(roleOrder, "John Doe");
        moveX = new Move(markX, playerOrder);

        // Setup for the second move with O type
        position2 = new Position(30, 40);
        typeO = Type.O;
        markO = new Mark(position2, typeO);

        roleChaos = Role.CHAOS;
        playerChaos = new Player(roleChaos, "Jane Smith");
        moveO = new Move(markO, playerChaos);
    }

    @Test
    void testConstructor() {
        assertNotNull(moveX);
    }

    @Test
    void testGetMark() {
        assertEquals(markX, moveX.getMark());
    }

    @Test
    void testGetPlayer() {
        assertEquals(playerOrder, moveX.getPlayer());
    }

    @Test
    void testToString() {
        String expected = "Move{mark=Mark{position=Position{row=10, column=20}, type=Type{name='X'}}, player=Player{role=ORDER, name='John Doe'}}";
        assertEquals(expected, moveX.toString());
    }

    @Test
    void testIsMarkTypeEqual() {
        // Test with the same mark type
        Move anotherMoveX = new Move(markX, playerChaos);
        assertTrue(moveX.isMarkTypeEqual(anotherMoveX));

        // Test with different mark types
        assertFalse(moveX.isMarkTypeEqual(moveO));
    }
    @Test

    void PlayerAndMarkCorrectlyAssigned() {
    Player player1 = new Player(Role.ORDER, "Dylan");
    Mark mark1 = new Mark(new Position(2, 3), Type.X);
    Move move = new Move(mark1, player1);

    assertEquals("Dylan", move.getPlayer().getName());
    assertEquals(Type.X, move.getMark().getType());

    assertNotEquals("Bob", move.getPlayer().getName());
    assertNotEquals(Type.O, move.getMark().getType());
}
    
}


package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveTest {

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
    public void setUp() {
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
    public void testConstructor() {
        assertNotNull(moveX);
    }

    @Test
    public void testGetMark() {
        assertEquals(markX, moveX.getMark());
    }

    @Test
    public void testSetMark() {
        Position newPosition = new Position(30, 40);
        Type newType = Type.O;
        Mark newMark = new Mark(newPosition, newType);

        moveX.setMark(newMark);
        assertEquals(newMark, moveX.getMark());
    }

    @Test
    public void testGetPlayer() {
        assertEquals(playerOrder, moveX.getPlayer());
    }

    @Test
    public void testSetPlayer() {
        Role newRole = Role.CHAOS;
        Player newPlayer = new Player(newRole, "Jane Smith");

        moveX.setPlayer(newPlayer);
        assertEquals(newPlayer, moveX.getPlayer());
    }

    @Test
    public void testToString() {
        String expected = "Move{mark=Mark{position=Position{row=10, column=20}, type=Type{name='X'}}, player=Player{role=ORDER, name='John Doe'}}";
        assertEquals(expected, moveX.toString());
    }

    @Test
    public void testIsMarkTypeEqual() {
        // Test with the same mark type
        Move anotherMoveX = new Move(markX, playerChaos);
        assertTrue(moveX.isMarkTypeEqual(anotherMoveX));

        // Test with different mark types
        assertFalse(moveX.isMarkTypeEqual(moveO));
    }
}
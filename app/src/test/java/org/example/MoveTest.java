package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveTest {

    private Position position;
    private Type type;
    private Mark mark;
    private Role role;
    private Player player;
    private Move move;

    @BeforeEach
    public void setUp() {
        // Initialize objects before each test
        position = new Position(10, 20);
        type = new Type("CROSS");
        mark = new Mark(position, type);

        role = Role.ORDER;
        player = new Player(role, "John Doe");

        move = new Move(mark, player);
    }

    @Test
    public void testConstructor() {
        assertNotNull(move);
    }

    @Test
    public void testGetMark() {
        assertEquals(mark, move.getMark());
    }

    @Test
    public void testSetMark() {
        Position newPosition = new Position(30, 40);
        Type newType = new Type("CIRCLE");
        Mark newMark = new Mark(newPosition, newType);

        move.setMark(newMark);
        assertEquals(newMark, move.getMark());
    }

    @Test
    public void testGetPlayer() {
        assertEquals(player, move.getPlayer());
    }

    @Test
    public void testSetPlayer() {
        Role newRole = Role.CHAOS;
        Player newPlayer = new Player(newRole, "Jane Smith");

        move.setPlayer(newPlayer);
        assertEquals(newPlayer, move.getPlayer());
    }

    @Test
    public void testToString() {
        String expected = "Move{mark=Mark{position=Position{row=10, column=20}, type=Type{name='CROSS'}}, player=Player{role=ORDER, name='John Doe'}}";
        assertEquals(expected, move.toString());
    }
}
package org.example;

public class Player {
    private final Role role;
    private final String name;

    public Player(Role role, String name) {
        this.role = role;
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
}
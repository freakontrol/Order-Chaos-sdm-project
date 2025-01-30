package org.example;

import org.example.Role;

public class Player {
    private final Role role;

    public Player(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}

package org.example;

public class Mark {
    private Position position;
    private Type type;

    public Mark(Position position, Type type) {
        this.position = position;
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "position=" + position.toString() +
                ", type=" + type.toString() +
                '}';
    }
}


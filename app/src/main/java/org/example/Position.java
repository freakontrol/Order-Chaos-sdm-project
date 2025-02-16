package org.example;

import java.util.Objects;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Row and column must be non-negative.");
        }
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException("Row must be non-negative.");
        }
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        if (column < 0) {
            throw new IllegalArgumentException("Column must be non-negative.");
        }
        this.column = column;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
package org.example;

import java.util.Objects;

public class Position {
    private int row;
    private int column;
    private int maxRows;
    private int maxColumns;

    public Position(int row, int column) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Row and column must be non-negative.");
        }
        this.row = row;
        this.column = column;
        this.maxRows = 6;
        this.maxColumns = 6;
    }
    public boolean isOnLeftEdge() {
        if (this.row == 0 && this.column > 0) {
            return true;
        }
        return false;
    }

    public boolean isOnRightEdge() {
        if (this.column == maxColumns - 1) {
            return true;
        }
        return false;
    }
    public boolean isOnTopEdge() {
        if (this.row > 0 && this.column == 0) {
            return true;
        }
        return false;
    }
    public boolean isOnBottomEdge() {
        if (this.row == maxRows - 1) {
            return true;
        }
        return false;
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

    public Position getUpLeft() {
        if (this.row <= 0 || this.column <= 0) {
            throw new IllegalArgumentException("Cannot return the Up Left Position, row and column would be negative.");
        } else {
            return new Position(this.row - 1, this.column - 1);
        }
        
    }
    public Position getUp() {
        if (this.row <= 0) {
            throw new IllegalArgumentException("Cannot return the Up Position, row would be negative.");
        } else {
        return new Position(this.row - 1, this.column);
        }
    }
    public Position getUpRight() {
        if (row <= 0 || this.column >= this.maxColumns) {
            throw new IllegalArgumentException("Cannot return the Up Right Position, row would be negative or column would exceed the maximum columns.");
        } else {
            return new Position(this.row - 1, this.column + 1);
        }
    }
    public Position getLeft() {
        if (this.column <= 0) {
            throw new IllegalArgumentException("Cannot return the Left Position, column would be negative.");
        } else {
            return new Position(this.row, this.column - 1);
        }
    }
    public Position getRight() {
        if (this.column >= this.maxColumns) {
            throw new IllegalArgumentException("Cannot return the Right Position, column would exceed the maximum columns.");
        } else {
            return new Position(this.row, this.column + 1);
        }
    }
    public Position getDownLeft() {
        if (row >= this.maxRows || this.column <= 0) {
            throw new IllegalArgumentException("Cannot return the Down Left Position, row would be negative or column would exceed the maximum columns.");
        } else {
            return new Position(this.row + 1, this.column - 1);
        }
    }
    public Position getDown() {
        if (row >= this.maxRows) {
            throw new IllegalArgumentException("Cannot return the Down Position, row would be negative.");
        } else {
            return new Position(this.row + 1, this.column);
        }
    }
    public Position getDownRight() {
        if (row >= this.maxRows || this.column >= this.maxColumns) {
            throw new IllegalArgumentException("Cannot return the Down Right Position, row or column would exceed the maximum rows or columns.");
        } else {
            return new Position(this.row + 1, this.column + 1);
        }
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
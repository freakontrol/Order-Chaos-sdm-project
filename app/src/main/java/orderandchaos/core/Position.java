package orderandchaos.core;

import java.util.Objects;

import orderandchaos.exceptions.OutOfBoundsException;

public class Position {
    private int row;
    private int column;
    private static final int MAX_ROWS = 6;
    private static final int MAX_COLUMNS = 6;

    public Position(int row, int column) {
        if (row < 0 || column < 0) {
            throw new OutOfBoundsException("Row and column must be non-negative.");
        }
        this.row = row;
        this.column = column;
    }

    public boolean isOnLeftEdge() {
        if (this.row == 0 && this.column > 0) {
            return true;
        }
        return false;
    }

    public boolean isOnRightEdge() {
        if (this.column == MAX_COLUMNS - 1) {
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
        if (this.row == MAX_ROWS - 1) {
            return true;
        }
        return false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        if (row < 0 || row >= MAX_ROWS) {
            throw new OutOfBoundsException("Row must be within the bounds of the board.");
        }
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        if (column < 0 || column >= MAX_COLUMNS) {
            throw new OutOfBoundsException("Column must be within the bounds of the board.");
        }
        this.column = column;
    }

    public Position getUpLeft() {
        if (this.row <= 0 || this.column <= 0) {
            throw new OutOfBoundsException("Cannot return the Up Left Position, row and column would be negative.");
        } else {
            return new Position(this.row - 1, this.column - 1);
        }
    }

    public Position getUp() {
        if (this.row <= 0) {
            throw new OutOfBoundsException("Cannot return the Up Position, row would be negative.");
        } else {
        return new Position(this.row - 1, this.column);
        }
    }
    

    public Position getUpRight() {
        if (this.row <= 0 || this.column >= MAX_COLUMNS) {
            throw new OutOfBoundsException("Cannot return the Up Right Position, row would be negative or column would exceed the maximum columns.");
        } else {
            return new Position(this.row - 1, this.column + 1);
        }
    }

    public Position getLeft() {
        if (this.column <= 0) {
            throw new OutOfBoundsException("Cannot return the Left Position, column would be negative.");
        } else {
            return new Position(this.row, this.column - 1);
        }
    }

    public Position getRight() { 
        if (this.column >= MAX_COLUMNS) {
            throw new OutOfBoundsException("Cannot return the Right Position, column would exceed the maximum columns.");
        } else {
            return new Position(this.row, this.column + 1);
        }
    }

    public Position getDownLeft() {
        if (this.row >= MAX_ROWS || this.column <= 0) {
            throw new OutOfBoundsException("Cannot return the Down Left Position, row would exceed the maximum rows or column would be negative.");
        } else {
            return new Position(this.row + 1, this.column - 1);
        }
    }

    public Position getDown() {
        if (this.row >= MAX_ROWS) {
            throw new OutOfBoundsException("Cannot return the Down Position, row would exceed the maximum rows.");
        } else {
            return new Position(this.row + 1, this.column);
        }
    }

    public Position getDownRight() {
        if (this.row >= MAX_ROWS || this.column >= MAX_COLUMNS) {
            throw new OutOfBoundsException("Cannot return the Down Right Position, row or column would exceed the maximum rows or columns.");
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

package savetheking.game;

import java.util.Objects;

public class Point {
    public int x;
    public int y;

    // Constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Equals method to compare two points (for easy comparisons)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    // ToString for easy printing and debugging
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Utility method to check if a point is within the bounds of the board
    public boolean isWithinBounds(int boardSize) {
        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }
}

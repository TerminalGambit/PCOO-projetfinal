package savetheking.game;

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

    // HashCode method, useful for collections that use hashing
    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
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

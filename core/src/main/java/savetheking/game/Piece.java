package savetheking.game;

import java.util.List;
import java.util.ArrayList;

public abstract class Piece {
    protected String color;
    protected Point position;

    public Piece(String color, Point position) {
        this.color = color;
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public Point getPosition() {
        return position;
    }

    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
        } else {
            throw new IllegalArgumentException("Position out of bounds: " + newPosition);
        }
    }

    // Abstract methods to be implemented by subclasses
    public abstract List<Point> getPossibleMoves(Board board);
    public abstract List<Point> getDefendedTiles(Board board);

    // Helper method example: check if a position is within the board bounds
    protected boolean isWithinBounds(Point point, int boardSize) {
        return point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (" + color + ")";
    }

    protected boolean isAlly(Point point, Board board) {
        Piece piece = board.getPieceAt(point);
        return piece != null && piece.getColor().equals(this.color);
    }
}

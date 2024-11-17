package savetheking.game;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(String color, Point position) {
        super(color, position);
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] moveOffsets = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] offset : moveOffsets) {
            int newX = position.x + offset[0];
            int newY = position.y + offset[1];
            Point newPoint = new Point(newX, newY);

            if (newPoint.x >= 0 && newPoint.x < board.getRowCount() &&
                newPoint.y >= 0 && newPoint.y < board.getColumnCount()) {
                possibleMoves.add(newPoint);
            }
        }
        return possibleMoves;
    }

    @Override
    public List<Point> getDefendedTiles(Board board) {
        // For knights, defended tiles are the same as possible moves
        return getPossibleMoves(board);
    }

    // @Override
    public void move(Point newPosition) {
        this.position = newPosition;
    }

    @Override
    public String toString() {
        return "Knight at " + position;
    }
}

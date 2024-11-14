package savetheking.game;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(String color, Point position) {
        super(color, position);
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] moveOffsets = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Vertical and horizontal moves
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Diagonal moves
        };

        for (int[] offset : moveOffsets) {
            int newX = position.x + offset[0];
            int newY = position.y + offset[1];
            Point newPoint = new Point(newX, newY);

            if (newPoint.isWithinBounds(board.getRowCount()) && newPoint.isWithinBounds(board.getColumnCount())) {
                Tile tile = board.getTileAt(newPoint);
                if (tile instanceof EmptyTile || (tile instanceof OccupiedTile && !((OccupiedTile) tile).getPiece().getColor().equals(this.color))) {
                    possibleMoves.add(newPoint);
                }
            }
        }

        // Placeholder for castling logic, if conditions are met
        // Uncomment the following lines if castling is added to `Board`:
        // addCastlingMoves(board, possibleMoves);

        return possibleMoves;
    }

    @Override
    public List<Point> getDefendedTiles(Board board) {
        return getPossibleMoves(board); // King defends the same tiles it can move to
    }

    @Override
    public void move(Point newPosition) {
        this.position = newPosition;
    }

    @Override
    public String toString() {
        return "King at " + position;
    }

    // Uncomment this method and update when implementing castling logic
    /*
    private void addCastlingMoves(Board board, List<Point> possibleMoves) {
        // Castling logic here, assuming necessary methods in Board
    }
    */
}

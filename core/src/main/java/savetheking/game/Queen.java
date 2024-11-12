package savetheking.game;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(String color, Point position) {
        super(color, position);
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] directions = {
            {1, 0},  // right
            {-1, 0}, // left
            {0, 1},  // up
            {0, -1}, // down
            {1, 1},  // diagonal up-right
            {1, -1}, // diagonal down-right
            {-1, 1}, // diagonal up-left
            {-1, -1} // diagonal down-left
        };

        for (int[] direction : directions) {
            int newX = position.x;
            int newY = position.y;

            while (true) {
                newX += direction[0];
                newY += direction[1];
                Point newPoint = new Point(newX, newY);

                if (!newPoint.isWithinBounds(board.getRowCount()) || !newPoint.isWithinBounds(board.getColumnCount())) {
                    break;
                }

                Tile tile = board.getTileAt(newPoint);

                if (tile instanceof EmptyTile) {
                    possibleMoves.add(newPoint);
                } else if (tile instanceof OccupiedTile) {
                    Piece pieceOnTile = ((OccupiedTile) tile).getPiece();
                    if (pieceOnTile.getColor().equals(this.color)) {
                        break;
                    }
                    possibleMoves.add(newPoint);
                    break;
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public List<Point> getDefendedTiles(Board board) {
        return getPossibleMoves(board);
    }

    @Override
    public void move(Point newPosition) {
        this.position = newPosition;
    }

    @Override
    public String toString() {
        return "Queen at " + position;
    }
}

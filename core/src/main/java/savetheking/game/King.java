package savetheking.game;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(String color, Point position, String texturePath) {
        super(color, position, texturePath);
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[] dx = {-1, 0, 1};
        int[] dy = {-1, 0, 1};

        for (int x : dx) {
            for (int y : dy) {
                if (x == 0 && y == 0) continue;

                Point newPosition = new Point(position.x + x, position.y + y);
                if (board.isWithinBounds(newPosition)) {
                    Tile tile = board.getTileAt(newPosition);
                    if (tile instanceof EmptyTile || (tile instanceof OccupiedTile && !((OccupiedTile) tile).getPiece().getColor().equals(this.color))) {
                        possibleMoves.add(newPosition);
                    }
                }
            }
        }
        return possibleMoves;
    }
}

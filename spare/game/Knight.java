package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(String color, Point position, Texture texture) {

        super(color, position, texture);
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[] dx = {-2, -1, 1, 2};
        int[] dy = {-2, -1, 1, 2};

        for (int x : dx) {
            for (int y : dy) {
                if (Math.abs(x) + Math.abs(y) == 3) { // Valid knight move
                    Point newPosition = new Point(position.x + x, position.y + y);
                    if (board.isWithinBounds(newPosition)) {
                        Tile tile = board.getTileAt(newPosition);
                        if (tile instanceof EmptyTile || (tile instanceof OccupiedTile && !((OccupiedTile) tile).getPiece().getColor().equals(this.color))) {
                            possibleMoves.add(newPosition);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }
}

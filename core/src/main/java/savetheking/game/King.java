package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(String color, Point position, Texture texture) {
        super(color, position, texture);
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
                    possibleMoves.add(newPosition);
                }
            }
        }
        return possibleMoves;
    }
}

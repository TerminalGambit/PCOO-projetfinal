package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(String color, Point position, Texture texture) {
        super(color, position, texture);
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[] directions = {-1, 1};

        for (int dx : directions) {
            for (int dy : directions) {
                Point current = new Point(position.x + dx, position.y + dy);
                while (board.isWithinBounds(current)) {
                    Tile tile = board.getTileAt(current);
                    if (tile instanceof EmptyTile) {
                        possibleMoves.add(current);
                    } else if (tile instanceof OccupiedTile) {
                        if (!((OccupiedTile) tile).getPiece().getColor().equals(this.color)) {
                            possibleMoves.add(current);
                        }
                        break;
                    }
                    current = new Point(current.x + dx, current.y + dy);
                }
            }
        }
        return possibleMoves;
    }
}

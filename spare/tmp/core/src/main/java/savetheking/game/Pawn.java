package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Pawn représente un pion dans le mode Solo Chess.
 */
public class Pawn extends Piece {

    public Pawn(String color, Point position, Texture texture) {

        super(color, position, texture);
    }

    /**
     * Détermine les mouvements possibles pour un pion.
     * @param board Le plateau actuel.
     * @return Une liste des positions possibles.
     */
    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int direction = "White".equals(color) ? -1 : 1; // Blancs montent, Noirs descendent

        Point forward = new Point(position.x + direction, position.y);
        if (board.isWithinBounds(forward) && board.getTileAt(forward) instanceof EmptyTile) {
            possibleMoves.add(forward);
        }

        // Captures diagonales
        Point captureLeft = new Point(position.x + direction, position.y - 1);
        Point captureRight = new Point(position.x + direction, position.y + 1);

        if (board.isWithinBounds(captureLeft) && board.getTileAt(captureLeft) instanceof OccupiedTile) {
            possibleMoves.add(captureLeft);
        }
        if (board.isWithinBounds(captureRight) && board.getTileAt(captureRight) instanceof OccupiedTile) {
            possibleMoves.add(captureRight);
        }

        return possibleMoves;
    }
}

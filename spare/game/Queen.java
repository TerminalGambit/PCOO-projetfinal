package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a queen in chess.
 * Combines the movement capabilities of the rook and bishop.
 */
public class Queen extends Piece {

    private int moveCount = 0; // Movement counter for Solo Chess rules

    /**
     * Constructor for the Queen class.
     *
     * @param color    The color of the queen ("White" or "Black").
     * @param position The initial position of the queen.
     * @param texture  The texture representing the queen.
     */
    public Queen(String color, Point position, Texture texture) {
        super(color, position, texture); // Pass the Texture object directly
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Straight directions (Rook)
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Diagonal directions (Bishop)
        };

        for (int[] direction : directions) {
            int newX = position.x;
            int newY = position.y;

            while (true) {
                newX += direction[0];
                newY += direction[1];
                Point newPoint = new Point(newX, newY);

                if (!newPoint.isWithinBounds(board.getRowCount())) break;

                Tile tile = board.getTileAt(newPoint);

                if (tile instanceof EmptyTile) {
                    possibleMoves.add(newPoint);
                } else if (tile instanceof OccupiedTile) {
                    Piece pieceOnTile = ((OccupiedTile) tile).getPiece();
                    if (pieceOnTile.getColor().equals(this.color)) break;
                    possibleMoves.add(newPoint);
                    break;
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            moveCount++;
            if (moveCount >= 2) {
                this.color = "Black";
            }
        } else {
            throw new IllegalArgumentException("Position out of bounds: " + newPosition);
        }
    }
}

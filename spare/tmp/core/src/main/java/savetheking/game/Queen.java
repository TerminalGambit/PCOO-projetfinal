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

        //System.out.println("Calculating possible moves for Queen at: " + position);

        for (int[] direction : directions) {
            int newX = position.x;
            int newY = position.y;

            //System.out.printf("Checking direction: (%d, %d)%n", direction[0], direction[1]);

            while (true) {
                newX += direction[0];
                newY += direction[1];
                Point newPoint = new Point(newX, newY);

                // Check if the new point is within bounds
                if (!newPoint.isWithinBounds(board.getRowCount())) {
                    System.out.printf("Out of bounds: %s%n", newPoint);
                    break;
                }

                // Get the tile at the new position
                Tile tile = board.getTileAt(newPoint);

                if (tile == null) {
                    //System.out.printf("Null tile encountered at: %s%n", newPoint);
                    break;
                }

                if (tile instanceof EmptyTile) {
                    //System.out.printf("Adding empty tile: %s%n", newPoint);
                    possibleMoves.add(newPoint);
                } else if (tile instanceof OccupiedTile) {
                    Piece pieceOnTile = ((OccupiedTile) tile).getPiece();

                    if (pieceOnTile == null) {
                        //System.out.printf("Error: OccupiedTile with null piece at: %s%n", newPoint);
                        break;
                    }

                    //System.out.printf("Encountered piece: %s at %s%n", pieceOnTile, newPoint);

                    if (pieceOnTile != null) {
                        System.out.printf("Capturing opposing piece at: %s%n", newPoint);
                        possibleMoves.add(newPoint);
                    }
                    break; // Stop further movement in this direction
                }
            }
        }

        System.out.println("Possible moves calculated: " + possibleMoves);
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

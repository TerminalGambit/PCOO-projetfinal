package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import java.util.List;

/**
 * Abstract class representing a chess piece in the Solo Chess mode.
 * Each piece has a color, position, move count, and associated texture for rendering.
 */
public abstract class Piece {
    protected String color; // The color of the piece ("White" or "Black")
    protected Point position; // The current position of the piece on the board
    protected int moveCount = 0; // The number of moves the piece has made
    private Texture texture; // Texture for rendering the piece

    /**
     * Constructor to initialize a chess piece with a color, position, and texture.
     *
     * @param color    The color of the piece ("White" or "Black").
     * @param position The initial position of the piece on the board.
     * @param texturePath The path to the texture file for this piece.
     */
    public Piece(String color, Point position, String texturePath) {
        this.color = color;
        this.position = position;
        this.texture = new Texture(texturePath);
    }

    /**
     * Gets the color of the piece.
     *
     * @return The color of the piece ("White" or "Black").
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the current position of the piece.
     *
     * @return The current position of the piece as a Point.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Moves the piece to a new position and increments the move count.
     *
     * @param newPosition The new position to move to.
     * @param boardSize   The size of the board.
     */
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            this.moveCount++;

            // Change color to "Black" after two moves
            if (this.moveCount >= 2 && "White".equals(this.color)) {
                this.color = "Black";
            }
        } else {
            throw new IllegalArgumentException("Position out of bounds: " + newPosition);
        }
    }

    /**
     * Gets the number of moves made by the piece.
     *
     * @return The move count of the piece.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Checks if a position is within the bounds of the board.
     *
     * @param point     The position to check.
     * @param boardSize The size of the board.
     * @return true if the position is within bounds, false otherwise.
     */
    protected boolean isWithinBounds(Point point, int boardSize) {
        return point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize;
    }

    /**
     * Abstract method to determine the possible moves for the piece.
     *
     * @param board The current state of the board.
     * @return A list of valid moves for the piece.
     */
    public abstract List<Point> getPossibleMoves(Board board);

    /**
     * Gets the texture associated with this piece.
     *
     * @return The Texture object for rendering.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Disposes of the texture associated with this piece.
     * Should be called when the piece is no longer needed.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Returns a string representation of the piece.
     *
     * @return A string describing the piece.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (" + color + ")";
    }
}

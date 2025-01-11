package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

/**
 * Abstract class representing a chess piece for the Solo Chess mode.
 * Each piece has a color, position, texture, and specific movement rules.
 */
public abstract class Piece {
    protected String color; // The color of the piece ("White" or "Black")
    protected Point position; // The current position of the piece on the board
    protected Texture texture; // Texture for graphical rendering
    private int moveCount; // Count of moves made by this piece

    /**
     * Constructor to initialize a piece with a color, position, and texture.
     *
     * @param color    The color of the piece ("White" or "Black").
     * @param position The initial position of the piece on the board.
     * @param texture  The texture representing the piece.
     */
    public Piece(String color, Point position, Texture texture) {
        this.color = color;
        this.position = position;
        this.texture = texture;
        this.moveCount = 0;
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
     * Sets the color of the piece.
     *
     * @param color The new color ("White" or "Black").
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the current position of the piece.
     *
     * @return The current position as a `Point`.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the texture of the piece for graphical rendering.
     *
     * @return The texture of the piece.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Moves the piece to a new position.
     *
     * @param newPosition The target position.
     * @param boardSize   The size of the board.
     */
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            this.moveCount++;
        } else {
            throw new IllegalArgumentException("Position out of bounds: " + newPosition);
        }
    }

    /**
     * Checks whether a position is within the bounds of the board.
     *
     * @param point     The position to check.
     * @param boardSize The size of the board.
     * @return `true` if the position is within bounds; otherwise `false`.
     */
    protected boolean isWithinBounds(Point point, int boardSize) {
        return point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize;
    }

    /**
     * Abstract method to determine the possible moves for a piece.
     *
     * @param board The current state of the board.
     * @return A list of positions where the piece can move.
     */
    public abstract List<Point> getPossibleMoves(Board board);

    /**
     * Renders the piece at the specified position on the board.
     *
     * @param batch    The SpriteBatch used for rendering.
     * @param position The position where the piece should be rendered.
     */
    public void render(SpriteBatch batch, Point position) {
        if (texture != null) {
            int screenX = position.y * 64; // Assuming tile size = 64
            int screenY = (8 - position.x - 1) * 64; // Flip y-axis for screen rendering
            batch.draw(texture, screenX, screenY, 64, 64);
        }
    }

    /**
     * Gets the number of moves made by this piece.
     *
     * @return The move count.
     */
    public int getMoveCount() {
        return this.moveCount;
    }


    /**
     * Returns a textual representation of the piece.
     *
     * @return A string describing the piece.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (" + color + ")";
    }

    /**
     * Disposes of resources associated with the piece's texture.
     */
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}

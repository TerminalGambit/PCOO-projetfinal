package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Abstract base class for tiles on the board.
 * Represents a single tile that can be occupied by a piece or empty.
 */
public abstract class Tile {
    protected final Point position; // Position of the tile on the board
    protected final int tileId;     // ID of the tile (e.g., for dark/light distinction)

    /**
     * Constructs a Tile with a specific position and tile ID.
     *
     * @param position The position of the tile on the board.
     * @param tileId   The ID representing the type of tile (e.g., dark green = 1, light white = 3).
     */
    public Tile(Point position, int tileId) {
        this.position = position;
        this.tileId = tileId;
    }

    /**
     * Gets the position of the tile on the board.
     *
     * @return The position of the tile.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Gets the ID of the tile (useful for rendering or identification).
     *
     * @return The tile ID.
     */
    public int getId() {
        return tileId;
    }

    /**
     * Checks whether the tile is occupied by a piece.
     *
     * @return True if the tile is occupied; false otherwise.
     */
    public abstract boolean isOccupied();

    /**
     * Gets the piece occupying the tile, if any.
     *
     * @return The piece on the tile, or null if the tile is empty.
     */
    public abstract Piece getPiece();

    /**
     * Renders the tile using the specified texture.
     *
     * @param batch   The SpriteBatch used for rendering.
     * @param texture The texture to render for this tile.
     */
    public void render(SpriteBatch batch, Texture texture) {
        int screenX = position.y * 64; // Assuming tile size = 64
        int screenY = (8 - position.x - 1) * 64; // Flip y-axis for screen rendering

        // Draw the texture on the screen
        batch.draw(texture, screenX, screenY, 64, 64);
    }
}

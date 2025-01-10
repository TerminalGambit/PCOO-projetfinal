package savetheking.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;

/**
 * Abstract base class for tiles on the board.
 * Represents a single tile that can be occupied by a piece or empty.
 */
public abstract class Tile {
    protected final Point position; // Position of the tile on the board
    protected final int tileId;     // ID of the tile (e.g., dark green = 1, light white = 3)
    protected final HashMap<String, String> properties; // Custom properties for the tile
    private boolean isHighlighted;  // Whether the tile is currently highlighted

    /**
     * Constructs a Tile with a specific position and tile ID.
     *
     * @param position The position of the tile on the board.
     * @param tileId   The ID representing the type of tile (e.g., dark green = 1, light white = 3).
     */
    public Tile(Point position, int tileId) {
        this.position = position;
        this.tileId = tileId;
        this.properties = new HashMap<String, String>();
        this.isHighlighted = false;
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
    public int getTileId() {
        return tileId;
    }

    /**
     * Checks whether the tile is highlighted.
     *
     * @return True if the tile is highlighted; false otherwise.
     */
    public boolean isHighlighted() {
        return isHighlighted;
    }

    /**
     * Sets the highlighted state of the tile.
     *
     * @param highlighted True to highlight the tile, false to remove the highlight.
     */
    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    /**
     * Gets the value of a custom property for this tile.
     *
     * @param key The property name.
     * @return The property value, or null if not found.
     */
    public String getProperty(String key) {
        return properties.get(key);
    }

    /**
     * Sets a custom property for this tile.
     *
     * @param key   The property name.
     * @param value The property value.
     */
    public void setProperty(String key, String value) {
        properties.put(key, value);
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

        // Draw the tile texture
        batch.draw(texture, screenX, screenY, 64, 64);

        // Apply highlight overlay if the tile is highlighted
        if (isHighlighted) {
            batch.end(); // End the batch to draw shapes
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(1, 1, 0, 0.4f)); // Yellow with transparency
            shapeRenderer.rect(screenX, screenY, 64, 64);
            shapeRenderer.end();
            batch.begin(); // Restart the batch for further rendering
        }
    }
}


package savetheking.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a layer in a TiledMap, such as a "Piece Layer" or "Board Layer".
 */
public class TiledLayer {
    private final int width;
    private final int height;
    private final Tile[][] tiles; // 2D array of tiles in this layer
    private final Map<String, String> customProperties; // Custom properties associated with the layer

    /**
     * Constructs a TiledLayer with the given dimensions.
     *
     * @param width  The width of the layer.
     * @param height The height of the layer.
     */
    public TiledLayer(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.customProperties = new HashMap<String, String>();
    }

    /**
     * Gets the width of the layer.
     *
     * @return The width of the layer.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the layer.
     *
     * @return The height of the layer.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retrieves a tile at the specified position.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The Tile at the specified position, or null if none exists.
     */
    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return tiles[x][y];
        }
        return null;
    }

    /**
     * Sets a custom property for the layer.
     *
     * @param key   The property name.
     * @param value The property value.
     */
    public void setLayerProperty(String key, String value) {
        customProperties.put(key, value);
    }

    /**
     * Gets a custom property value by key.
     *
     * @param key The property name.
     * @return The property value, or null if not found.
     */
    public String getCustomProperty(String key) {
        return customProperties.get(key);
    }
}

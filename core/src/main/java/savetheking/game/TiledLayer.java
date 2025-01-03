package savetheking.game;

import java.util.ArrayList;
import java.util.List;
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
     * @param width  The width of the layer in tiles.
     * @param height The height of the layer in tiles.
     */
    public TiledLayer(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.customProperties = null;
    }

    /**
     * Constructs a TiledLayer with a list of tiles and custom properties.
     *
     * @param tiles           A list of tiles in this layer.
     * @param customProperties The custom properties associated with the layer.
     */
    public TiledLayer(List<Tile> tiles, Map<String, String> customProperties) {
        if (tiles == null || tiles.isEmpty()) {
            throw new IllegalArgumentException("Tiles cannot be null or empty");
        }

        this.width = tiles.size();
        this.height = tiles.get(0) instanceof EmptyTile ? tiles.size() : 0; // Adjust logic as needed
        this.tiles = new Tile[width][height];
        for (Tile tile : tiles) {
            Point position = tile.getPosition();
            this.tiles[position.x][position.y] = tile;
        }
        this.customProperties = customProperties;
    }

    /**
     * Returns the width of the layer in tiles.
     *
     * @return The width of the layer.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the layer in tiles.
     *
     * @return The height of the layer.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Adds a tile to the layer at the specified position.
     *
     * @param tile The tile to add.
     * @param x    The x-coordinate of the position.
     * @param y    The y-coordinate of the position.
     */
    public void addTile(Tile tile, int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Tile position out of bounds: (" + x + ", " + y + ")");
        }
        tiles[x][y] = tile;
    }

    /**
     * Retrieves a tile from the layer at the specified position.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The tile at the specified position, or null if none exists.
     */
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Tile position out of bounds: (" + x + ", " + y + ")");
        }
        return tiles[x][y];
    }

    /**
     * Sets a custom property for the layer.
     *
     * @param key   The key of the custom property.
     * @param value The value of the custom property.
     */
    public void setCustomProperty(String key, String value) {
        if (customProperties != null) {
            customProperties.put(key, value);
        }
    }

    /**
     * Retrieves the value of a custom property by key.
     *
     * @param key The key of the custom property.
     * @return The value of the custom property, or null if it doesn't exist.
     */
    public String getCustomProperty(String key) {
        return customProperties != null ? customProperties.get(key) : null;
    }

    /**
     * Retrieves all custom properties of the layer.
     *
     * @return A map of all custom properties.
     */
    public Map<String, String> getCustomProperties() {
        return customProperties;
    }
}

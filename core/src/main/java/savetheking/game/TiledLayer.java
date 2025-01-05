package savetheking.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a layer in a TiledMap, such as a "Piece Layer" or "Board Layer".
 */
public class TiledLayer {
    private final int width;
    private final int height;
    private final int[][] tileIds; // 2D array of tile IDs in this layer
    private final Map<String, String> customProperties; // Custom properties associated with the layer
    private final Map<Point, Map<String, String>> tileProperties; // Per-tile properties

    /**
     * Constructs a TiledLayer with the given dimensions.
     *
     * @param width  The width of the layer.
     * @param height The height of the layer.
     */
    public TiledLayer(int width, int height) {
        this.width = width;
        this.height = height;
        this.tileIds = new int[width][height]; // Initialize with default tile IDs (0)
        this.customProperties = new HashMap<String, String>();
        this.tileProperties = new HashMap<Point, Map<String, String>>();
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
     * Retrieves a tile ID at the specified position.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The tile ID at the specified position, or 0 if out of bounds.
     */
    public int getTileId(int x, int y) {
        if (isWithinBounds(x, y)) {
            return tileIds[x][y];
        }
        return 0; // Default to 0 if out of bounds
    }

    /**
     * Retrieves a tile at the specified position.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The Tile object at the specified position, or null if none exists.
     */
    public Tile getTile(int x, int y) {
        if (isWithinBounds(x, y)) {
            int tileId = tileIds[x][y];
            Map<String, String> properties = tileProperties.get(new Point(x, y));
            return createTileFromId(tileId, x, y, properties);
        }
        return null; // Return null if out of bounds or no valid tile exists
    }

    /**
     * Creates a Tile object based on its ID and properties.
     *
     * @param tileId     The ID of the tile.
     * @param x          The x-coordinate of the tile.
     * @param y          The y-coordinate of the tile.
     * @param properties Additional properties for the tile.
     * @return A Tile object corresponding to the given tile ID.
     */
    private Tile createTileFromId(int tileId, int x, int y, Map<String, String> properties) {
        if (tileId == 0) {
            return new EmptyTile(new Point(x, y), tileId);
        } else {
            String type = properties != null ? properties.get("type") : null;
            String color = properties != null ? properties.get("color") : null;
            Piece piece = (type != null && color != null)
                ? PieceFactory.createPiece(type, color, new Point(x, y))
                : null;
            return new OccupiedTile(new Point(x, y), tileId, piece);
        }
    }

    /**
     * Sets a tile ID at the specified position.
     *
     * @param x      The x-coordinate of the tile.
     * @param y      The y-coordinate of the tile.
     * @param tileId The tile ID to set.
     */
    public void setTileId(int x, int y, int tileId) {
        if (isWithinBounds(x, y)) {
            tileIds[x][y] = tileId;
        } else {
            throw new IllegalArgumentException("Position out of bounds: (" + x + ", " + y + ")");
        }
    }

    /**
     * Adds properties to a specific tile.
     *
     * @param x          The x-coordinate of the tile.
     * @param y          The y-coordinate of the tile.
     * @param properties A map of properties to add to the tile.
     */
    public void setTileProperties(int x, int y, Map<String, String> properties) {
        if (isWithinBounds(x, y)) {
            tileProperties.put(new Point(x, y), properties);
        }
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

    /**
     * Fills the layer with a default tile ID.
     *
     * @param tileId The default tile ID to fill the layer with.
     */
    public void fillLayer(int tileId) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tileIds[x][y] = tileId;
            }
        }
    }

    /**
     * Checks if a given position is within the bounds of the layer.
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return True if the position is within bounds, false otherwise.
     */
    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Gets the name of the layer from its custom properties.
     *
     * @return The name of the layer, or null if the property is not set.
     */
    public String getName() {
        return customProperties.get("name");
    }
}

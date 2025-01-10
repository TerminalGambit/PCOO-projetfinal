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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileId(int x, int y) {
        if (isWithinBounds(x, y)) {
            return tileIds[x][y];
        }
        return 0; // Default to 0 if out of bounds
    }

    public Tile getTile(int x, int y) {
        if (isWithinBounds(x, y)) {
            int tileId = tileIds[x][y];
            Map<String, String> properties = tileProperties.get(new Point(x, y));
            return createTileFromId(tileId, x, y, properties);
        }
        return null;
    }

    private Tile createTileFromId(int tileId, int x, int y, Map<String, String> properties) {
        try {
            if (tileId == 0) {
                return new EmptyTile(new Point(x, y), tileId); // Fixed: Pass both Point and tileId
            } else {
                String type = properties != null ? properties.get("type") : null;
                String color = properties != null ? properties.get("color") : null;
                Piece piece = (type != null && color != null)
                    ? PieceFactory.createPiece(type, color, new Point(x, y))
                    : null;
                return new OccupiedTile(new Point(x, y), tileId, piece);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to create tile at (" + x + ", " + y + "): " + e.getMessage());
            return null;
        }
    }

    public void setTileId(int x, int y, int tileId) {
        if (isWithinBounds(x, y)) {
            tileIds[x][y] = tileId;
        } else {
            throw new IllegalArgumentException("Position out of bounds: (" + x + ", " + y + ")");
        }
    }

    public void setTileProperties(int x, int y, Map<String, String> properties) {
        if (isWithinBounds(x, y)) {
            tileProperties.put(new Point(x, y), properties);
        }
    }

    public void setLayerProperty(String key, String value) {
        customProperties.put(key, value);
    }

    public String getCustomProperty(String key) {
        return customProperties.get(key);
    }

    public void fillLayer(int tileId) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tileIds[x][y] = tileId;
            }
        }
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public String getName() {
        return customProperties.get("name");
    }
}

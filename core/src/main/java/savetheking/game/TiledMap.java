package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entire TiledMap structure, consisting of multiple layers and pieces.
 */
public class TiledMap {
    private final List<TiledLayer> layers;
    private final List<Piece> pieces; // List of pieces on the map
    private final java.util.Map<String, String> mapProperties;

    /**
     * Constructs an empty TiledMap.
     */
    public TiledMap() {
        this.layers = new ArrayList<TiledLayer>();
        this.pieces = new ArrayList<Piece>();
        this.mapProperties = new java.util.HashMap<String, String>();
    }

    /**
     * Adds a layer to the map.
     *
     * @param layer The layer to add.
     */
    public void addLayer(TiledLayer layer) {
        layers.add(layer);
    }

    /**
     * Returns the list of layers in the map.
     *
     * @return List of TiledLayers.
     */
    public List<TiledLayer> getLayers() {
        return layers;
    }

    /**
     * Sets the pieces on the map.
     *
     * @param pieces The list of pieces to set.
     */
    public void setPieces(List<Piece> pieces) {
        this.pieces.clear();
        this.pieces.addAll(pieces);
    }

    /**
     * Gets the list of pieces on the map.
     *
     * @return List of pieces.
     */
    public List<Piece> getPieces() {
        return pieces;
    }

    /**
     * Sets a map property.
     *
     * @param key The property key.
     * @param value The property value.
     */
    public void setMapProperty(String key, String value) {
        mapProperties.put(key, value);
    }

    /**
     * Retrieves a map property by key.
     *
     * @param key The key of the property.
     * @return The value of the property, or null if not found.
     */
    public String getMapProperty(String key) {
        return mapProperties.get(key);
    }
}

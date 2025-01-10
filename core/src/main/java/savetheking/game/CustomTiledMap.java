package savetheking.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the entire TiledMap structure, consisting of multiple layers and pieces.
 */

public class CustomTiledMap {

    private final Map<String, TiledLayer> objectLayers = new HashMap<String, TiledLayer>(); // Store object layers

    public TiledLayer getObjectLayer(String name) {
        return objectLayers.get(name);
    }

    public void addObjectLayer(String name, TiledLayer layer) {
        objectLayers.put(name, layer);
    }

    private final List<TiledLayer> layers;
    private final List<Piece> pieces; // List of pieces on the map
    private final Map<String, String> mapProperties;

    /**
     * Constructs an empty TiledMap.
     */
    public CustomTiledMap() {
        this.layers = new ArrayList<TiledLayer>();
        this.pieces = new ArrayList<Piece>();
        this.mapProperties = new HashMap<String, String>();
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
     * Retrieves a layer by name.
     *
     * @param name The name of the layer.
     * @return The matching TiledLayer, or null if not found.
     */
    public TiledLayer getLayer(String name) {
        for (TiledLayer layer : layers) {
            if (name.equals(layer.getName())) {
                return layer;
            }
        }
        return null;
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

    /**
     * Retrieves the width of the map based on the first layer.
     *
     * @return The width of the map.
     */
    public int getWidth() {
        if (!layers.isEmpty()) {
            return layers.get(0).getWidth();
        }
        return 0;
    }

    /**
     * Retrieves the height of the map based on the first layer.
     *
     * @return The height of the map.
     */
    public int getHeight() {
        if (!layers.isEmpty()) {
            return layers.get(0).getHeight();
        }
        return 0;
    }
}

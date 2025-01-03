package savetheking.game;

import java.util.*;

/**
 * Represents the entire TiledMap structure, consisting of multiple layers.
 */
public class TiledMap {
    private final List<TiledLayer> layers; // List of layers in the map
    private final Map<String, String> mapProperties; // Global map-level properties

    /**
     * Constructs a TiledMap with an initial list of layers.
     *
     * @param layers The layers in the map.
     */
    public TiledMap(List<TiledLayer> layers) {
        this.layers = new ArrayList<TiledLayer>(layers);
        this.mapProperties = new HashMap<String, String>();
    }

    /**
     * Constructs an empty TiledMap with no layers.
     */
    public TiledMap() {
        this.layers = new ArrayList<TiledLayer>();
        this.mapProperties = new HashMap<String, String>();
    }

    /**
     * Returns all layers in the map.
     *
     * @return List of layers.
     */
    public List<TiledLayer> getLayers() {
        return new ArrayList<TiledLayer>(layers);
    }

    /**
     * Retrieves a specific layer by name.
     *
     * @param name The name of the layer.
     * @return The TiledLayer with the specified name, or an empty Optional if not found.
     */
    public Optional<TiledLayer> getLayerByName(String name) {
        for (TiledLayer layer : layers) {
            if (name.equals(layer.getCustomProperty("name"))) {
                return Optional.of(layer);
            }
        }
        return Optional.empty();
    }

    /**
     * Adds a new layer to the map.
     *
     * @param layer The TiledLayer to add.
     */
    public void addLayer(TiledLayer layer) {
        layers.add(layer);
    }

    /**
     * Removes a layer from the map by name.
     *
     * @param name The name of the layer to remove.
     * @return True if the layer was removed, false otherwise.
     */
    public boolean removeLayer(String name) {
        for (Iterator<TiledLayer> iterator = layers.iterator(); iterator.hasNext(); ) {
            TiledLayer layer = iterator.next();
            if (name.equals(layer.getCustomProperty("name"))) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of layers in the map.
     *
     * @return The number of layers.
     */
    public int getLayerCount() {
        return layers.size();
    }

    /**
     * Sets a global map-level property.
     *
     * @param key   The property key.
     * @param value The property value.
     */
    public void setMapProperty(String key, String value) {
        mapProperties.put(key, value);
    }

    /**
     * Gets a global map-level property by key.
     *
     * @param key The property key.
     * @return The property value, or null if it doesn't exist.
     */
    public String getMapProperty(String key) {
        return mapProperties.get(key);
    }

    /**
     * Returns all global map-level properties.
     *
     * @return A map of all properties.
     */
    public Map<String, String> getMapProperties() {
        return new HashMap<String, String>(mapProperties);
    }
}

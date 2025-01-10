package savetheking.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomTiledMap {
    private final List<TiledLayer> layers;
    private final List<Piece> pieces;
    private final Map<String, String> mapProperties;

    public CustomTiledMap() {
        this.layers = new ArrayList<TiledLayer>();
        this.pieces = new ArrayList<Piece>();
        this.mapProperties = new HashMap<String, String>();
    }

    public void addLayer(TiledLayer layer) {
        layers.add(layer);
    }

    public List<TiledLayer> getLayers() {
        return layers;
    }

    public TiledLayer getLayer(String name) {
        for (TiledLayer layer : layers) {
            if (name.equals(layer.getName())) {
                return layer;
            }
        }
        return null;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces.clear();
        this.pieces.addAll(pieces);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setMapProperty(String key, String value) {
        mapProperties.put(key, value);
    }

    public String getMapProperty(String key) {
        return mapProperties.get(key);
    }

    public int getWidth() {
        if (!layers.isEmpty()) {
            return layers.get(0).getWidth();
        }
        return 0;
    }

    public int getHeight() {
        if (!layers.isEmpty()) {
            return layers.get(0).getHeight();
        }
        return 0;
    }
}

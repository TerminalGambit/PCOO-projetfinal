package savetheking.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Responsible for loading and parsing Tiled (.tmx) files into TiledMap objects.
 */
public class TiledLoader {
    private final TmxMapLoader loader;

    /**
     * Constructs a TiledLoader with a default TmxMapLoader.
     */
    public TiledLoader() {
        this.loader = new TmxMapLoader();
    }

    /**
     * Loads a Tiled (.tmx) file and converts it into a custom TiledMap object.
     *
     * @param filePath The path to the .tmx file.
     * @return A custom TiledMap object representing the loaded map.
     */
    public TiledMap load(String filePath) {
        // Load the map using LibGDX's TiledMap loader
        com.badlogic.gdx.maps.tiled.TiledMap gdxTiledMap = loader.load(filePath);
        TiledMap customTiledMap = new TiledMap();

        // Parse global map properties
        MapProperties mapProperties = gdxTiledMap.getProperties();
        for (Iterator<String> it = mapProperties.getKeys(); it.hasNext(); ) {
            String key = it.next();
            customTiledMap.setMapProperty(key, mapProperties.get(key).toString());
        }

        // Parse layers
        for (MapLayer gdxLayer : gdxTiledMap.getLayers()) {
            if (gdxLayer instanceof TiledMapTileLayer) {
                TiledLayer layer = parseTileLayer((TiledMapTileLayer) gdxLayer);
                customTiledMap.addLayer(layer);
            }
        }

        return customTiledMap;
    }

    /**
     * Parses a TiledMapTileLayer into a custom TiledLayer.
     *
     * @param tileLayer The TiledMapTileLayer to parse.
     * @return A custom TiledLayer representing the tile layer.
     */
    private TiledLayer parseTileLayer(TiledMapTileLayer tileLayer) {
        List<Tile> tiles = new ArrayList<Tile>();
        Map<String, String> customProperties = new java.util.HashMap<String, String>();

        // Parse layer properties
        MapProperties layerProperties = tileLayer.getProperties();
        for (Iterator<String> it = layerProperties.getKeys(); it.hasNext(); ) {
            String key = it.next();
            customProperties.put(key, layerProperties.get(key).toString());
        }

        // Parse tiles in the layer
        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                if (cell != null) {
                    // Custom handling for different tile types can be added here
                    Tile tile = new EmptyTile(new Point(x, y)); // Defaulting to EmptyTile
                    tiles.add(tile);
                }
            }
        }

        return new TiledLayer(tiles, customProperties);
    }
}

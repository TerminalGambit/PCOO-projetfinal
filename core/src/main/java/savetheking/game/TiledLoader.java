
package savetheking.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Iterator;

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
                System.out.println("Found a TiledMapTileLayer: " + gdxLayer.getName());
                if (gdxLayer.getName().equalsIgnoreCase("Board Layer")) {
                    System.out.println("Loading Board Layer...");
                    TiledLayer layer = parseTileLayer((TiledMapTileLayer) gdxLayer);
                    customTiledMap.addLayer(layer);
                    System.out.println("Board Layer loaded successfully.");
                }
            } else {
                System.out.println("Non-TiledMapTileLayer found: " + gdxLayer.getName());
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
        TiledLayer customLayer = new TiledLayer(tileLayer.getWidth(), tileLayer.getHeight());
        MapProperties layerProperties = tileLayer.getProperties();

        // Parse layer properties
        for (Iterator<String> it = layerProperties.getKeys(); it.hasNext(); ) {
            String key = it.next();
            customLayer.setLayerProperty(key, layerProperties.get(key).toString());
        }

        // Debug tile parsing
        System.out.println("Parsing tiles for layer: " + tileLayer.getName());

        // Parse tiles
        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                if (cell != null) {
                    System.out.println("Tile at (" + x + ", " + y + ") loaded.");
                } else {
                    System.out.println("No tile at (" + x + ", " + y + ").");
                }
            }
        }

        return customLayer;
    }
}

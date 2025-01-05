package savetheking.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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
        System.out.println("Loading TMX file: " + filePath);
        com.badlogic.gdx.maps.tiled.TiledMap gdxTiledMap = loader.load(filePath);
        TiledMap customTiledMap = new TiledMap();

        // Parse global map properties
        System.out.println("Parsing global map properties...");
        MapProperties mapProperties = gdxTiledMap.getProperties();
        for (Iterator<String> it = mapProperties.getKeys(); it.hasNext(); ) {
            String key = it.next();
            System.out.println("Map property - Key: " + key + ", Value: " + mapProperties.get(key));
            customTiledMap.setMapProperty(key, mapProperties.get(key).toString());
        }

        // Parse layers
        for (MapLayer gdxLayer : gdxTiledMap.getLayers()) {
            if (gdxLayer instanceof TiledMapTileLayer) {
                System.out.println("Found a TiledMapTileLayer: " + gdxLayer.getName());
                TiledLayer layer = parseTileLayer((TiledMapTileLayer) gdxLayer);
                customTiledMap.addLayer(layer);
                System.out.println("Layer loaded successfully: " + gdxLayer.getName());
            } else {
                System.out.println("Processing non-TiledMapTileLayer: " + gdxLayer.getName());
                parseObjectGroupLayer(gdxLayer, customTiledMap);
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
        System.out.println("Parsing TiledMapTileLayer: " + tileLayer.getName());
        TiledLayer customLayer = new TiledLayer(tileLayer.getWidth(), tileLayer.getHeight());
        MapProperties layerProperties = tileLayer.getProperties();

        // Parse layer properties
        for (Iterator<String> it = layerProperties.getKeys(); it.hasNext(); ) {
            String key = it.next();
            System.out.println("Layer property - Key: " + key + ", Value: " + layerProperties.get(key));
            customLayer.setLayerProperty(key, layerProperties.get(key).toString());
        }

        // Parse tiles
        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                if (cell != null) {
                    customLayer.setTileId(x, y, cell.getTile().getId());
                    System.out.println("Tile at (" + x + ", " + y + ") with ID: " + cell.getTile().getId());
                } else {
                    System.out.println("No tile at (" + x + ", " + y + ").");
                }
            }
        }

        return customLayer;
    }

    /**
     * Parses object group layers, typically used for piece placement.
     *
     * @param gdxLayer       The MapLayer to parse.
     * @param customTiledMap The TiledMap to populate with pieces.
     */
    private void parseObjectGroupLayer(MapLayer gdxLayer, TiledMap customTiledMap) {
        MapObjects objects = gdxLayer.getObjects();
        System.out.println("Processing object group layer: " + gdxLayer.getName() + " with " + objects.getCount() + " objects.");

        for (RectangleMapObject obj : objects.getByType(RectangleMapObject.class)) {
            MapProperties properties = obj.getProperties();

            System.out.println("Processing object with properties: " + properties);

            // Extract piece properties
            String type = properties.get("type", String.class);
            String color = properties.get("color", String.class);
            Integer gid = properties.get("gid", Integer.class);
            Float x = properties.get("x", Float.class);
            Float y = properties.get("y", Float.class);

            System.out.println("Parsed object: Type=" + type + ", Color=" + color + ", GID=" + gid + ", X=" + x + ", Y=" + y);

            if (type != null && color != null && x != null && y != null) {
                Point position = new Point(Math.round(x / 64), Math.round(y / 64)); // Assuming 64x64 tiles

                System.out.println("Attempting to create piece using PieceFactory...");
                Piece piece = PieceFactory.createPiece(type, color, position);

                if (piece != null) {
                    System.out.println("Piece created successfully: " + piece);
                    customTiledMap.getPieces().add(piece);
                } else {
                    System.err.println("Failed to create piece: " + type + ", " + color);
                }
            } else {
                System.err.println("Incomplete piece definition in layer: " + gdxLayer.getName());
            }
        }
    }
}

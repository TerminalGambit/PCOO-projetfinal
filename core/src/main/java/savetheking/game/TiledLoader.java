package savetheking.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;

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
        System.out.println("Processing object group layer: " + gdxLayer.getName() + " with " + gdxLayer.getObjects().getCount() + " objects.");

        for (MapObject obj : gdxLayer.getObjects()) {
            // Log the type of object dynamically
            System.out.println("Found object of type: " + obj.getClass().getCanonicalName());

            // Check known types and handle accordingly
            if (obj instanceof RectangleMapObject) {
                System.out.println("Object is a RectangleMapObject.");
                RectangleMapObject rectangleObject = (RectangleMapObject) obj;
                processRectangleMapObject(rectangleObject, customTiledMap);
            } else if (obj instanceof TiledMapTileMapObject) {
                System.out.println("Object is a TiledMapTileMapObject.");
                TiledMapTileMapObject tileMapObject = (TiledMapTileMapObject) obj;
                processTiledMapTileMapObject(tileMapObject, customTiledMap);
            } else {
                System.err.println("Unknown or unsupported MapObject type: " + obj.getClass().getCanonicalName());
            }
        }
    }

    /**
     * Processes a RectangleMapObject to extract properties and create a Piece.
     */
    private void processRectangleMapObject(RectangleMapObject obj, TiledMap customTiledMap) {
        MapProperties properties = obj.getProperties();
        System.out.println("Rectangle Object properties: " + properties);

        String type = properties.get("type", String.class);
        String color = properties.get("color", String.class);
        Float x = obj.getRectangle().x;
        Float y = obj.getRectangle().y;

        if (type == null || color == null || x == null || y == null) {
            System.err.println("Incomplete or invalid RectangleMapObject definition. Skipping object.");
            return;
        }

        int boardX = Math.round(x / 64); // Assuming 64x64 tiles
        int boardY = Math.round((512 - y) / 64); // Adjust for LibGDX bottom-left origin
        Point position = new Point(boardX, boardY);

        System.out.println("Calculated Board Position (Rectangle Object): " + position);
        createPieceFromFactory(type, color, position, customTiledMap);
    }

    /**
     * Processes a TiledMapTileMapObject to extract properties and create a Piece.
     */
    private void processTiledMapTileMapObject(TiledMapTileMapObject obj, TiledMap customTiledMap) {
        // Fetch properties
        MapProperties properties = obj.getProperties();

        // Debugging: Log all properties
        System.out.println("TiledMapTileMapObject properties: " + properties);

        // Extract required properties
        String type = properties.get("type", String.class);
        String color = properties.get("color", String.class);
        Float x = obj.getX();
        Float y = obj.getY();

        // Validate attributes
        if (type == null || color == null || x == null || y == null) {
            System.err.println("Incomplete or invalid TiledMapTileMapObject definition. Skipping object.");
            return;
        }

        // Convert to board coordinates
        int boardX = Math.round(x / 64); // Assuming 64x64 tiles
        int boardY = Math.round((512 - y) / 64); // Adjust for LibGDX bottom-left origin
        Point position = new Point(boardX, boardY);

        System.out.println("Calculated Board Position (TiledMapTileMapObject): " + position);

        // Create piece and add to the map
        createPieceFromFactory(type, color, position, customTiledMap);
    }

    /**
     * Attempts to create a piece using the PieceFactory and adds it to the TiledMap.
     *
     * @param type            The type of the piece (e.g., "Queen", "Rook").
     * @param color           The color of the piece (e.g., "White", "Black").
     * @param position        The board position of the piece.
     * @param customTiledMap  The custom TiledMap to populate.
     */
    private void createPieceFromFactory(String type, String color, Point position, TiledMap customTiledMap) {
        System.out.println("Attempting to create piece using PieceFactory...");
        Piece piece = PieceFactory.createPiece(type, color, position);

        if (piece != null) {
            System.out.println("Piece created successfully: " + piece);
            customTiledMap.getPieces().add(piece);
        } else {
            System.err.println("Failed to create piece: Type=" + type + ", Color=" + color);
        }
    }
}

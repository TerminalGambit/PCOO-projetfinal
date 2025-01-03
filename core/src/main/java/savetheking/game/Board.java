package savetheking.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Board class represents a chessboard for the Solo Chess game.
 * It integrates with TiledMap to dynamically load the board and piece layout.
 */
public class Board implements Observable {
    private final Tile[][] tiles;
    private final int rowCount;
    private final int columnCount;
    private final List<Observer> observers = new ArrayList<Observer>();
    private TiledMap tiledMap;

    /**
     * Constructs a Board with specified dimensions.
     *
     * @param rowCount Number of rows on the board.
     * @param columnCount Number of columns on the board.
     */
    public Board(int rowCount, int columnCount) {
        if (rowCount <= 0 || columnCount <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive.");
        }
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.tiles = new Tile[rowCount][columnCount];
        initializeBoard();
    }

    /**
     * Constructs a Board using a TiledMap for initialization.
     *
     * @param tiledMap The TiledMap to load the board from.
     */
    public Board(TiledMap tiledMap) {
        this.rowCount = extractLayerHeight(tiledMap, "Board Layer");
        this.columnCount = extractLayerWidth(tiledMap, "Board Layer");
        this.tiles = new Tile[rowCount][columnCount];
        this.tiledMap = tiledMap;
        initializeFromTiledMap();
    }

    private int extractLayerHeight(TiledMap tiledMap, String layerName) {
        TiledLayer layer = findLayerByName(tiledMap, layerName);
        return layer != null ? layer.getHeight() : 0;
    }

    private int extractLayerWidth(TiledMap tiledMap, String layerName) {
        TiledLayer layer = findLayerByName(tiledMap, layerName);
        return layer != null ? layer.getWidth() : 0;
    }

    private TiledLayer findLayerByName(TiledMap tiledMap, String layerName) {
        List<TiledLayer> layers = tiledMap.getLayers();
        for (Iterator<TiledLayer> it = layers.iterator(); it.hasNext(); ) {
            TiledLayer layer = it.next();
            if (layerName.equals(layer.getCustomProperty("name"))) {
                return layer;
            }
        }
        return null;
    }

    /**
     * Initializes the board with EmptyTiles.
     */
    private void initializeBoard() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles[i][j] = new EmptyTile(new Point(i, j));
            }
        }
    }

    /**
     * Initializes the board using data from the TiledMap.
     */
    private void initializeFromTiledMap() {
        TiledLayer pieceLayer = findLayerByName(tiledMap, "Piece Layer");
        if (pieceLayer != null) {
            for (int x = 0; x < rowCount; x++) {
                for (int y = 0; y < columnCount; y++) {
                    Tile tile = pieceLayer.getTile(x, y);
                    if (tile == null) {
                        tile = new EmptyTile(new Point(x, y)); // Default
                    }
                    tiles[x][y] = tile;
                }
            }
        }
    }

    /**
     * Returns the tile at the specified position.
     *
     * @param position The position of the tile.
     * @return The tile at the specified position or null if out of bounds.
     */
    public Tile getTileAt(Point position) {
        if (!isWithinBounds(position)) {
            return null;
        }
        return tiles[position.x][position.y];
    }

    /**
     * Places a piece on the board.
     *
     * @param piece The piece to place.
     * @param position The position to place the piece at.
     */
    public void placePiece(Piece piece, Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        tiles[position.x][position.y] = new OccupiedTile(position, piece);
        notifyObservers();
    }

    /**
     * Removes a piece from the board.
     *
     * @param position The position to remove the piece from.
     */
    public void removePiece(Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        tiles[position.x][position.y] = new EmptyTile(position);
        notifyObservers();
    }

    /**
     * Checks if a position is within the bounds of the board.
     *
     * @param position The position to check.
     * @return True if the position is within bounds, false otherwise.
     */
    public boolean isWithinBounds(Point position) {
        return position.x >= 0 && position.x < rowCount && position.y >= 0 && position.y < columnCount;
    }

    /**
     * Adds an observer to the board.
     *
     * @param observer The observer to add.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifies all observers of changes to the board.
     */
    public void notifyObservers() {
        for (Iterator<Observer> it = observers.iterator(); it.hasNext(); ) {
            Observer observer = it.next();
            observer.update();
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }
}

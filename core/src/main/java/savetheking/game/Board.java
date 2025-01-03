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
     * Initializes the board with empty tiles.
     */
    public void initializeBoard() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles[i][j] = new EmptyTile(new Point(i, j));
            }
        }
        notifyObservers(); // Notify observers after board initialization
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
        notifyObservers();
    }

    /**
     * Moves a piece from the start position to the end position.
     *
     * @param start The starting position of the piece.
     * @param end The ending position of the piece.
     */
    public void movePiece(Point start, Point end) {
        if (!isWithinBounds(start) || !isWithinBounds(end)) {
            throw new IllegalArgumentException("Positions must be within the bounds of the board.");
        }
        Tile startTile = getTileAt(start);
        if (startTile instanceof OccupiedTile) {
            OccupiedTile occupiedTile = (OccupiedTile) startTile;
            Piece piece = occupiedTile.getPiece();
            removePiece(start);
            placePiece(piece, end);
            piece.move(end, rowCount);
        }
        notifyObservers();
    }

    /**
     * Returns a list of all remaining pieces on the board.
     *
     * @return A list of remaining pieces.
     */
    public List<Piece> getRemainingPieces() {
        List<Piece> remainingPieces = new ArrayList<Piece>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    remainingPieces.add(((OccupiedTile) tile).getPiece());
                }
            }
        }
        return remainingPieces;
    }

    public Tile getTileAt(Point position) {
        if (!isWithinBounds(position)) {
            return null;
        }
        return tiles[position.x][position.y];
    }

    public boolean isWithinBounds(Point position) {
        return position.x >= 0 && position.x < rowCount && position.y >= 0 && position.y < columnCount;
    }

    public void placePiece(Piece piece, Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        tiles[position.x][position.y] = new OccupiedTile(position, piece);
        notifyObservers();
    }

    public void removePiece(Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        tiles[position.x][position.y] = new EmptyTile(position);
        notifyObservers();
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

package savetheking.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * The Board class represents a chessboard for the Solo Chess game.
 */
public class Board implements Observable {
    private final Tile[][] tiles;
    private final int rowCount;
    private final int columnCount;
    private final List<Observer> observers = new ArrayList<Observer>();
    private final TiledMap tiledMap;

    public Board(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        this.rowCount = tiledMap.getProperties().get("height", Integer.class);
        this.columnCount = tiledMap.getProperties().get("width", Integer.class);
        this.tiles = new Tile[rowCount][columnCount];
        initializeBoard();
    }

    public void initializeBoard() {
        if (tiledMap != null) {
            initializeFromTiledMap();
        } else {
            clearBoard();
        }
    }

    private void clearBoard() {
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                tiles[x][y] = new EmptyTile(new Point(x, y), 0);
            }
        }
        notifyObservers();
    }

    private void initializeFromTiledMap() {
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board Layer");

        MapLayer pieceLayer = tiledMap.getLayers().get("Piece Layer");
        if (pieceLayer != null) {
            for (MapObject object : pieceLayer.getObjects()) {
                // Extract properties for the piece
                MapProperties properties = object.getProperties();
                String type = properties.get("type", String.class);
                String color = properties.get("color", String.class);

                // Convert Tiled coordinates to board coordinates
                int x = properties.get("x", Float.class).intValue() / 64; // Adjust for tile size
                int y = (int) ((properties.get("y", Float.class) - 64) / 64); // Adjust for tile size and align grid

                // Flip the y-coordinate to match the board's origin at the bottom-left
                y = (rowCount - 1) - y;

                // Ensure the position is within bounds before placing
                if (!isWithinBounds(new Point(x, y))) {
                    throw new IllegalArgumentException("Position out of bounds: (" + x + ", " + y + ")");
                }

                // Create and place the piece using the PieceFactory
                Point position = new Point(x, y);
                Piece piece = PieceFactory.createPiece(type, color, position);
                placePiece(piece, position);
            }
        }

        if (pieceLayer == null) {
            throw new IllegalStateException("Piece Layer not found in the TiledMap.");
        }

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                int tileId = boardLayer != null && boardLayer.getCell(x, y) != null
                    ? boardLayer.getCell(x, y).getTile().getId()
                    : 0;

                // Initialize tiles as Empty or Occupied based on layer data
                if (boardLayer != null && boardLayer.getCell(x, y) != null) {
                    initializePieceTile(x, y, tileId);
                } else {
                    tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
                }
            }
        }
        notifyObservers();
    }

    private void initializePieceTile(int x, int y, int tileId) {
        // If no specific logic for initialization here, this can be removed or simplified
        tiles[x][y] = new OccupiedTile(new Point(x, y), tileId, null);
    }

    public boolean isValidMove(Point start, Point end) {
        if (!isWithinBounds(start) || !isWithinBounds(end)) {
            return false;
        }
        Tile startTile = getTileAt(start);
        if (startTile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) startTile).getPiece();
            if (piece != null) {
                return piece.getPossibleMoves(this).contains(end);
            }
        }
        return false;
    }

    public void movePiece(Point start, Point end) {
        if (!isWithinBounds(start) || !isWithinBounds(end)) {
            throw new IllegalArgumentException("Positions must be within the bounds of the board.");
        }
        Tile startTile = getTileAt(start);
        if (startTile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) startTile).getPiece();
            removePiece(start);
            placePiece(piece, end);
            piece.move(end, rowCount);
        }
        notifyObservers();
    }

    public List<Piece> getRemainingPieces() {
        List<Piece> remainingPieces = new ArrayList<Piece>();
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile instanceof OccupiedTile) {
                    remainingPieces.add(((OccupiedTile) tile).getPiece());
                }
            }
        }
        return remainingPieces;
    }

    public Tile getTileAt(Point position) {
        return isWithinBounds(position) ? tiles[position.x][position.y] : null;
    }

    public boolean isWithinBounds(Point position) {
        return position.x >= 0 && position.x < rowCount && position.y >= 0 && position.y < columnCount;
    }

    public void placePiece(Piece piece, Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        tiles[position.x][position.y] = new OccupiedTile(position, 0, piece);
        notifyObservers();
    }

    public void removePiece(Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        tiles[position.x][position.y] = new EmptyTile(position, 0);
        notifyObservers();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void dispose() {
        if (tiledMap != null) {
            tiledMap.dispose();
        }
    }
}

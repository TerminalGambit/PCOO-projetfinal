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
    private final boolean pieceDebug = false; // Toggle this to enable/disable debug mode
    private final int tileSize; // Size of each tile in pixels
    private final PieceFactory pieceFactory; // Reference to the shared PieceFactory

    public Board(TiledMap tiledMap, int tileSize, PieceFactory pieceFactory) {
        this.tiledMap = tiledMap;
        this.tileSize = tileSize;
        this.rowCount = tiledMap.getProperties().get("height", Integer.class);
        this.columnCount = tiledMap.getProperties().get("width", Integer.class);
        this.tiles = new Tile[rowCount][columnCount];
        this.pieceFactory = pieceFactory; // Set the shared PieceFactory
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
        // Fetch the Board Layer
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Board Layer");
        if (pieceDebug) {
            System.out.println("Debug: Board Layer = " + (boardLayer != null ? "Found" : "Not Found"));
        }

        // Fetch the Piece Layer
        MapLayer pieceLayer = tiledMap.getLayers().get("Piece Layer");
        if (pieceLayer != null) {
            for (MapObject object : pieceLayer.getObjects()) {
                MapProperties properties = object.getProperties();

                // Extract position and properties from Piece Layer
                float x = properties.get("x", Float.class);
                float y = properties.get("y", Float.class);
                int gridX = Math.round(x / tileSize);
                int gridY = (rowCount - 1) - Math.round(y / tileSize);

                // Extract tile ID directly from the object (GID is the key)
                int tileId = properties.get("gid", Integer.class);

                if (pieceDebug) {
                    System.out.printf("Debug: Found object at (%d, %d) with Tile ID: %d%n", gridX, gridY, tileId);
                }

                if (tileId > 0) { // Ensure tileId is valid
                    initializePieceTile(gridX, gridY, tileId); // Pass to initializePieceTile
                }
            }
        } else {
            throw new IllegalStateException("Piece Layer not found in the TiledMap.");
        }

        // Initialize remaining board tiles
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                if (tiles[x][y] == null) {
                    int tileId = boardLayer != null && boardLayer.getCell(x, y) != null
                        ? boardLayer.getCell(x, y).getTile().getId()
                        : 0;

                    tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
                    if (pieceDebug) {
                        System.out.printf("Debug: Initialized empty tile at (%d, %d) with Tile ID: %d%n", x, y, tileId);
                    }
                }
            }
        }

        notifyObservers();
    }

    private void initializePieceTile(int x, int y, int tileId) {
        if (pieceDebug) {
            System.out.printf("Debug: Initializing piece tile at (%d, %d) with Tile ID: %d%n", x, y, tileId);
        }

        // Retrieve the piece type and color
        String type = getTypeFromTileId(tileId);
        String color = getColorFromTileId(tileId);

        // Check if there's a piece associated with the tile ID
        if (type != null && color != null) {
            Piece piece = pieceFactory.createPiece(type, color, new Point(x, y));
            tiles[x][y] = new OccupiedTile(new Point(x, y), tileId, piece);
            if (pieceDebug) {
                System.out.printf("Debug: Created %s %s at (%d, %d)%n", color, type, x, y);
            }
        } else {
            if (pieceDebug) {
                System.out.printf("Debug: No piece associated with Tile ID: %d at (%d, %d)%n", tileId, x, y);
            }
        }
    }

    private String getTypeFromTileId(int tileId) {
        switch (tileId) {
            case 0:
            case 6:
                return "bishop";
            case 1:
            case 7:
                return "king";
            case 2:
            case 8:
                return "knight";
            case 3:
            case 9:
                return "pawn";
            case 4:
            case 10:
                return "queen";
            case 5:
            case 11:
                return "rook";
            default:
                return null;
        }
    }

    private String getColorFromTileId(int tileId) {
        if (tileId >= 0 && tileId <= 5) {
            return "black";
        } else if (tileId >= 6 && tileId <= 11) {
            return "white";
        }
        return null;
    }

    public Tile getTileAt(Point position) {
        if (isWithinBounds(position)) {
            //System.out.println("Position: " + position + " is within bounds." + "Returning tile at position: " + tiles[position.x][position.y] + " with instance of : " + tiles[position.x][position.y].getClass().getSimpleName());
            return tiles[position.x][position.y];
        }
        return null;
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

    /**
     * Moves a piece from the start position to the end position on the board.
     * Updates the board tiles to reflect the move and notifies observers.
     *
     * @param start The starting position of the piece.
     * @param end   The destination position of the piece.
     * @throws IllegalArgumentException if the start or end positions are out of bounds.
     */
    public void movePiece(Point start, Point end) {
        if (!isWithinBounds(start) || !isWithinBounds(end)) {
            throw new IllegalArgumentException("Positions must be within the bounds of the board.");
        }

        Tile startTile = getTileAt(start);
        Tile endTile = getTileAt(end);

        if (startTile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) startTile).getPiece();

            // Debug: Log details of the move
            System.out.printf("Moving piece: %s from %s to %s%n", piece, start, end);

            // Remove the piece from the start tile
            tiles[start.x][start.y] = new EmptyTile(start, 0);
            System.out.println("Start tile set to empty.");

            // Place the piece on the end tile
            tiles[end.x][end.y] = new OccupiedTile(end, 0, piece);
            System.out.printf("End tile updated with piece: %s at position %s%n", piece, end);

            // Update the piece's position
            piece.move(end, rowCount);
        } else {
            System.err.printf("Error: No piece found at start tile (%s)%n", start);
        }

        // Notify observers about the state change
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
}

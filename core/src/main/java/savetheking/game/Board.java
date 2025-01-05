package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
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
    private final TiledMap tiledMap;

    /**
     * Constructs a Board using a TiledMap for initialization.
     *
     * @param tiledMap The TiledMap to load the board from.
     */
    public Board(TiledMap tiledMap) {
        this.rowCount = tiledMap.getHeight();
        this.columnCount = tiledMap.getWidth();
        this.tiles = new Tile[rowCount][columnCount];
        this.tiledMap = tiledMap;
        initializeBoard(); // Initialize the board
    }

    /**
     * Initializes the board using data from the TiledMap or clears the board if TiledMap is null.
     */
    public void initializeBoard() {
        if (tiledMap != null) {
            initializeFromTiledMap();
        } else {
            // Clear the board with empty tiles
            for (int x = 0; x < rowCount; x++) {
                for (int y = 0; y < columnCount; y++) {
                    tiles[x][y] = new EmptyTile(new Point(x, y), 0); // Default tileId 0
                }
            }
            notifyObservers();
        }
    }

    /**
     * Initializes the board using data from the TiledMap.
     */
    private void initializeFromTiledMap() {
        TiledLayer boardLayer = tiledMap.getLayer("Board Layer");
        TiledLayer pieceLayer = tiledMap.getLayer("Piece Layer");

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                int tileId = boardLayer != null ? boardLayer.getTileId(x, y) : 0;

                if (pieceLayer != null && pieceLayer.getTile(x, y) != null) {
                    Tile pieceTile = pieceLayer.getTile(x, y);
                    String type = pieceTile.getProperty("type");
                    String color = pieceTile.getProperty("color");
                    Point position = new Point(x, y);

                    if (type != null && color != null) {
                        // Use PieceFactory to create the piece
                        Piece piece = PieceFactory.createPiece(type, color, position);
                        tiles[x][y] = new OccupiedTile(position, tileId, piece);
                        System.out.println("Created OccupiedTile at (" + x + ", " + y + ") with piece: " + piece);
                    } else {
                        tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
                        System.out.println("Missing piece data at (" + x + ", " + y + "), created EmptyTile.");
                    }
                } else {
                    tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
                    System.out.println("No piece found at (" + x + ", " + y + "), created EmptyTile.");
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
        tiles[position.x][position.y] = new OccupiedTile(position, 0, piece); // Default tileId 0 for new piece
        System.out.println("Placed piece at (" + position.x + ", " + position.y + "): " + piece);
        notifyObservers();
    }

    public void removePiece(Point position) {
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        tiles[position.x][position.y] = new EmptyTile(position, 0); // Default tileId 0
        System.out.println("Removed piece from (" + position.x + ", " + position.y + ")");
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

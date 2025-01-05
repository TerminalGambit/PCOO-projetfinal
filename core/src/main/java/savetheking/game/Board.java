package savetheking.game;

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
        initializeBoard();
    }

    /**
     * Initializes the board using data from the TiledMap or clears the board if TiledMap is null.
     */
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
        TiledLayer boardLayer = tiledMap.getLayer("Board Layer");
        TiledLayer pieceLayer = tiledMap.getLayer("Piece Layer");

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                int tileId = boardLayer != null ? boardLayer.getTileId(x, y) : 0;

                if (pieceLayer != null && pieceLayer.getTile(x, y) != null) {
                    initializePieceTile(x, y, tileId, pieceLayer.getTile(x, y));
                } else {
                    tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
                }
            }
        }
        notifyObservers();
    }

    private void initializePieceTile(int x, int y, int tileId, Tile pieceTile) {
        String type = pieceTile.getProperty("type");
        String color = pieceTile.getProperty("color");
        Point position = new Point(x, y);

        if (type != null && color != null) {
            Piece piece = PieceFactory.createPiece(type, color, position);
            tiles[x][y] = new OccupiedTile(position, tileId, piece);
        } else {
            tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
        }
    }

    /**
     * Moves a piece from the start position to the end position.
     *
     * @param start The starting position.
     * @param end   The ending position.
     */
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

    /**
     * Retrieves a list of all remaining pieces on the board.
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

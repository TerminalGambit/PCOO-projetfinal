package savetheking.game;

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
    private final CustomTiledMap tiledMap;

    public Board(CustomTiledMap tiledMap) {
        this.rowCount = tiledMap.getHeight();
        this.columnCount = tiledMap.getWidth();
        this.tiles = new Tile[rowCount][columnCount];
        this.tiledMap = tiledMap;
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
        TiledLayer boardLayer = tiledMap.getLayer("Board Layer");

        TiledLayer pieceLayer = tiledMap.getLayer("Piece Layer");
        if (pieceLayer == null) {
            pieceLayer = tiledMap.getObjectLayer("Piece Layer"); // Attempt to retrieve object layer
        }


        if (pieceLayer == null) {
            throw new IllegalStateException("Piece Layer not found in the TiledMap.");
        }

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                int tileId = boardLayer != null ? boardLayer.getTileId(x, y) : 0;
                System.out.println("Tile ID: " + tileId);
                System.out.println("Piece Layer: " + pieceLayer);
                System.out.println("Piece Layer Tile: " + pieceLayer.getTile(x, y));

                if (pieceLayer.getTile(x, y) != null) {
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
            System.out.println("CASE 1 Created piece: " + piece + "Occupied tile: " + pieceTile);
            tiles[x][y] = new OccupiedTile(position, tileId, piece);
        } else {
            System.out.println("CASE 2 Error: Invalid piece type or color for tile at position: " + position + " Empty tile created.");
            tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
        }
    }

    public boolean isValidMove(Point start, Point end) {
        if (!isWithinBounds(start) || !isWithinBounds(end)) {
            return false;
        }

        Tile startTile = getTileAt(start);
        if (startTile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) startTile).getPiece();
            if (piece != null) {
                List<Point> possibleMoves = piece.getPossibleMoves(this);
                return possibleMoves.contains(end);
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

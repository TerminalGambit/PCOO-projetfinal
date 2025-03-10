package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.List;

public class Board implements Observable {
    private final Tile[][] tiles;
    private final int rowCount;
    private final int columnCount;
    private final List<Observer> observers = new ArrayList<Observer>();
    private final CustomTiledMap tiledMap;
    private List<Piece> pieces;

    public Board(CustomTiledMap tiledMap) {
        this.rowCount = tiledMap.getHeight();
        this.columnCount = tiledMap.getWidth();
        this.tiles = new Tile[rowCount][columnCount];
        this.tiledMap = tiledMap;
        this.pieces = new ArrayList<Piece>();
        initializeBoard();
    }

    public void initializeBoard() {
        System.out.println("[DEBUG] Initializing board...");
        if (tiledMap != null) {
            System.out.println("[DEBUG] Using tiled map for initialization.");
            initializeFromTiledMap();
        } else {
            System.out.println("[DEBUG] No tiled map provided. Clearing board.");
            clearBoard();
        }
    }

    private void clearBoard() {
        System.out.println("[DEBUG] Clearing the board...");
        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                tiles[x][y] = new EmptyTile(new Point(x, y), 0);
                System.out.println("[DEBUG] Cleared tile at (" + x + ", " + y + ").");
                System.out.println("[DEBUG] Initialized tile at (" + x + ", " + y + ") with texture: " + (tiles[x][y].getTexture() != null ? "Loaded" : "Null"));
            }
        }
        notifyObservers();
    }

    private void initializeFromTiledMap() {
        System.out.println("[DEBUG] Initializing board from tiled map...");
        TiledLayer boardLayer = tiledMap.getLayer("Board Layer");
        TiledLayer pieceLayer = tiledMap.getLayer("Piece Layer");

        for (int x = 0; x < rowCount; x++) {
            for (int y = 0; y < columnCount; y++) {
                int tileId = boardLayer != null ? boardLayer.getTileId(x, y) : 0;

                if (pieceLayer != null && pieceLayer.getTile(x, y) != null) {
                    initializePieceTile(x, y, tileId, pieceLayer.getTile(x, y));
                } else {
                    System.out.println("[DEBUG] No tiled map provided. Clearing board.");
                    tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
                    System.out.println("[DEBUG] Cleared tile at (" + x + ", " + y + ").");
                    System.out.println("[DEBUG] Initialized tile at (" + x + ", " + y + ") with texture: " + (tiles[x][y].getTexture() != null ? "Loaded" : "Null"));
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
            if (piece != null) {
                tiles[x][y] = new OccupiedTile(position, tileId, piece);
                System.out.println("[DEBUG] Cleared tile at (" + x + ", " + y + ").");
                System.out.println("[DEBUG] Initialized tile at (" + x + ", " + y + ") with texture: " + (tiles[x][y].getTexture() != null ? "Loaded" : "Null"));
                pieces.add(piece);
                System.out.println("Placed piece: " + piece + " at position: " + position);
            } else {
                System.out.println("[DEBUG] No tiled map provided. Clearing board.");
                tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
                System.out.println("[DEBUG] Cleared tile at (" + x + ", " + y + ").");
                System.out.println("[DEBUG] Initialized tile at (" + x + ", " + y + ") with texture: " + (tiles[x][y].getTexture() != null ? "Loaded" : "Null"));
                System.out.println("Failed to create piece at position: " + position);
            }
        } else {
            System.out.println("[DEBUG] No tiled map provided. Clearing board.");
            tiles[x][y] = new EmptyTile(new Point(x, y), tileId);
            System.out.println("[DEBUG] Cleared tile at (" + x + ", " + y + ").");
            System.out.println("[DEBUG] Initialized tile at (" + x + ", " + y + ") with texture: " + (tiles[x][y].getTexture() != null ? "Loaded" : "Null"));
            System.out.println("No piece at position: " + position);
        }
    }

    public void render(SpriteBatch batch) {
        // Render all tiles first
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                Tile tile = tiles[row][col];
                Texture texture = tile.getTexture();

                if (texture == null) {
                    System.err.println("[ERROR] Texture is null for tile at position: (" + row + ", " + col + "). Tile Type: " + tile.getClass().getSimpleName());
                    continue; // Skip rendering this tile
                }

                try {
                    tile.render(batch, texture);
                } catch (Exception e) {
                    System.err.println("[ERROR] Failed to render tile at (" + row + ", " + col + "): " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // Render all pieces
        for (Piece piece : pieces) {
            try {
                piece.render(batch);
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to render piece at position: " + piece.getPosition() + ": " + e.getMessage());
                e.printStackTrace();
            }
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

    public List<Piece> getPieces() {
        return pieces;
    }
}

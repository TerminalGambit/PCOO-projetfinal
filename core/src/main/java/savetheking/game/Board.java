package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The Board class represents the chessboard and manages the tiles and pieces.
 */
public class Board implements Observable {
    private final Tile[][] tiles;
    private final int rowCount;
    private final int columnCount;
    private final List<Observer> observers = new ArrayList<Observer>();

    /**
     * Constructor for a square board.
     * @param size The size of the board (rows and columns).
     */
    public Board(int size) {
        this(size, size);
    }

    /**
     * Constructor for a rectangular board.
     * @param rowCount The number of rows.
     * @param columnCount The number of columns.
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
     * Places a piece at the given position on the board.
     * @param piece The piece to place.
     * @param position The position to place the piece at.
     */
    public void placePiece(Piece piece, Point position) {
        if (piece == null || position == null) {
            throw new IllegalArgumentException("Piece and position cannot be null.");
        }
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        tiles[position.x][position.y] = new OccupiedTile(position, piece);
        notifyObservers();
    }

    /**
     * Removes a piece from the given position on the board.
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
     * Returns the tile at the given position on the board.
     * @param position The position of the tile.
     * @return The tile at the given position.
     */
    public Tile getTileAt(Point position) {
        if (!isWithinBounds(position)) {
            return null;
        }
        return tiles[position.x][position.y];
    }

    /**
     * Returns the number of rows on the board.
     * @return The number of rows.
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Returns the number of columns on the board.
     * @return The number of columns.
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Checks if a given position is within the bounds of the board.
     * @param position The position to check.
     * @return True if the position is within bounds, false otherwise.
     */
    public boolean isWithinBounds(Point position) {
        return position != null &&
            position.x >= 0 && position.x < rowCount &&
            position.y >= 0 && position.y < columnCount;
    }

    /**
     * Moves a piece from the start position to the end position.
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

    /**
     * Adds an observer to the board.
     * @param observer The observer to add.
     */
    @Override
    public void addObserver(Observer observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    /**
     * Notifies all observers of changes.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Prints a representation of the board to the console for debugging.
     */
    public void printBoard() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                System.out.print(tile instanceof OccupiedTile ? "O " : ". ");
            }
            System.out.println();
        }
    }
}

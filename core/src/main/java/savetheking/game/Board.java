package savetheking.game;

/**
 * The Board class represents a chessboard-like grid that holds Tile objects.
 * It can support different board sizes and allows placing and removing pieces on specific tiles.
 */
public class Board {
    private Tile[][] tiles; // 2D array representing the board grid with Tile objects
    private int rowCount;   // Number of rows in the board
    private int columnCount; // Number of columns in the board

    /**
     * Constructor for a square board.
     * @param size The size of the board (both rows and columns).
     */
    public Board(int size) {
        this(size, size); // Calls the two-parameter constructor with the same row and column count
    }

    /**
     * Constructor for a rectangular board with specified row and column counts.
     * @param rowCount The number of rows in the board.
     * @param columnCount The number of columns in the board.
     */
    public Board(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        initializeBoard(); // Sets up the initial board with empty tiles
    }

    /**
     * Initializes the board by filling it with EmptyTile instances for each position.
     * This prepares the board for placing pieces later.
     */
    public void initializeBoard() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles[i][j] = new EmptyTile(new Point(i, j)); // Creates an empty tile at each position
            }
        }
    }

    /**
     * Places a piece on a specific tile. If the tile is empty, it becomes occupied.
     * If it's already occupied, the existing piece is replaced with the new piece.
     * @param piece The piece to place on the board.
     * @param position The position on the board to place the piece.
     */
    public void placePiece(Piece piece, Point position) {
        Tile tile = tiles[position.x][position.y];
        if (tile instanceof EmptyTile) {
            tiles[position.x][position.y] = new OccupiedTile(position, piece);
        } else if (tile instanceof OccupiedTile) {
            ((OccupiedTile) tile).setPiece(piece); // Replaces the piece if already occupied
        }
    }

    /**
     * Removes a piece from a specific position on the board.
     * If the tile is occupied, it becomes an empty tile after removing the piece.
     * @param position The position from which to remove the piece.
     */
    public void removePiece(Point position) {
        Tile tile = tiles[position.x][position.y];
        if (tile instanceof OccupiedTile) {
            tiles[position.x][position.y] = new EmptyTile(position); // Reverts to an empty tile
        }
    }

    /**
     * Retrieves the tile at a specified position on the board.
     * @param position The position to retrieve the tile from.
     * @return The tile at the specified position or null if out of bounds.
     */
    public Tile getTileAt(Point position) {
        if (position.isWithinBounds(rowCount) && position.isWithinBounds(columnCount)) {
            return tiles[position.x][position.y];
        }
        return null; // Returns null if the position is out of the board’s bounds
    }

    /**
     * Gets the number of rows in the board.
     * @return The row count of the board.
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Gets the number of columns in the board.
     * @return The column count of the board.
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
        return position.x >= 0 && position.x < this.rowCount &&
            position.y >= 0 && position.y < this.columnCount;
    }
}

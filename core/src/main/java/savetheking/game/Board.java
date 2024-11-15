package savetheking.game;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Checks if all tiles between start and end positions are empty, useful for moves like castling.
     * Assumes start and end are aligned either horizontally, vertically, or diagonally.
     * @param start The starting point.
     * @param end The ending point.
     * @return True if all tiles between start and end are empty, false otherwise.
     */
    public boolean isPathClear(Point start, Point end) {
        int dx = Integer.compare(end.x, start.x); // Direction step for x-axis
        int dy = Integer.compare(end.y, start.y); // Direction step for y-axis

        int currentX = start.x + dx;
        int currentY = start.y + dy;

        // Traverse from start to end, excluding end position itself
        while (currentX != end.x || currentY != end.y) {
            Point currentPoint = new Point(currentX, currentY);
            Tile tile = getTileAt(currentPoint);

            if (tile instanceof OccupiedTile) {
                return false; // Path is not clear if any tile is occupied
            }

            currentX += dx;
            currentY += dy;
        }

        return true; // Path is clear if no occupied tiles were encountered
    }

    /**
     * Retrieves all pieces of the opponent of a given color.
     * @param color The color of the current player.
     * @return A list of opponent pieces on the board.
     */
    public List<Piece> getOpponentPieces(String color) {
        List<Piece> opponentPieces = new ArrayList<Piece>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (!piece.getColor().equals(color)) {
                        opponentPieces.add(piece);
                    }
                }
            }
        }
        return opponentPieces;
    }

    /**
     * Retrieves all pieces of the current player of a given color.
     * @param color The color of the current player.
     * @return A list of player's pieces on the board.
     */
    public List<Piece> getPlayerPieces(String color) {
        List<Piece> playerPieces = new ArrayList<Piece>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (piece.getColor().equals(color)) {
                        playerPieces.add(piece);
                    }
                }
            }
        }
        return playerPieces;
    }

    /**
     * Creates a deep copy of the current board, including the positions of all pieces.
     * @return A copy of the board.
     */
    public Board copy() {
        Board copy = new Board(rowCount, columnCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    copy.placePiece(piece, new Point(i, j));
                } else {
                    copy.tiles[i][j] = new EmptyTile(new Point(i, j));
                }
            }
        }
        return copy;
    }

    /**
     * Moves a piece from a start position to an end position.
     * @param start The starting position of the piece.
     * @param end The ending position of the piece.
     */
    public void movePiece(Point start, Point end) {
        Tile startTile = getTileAt(start);
        if (startTile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) startTile).getPiece();
            removePiece(start); // Remove from start position
            placePiece(piece, end); // Place at end position
            piece.move(end); // Update piece's position
        }
    }

    /**
     * Retrieves a piece at a specified position on the board.
     * @param position The position to retrieve the piece from.
     * @return The piece at the specified position, or null if no piece is present.
     */
    public Piece getPieceAt(Point position) {
        Tile tile = getTileAt(position);
        if (tile instanceof OccupiedTile) {
            return ((OccupiedTile) tile).getPiece();
        }
        return null;
    }

    /**
     * Checks if a given position is under attack by any opponent piece.
     * @param position The position to check.
     * @param color The color of the player who wants to know if the position is under attack.
     * @return True if the position is under attack, false otherwise.
     */
    public boolean isPositionUnderAttack(Point position, String color) {
        List<Piece> opponentPieces = getOpponentPieces(color); // Retrieve all opponent pieces

        // Check if any opponent piece can move to the given position
        for (Piece piece : opponentPieces) {
            List<Point> possibleMoves = piece.getPossibleMoves(this);
            for (Point move : possibleMoves) {
                if (move.equals(position)) {
                    return true; // Position is under attack
                }
            }
        }
        return false; // No opponent piece can attack this position
    }
}

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
     * Checks if a given tile is occupied by a piece of a specific color.
     * @param position The position to check.
     * @param color The color to match.
     * @return True if the tile is occupied by a piece of the specified color, otherwise false.
     */
    public boolean isOccupiedByColor(Point position, String color) {
        Tile tile = getTileAt(position);
        if (tile instanceof OccupiedTile) {
            Piece piece = ((OccupiedTile) tile).getPiece();
            return piece.getColor().equals(color);
        }
        return false;
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
     * Updates the board state based on the current positions of the pieces.
     * This can be used to refresh defended tiles or other dynamic attributes.
     */
    public void updateBoard() {
        // Reset all defended states
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles[i][j].setDefended(false);
            }
        }

        // Recalculate defended tiles
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    List<Point> defendedTiles = piece.getDefendedTiles(this);
                    for (Point point : defendedTiles) {
                        Tile defendedTile = getTileAt(point);
                        if (defendedTile != null) {
                            defendedTile.setDefended(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Resizes the board to a new number of rows and columns.
     * This method clears the current board state and reinitializes the board.
     * @param rows The new number of rows.
     * @param columns The new number of columns.
     */
    public void resizeBoard(int rows, int columns) {
        this.rowCount = rows;
        this.columnCount = columns;
        tiles = new Tile[rows][columns];
        initializeBoard(); // Reinitialize with the new dimensions
    }



    /**
     * Retrieves all empty tiles on the board.
     * @return A list of empty tiles (positions) on the board.
     */
    public List<Point> getEmptyTiles() {
        List<Point> emptyTiles = new ArrayList<Point>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof EmptyTile) {
                    emptyTiles.add(tile.getPosition());
                }
            }
        }
        return emptyTiles;
    }

    /**
     * Resets the board by clearing all pieces and reinitializing all tiles.
     */
    public void resetBoard() {
        initializeBoard();
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
        if (!isWithinBounds(position)) {
            throw new IllegalArgumentException("Position out of bounds: " + position);
        }
        Tile tile = getTileAt(position);
        return (tile instanceof OccupiedTile) ? ((OccupiedTile) tile).getPiece() : null;
    }

    /**
     * Checks if a given position is under attack by any opponent piece.
     * @param position The position to check.
     * @param color The color of the player who wants to know if the position is under attack.
     * @return True if the position is under attack, false otherwise.
     */
    public boolean isPositionUnderAttack(Point position, String color) {
        List<Piece> opponentPieces = getOpponentPieces(color);
        for (Piece piece : opponentPieces) {
            List<Point> possibleMoves = piece.getPossibleMoves(this);
            for (Point move : possibleMoves) {
                if (move.equals(position)) {
                    return true; // Position is under attack
                }
            }
        }
        return false; // Position is not under attack
    }

    /**
     * Prints the board to the console with a visual representation.
     * Empty tiles are represented with '.', and pieces are shown with their color and type.
     */
    public void printBoard() {
        final String EMPTY_TILE_REPRESENTATION = "."; // Representation for empty tiles
        final String ROW_HEADER = "   "; // Spacing for column headers

        // Print column headers
        StringBuilder columnHeaders = new StringBuilder(ROW_HEADER);
        for (int col = 0; col < columnCount; col++) {
            columnHeaders.append((char) ('a' + col)).append(" ");
        }
        System.out.println(columnHeaders.toString());

        // Print rows with tiles
        for (int i = 0; i < rowCount; i++) {
            // Print row number on the left
            System.out.print((rowCount - i) + " ");

            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];

                // Represent empty tiles and pieces
                if (tile instanceof EmptyTile) {
                    System.out.print(" " + EMPTY_TILE_REPRESENTATION);
                } else if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    char pieceChar = piece.toString().charAt(0); // First letter of the piece type
                    char colorChar = piece.getColor().charAt(0);  // First letter of the color
                    System.out.print(" " + colorChar + pieceChar);
                }
            }

            // Print row number on the right
            System.out.println(" " + (rowCount - i));
        }

        // Print column headers again
        System.out.println(columnHeaders.toString());
    }

    /**
     * Retrieves all tiles that are defended by the player's pieces.
     * A defended tile is one that can be reached by a move of any piece of the given color.
     * @return A list of points representing the defended tiles on the board.
     */
    public List<Point> getDefendedTiles() {
        List<Point> defendedTiles = new ArrayList<Point>();

        // Iterate through all tiles on the board
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Tile tile = tiles[i][j];
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    // Add all tiles that this piece defends
                    defendedTiles.addAll(piece.getDefendedTiles(this));
                }
            }
        }

        return defendedTiles;
    }
}

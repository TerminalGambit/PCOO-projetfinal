package savetheking.game;

/**
 * Tile is an abstract class representing a tile on the board.
 * It provides methods for checking if the tile is defended or occupied, and handles its position.
 */
public abstract class Tile {
    protected Point position; // The position of the tile on the board
    protected boolean isDefended; // Flag indicating if the tile is defended by a piece

    /**
     * Constructor to initialize the tile with a position.
     * @param position The position of this tile on the board.
     */
    public Tile(Point position) {
        this.position = position;
        this.isDefended = false; // By default, a tile is not defended
    }

    public Tile() {
        this.isDefended = false;
    }

    /**
     * Checks if this tile is occupied by a piece.
     * @return True if the tile is an instance of OccupiedTile; otherwise, false.
     */
    public boolean isOccupied() {
        return this instanceof OccupiedTile;
    }

    /**
     * Gets the position of this tile.
     * @return The position of the tile.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Checks if this tile is defended.
     * @return True if the tile is defended; otherwise, false.
     */
    public boolean isDefended() {
        return isDefended;
    }

    /**
     * Sets the defended status of this tile.
     * @param defended The new defended status.
     */
    public void setDefended(boolean defended) {
        this.isDefended = defended;
    }

    // Abstract methods for managing pieces
    public abstract void setPiece(Piece piece);
    public abstract Piece getPiece();
    public abstract void removePiece();
}

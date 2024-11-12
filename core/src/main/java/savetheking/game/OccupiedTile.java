package savetheking.game;

/**
 * OccupiedTile represents a tile on the board that currently holds a piece.
 * It allows getting, setting, and removing the piece it holds.
 */
public class OccupiedTile extends Tile {
    private Piece piece; // The piece currently occupying this tile

    /**
     * Constructor to initialize an occupied tile with a position and a piece.
     * @param position The position of this tile on the board.
     * @param piece The piece occupying this tile.
     */
    public OccupiedTile(Point position, Piece piece) {
        super(position);
        this.piece = piece;
    }

    /**
     * Sets a piece on this occupied tile.
     * @param piece The piece to set on this tile.
     */
    @Override
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Retrieves the piece currently occupying this tile.
     * @return The piece on this tile.
     */
    @Override
    public Piece getPiece() {
        return piece;
    }

    /**
     * Removes the piece from this tile, making it effectively empty.
     */
    @Override
    public void removePiece() {
        this.piece = null;
    }
}

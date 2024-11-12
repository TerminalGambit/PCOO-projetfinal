package savetheking.game;

/**
 * EmptyTile represents a tile that does not currently hold a piece.
 * It overrides methods for managing pieces to indicate that no piece can be set directly.
 */
public class EmptyTile extends Tile {

    /**
     * Constructor to initialize an empty tile at a specific position.
     * @param position The position of this tile on the board.
     */
    public EmptyTile(Point position) {
        super(position);
    }

    /**
     * Throws an exception because pieces cannot be placed directly on an empty tile.
     * Instead, the Board class should handle piece placement, converting the tile to OccupiedTile.
     * @param piece The piece to place (not applicable for EmptyTile).
     */
    @Override
    public void setPiece(Piece piece) {
        throw new UnsupportedOperationException("Cannot set a piece on an empty tile directly. Use Board to handle conversion to OccupiedTile.");
    }

    /**
     * Returns null because there is no piece on an empty tile.
     * @return Null, indicating no piece is present.
     */
    @Override
    public Piece getPiece() {
        return null;
    }

    /**
     * Does nothing, as there is no piece to remove on an empty tile.
     */
    @Override
    public void removePiece() {
        // Nothing to remove, as it's already empty
    }
}

package savetheking.game;

public class EmptyTile extends Tile {

    /**
     * Constructs an EmptyTile with the specified position and ID.
     *
     * @param position The position of the tile on the board.
     * @param id       The ID of the tile.
     */
    public EmptyTile(Point position, int id) {
        super(position, id);
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}

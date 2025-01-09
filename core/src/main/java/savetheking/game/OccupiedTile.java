package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

/**
 * Class representing a tile occupied by a piece on the board.
 */
class OccupiedTile extends Tile {
    private final Piece piece;

    /**
     * Constructs an OccupiedTile at a specific position with a piece.
     *
     * @param position The position of the tile on the board.
     * @param tileId   The ID representing the type of tile (e.g., dark green = 1, light white = 3).
     * @param piece    The piece occupying the tile.
     */
    public OccupiedTile(Point position, int tileId, Piece piece) {
        super(position, tileId);
        this.piece = piece;
    }

    @Override
    public boolean isOccupied() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
    public void render(SpriteBatch batch, Texture texture) {
        // Render the base tile
        super.render(batch, texture);

        // Render the piece on top of the tile
        if (piece != null) {
            piece.render(batch);
        }
    }
}

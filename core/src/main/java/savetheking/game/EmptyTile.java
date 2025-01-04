package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import savetheking.game.Piece;
import savetheking.game.Point;
import savetheking.game.Tile;

/**
    * Class representing an empty tile on the board.
    */

class EmptyTile extends Tile {

    /**
     * Constructs an EmptyTile at a specific position.
     *
     * @param position The position of the tile on the board.
     * @param tileId   The ID representing the type of tile (e.g., dark green = 1, light white = 3).
     */
    public EmptyTile(Point position, int tileId) {
        super(position, tileId);
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


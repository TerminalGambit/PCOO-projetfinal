package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Abstract base class for tiles on the board.
 */
public abstract class Tile {
    protected final Point position;

    public Tile(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public abstract void render(SpriteBatch batch, Texture texture);
}

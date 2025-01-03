
package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents an empty tile on the board.
 */
public class EmptyTile extends Tile {
    public EmptyTile(Point position) {
        super(position);
    }

    @Override
    public boolean isOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }

    @Override
    public void render(SpriteBatch batch, Texture texture) {
        // Render the empty tile using the provided texture
        batch.draw(texture, position.getX() * 64, position.getY() * 64, 64, 64);
    }
}

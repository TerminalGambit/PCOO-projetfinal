
package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a tile occupied by a chess piece.
 */
public class OccupiedTile extends Tile {
    private Piece piece;

    public OccupiedTile(Point position, Piece piece) {
        super(position);
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
        // Render the tile and delegate piece rendering to external logic
        batch.draw(texture, position.getX() * 64, position.getY() * 64, 64, 64);
    }
}

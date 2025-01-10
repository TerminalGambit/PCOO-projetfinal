package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import java.util.HashMap;

public abstract class Tile {
    protected final Point position;
    protected final int tileId;
    protected final HashMap<String, String> properties;
    private boolean isHighlighted;
    protected Texture texture;

    public Tile(Point position, int tileId) {
        this.position = position;
        this.tileId = tileId;
        this.properties = new HashMap<String, String>();
        this.isHighlighted = false;
    }

    public Point getPosition() {
        return position;
    }

    public int getTileId() {
        return tileId;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void render(SpriteBatch batch, Texture texture) {
        if (texture == null) {
            System.err.println("Error: Texture is null for tile at position: " + position);
            return;
        }

        int screenX = position.y * 64;
        int screenY = (8 - position.x - 1) * 64;

        batch.draw(texture, screenX, screenY, 64, 64);

        if (isHighlighted) {
            batch.end();
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(1, 1, 0, 0.4f));
            shapeRenderer.rect(screenX, screenY, 64, 64);
            shapeRenderer.end();
            shapeRenderer.dispose();
            batch.begin();
        }
    }
}

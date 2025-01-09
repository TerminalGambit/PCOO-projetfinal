package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;

public abstract class Piece {
    protected String color;
    protected Point position;
    protected Texture texture;
    private int moveCount;

    public Piece(String color, Point position, Texture texture) {
        if (texture == null) {
            throw new IllegalArgumentException("Texture cannot be null");
        }
        this.color = color;
        this.position = position;
        this.texture = texture;
        this.moveCount = 0;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            moveCount++;
        } else {
            throw new IllegalArgumentException("Position out of bounds: " + newPosition);
        }
    }

    protected boolean isWithinBounds(Point point, int boardSize) {
        return point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize;
    }

    public abstract List<Point> getPossibleMoves(Board board);

    public void render(SpriteBatch batch) {
        int screenX = position.y * 64;
        int screenY = (8 - position.x - 1) * 64;
        batch.draw(texture, screenX, screenY, 64, 64);
    }

    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (" + color + ") at " + position;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}

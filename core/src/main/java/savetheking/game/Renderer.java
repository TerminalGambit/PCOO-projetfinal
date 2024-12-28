package savetheking.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.Map;

public class Renderer implements Observer {
    private final Board board;
    private final Map<String, Texture> textures; // Map to store textures for each piece type
    private final ShapeRenderer shapeRenderer; // For rendering the board grid
    private static final int TILE_SIZE = 64; // Size of each tile in pixels

    /**
     * Constructor for the Renderer.
     *
     * @param board The board to render.
     */
    public Renderer(Board board) {
        this.board = board;
        this.textures = new HashMap<String, Texture>();
        this.shapeRenderer = new ShapeRenderer();
        loadTextures();
    }

    /**
     * Load textures for all pieces.
     */
    private void loadTextures() {
        textures.put("King", new Texture("pieces/wk.png"));
        textures.put("Queen", new Texture("pieces/wq.png"));
        textures.put("Bishop", new Texture("pieces/wb.png"));
        textures.put("Rook", new Texture("pieces/wr.png"));
        textures.put("Knight", new Texture("pieces/wn.png"));
        textures.put("Pawn", new Texture("pieces/wp.png"));
    }

    /**
     * Render the board and all pieces.
     *
     * @param batch The SpriteBatch used for rendering.
     */
    public void render(SpriteBatch batch) {
        // Render the chessboard grid
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getRowCount(); col++) {
                if ((row + col) % 2 == 0) {
                    shapeRenderer.setColor(Color.LIGHT_GRAY); // Light tiles
                } else {
                    shapeRenderer.setColor(Color.DARK_GRAY); // Dark tiles
                }
                shapeRenderer.rect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        shapeRenderer.end();

        // Render pieces
        batch.begin();
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getRowCount(); col++) {
                Tile tile = board.getTileAt(new Point(row, col));
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    Texture texture = textures.get(piece.getClass().getSimpleName());
                    if (texture != null) {
                        batch.draw(texture, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
        batch.end();
    }

    /**
     * Observer update method.
     * Called when the board or game state changes.
     */
    @Override
    public void update() {
        System.out.println("Renderer notified of changes.");
        // In a real application, you might trigger a re-render here
    }

    /**
     * Dispose of resources to prevent memory leaks.
     */
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        shapeRenderer.dispose();
    }
}

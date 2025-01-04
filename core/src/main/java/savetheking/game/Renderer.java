package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Renderer class is responsible for drawing the game board and pieces on the screen.
 */
public class Renderer {
    private final Board board;
    private final int tileSize;
    private final SpriteBatch batch;
    private final BitmapFont debugFont;

    // Temporary test texture
    private final Texture testTexture;

    /**
     * Constructs a Renderer with the specified board and tile size.
     *
     * @param board    The game board to render.
     * @param tileSize The size of each tile in pixels.
     */
    public Renderer(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.batch = new SpriteBatch();
        this.debugFont = new BitmapFont(); // For debug purposes

        // Load test texture for debugging
        this.testTexture = new Texture("dark-green.png");
        if (testTexture != null) {
            System.out.println("Test texture (dark-green.png) loaded successfully.");
        } else {
            System.out.println("Failed to load test texture.");
        }
    }

    /**
     * Returns a test texture for temporary rendering.
     *
     * @return The test texture.
     */
    public Texture getTestTexture() {
        return testTexture;
    }

    public void render(SpriteBatch batch) {
        renderBoardLayer(); // Render the board
        renderPieces(batch); // Add logic to render pieces
    }

    private void renderPieces(SpriteBatch batch) {
        // Example logic for rendering pieces
        for (int x = 0; x < board.getRowCount(); x++) {
            for (int y = 0; y < board.getColumnCount(); y++) {
                Tile tile = board.getTileAt(new Point(x, y));
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    // piece.render(batch); // Assuming Piece has a render method
                }
            }
        }
    }

    public void renderBoardLayer() {
        batch.begin();

        Texture darkSquareTexture = new Texture("dark-green.png");
        Texture lightSquareTexture = new Texture("light-white.png");

        for (int x = 0; x < board.getRowCount(); x++) {
            for (int y = 0; y < board.getColumnCount(); y++) {
                boolean isDarkSquare = (x + y) % 2 == 0;
                Texture texture = isDarkSquare ? darkSquareTexture : lightSquareTexture;
                int screenX = y * tileSize; // Adjusted for column-major rendering
                int screenY = (board.getRowCount() - x - 1) * tileSize;

                batch.draw(texture, screenX, screenY, tileSize, tileSize);
            }
        }

        batch.end();

        darkSquareTexture.dispose();
        lightSquareTexture.dispose();
    }

    public void dispose() {
        batch.dispose();
        debugFont.dispose();
        testTexture.dispose(); // Dispose of the test texture
    }
}

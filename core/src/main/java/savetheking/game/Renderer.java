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
                    //piece.render(batch); // Assuming Piece has a render method
                }
            }
        }
    }

    /**
     * Renders the board layer.
     */
    public void renderBoardLayer() {
        batch.begin();

        Texture darkSquareTexture = new Texture("dark-green.png");
        Texture lightSquareTexture = new Texture("light-white.png");

        for (int x = 0; x < board.getRowCount(); x++) {
            for (int y = 0; y < board.getColumnCount(); y++) {
                // Determine if the square is dark or light
                boolean isDarkSquare = (x + y) % 2 == 0;

                // Select the appropriate texture
                Texture texture = isDarkSquare ? darkSquareTexture : lightSquareTexture;

                // Calculate screen position
                int screenX = y * tileSize; // Adjusted for column-major rendering
                int screenY = (board.getRowCount() - x - 1) * tileSize;

                // Render the square
                batch.draw(texture, screenX, screenY, tileSize, tileSize);
            }
        }

        batch.end();

        // Dispose of textures after use
        darkSquareTexture.dispose();
        lightSquareTexture.dispose();
    }

    /**
     * Disposes of the batch and font resources.
     */
    public void dispose() {
        batch.dispose();
        debugFont.dispose();
    }
}

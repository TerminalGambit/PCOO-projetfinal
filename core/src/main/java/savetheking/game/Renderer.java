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

    private Texture darkSquareTexture;
    private Texture lightSquareTexture;

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

        // Load textures with debug
        try {
            darkSquareTexture = new Texture("dark-green.png");
            if (darkSquareTexture != null) {
                System.out.println("Dark green texture loaded successfully.");
            } else {
                System.out.println("Failed to load dark green texture.");
            }

            lightSquareTexture = new Texture("light-white.png");
            if (lightSquareTexture != null) {
                System.out.println("Light white texture loaded successfully.");
            } else {
                System.out.println("Failed to load light white texture.");
            }
        } catch (Exception e) {
            System.err.println("Error loading textures: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Main render method for the Renderer.
     */
    public void render(SpriteBatch batch) {
        // Start batch rendering
        batch.begin();

        // Debug: Draw simple text to verify rendering
        debugFont.draw(batch, "Rendering Debug: Board and Pieces", 50, 50);

        // Render board layer
        renderBoardLayer();

        // Render pieces (logic pending implementation)
        renderPieces(batch);

        // End batch rendering
        batch.end();
    }

    /**
     * Renders the pieces on the board.
     */
    private void renderPieces(SpriteBatch batch) {
        // Example logic for rendering pieces
        for (int x = 0; x < board.getRowCount(); x++) {
            for (int y = 0; y < board.getColumnCount(); y++) {
                Tile tile = board.getTileAt(new Point(x, y));
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    // Commented out as piece rendering logic is not implemented yet
                    // piece.render(batch);
                }
            }
        }
    }

    /**
     * Renders the board layer.
     */
    public void renderBoardLayer() {
        // Debug: Confirm method is called
        System.out.println("Rendering Board Layer...");

        for (int x = 0; x < board.getRowCount(); x++) {
            for (int y = 0; y < board.getColumnCount(); y++) {
                // Determine if the square is dark or light
                boolean isDarkSquare = (x + y) % 2 == 0;

                // Select the appropriate texture
                Texture texture = isDarkSquare ? darkSquareTexture : lightSquareTexture;

                // Calculate screen position
                int screenX = y * tileSize; // Adjusted for column-major rendering
                int screenY = (board.getRowCount() - x - 1) * tileSize;

                // Debug rendering position
                System.out.println("Rendering " + (isDarkSquare ? "dark" : "light") +
                    " square at (" + screenX + ", " + screenY + ").");

                // Render the square
                batch.draw(texture, screenX, screenY, tileSize, tileSize);
            }
        }
    }

    /**
     * Disposes of resources.
     */
    public void dispose() {
        batch.dispose();
        debugFont.dispose();

        if (darkSquareTexture != null) darkSquareTexture.dispose();
        if (lightSquareTexture != null) lightSquareTexture.dispose();
    }
}

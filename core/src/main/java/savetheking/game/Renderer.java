package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Renderer class is responsible for drawing the game board and pieces on the screen.
 */
public class Renderer {
    private final Board board;
    private final int tileSize; // Size of each tile in pixels
    private final SpriteBatch batch;
    private final Texture darkSquareTexture;
    private final Texture lightSquareTexture;
    private final boolean debugMode; // Toggle for enabling/disabling debug logs

    /**
     * Constructs a Renderer with the specified board, tile size, and debug mode.
     *
     * @param board     The game board to render.
     * @param tileSize  The size of each tile in pixels.
     */
    public Renderer(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.batch = new SpriteBatch();
        this.darkSquareTexture = new Texture("dark-green.png");
        this.lightSquareTexture = new Texture("light-white.png");
        this.debugMode = false; // Initialize the debug mode
    }

    /**
     * Renders the entire game: board and pieces.
     *
     * @param batch The SpriteBatch used for rendering.
     */
    public void render(SpriteBatch batch) {
        renderBoardLayer(batch);
        renderPieces(batch); // Render all pieces
    }

    /**
     * Renders the board layer (checkered tiles).
     *
     * @param batch The SpriteBatch used for rendering.
     */
    private void renderBoardLayer(SpriteBatch batch) {
        batch.begin();

        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                Tile tile = board.getTileAt(new Point(row, col));

                if (tile != null) {
                    // Determine if it's a dark or light tile
                    boolean isDarkSquare = (row + col) % 2 == 1; // Checkerboard pattern
                    Texture texture = isDarkSquare ? darkSquareTexture : lightSquareTexture;

                    // Calculate screen position
                    int screenX = col * tileSize;
                    int screenY = (board.getRowCount() - row - 1) * tileSize;

                    // Debug: Log tile rendering details if debugMode is enabled
                    if (debugMode) {
                        System.out.println("Rendering at grid (" + row + ", " + col +
                            "), screen (" + screenX + ", " + screenY + "), DarkSquare: " + isDarkSquare);
                    }

                    // Render the square
                    batch.draw(texture, screenX, screenY, tileSize, tileSize);
                } else {
                    // Debug: No tile found
                    if (debugMode) {
                        System.out.println("No tile found at (" + row + ", " + col + ")");
                    }
                }
            }
        }

        batch.end();
    }

    /**
     * Renders the pieces on the board.
     *
     * @param batch The SpriteBatch used for rendering.
     */
    private void renderPieces(SpriteBatch batch) {
        batch.begin();

        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                Tile tile = board.getTileAt(new Point(row, col));
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (piece != null) {
                        // Use Piece.render with the grid position
                        piece.render(batch, new Point(row, col));

                        // Debug: Log piece rendering details if debugMode is enabled
                        if (debugMode) {
                            System.out.println("Rendering piece at grid (" + row + ", " + col + "): " + piece);
                        }
                    }
                }
            }
        }

        batch.end();
    }

    /**
     * Disposes of resources used by the Renderer.
     */
    public void dispose() {
        batch.dispose();
        darkSquareTexture.dispose();
        lightSquareTexture.dispose();
    }
}

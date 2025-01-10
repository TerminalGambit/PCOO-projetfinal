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

    // Separate debug modes
    private final boolean boardDebugMode;
    private final boolean pieceDebugMode;

    /**
     * Constructs a Renderer with the specified board, tile size, and debug modes.
     *
     * @param board          The game board to render.
     * @param tileSize       The size of each tile in pixels.
     */
    public Renderer(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.batch = new SpriteBatch();
        this.darkSquareTexture = new Texture("dark-green.png");
        this.lightSquareTexture = new Texture("light-white.png");
        this.boardDebugMode = false;
        this.pieceDebugMode = true;
    }

    /**
     * Renders the entire game: board and pieces.
     */
    public void render() {
        renderBoardLayer();
        renderPieces();
    }

    /**
     * Renders the board layer (checkered tiles).
     */
    private void renderBoardLayer() {
        batch.begin();

        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                // Determine if it's a dark or light tile
                boolean isDarkSquare = (row + col) % 2 == 1; // Checkerboard pattern
                Texture texture = isDarkSquare ? darkSquareTexture : lightSquareTexture;

                // Calculate screen position
                int screenX = col * tileSize;
                int screenY = (board.getRowCount() - row - 1) * tileSize;

                // Debug: Log tile rendering details if boardDebugMode is enabled
                if (boardDebugMode) {
                    System.out.println("Rendering tile at grid (" + row + ", " + col +
                        "), screen (" + screenX + ", " + screenY + "), DarkSquare: " + isDarkSquare);
                }

                // Render the square
                batch.draw(texture, screenX, screenY, tileSize, tileSize);
            }
        }

        batch.end();
    }

    /**
     * Renders the pieces on the board.
     */
    private void renderPieces() {
        batch.begin();

        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                Tile tile = board.getTileAt(new Point(row, col));
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (piece != null) {
                        // Calculate screen position
                        int screenX = col * tileSize;
                        int screenY = (board.getRowCount() - row - 1) * tileSize;

                        // Debug: Log rendering details
                        if (pieceDebugMode) {
                            System.out.printf("Rendering piece: %s at Grid(%d, %d) -> Screen(%d, %d)%n",
                                piece, row, col, screenX, screenY);
                        }

                        // Render the piece texture
                        Texture texture = piece.getTexture();
                        if (texture != null) {
                            batch.draw(texture, screenX, screenY, tileSize, tileSize);
                        } else if (pieceDebugMode) {
                            System.out.printf("Warning: Texture is null for piece %s at (%d, %d)%n",
                                piece, row, col);
                        }
                    } else if (pieceDebugMode) {
                        System.out.println("No piece found at Grid(" + row + ", " + col + ")");
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

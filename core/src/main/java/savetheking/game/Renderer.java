package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

/**
 * Renderer class is responsible for drawing the game board and pieces on the screen.
 */
public class Renderer {
    private Board board;
    private final int tileSize; // Size of each tile in pixels
    private final SpriteBatch batch;
    private final Texture darkSquareTexture;
    private final Texture lightSquareTexture;
    private final Map<Point, Texture> pieceTextures; // Maps grid positions to textures

    // Separate debug modes
    private final boolean boardDebugMode;
    private final boolean pieceDebugMode;

    /**
     * Constructs a Renderer with the specified board, tile size, and debug modes.
     *
     * @param board    The game board to render.
     * @param tileSize The size of each tile in pixels.
     */
    public Renderer(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.batch = new SpriteBatch();
        this.darkSquareTexture = new Texture("dark-green.png");
        this.lightSquareTexture = new Texture("light-white.png");
        this.pieceTextures = new HashMap<Point, Texture>();
        this.boardDebugMode = false; // Enable board debug mode for troubleshooting
        this.pieceDebugMode = false; // Enable piece debug mode for troubleshooting
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
                    System.out.printf("Rendering tile at Grid(%d, %d) -> Screen(%d, %d), DarkSquare: %b%n",
                        row, col, screenX, screenY, isDarkSquare);
                }

                // Render the square
                batch.draw(texture, screenX, screenY, tileSize, tileSize);
            }
        }

        batch.end();
    }

    /**
     * Sets the board for the Renderer.
     *
     * @param board The board to render.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Renders the pieces on the board.
     */
    private void renderPieces() {
        batch.begin();

        for (Map.Entry<Point, Texture> entry : pieceTextures.entrySet()) {
            Point position = entry.getKey();
            Texture texture = entry.getValue();

            // Calculate screen position
            int screenX = position.y * tileSize;
            int screenY = (board.getRowCount() - position.x - 1) * tileSize;

            // Debug: Log rendering details
            if (pieceDebugMode) {
                System.out.printf("Rendering piece at Grid(%d, %d) -> Screen(%d, %d)%n",
                    position.x, position.y, screenX, screenY);
            }

            batch.draw(texture, screenX, screenY, tileSize, tileSize);
        }

        batch.end();
    }

    /**
     * Notifies the renderer about a new piece and updates its position and texture.
     *
     * @param piece The piece to render.
     */
    public void notifyNewPiece(Piece piece) {
        if (piece != null && piece.getTexture() != null && piece.getPosition() != null) {
            pieceTextures.put(piece.getPosition(), piece.getTexture());
            if (pieceDebugMode) {
                System.out.printf("Renderer: Notified of new piece %s at %s%n", piece, piece.getPosition());
            }
        } else {
            System.err.println("Error: Cannot render piece. Texture, position, or piece itself is null.");
        }
    }

    /**
     * Updates the position of an existing piece in the renderer.
     *
     * @param oldPosition The previous position of the piece.
     * @param newPosition The new position of the piece.
     */
    public void updatePiecePosition(Point oldPosition, Point newPosition) {
        Texture texture = pieceTextures.remove(oldPosition);
        if (texture != null) {
            pieceTextures.put(newPosition, texture);
            if (pieceDebugMode) {
                System.out.printf("Renderer: Moved piece from %s to %s%n", oldPosition, newPosition);
            }
        } else if (pieceDebugMode) {
            System.err.printf("Error: No piece found at %s to move%n", oldPosition);
        }
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

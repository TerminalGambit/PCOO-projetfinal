package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Renderer class is responsible for drawing the game board and pieces on the screen.
 */
public class Renderer {
    private final Board board;
    private final int tileSize;
    private final SpriteBatch batch;
    private final Texture darkSquareTexture;
    private final Texture lightSquareTexture;

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
        this.darkSquareTexture = new Texture("dark-green.png");
        this.lightSquareTexture = new Texture("light-white.png");
    }

    /**
     * Renders the entire game: board and pieces.
     *
     * @param batch The SpriteBatch used for rendering.
     */
    public void render(SpriteBatch batch) {
        renderBoardLayer(batch);
        //renderPieces(batch);
    }

    /**
     * Renders the board layer (checkered tiles).
     *
     * @param batch The SpriteBatch used for rendering.
     */
    private void renderBoardLayer(SpriteBatch batch) {
        batch.begin();

        for (int x = 0; x < board.getRowCount(); x++) {
            for (int y = 0; y < board.getColumnCount(); y++) {
                Tile tile = board.getTileAt(new Point(x, y));

                if (tile != null) {
                    // Get tile ID and determine texture
                    int tileId = tile.getId();
                    boolean isDarkSquare = tileId == 1; // Assuming dark green = 1
                    Texture texture = isDarkSquare ? darkSquareTexture : lightSquareTexture;

                    // Calculate screen position
                    int screenX = y * tileSize;
                    int screenY = (board.getRowCount() - x - 1) * tileSize;

                    // Debug: Log tile rendering details
                    System.out.println("Rendering Tile ID: " + tileId + " at grid (" + x + ", " + y +
                        "), screen (" + screenX + ", " + screenY + ")");

                    // Render the square
                    batch.draw(texture, screenX, screenY, tileSize, tileSize);
                } else {
                    System.out.println("No tile found at (" + x + ", " + y + ")");
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

        for (int x = 0; x < board.getRowCount(); x++) {
            for (int y = 0; y < board.getColumnCount(); y++) {
                Tile tile = board.getTileAt(new Point(x, y));
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    //piece.render(batch); // Ensure each Piece has a render method
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

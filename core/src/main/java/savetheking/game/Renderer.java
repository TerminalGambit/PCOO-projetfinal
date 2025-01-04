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

    public Renderer(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.batch = new SpriteBatch();
        this.debugFont = new BitmapFont();

        try {
            darkSquareTexture = new Texture("dark-green.png");
            lightSquareTexture = new Texture("light-white.png");

            System.out.println("Dark green texture loaded successfully.");
            System.out.println("Light white texture loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading textures: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        debugFont.draw(batch, "Rendering Debug: Board and Pieces", 50, 50);
        batch.end();

        // Render the board
        renderBoardLayer();
    }

    public void renderBoardLayer() {
        System.out.println("renderBoardLayer() invoked.");

        if (darkSquareTexture == null || lightSquareTexture == null) {
            System.err.println("Error: One of the textures is null.");
            return;
        }

        batch.begin();

        for (int x = 0; x < board.getRowCount(); x++) {
            for (int y = 0; y < board.getColumnCount(); y++) {
                boolean isDarkSquare = (x + y) % 2 == 0;
                Texture texture = isDarkSquare ? darkSquareTexture : lightSquareTexture;

                int screenX = y * tileSize;
                int screenY = (board.getRowCount() - x - 1) * tileSize;

                // Debug screen coordinates
                System.out.println("Rendering " + (isDarkSquare ? "dark" : "light") +
                    " square at (" + screenX + ", " + screenY + ").");

                // Render the tile
                batch.draw(texture, screenX, screenY, tileSize, tileSize);
            }
        }

        batch.end();
    }

    public void dispose() {
        batch.dispose();
        debugFont.dispose();
        if (darkSquareTexture != null) darkSquareTexture.dispose();
        if (lightSquareTexture != null) lightSquareTexture.dispose();
    }
}

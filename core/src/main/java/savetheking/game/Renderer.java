package savetheking.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * The Renderer class is responsible for rendering the chessboard and pieces.
 */
public class Renderer implements Observer {
    private final Board board;
    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    private final int tileSize;

    /**
     * Constructor for the Renderer class.
     *
     * @param board The game board to render.
     * @param tileSize The size of each tile in pixels.
     */
    public Renderer(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, tileSize * board.getColumnCount(), tileSize * board.getRowCount());
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.board.addObserver(this); // Register this renderer as an observer
    }

    /**
     * Called when the board is updated to trigger rendering changes.
     */
    @Override
    public void update() {
        System.out.println("Renderer: Board updated, triggering re-render.");
    }

    /**
     * Renders the chessboard and its pieces.
     *
     * @param batch The SpriteBatch to use for rendering pieces.
     */
    public void render(SpriteBatch batch) {
        // Render the chessboard tiles
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                if ((row + col) % 2 == 0) {
                    shapeRenderer.setColor(Color.LIGHT_GRAY); // Light tiles
                } else {
                    shapeRenderer.setColor(Color.DARK_GRAY); // Dark tiles
                }
                shapeRenderer.rect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
        shapeRenderer.end();

        // Render the pieces on the board
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                Tile tile = board.getTileAt(new Point(row, col));
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (piece != null && piece.getTexture() != null) {
                        batch.draw(piece.getTexture(), col * tileSize, row * tileSize, tileSize, tileSize);
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
        shapeRenderer.dispose();
        spriteBatch.dispose();
    }
}

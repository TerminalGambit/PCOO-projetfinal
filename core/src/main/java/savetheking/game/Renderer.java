package savetheking.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Renderer implements Observer {
    private final Board board;
    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    private final int tileSize;

    public Renderer(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, tileSize * board.getColumnCount(), tileSize * board.getRowCount());
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.board.addObserver(this);
    }

    @Override
    public void update() {
        System.out.println("Renderer: Board updated, triggering re-render.");
    }

    public void render(SpriteBatch batch, Board board) {
        renderBoard();
        renderPieces(batch);
    }

    private void renderBoard() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                shapeRenderer.setColor((row + col) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                shapeRenderer.rect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }
        shapeRenderer.end();
    }

    private void renderPieces(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                Tile tile = board.getTileAt(new Point(row, col));
                if (tile instanceof OccupiedTile) {
                    Piece piece = ((OccupiedTile) tile).getPiece();
                    if (piece != null) {
                        batch.draw(piece.getTexture(), col * tileSize, row * tileSize, tileSize, tileSize);
                    }
                }
            }
        }
        batch.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
    }
}

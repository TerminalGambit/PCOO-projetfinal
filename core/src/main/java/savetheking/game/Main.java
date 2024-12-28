package savetheking.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private static final int TILE_SIZE = 64; // Size of each tile in pixels
    private static final int BOARD_SIZE = 8; // Number of rows and columns

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Texture kingTexture;
    private Texture queenTexture;
    private Texture bishopTexture;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Load textures from the correct paths
        kingTexture = new Texture(Gdx.files.internal("pieces/wk.png")); // White King
        queenTexture = new Texture(Gdx.files.internal("pieces/wq.png")); // White Queen
        bishopTexture = new Texture(Gdx.files.internal("pieces/wb.png")); // White Bishop
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        camera.update();

        // Render chessboard grid
        shapeRenderer.setProjectionMatrix(camera.combined);
        renderBoard();

        // Render pieces
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(kingTexture, 2 * TILE_SIZE, 4 * TILE_SIZE, TILE_SIZE, TILE_SIZE); // King at (2, 4)
        batch.draw(queenTexture, 3 * TILE_SIZE, 4 * TILE_SIZE, TILE_SIZE, TILE_SIZE); // Queen at (3, 4)
        batch.draw(bishopTexture, 4 * TILE_SIZE, 5 * TILE_SIZE, TILE_SIZE, TILE_SIZE); // Bishop at (4, 5)
        batch.end();
    }

    private void renderBoard() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    shapeRenderer.setColor(Color.LIGHT_GRAY); // Light tiles
                } else {
                    shapeRenderer.setColor(Color.DARK_GRAY); // Dark tiles
                }
                shapeRenderer.rect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        kingTexture.dispose();
        queenTexture.dispose();
        bishopTexture.dispose();
    }
}

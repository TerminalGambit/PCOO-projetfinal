package savetheking.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The Main class serves as the entry point for the Solo Chess game.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Board board;
    private Renderer renderer;
    private Controller controller;
    private PlayingState playingState;

    @Override
    public void create() {
        int tileSize = 64; // Define the size of each tile
        int boardSize = 8 * tileSize; // Total board size (8x8 board)

        // Adjust the window size to match the board dimensions
        Gdx.graphics.setWindowedMode(boardSize, boardSize);

        String mapPath = "ChessBoardWithPieces.tmx";

        try {
            // Load the Tiled map using TmxMapLoader and initialize the board
            TmxMapLoader mapLoader = new TmxMapLoader();
            TiledMap tiledMap = mapLoader.load(mapPath);
            board = new Board(tiledMap);

            // Initialize Renderer, Controller, and Game State
            renderer = new Renderer(board, tileSize);
            batch = new SpriteBatch();
            controller = new Controller(board);
            playingState = new PlayingState(board, controller, renderer);

        } catch (Exception e) {
            System.err.println("Failed to initialize game components: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1);

        // Check if an update is required
        if (!controller.needsUpdate()) {
            return;
        }

        // Update and render the actual game state
        float deltaTime = Gdx.graphics.getDeltaTime();
        playingState.update(deltaTime);
        playingState.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
        if (board != null) {
            board.dispose(); // Ensure resources in Board are also disposed of if applicable
        }
    }
}

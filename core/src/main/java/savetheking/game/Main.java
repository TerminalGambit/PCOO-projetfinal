package savetheking.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        String mapPath = "ChessBoardWithPieces.tmx";

        try {
            // Load the Tiled map and initialize the board
            TiledLoader tiledLoader = new TiledLoader();
            TiledMap tiledMap = tiledLoader.load(mapPath); // Ensure the correct method name for loading the map
            board = new Board(tiledMap); // Ensure Board accepts a TiledMap in its constructor

            // Initialize Renderer, Controller, and Game State
            renderer = new Renderer(board, tileSize); // Pass the board and tile size
            batch = new SpriteBatch();
            controller = new Controller(board);
            playingState = new PlayingState(board, controller, renderer);
        } catch (Exception e) {
            System.err.println("Failed to load the Tiled map: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with a black background

        float deltaTime = Gdx.graphics.getDeltaTime();
        playingState.update(deltaTime); // Update the game state
        playingState.render(batch); // Render the game state
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }
}

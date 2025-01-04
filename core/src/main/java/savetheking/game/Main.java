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
            // Debug: Start game initialization
            System.out.println("Starting game initialization...");

            // Load the Tiled map and initialize the board
            TiledLoader tiledLoader = new TiledLoader();
            TiledMap tiledMap = tiledLoader.load(mapPath); // Ensure the correct method name for loading the map
            System.out.println("Tiled map loaded successfully: " + mapPath);

            board = new Board(tiledMap); // Ensure Board accepts a TiledMap in its constructor
            System.out.println("Board initialized with " + board.getRowCount() + " rows and " + board.getColumnCount() + " columns.");

            // Initialize Renderer, Controller, and Game State
            renderer = new Renderer(board, tileSize); // Pass the board and tile size
            batch = new SpriteBatch();
            controller = new Controller(board);
            playingState = new PlayingState(board, controller, renderer);

            System.out.println("Game components initialized successfully.");

        } catch (Exception e) {
            System.err.println("Failed to initialize game components: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with a black background

        // Debug: Confirm render loop execution
        System.out.println("Rendering frame...");

        // Temporary test to verify rendering
        try {
            batch.begin();
            batch.draw(renderer.getTestTexture(), 100, 100, 64, 64); // Test rendering a single tile at (100, 100)
            batch.end();
        } catch (Exception e) {
            System.err.println("Error during temporary render test: " + e.getMessage());
        }

        // Update and render the actual game state
        float deltaTime = Gdx.graphics.getDeltaTime();
        playingState.update(deltaTime); // Update the game state
        playingState.render(batch); // Render the game state

        // Debug: Render complete
        System.out.println("Frame rendered.");
    }

    @Override
    public void dispose() {
        // Dispose resources
        System.out.println("Disposing game resources...");
        batch.dispose();
        renderer.dispose();
    }
}

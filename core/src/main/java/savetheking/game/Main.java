package savetheking.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx; // Import Gdx for accessing delta time and other utilities
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The Main class serves as the entry point for the game.
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
        board = new Board(8); // Create an 8x8 board
        renderer = new Renderer(board, tileSize); // Pass both the board and tileSize to the Renderer
        batch = new SpriteBatch();
        controller = new Controller(board);
        playingState = new PlayingState(board, controller, renderer); // Updated to match PlayingState constructor
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with a black background

        float deltaTime = Gdx.graphics.getDeltaTime(); // Fix for 'Cannot resolve symbol Gdx'
        playingState.update(deltaTime); // Update the current state
        playingState.render(batch); // Render the current state
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }
}

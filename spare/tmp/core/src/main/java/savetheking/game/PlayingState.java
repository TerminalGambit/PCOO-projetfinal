package savetheking.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Represents the active state of the game where players interact and move pieces.
 */
public class PlayingState implements GameStateInterface {
    private final Controller controller;
    private final Renderer renderer;
    private final Board board;

    public PlayingState(Board board, Controller controller, Renderer renderer) {
        this.board = board;
        this.controller = controller;
        this.renderer = renderer;
    }

    @Override
    public void enterState() {
        System.out.println("Entering PlayingState...");
        // Reset or initialize any state-specific resources
    }

    @Override
    public void update(float deltaTime) {
        // Handle input processing
        if (Gdx.input.justTouched()) {
            int screenX = Gdx.input.getX(); // Screen x-coordinate
            int screenY = Gdx.input.getY(); // Screen y-coordinate
            screenY = Gdx.graphics.getHeight() - screenY; // Invert y-axis

            // Translate screen coordinates to board coordinates
            Point clickedPoint = translateScreenToBoard(screenX, screenY);
            if (clickedPoint != null) {
                controller.handleInput(clickedPoint);
            }
        }

        // Update game state, e.g., timer
        controller.update(deltaTime);
    }

    @Override
    public void render() {
        renderer.render();
    }

    /**
     * Translates screen coordinates to board coordinates based on the board's rendering.
     *
     * @param screenX The x-coordinate of the screen.
     * @param screenY The y-coordinate of the screen.
     * @return The corresponding board position as a Point, or null if outside the board.
     */
    private Point translateScreenToBoard(int screenX, int screenY) {
        int tileSize = 64; // Assuming tiles are 64x64 pixels
        int boardSize = board.getRowCount();

        // Convert from screen coordinates to board coordinates
        int boardX = screenX / tileSize;
        int boardY = (Gdx.graphics.getHeight() - screenY) / tileSize; // Invert y-axis

        Point clickedPoint = new Point(boardY, boardX); // Note: Row = y, Column = x
        if (board.isWithinBounds(clickedPoint)) {
            return clickedPoint;
        }
        return null;
    }
}

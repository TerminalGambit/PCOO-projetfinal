package savetheking.game;

/**
 * Represents the paused state of the game.
 */
public class PausedState implements GameStateInterface {
    @Override
    public void enterState() {
        System.out.println("Entering PausedState...");
        // Perform any setup necessary when the game enters the paused state
    }

    @Override
    public void update(float deltaTime) {
        System.out.println("Updating PausedState...");
        // Handle updates specific to the paused state, if any
    }

    @Override
    public void render() {
        System.out.println("Rendering PausedState...");
        // Render the paused state (e.g., paused menu, dimmed background)
    }
}

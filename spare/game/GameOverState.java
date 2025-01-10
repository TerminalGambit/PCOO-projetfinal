package savetheking.game;

/**
 * Represents the game over state.
 */
public class GameOverState implements GameStateInterface {
    @Override
    public void enterState() {
        System.out.println("Entering GameOverState...");
        // Perform any setup necessary when the game enters the game over state
    }

    @Override
    public void update(float deltaTime) {
        System.out.println("Updating GameOverState...");
        // Handle updates specific to the game over state, if any
    }

    @Override
    public void render() {
        System.out.println("Rendering GameOverState...");
        // Render the game over screen or menu
    }
}

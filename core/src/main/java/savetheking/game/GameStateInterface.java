package savetheking.game;

public interface GameStateInterface {
    /**
     * Called when entering this state.
     */
    void enterState();

    /**
     * Updates the game state.
     *
     * @param deltaTime Time since the last update.
     */
    void update(float deltaTime);

    /**
     * Renders the game state.
     */
    void render();
}

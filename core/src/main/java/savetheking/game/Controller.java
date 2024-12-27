package savetheking.game;

public class Controller {
    private Board board;
    private GameStateInterface gameState;

    public Controller(Board board, GameStateInterface gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    public void handleInput(String input) {
        // Process input (e.g., move a piece or reset the board)
        System.out.println("Handling input: " + input);
    }

    public void update(float deltaTime) {
        gameState.update(deltaTime);
    }
}

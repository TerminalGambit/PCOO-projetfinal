package savetheking.game;

public class GameManager {
    private Board board;
    private GameStateInterface gameState;

    public GameManager(Board board, GameStateInterface initialState) {
        this.board = board;
        this.gameState = initialState;
    }

    public static GameManager getInstance() {
        return null;
    }

    public void startGame() {
        System.out.println("Game started!");
        board.initializeBoard();
        gameState.enterState();
    }

    public void endGame() {

        System.out.println("Game ended!");
    }

    public void setGameState(GameStateInterface newState) {
        System.out.println("Transitioning to new state: " + newState.getClass().getSimpleName());
        this.gameState = newState;
        gameState.enterState();
    }

    public void handleMove(Point start, Point end) {
        if (board.isValidMove(start, end)) {
            board.movePiece(start, end);
            System.out.println("Move from " + start + " to " + end + " completed.");
        } else {
            System.out.println("Invalid move attempted from " + start + " to " + end + ".");
        }
    }

    public void gameLoop(float deltaTime) {
        gameState.update(deltaTime);
        gameState.render();
    }
}

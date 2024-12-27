package savetheking.game;

public class GameManager {
    private Board board;
    private GameStateInterface gameState;

    public GameManager(Board board, GameStateInterface initialState) {
        this.board = board;
        this.gameState = initialState;
    }

    public void startGame() {
        System.out.println("Game started!");
    }

    public void endGame() {
        System.out.println("Game ended!");
    }

    public void handleMove(Point start, Point end) {
        board.movePiece(start, end);
    }
}

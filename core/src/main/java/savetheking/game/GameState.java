package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameState class manages the main state of the game, including the score, timer, round progression,
 * difficulty level, and move recording via PGN format.
 */
public class GameState {
    private static GameState instance; // Singleton instance
    private int timer;                 // Time remaining in seconds
    private int score;                 // Current score
    private String difficulty;         // Difficulty level of the game
    private int round;                 // Current game round
    private PGN pgn;                   // PGN instance to manage move recording and history
    private List<Move> moves;          // Stores move history for the game

    /**
     * Private constructor to initialize the game state with default values.
     */
    private GameState() {
        this.timer = 300; // Default timer of 5 minutes
        this.score = 0;
        this.difficulty = "Normal";
        this.round = 1;
        this.pgn = new PGN();
        this.moves = new ArrayList<Move>();
    }

    /**
     * Provides access to the singleton instance of GameState.
     * @return The singleton GameState instance.
     */
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    /**
     * Updates the timer based on the time delta provided.
     * @param deltaTime The amount of time to decrement from the timer, typically in seconds.
     */
    public void updateTimer(float deltaTime) {
        this.timer -= deltaTime;
        if (this.timer < 0) this.timer = 0;
    }

    /**
     * Checks if the game is over, which can occur if the timer reaches zero or other end conditions.
     * @return true if the game is over, false otherwise.
     */
    public boolean checkGameOver() {
        return this.timer <= 0;
    }

    /**
     * Increments the player's score by a standard amount.
     */
    public void incrementScore() {
        this.score += 10; // Arbitrary increment for each correct action or move
    }

    /**
     * Renders the game state visually, such as drawing elements on the screen.
     * @param batch A SpriteBatch instance to handle rendering.
     */
    public void render(SpriteBatch batch) {
        // Placeholder: Use batch to render any game-related visuals, such as score, timer, etc.
    }

    /**
     * Advances the game to the next round, resetting certain elements as necessary.
     */
    public void advanceRound() {
        this.round++;
        this.timer = 300; // Reset timer for the new round if required
    }

    /**
     * Sets the difficulty level of the game.
     * @param level The new difficulty level, such as "Easy", "Normal", or "Hard".
     */
    public void setDifficulty(String level) {
        this.difficulty = level;
    }

    /**
     * Records a move in PGN format and adds it to the move history.
     * @param piece The piece being moved.
     * @param start The starting position of the piece.
     * @param end The ending position of the piece.
     * @param capture Whether this move involved capturing an opponent's piece.
     * @param enPassant Whether this move is an en passant capture (specific to pawns).
     */
    public void recordMove(Piece piece, Point start, Point end, boolean capture, boolean enPassant) {
        Move move = new Move(piece, start, end, capture, enPassant);
        moves.add(move); // Add move to history
        pgn.addMove(piece, start, end, capture, enPassant);
    }

    /**
     * Retrieves the last move recorded in the move history.
     * @return The last Move object, or null if there are no moves.
     */
    public Move getLastMove() {
        return moves.isEmpty() ? null : moves.get(moves.size() - 1);
    }

    /**
     * Saves the game’s current PGN move history to a file.
     * @param filePath The path to the file where the PGN will be saved.
     */
    public void saveGame(String filePath) {
        pgn.saveToFile(filePath);
    }

    /**
     * Loads a saved PGN move history from a file and restores it to the game’s move history.
     * @param filePath The path to the file from which the PGN will be loaded.
     */
    public void loadGame(String filePath) {
        pgn.loadFromFile(filePath);
    }

    /**
     * Retrieves the move history recorded in PGN format.
     * @return A list of moves in PGN notation.
     */
    public List<String> getMoveHistory() {
        return pgn.getMoveHistory();
    }

    // Getters and Setters for the GameState attributes
    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getRound() {
        return round;
    }
}

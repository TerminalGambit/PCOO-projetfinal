package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameState class manages the main state of the game, including the score, timer, and round progression.
 * It is simplified for Solo Chess mode.
 */
public class GameState {
    private static GameState instance; // Singleton instance
    private int timer;                 // Time remaining in seconds
    private int score;                 // Current score
    private String difficulty;         // Difficulty level of the game
    private int round;                 // Current game round
    private List<Move> moves;          // Stores move history for the game

    /**
     * Private constructor to initialize the game state with default values.
     */
    private GameState() {
        this.timer = 30000; // Default timer of 5 minutes
        this.score = 0;
        this.difficulty = "Normal";
        this.round = 1;
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
     * Checks if the game is over, which can occur if the timer reaches zero.
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
     * Records a move in the move history.
     * @param piece The piece being moved.
     * @param start The starting position of the piece.
     * @param end The ending position of the piece.
     * @param capture Whether this move involved capturing an opponent's piece.
     */
    public void recordMove(Piece piece, Point start, Point end, boolean capture) {
        Move move = new Move(piece, start, end, capture, false); // En passant is irrelevant in Solo Chess
        moves.add(move); // Add move to history
    }

    /**
     * Retrieves the last move recorded in the move history.
     * @return The last Move object, or null if there are no moves.
     */
    public Move getLastMove() {
        return moves.isEmpty() ? null : moves.get(moves.size() - 1);
    }

    /**
     * Retrieves the move history.
     * @return A list of Move objects representing the move history.
     */
    public List<Move> getMoveHistory() {
        return new ArrayList<Move>(moves); // Return a copy of the move history
    }

    /**
     * Renders the game state visually, such as drawing elements on the screen.
     * @param batch A SpriteBatch instance to handle rendering.
     */
    public void render(SpriteBatch batch) {
        // Placeholder: Render game state visuals, such as score and timer
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

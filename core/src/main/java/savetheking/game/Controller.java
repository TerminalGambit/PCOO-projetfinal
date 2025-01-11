package savetheking.game;

import java.util.List;

/**
 * Handles the game logic and user interactions.
 */
public class Controller {
    private final Board board;
    private final GameState gameState; // Singleton instance for managing the game state
    private Piece selectedPiece = null; // Currently selected piece
    private boolean isGameFinished = false;

    public Controller(Board board) {
        this.board = board;
        this.gameState = GameState.getInstance();
    }

    /**
     * Handles user input to select or move pieces based on the clicked point.
     * @param clickedPoint The point clicked by the user.
     */
    public void handleInput(Point clickedPoint) {
        if (isGameFinished) {
            System.out.println("Game is finished. Reset to play again.");
            return;
        }

        if (!board.isWithinBounds(clickedPoint)) {
            System.out.println("Clicked outside the board: " + clickedPoint);
            return;
        }

        Tile clickedTile = board.getTileAt(clickedPoint);

        if (selectedPiece == null) {
            handleSelection(clickedTile);
        } else {
            handleMove(clickedPoint, clickedTile);
        }
    }


    public void handleSelection(Tile clickedTile) {
        if (clickedTile instanceof OccupiedTile) {
            selectedPiece = ((OccupiedTile) clickedTile).getPiece();
            System.out.printf("Selected piece: %s%n", selectedPiece);
        } else {
            selectedPiece = null;
            System.out.println("No piece found on the clicked tile.");
        }
    }

    /**
     * Handles piece movement logic.
     * @param clickedPoint The point where the piece is being moved.
     * @param clickedTile  The tile at the clicked point.
     */
    private void handleMove(Point clickedPoint, Tile clickedTile) {
        List<Point> validMoves = selectedPiece.getPossibleMoves(board);

        if (validMoves.contains(clickedPoint)) {
            performMove(clickedPoint, clickedTile);
        } else {
            System.out.println("Invalid move for the selected piece.");
        }
    }

    /**
     * Performs the actual move of a selected piece.
     * @param clickedPoint The destination point.
     * @param clickedTile  The tile at the destination.
     */
    private void performMove(Point clickedPoint, Tile clickedTile) {
        System.out.println("Moving piece to: " + clickedPoint);
        board.movePiece(selectedPiece.getPosition(), clickedPoint);

        // Update the piece's state and handle two-move rule
        selectedPiece.move(clickedPoint, board.getRowCount());
        if (selectedPiece.getMoveCount() >= 2 && "White".equals(selectedPiece.getColor())) {
            selectedPiece.setColor("Black");
        }

        // Record the move in the game state
        boolean isCapture = clickedTile instanceof OccupiedTile;
        gameState.recordMove(selectedPiece, selectedPiece.getPosition(), clickedPoint, isCapture);

        // Check if the game is finished
        checkGameFinished();

        // Deselect the piece
        selectedPiece = null;
    }

    /**
     * Highlights valid moves for the selected piece.
     * @param piece The selected piece.
     */
    private void highlightValidMoves(Piece piece) {
        if (piece != null) {
            List<Point> validMoves = piece.getPossibleMoves(board);
            for (Point move : validMoves) {
                Tile tile = board.getTileAt(move);
                if (tile != null) {
                    tile.setHighlighted(true); // Highlight valid moves
                }
            }
            System.out.println("Highlighted valid moves for the selected piece.");
        }
    }

    /**
     * Checks whether the game is finished based on Solo Chess rules.
     */
    private void checkGameFinished() {
        List<Piece> remainingPieces = board.getRemainingPieces();

        if (remainingPieces.size() == 1 && remainingPieces.get(0) instanceof King) {
            System.out.println("Game finished! The king is the last piece remaining. Congratulations!");
            isGameFinished = true;
        } else if (remainingPieces.isEmpty()) {
            System.out.println("Game over! No pieces remaining.");
            isGameFinished = true;
        }
    }

    /**
     * Resets the board to its initial state.
     */
    public void resetBoard() {
        board.initializeBoard(); // Reset the board
        gameState.setScore(0);   // Reset score
        gameState.setTimer(3000); // Reset timer
        gameState.advanceRound(); // Move to the next round
        isGameFinished = false;  // Reset game status
        selectedPiece = null;    // Clear selected piece
        System.out.println("Board has been reset to its initial state.");
    }

    /**
     * Updates the game state (called each frame in the game loop).
     * @param deltaTime The time elapsed since the last update.
     */
    public void update(float deltaTime) {
        gameState.updateTimer(deltaTime);

        if (gameState.checkGameOver()) {
            System.out.println("Time's up! Game over.");
            isGameFinished = true;
        }
    }

    /**
     * Checks if the game state needs an update.
     * @return True if the state has changed, otherwise false.
     */
    public boolean needsUpdate() {
        return !isGameFinished;
    }
}

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
        //System.out.println("Valid moves for the selected piece: " + validMoves + "the selected piece: " + selectedPiece + "that is an instance of " + selectedPiece.getClass().getSimpleName());

        if (validMoves.contains(clickedPoint)) {
            performMove(clickedPoint, clickedTile);
        } else {
            System.out.println("Invalid move for the selected piece.");
        }
    }

    /**
     * Performs the actual move of a selected piece.
     * Updates the board state, piece state, game state, and handles rendering updates.
     *
     * @param clickedPoint The destination point for the piece.
     * @param clickedTile  The tile at the destination point.
     */
    private void performMove(Point clickedPoint, Tile clickedTile) {
        System.out.printf("Performing move for piece: %s from %s to %s%n", selectedPiece, selectedPiece.getPosition(), clickedPoint);

        // Step 1: Update the board's state
        board.movePiece(selectedPiece.getPosition(), clickedPoint);
        System.out.println("Board updated: Piece moved to new position.");

        // Step 2: Update the piece's internal state
        selectedPiece.move(clickedPoint, board.getRowCount());
        System.out.printf("Piece state updated: New position = %s, Move count = %d%n", selectedPiece.getPosition(), selectedPiece.getMoveCount());

        // Step 3: Handle color change for two-move rule
        if (selectedPiece.getMoveCount() >= 2 && "White".equalsIgnoreCase(selectedPiece.getColor())) {
            selectedPiece.setColor("Black");
            System.out.println("Piece color changed to Black after two moves.");
        }

        // Step 4: Record the move in the game state
        boolean isCapture = clickedTile instanceof OccupiedTile;
        gameState.recordMove(selectedPiece, selectedPiece.getPosition(), clickedPoint, isCapture);
        System.out.println("Game state updated: Move recorded.");

        // Step 5: Check if the game is finished
        checkGameFinished();

        // Step 6: Deselect the piece
        selectedPiece = null;
        System.out.println("Piece deselected. Ready for next action.");

        // Rendering step (if needed):
        // Ensure rendering updates after the move if the rendering relies on board state changes.
        board.notifyObservers();
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

        int blackPieceCount = 0;
        boolean kingExists = false;

        // Count black pieces and check for the presence of a king
        for (Piece piece : remainingPieces) {
            if (piece instanceof King) {
                kingExists = true;
            }
            if ("Black".equalsIgnoreCase(piece.getColor())) {
                blackPieceCount++;
            }
        }

        // Case 1: Only the king remains (win condition)
        if (remainingPieces.size() == 1 && kingExists) {
            System.out.println("Game finished! The king is the last piece remaining. Congratulations!");
            isGameFinished = true;
        }
        // Case 2: All remaining pieces are black (lose condition)
        else if (blackPieceCount == remainingPieces.size()) {
            System.out.println("Game over! All remaining pieces are black. You lost.");
            isGameFinished = true;
        }
        // Case 3: More than one black piece remains (lose condition)
        else if (blackPieceCount > 1) {
            System.out.println("Game over! More than one black piece remains. You lost.");
            isGameFinished = true;
        }
        // Case 4: Continue the game
        else {
            System.out.println("Game continues. Remaining pieces: " + remainingPieces.size());
        }

        if (isGameFinished) {
            System.out.println("Game is finished. Reset the board to play again.");
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

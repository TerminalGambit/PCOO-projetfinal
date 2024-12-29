package savetheking.game;

import java.util.List;

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
            // No piece selected, attempt to select one
            if (clickedTile instanceof OccupiedTile) {
                selectedPiece = ((OccupiedTile) clickedTile).getPiece();
                System.out.println("Selected piece: " + selectedPiece);
                highlightValidMoves(selectedPiece); // Optional: Highlight valid moves for the selected piece
            } else {
                System.out.println("Clicked on an empty tile, no piece selected.");
            }
        } else {
            // A piece is already selected, attempt to move it
            if (clickedTile instanceof EmptyTile || clickedTile instanceof OccupiedTile) {
                List<Point> validMoves = selectedPiece.getPossibleMoves(board);

                if (validMoves.contains(clickedPoint)) {
                    System.out.println("Moving piece to: " + clickedPoint);
                    board.movePiece(selectedPiece.getPosition(), clickedPoint);
                    selectedPiece.move(clickedPoint, board.getRowCount()); // Update the piece's state
                    gameState.recordMove(selectedPiece, selectedPiece.getPosition(), clickedPoint, clickedTile instanceof OccupiedTile);

                    // Check if the game is finished after the move
                    checkGameFinished();

                    // Deselect the piece
                    selectedPiece = null;
                } else {
                    System.out.println("Invalid move for the selected piece.");
                }
            } else {
                System.out.println("Invalid tile clicked.");
            }
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
        gameState.setTimer(300); // Reset timer
        gameState.advanceRound(); // Move to the next round
        isGameFinished = false;  // Reset game status
        selectedPiece = null;    // Clear selected piece
        System.out.println("Board has been reset to its initial state.");
    }

    /**
     * Highlights valid moves for the selected piece.
     * @param piece The selected piece for which to highlight moves.
     */
    private void highlightValidMoves(Piece piece) {
        if (piece != null) {
            List<Point> validMoves = piece.getPossibleMoves(board);
            for (Point move : validMoves) {
                Tile tile = board.getTileAt(move);
                if (tile != null) {
                    tile.setDefended(true); // Example of using the `setDefended` method
                }
            }
            System.out.println("Highlighted valid moves for the selected piece.");
        }
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
}

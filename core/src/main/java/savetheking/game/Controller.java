package savetheking.game;

import java.util.List;

public class Controller {
    private final Board board;
    private GameStateInterface gameState;
    private Piece selectedPiece = null; // Track the currently selected piece

    public Controller(Board board, GameStateInterface gameState) {
        this.board = board;
        this.gameState = gameState;
    }

    public void handleInput(Point clickedPoint) {
        if (!board.isWithinBounds(clickedPoint)) {
            System.out.println("Clicked outside the board: " + clickedPoint);
            return;
        }

        Tile clickedTile = board.getTileAt(clickedPoint);

        if (selectedPiece == null) {
            // No piece is selected, try selecting one
            if (clickedTile instanceof OccupiedTile) {
                selectedPiece = ((OccupiedTile) clickedTile).getPiece();
                System.out.println("Selected piece: " + selectedPiece);
            } else {
                System.out.println("Clicked on an empty tile, no piece selected.");
            }
        } else {
            // A piece is already selected, try to move it
            if (clickedTile instanceof EmptyTile || clickedTile instanceof OccupiedTile) {
                List<Point> validMoves = selectedPiece.getPossibleMoves(board);

                if (validMoves.contains(clickedPoint)) {
                    // Move the selected piece
                    System.out.println("Moving piece to: " + clickedPoint);
                    board.movePiece(selectedPiece.getPosition(), clickedPoint);
                    selectedPiece = null; // Deselect the piece after moving
                } else {
                    System.out.println("Invalid move for the selected piece.");
                }
            } else {
                System.out.println("Invalid tile clicked.");
            }
        }
    }

    public void update(float deltaTime) {
        gameState.update(deltaTime);
    }
}

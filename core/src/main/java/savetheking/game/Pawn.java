package savetheking.game;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private boolean firstMove;
    private int direction; // +1 for moving up the board, -1 for moving down

    public Pawn(String color, Point position, int direction) {
        super(color, position);
        this.firstMove = true;
        this.direction = direction;
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();

        // Standard forward move
        Point forwardMove = new Point(position.x + direction, position.y);
        if (board.isWithinBounds(forwardMove) && board.getTileAt(forwardMove) instanceof EmptyTile) {
            possibleMoves.add(forwardMove);
        }

        // Double forward move if it's the pawn's first move
        if (firstMove) {
            Point doubleForwardMove = new Point(position.x + 2 * direction, position.y);
            if (board.isWithinBounds(doubleForwardMove) && board.getTileAt(forwardMove) instanceof EmptyTile
                && board.getTileAt(doubleForwardMove) instanceof EmptyTile) {
                possibleMoves.add(doubleForwardMove);
            }
        }

        // Diagonal capture moves
        addCaptureMoves(board, possibleMoves);

        // Check for en passant move
        addEnPassantMove(board, possibleMoves);

        return possibleMoves;
    }

    private void addCaptureMoves(Board board, List<Point> possibleMoves) {
        Point captureLeft = new Point(position.x + direction, position.y - 1);
        Point captureRight = new Point(position.x + direction, position.y + 1);

        if (board.isWithinBounds(captureLeft) && board.getTileAt(captureLeft) instanceof OccupiedTile) {
            Piece leftPiece = board.getTileAt(captureLeft).getPiece();
            if (!leftPiece.getColor().equals(this.color)) {
                possibleMoves.add(captureLeft);
            }
        }
        if (board.isWithinBounds(captureRight) && board.getTileAt(captureRight) instanceof OccupiedTile) {
            Piece rightPiece = board.getTileAt(captureRight).getPiece();
            if (!rightPiece.getColor().equals(this.color)) {
                possibleMoves.add(captureRight);
            }
        }
    }

    private void addEnPassantMove(Board board, List<Point> possibleMoves) {
        // Retrieve the last move from the game state or PGN
        GameState gameState = GameState.getInstance(); // assuming singleton pattern
        Move lastMove = gameState.getLastMove(); // hypothetical method to get last move

        if (lastMove != null && lastMove.getPiece() instanceof Pawn && Math.abs(lastMove.getStart().x - lastMove.getEnd().x) == 2) {
            // Check if last move was a two-square advance by a pawn, and if it's adjacent
            Point lastMoveEnd = lastMove.getEnd();
            if (Math.abs(lastMoveEnd.y - position.y) == 1 && lastMoveEnd.x == position.x) {
                // Eligible for en passant
                Point enPassantSquare = new Point(lastMoveEnd.x + direction, lastMoveEnd.y);
                possibleMoves.add(enPassantSquare);
            }
        }
    }

    @Override
    public List<Point> getDefendedTiles(Board board) {
        List<Point> defendedTiles = new ArrayList<Point>();
        defendedTiles.add(new Point(position.x + direction, position.y - 1));
        defendedTiles.add(new Point(position.x + direction, position.y + 1));
        return defendedTiles;
    }

    // @Override
    public void move(Point newPosition) {
        this.position = newPosition;
        this.firstMove = false;
    }

    @Override
    public String toString() {
        return "Pawn at " + position;
    }

    public boolean canPromote(Board board) {
        int promotionRow = this.getColor().equals("White") ? board.getRowCount() - 1 : 0;
        return this.position.x == promotionRow;
    }
}

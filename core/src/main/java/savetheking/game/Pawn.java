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

        // One-square forward move
        Point forwardMove = new Point(position.x + direction, position.y);
        if (board.isWithinBounds(forwardMove) && board.getTileAt(forwardMove) instanceof EmptyTile) {
            possibleMoves.add(forwardMove);
        }

        // Two-square forward move if it's the pawn's first move
        if (firstMove) {
            Point doubleForwardMove = new Point(position.x + 2 * direction, position.y);
            if (board.isWithinBounds(doubleForwardMove) && board.getTileAt(forwardMove) instanceof EmptyTile
                && board.getTileAt(doubleForwardMove) instanceof EmptyTile) {
                possibleMoves.add(doubleForwardMove);
            }
        }

        // Diagonal capture moves
        Point captureLeft = new Point(position.x + direction, position.y - 1);
        Point captureRight = new Point(position.x + direction, position.y + 1);

        // Check for an opponent piece on each diagonal
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

        return possibleMoves;
    }

    @Override
    public List<Point> getDefendedTiles(Board board) {
        // For pawns, defended tiles are the diagonals where it could potentially capture
        List<Point> defendedTiles = new ArrayList<Point>();
        defendedTiles.add(new Point(position.x + direction, position.y - 1));
        defendedTiles.add(new Point(position.x + direction, position.y + 1));
        return defendedTiles;
    }

    @Override
    public void move(Point newPosition) {
        this.position = newPosition;
        this.firstMove = false; // After the first move, set firstMove to false
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

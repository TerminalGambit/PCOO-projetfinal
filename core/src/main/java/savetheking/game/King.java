package savetheking.game;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private boolean hasMoved = false;

    public King(String color, Point position) {
        super(color, position);
    }

    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] moveOffsets = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Vertical and horizontal moves
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Diagonal moves
        };

        // Regular one-square moves
        for (int[] offset : moveOffsets) {
            int newX = position.x + offset[0];
            int newY = position.y + offset[1];
            Point newPoint = new Point(newX, newY);

            if (newPoint.isWithinBounds(board.getRowCount()) && newPoint.isWithinBounds(board.getColumnCount())) {
                Tile tile = board.getTileAt(newPoint);
                if (tile instanceof EmptyTile || (tile instanceof OccupiedTile && !((OccupiedTile) tile).getPiece().getColor().equals(this.color))) {
                    possibleMoves.add(newPoint);
                }
            }
        }

        // Castling moves (if applicable)
        addCastlingMoves(board, possibleMoves);

        return possibleMoves;
    }

    /**
     * Adds castling moves to possible moves if the conditions for castling are met.
     * @param board The board on which the game is being played.
     * @param possibleMoves The list of possible moves to add castling moves to.
     */
    private void addCastlingMoves(Board board, List<Point> possibleMoves) {
        if (!hasMoved) { // Check if King has not moved
            // Kingside castling
            Rook kingsideRook = board.getPieceAt(new Point(position.x, board.getColumnCount() - 1)) instanceof Rook
                ? (Rook) board.getPieceAt(new Point(position.x, board.getColumnCount() - 1))
                : null;
            if (kingsideRook != null && !kingsideRook.hasMoved() && board.isPathClear(position, kingsideRook.getPosition()) && !board.isPositionUnderAttack(new Point(position.x, position.y + 1), this.color) && !board.isPositionUnderAttack(new Point(position.x, position.y + 2), this.color)) {
                possibleMoves.add(new Point(position.x, position.y + 2));
            }

            // Queenside castling
            Rook queensideRook = board.getPieceAt(new Point(position.x, 0)) instanceof Rook
                ? (Rook) board.getPieceAt(new Point(position.x, 0))
                : null;
            if (queensideRook != null && !queensideRook.hasMoved() && board.isPathClear(position, queensideRook.getPosition()) && !board.isPositionUnderAttack(new Point(position.x, position.y - 1), this.color) && !board.isPositionUnderAttack(new Point(position.x, position.y - 2), this.color)) {
                possibleMoves.add(new Point(position.x, position.y - 2));
            }
        }
    }

    @Override
    public List<Point> getDefendedTiles(Board board) {
        return getPossibleMoves(board); // King defends the same tiles it can move to
    }

    @Override
    public void move(Point newPosition) {
        this.position = newPosition;
        this.hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public String toString() {
        return "King at " + position;
    }

    // Uncomment this method and update when implementing castling logic
    /*
    private void addCastlingMoves(Board board, List<Point> possibleMoves) {
        // Castling logic here, assuming necessary methods in Board
    }
    */
}

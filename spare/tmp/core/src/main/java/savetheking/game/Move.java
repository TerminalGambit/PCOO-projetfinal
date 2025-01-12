package savetheking.game;

public class Move {
    private Piece piece;      // The piece that is moved
    private Point start;      // The starting position of the piece
    private Point end;        // The ending position of the piece
    private boolean capture;  // Whether this move is a capture
    private boolean enPassant; // Whether this move is an en passant capture

    /**
     * Constructor for the Move class.
     * @param piece The piece being moved.
     * @param start The starting position of the piece.
     * @param end The ending position of the piece.
     * @param capture True if this move captures an opponent piece, false otherwise.
     * @param enPassant True if this move is an en passant capture, false otherwise.
     */
    public Move(Piece piece, Point start, Point end, boolean capture, boolean enPassant) {
        this.piece = piece;
        this.start = start;
        this.end = end;
        this.capture = capture;
        this.enPassant = enPassant;
    }

    // Getter for the piece involved in the move
    public Piece getPiece() {
        return piece;
    }

    // Getter for the starting position
    public Point getStart() {
        return start;
    }

    // Getter for the ending position
    public Point getEnd() {
        return end;
    }

    // Determines if the move is a capture
    public boolean isCapture() {
        return capture;
    }

    // Determines if the move is an en passant capture
    public boolean isEnPassant() {
        return enPassant;
    }
}

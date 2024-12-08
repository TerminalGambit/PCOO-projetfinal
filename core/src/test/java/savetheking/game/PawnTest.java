package savetheking.game;

import java.util.List;

public class PawnTest {
    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        testPawnStandardMoves();
        testPawnCaptureMoves();
        testPawnDoubleMove();
        testPawnPromotion();
        testPawnEnPassant(); // New test case for en passant
    }

    private static void testPawnStandardMoves() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(1, 4), 1);

        board.placePiece(pawn, new Point(1, 4));
        System.out.println("Testing Pawn standard moves from (1, 4) on an 8x8 board:");
        List<Point> moves = pawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: (2,4), (3,4) if starting move.\n");
    }

    private static void testPawnCaptureMoves() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(3, 3), 1);

        board.placePiece(pawn, new Point(3, 3));
        board.placePiece(new Rook("black", new Point(4, 4)), new Point(4, 4));
        board.placePiece(new Knight("black", new Point(4, 2)), new Point(4, 2));

        System.out.println("Testing Pawn capture moves from (3, 3) with opponents:");
        List<Point> moves = pawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Standard move forward and captures at (4,4) and (4,2).\n");
    }

    private static void testPawnDoubleMove() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(1, 4), 1);

        board.placePiece(pawn, new Point(1, 4));
        System.out.println("Testing Pawn double move from starting position (1, 4):");
        List<Point> moves = pawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Double-move option (3,4) and one-square move (2,4) from (1, 4).\n");
    }

    private static void testPawnPromotion() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(6, 4), 1);

        board.placePiece(pawn, new Point(6, 4));
        System.out.println("Testing Pawn promotion moves from (6, 4):");
        List<Point> moves = pawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Promotion check at (7,4).\n");
    }

    private static void testPawnEnPassant() {
        // Set up a board with the en passant condition
        Board board = new Board(8);
        Pawn whitePawn = new Pawn("white", new Point(4, 4), 1); // The pawn that will attempt en passant
        Pawn blackPawn = new Pawn("black", new Point(6, 5), -1); // The pawn that moves two steps

        // Place the pawns on the board
        board.placePiece(whitePawn, new Point(4, 4));
        board.placePiece(blackPawn, new Point(6, 5));

        // Simulate black pawn's two-square move
        blackPawn.move(new Point(4, 5));
        GameState.getInstance().recordMove(blackPawn, new Point(6, 5), new Point(4, 5), false, false);

        System.out.println("Testing Pawn en passant move from (4, 4):");
        List<Point> moves = whitePawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: En passant capture at (5, 5).\n");
    }

    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

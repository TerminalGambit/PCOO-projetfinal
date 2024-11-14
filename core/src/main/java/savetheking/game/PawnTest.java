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
    }

    private static void testPawnStandardMoves() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(1, 4), 1); // Moving up the board

        // Place the pawn at (1, 4), its starting position
        board.placePiece(pawn, new Point(1, 4));

        System.out.println("Testing Pawn standard moves from (1, 4) on an 8x8 board:");
        List<Point> moves = pawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: (2,4), (3,4) if starting move.\n");
    }

    private static void testPawnCaptureMoves() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(3, 3), 1); // Moving up the board

        // Place the pawn and opposing pieces to simulate captures
        board.placePiece(pawn, new Point(3, 3));
        board.placePiece(new Rook("black", new Point(4, 4)), new Point(4, 4)); // Opponent piece diagonal right
        board.placePiece(new Knight("black", new Point(4, 2)), new Point(4, 2)); // Opponent piece diagonal left

        System.out.println("Testing Pawn capture moves from (3, 3) with opponents:");
        List<Point> moves = pawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Standard move forward and captures at (4,4) and (4,2).\n");
    }

    private static void testPawnDoubleMove() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(1, 4), 1); // Moving up the board

        // Place the pawn at (1, 4), simulating its starting double-move capability
        board.placePiece(pawn, new Point(1, 4));

        System.out.println("Testing Pawn double move from starting position (1, 4):");
        List<Point> moves = pawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Double-move option (3,4) and one-square move (2,4) from (1, 4).\n");
    }

    private static void testPawnPromotion() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(6, 4), 1); // Moving up the board

        // Place the pawn one move away from promotion
        board.placePiece(pawn, new Point(6, 4));

        System.out.println("Testing Pawn promotion moves from (6, 4):");
        List<Point> moves = pawn.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Promotion check at (7,4).\n");
    }

    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

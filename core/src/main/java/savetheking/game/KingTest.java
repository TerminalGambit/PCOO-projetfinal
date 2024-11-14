package savetheking.game;

import java.util.List;

public class KingTest {
    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        testKingCenterMoves();
        testKingEdgeMoves();
        testKingBlockedMoves();
    }

    private static void testKingCenterMoves() {
        Board board = new Board(8);
        King king = new King("white", new Point(4, 4));

        // Place the king at the center of the board
        board.placePiece(king, new Point(4, 4));

        System.out.println("Testing King moves from (4, 4) on an 8x8 board:");
        List<Point> moves = king.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: One-square moves in all directions from (4, 4).\n");
    }

    private static void testKingEdgeMoves() {
        Board board = new Board(8);
        King king = new King("black", new Point(0, 0));

        // Place the king on the edge of the board
        board.placePiece(king, new Point(0, 0));

        System.out.println("Testing King moves from (0, 0) on an 8x8 board:");
        List<Point> moves = king.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Moves in available directions from (0, 0) up to board edge.\n");
    }

    private static void testKingBlockedMoves() {
        Board board = new Board(8);
        King king = new King("white", new Point(4, 4));

        // Place the king and surrounding pieces
        board.placePiece(king, new Point(4, 4));
        board.placePiece(new Rook("white", new Point(4, 3)), new Point(4, 3)); // Friendly piece on left
        board.placePiece(new Knight("black", new Point(4, 5)), new Point(4, 5)); // Opponent piece on right

        System.out.println("Testing King moves from (4, 4) with blockers on the board:");
        List<Point> moves = king.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Blocked by friendly piece on left; can capture opponent on right.\n");
    }

    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

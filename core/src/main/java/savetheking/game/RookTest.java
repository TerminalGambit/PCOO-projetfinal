package savetheking.game;

import java.util.List;

public class RookTest {
    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        testRookCenterMoves();
        testRookEdgeMoves();
        testRookBlockedMoves();
    }

    private static void testRookCenterMoves() {
        Board board = new Board(8);
        Rook rook = new Rook("white", new Point(4, 4));

        // Use the Board's placePiece method to correctly place the rook
        board.placePiece(rook, new Point(4, 4));

        System.out.println("Testing Rook moves from (4, 4) on an 8x8 board:");
        List<Point> moves = rook.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Straight lines in all directions from (4, 4) up to edges.\n");
    }

    private static void testRookEdgeMoves() {
        Board board = new Board(8);
        Rook rook = new Rook("black", new Point(0, 0));

        // Use the Board's placePiece method to correctly place the rook
        board.placePiece(rook, new Point(0, 0));

        System.out.println("Testing Rook moves from (0, 0) on an 8x8 board:");
        List<Point> moves = rook.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Moves along row and column up to board edges.\n");
    }

    private static void testRookBlockedMoves() {
        Board board = new Board(8);
        Rook rook = new Rook("white", new Point(2, 2));

        // Place the rook and some blocking pieces on the board
        board.placePiece(rook, new Point(2, 2));
        board.placePiece(new Rook("white", new Point(2, 5)), new Point(2, 5)); // Friendly piece blocking horizontal
        board.placePiece(new Rook("black", new Point(6, 2)), new Point(6, 2)); // Opponent piece blocking vertical

        System.out.println("Testing Rook moves from (2, 2) with blockers on the board:");
        List<Point> moves = rook.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Blocked horizontally by friendly piece, can capture opponent vertically.\n");
    }

    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

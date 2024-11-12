package savetheking.game;

import java.util.List;

public class KnightTest {

    public static void main(String[] args) {
        // Run all knight test cases
        testKnightCenterMoves();
        testKnightEdgeMoves();
        testKnightCornerMoves();
    }

    /**
     * Test case: Knight in the center of the board
     */
    private static void testKnightCenterMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("White", new Point(4, 4));
        List<Point> moves = knight.getPossibleMoves(board);

        System.out.println("Knight moves from (4,4) on an 8x8 board:");
        printMoves(moves);
        System.out.println("Expected: (6,5), (6,3), (2,5), (2,3), (5,6), (5,2), (3,6), (3,2)");
    }

    /**
     * Test case: Knight at the edge of the board
     */
    private static void testKnightEdgeMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("White", new Point(0, 1));
        List<Point> moves = knight.getPossibleMoves(board);

        System.out.println("Knight moves from (0,1) on an 8x8 board:");
        printMoves(moves);
        System.out.println("Expected: (2,2), (2,0), (1,3)");
    }

    /**
     * Test case: Knight in the corner of the board
     */
    private static void testKnightCornerMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("White", new Point(0, 0));
        List<Point> moves = knight.getPossibleMoves(board);

        System.out.println("Knight moves from (0,0) on an 8x8 board:");
        printMoves(moves);
        System.out.println("Expected: (2,1), (1,2)");
    }

    /**
     * Utility method to print moves in a standardized format.
     */
    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

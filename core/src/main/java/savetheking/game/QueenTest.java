package savetheking.game;

import java.util.List;

public class QueenTest {
    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        testQueenCenterMoves();
        testQueenEdgeMoves();
        testQueenBlockedMoves();
    }

    private static void testQueenCenterMoves() {
        Board board = new Board(8);
        Queen queen = new Queen("white", new Point(4, 4));

        board.placePiece(queen, new Point(4, 4));

        System.out.println("Testing Queen moves from (4, 4) on an 8x8 board:");
        List<Point> moves = queen.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Straight lines and diagonal moves in all directions from (4, 4) up to edges.\n");
    }

    private static void testQueenEdgeMoves() {
        Board board = new Board(8);
        Queen queen = new Queen("black", new Point(0, 0));

        board.placePiece(queen, new Point(0, 0));

        System.out.println("Testing Queen moves from (0, 0) on an 8x8 board:");
        List<Point> moves = queen.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Moves along row, column, and diagonal up to board edges.\n");
    }

    private static void testQueenBlockedMoves() {
        Board board = new Board(8);
        Queen queen = new Queen("white", new Point(2, 2));

        board.placePiece(queen, new Point(2, 2));
        board.placePiece(new Rook("white", new Point(2, 5)), new Point(2, 5)); // Friendly piece blocking horizontal
        board.placePiece(new Bishop("black", new Point(5, 5)), new Point(5, 5)); // Opponent piece blocking diagonal

        System.out.println("Testing Queen moves from (2, 2) with blockers on the board:");
        List<Point> moves = queen.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Blocked horizontally by friendly piece, can capture opponent on diagonal.\n");
    }

    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

package savetheking.game;

import java.util.List;

public class BishopTest {
    public static void main(String[] args) {
        runTests();
    }

    public static void runTests() {
        testBishopCenterMoves();
        testBishopEdgeMoves();
        testBishopBlockedMoves();
    }

    private static void testBishopCenterMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("white", new Point(4, 4));

        // Place the bishop in the center of the board
        board.placePiece(bishop, new Point(4, 4));

        System.out.println("Testing Bishop moves from (4, 4) on an 8x8 board:");
        List<Point> moves = bishop.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Diagonal moves in all four directions from (4, 4) up to board edges.\n");
    }

    private static void testBishopEdgeMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("black", new Point(0, 0));

        // Place the bishop at the edge of the board
        board.placePiece(bishop, new Point(0, 0));

        System.out.println("Testing Bishop moves from (0, 0) on an 8x8 board:");
        List<Point> moves = bishop.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Diagonal moves only in two directions (up-right and down-right) up to board edges.\n");
    }

    private static void testBishopBlockedMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("white", new Point(2, 2));

        // Place the bishop and some blocking pieces on the board
        board.placePiece(bishop, new Point(2, 2));
        board.placePiece(new Rook("white", new Point(4, 4)), new Point(4, 4)); // Friendly piece blocking diagonal
        board.placePiece(new Rook("black", new Point(0, 0)), new Point(0, 0)); // Opponent piece on another diagonal

        System.out.println("Testing Bishop moves from (2, 2) with blockers on the board:");
        List<Point> moves = bishop.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Expected: Blocked diagonally by friendly piece, can capture opponent on the other diagonal.\n");
    }

    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

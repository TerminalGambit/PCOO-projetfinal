package savetheking.game;

import java.util.List;

public class GameRulesTest {
    public static void main(String[] args) {
        testKingInCheck();
        testCheckmate();
        testCastling();
    }

    public static void testKingInCheck() {
        Board board = new Board(8);
        King king = new King("white", new Point(0, 4));
        board.placePiece(king, new Point(0, 4));

        Rook attackingRook = new Rook("black", new Point(0, 0));
        board.placePiece(attackingRook, new Point(0, 0));

        GameState gameState = GameState.getInstance();
        boolean isInCheck = gameState.isInCheck(king, board);

        System.out.println("King in check test: " + (isInCheck ? "Passed" : "Failed"));
    }

    public static void testCheckmate() {
        Board board = new Board(8);
        King king = new King("white", new Point(0, 4)); // e1
        board.placePiece(king, new Point(0, 4));

        Rook rook1 = new Rook("black", new Point(0, 0)); // a1
        Rook rook2 = new Rook("black", new Point(1, 1)); // b2
        board.placePiece(rook1, new Point(0, 0));
        board.placePiece(rook2, new Point(1, 1));

        GameState gameState = GameState.getInstance();

        // Add diagnostic print to check if king is initialized
        System.out.println("King object before isCheckmate: " + king);

        boolean isCheckmate = gameState.isCheckmate(king, board);

        System.out.println("Ladder mate test (Rooks on a1 and b2, King on e1): " + (isCheckmate ? "Passed" : "Failed"));
    }

    public static void testCastling() {
        Board board = new Board(8);
        King king = new King("white", new Point(0, 4));
        Rook kingsideRook = new Rook("white", new Point(0, 7));
        Rook queensideRook = new Rook("white", new Point(0, 0));

        board.placePiece(king, new Point(0, 4));
        board.placePiece(kingsideRook, new Point(0, 7));
        board.placePiece(queensideRook, new Point(0, 0));

        List<Point> kingMoves = king.getPossibleMoves(board);
        boolean kingsideCastling = kingMoves.contains(new Point(0, 6));
        boolean queensideCastling = kingMoves.contains(new Point(0, 2));

        System.out.println("Kingside castling test: " + (kingsideCastling ? "Passed" : "Failed"));
        System.out.println("Queenside castling test: " + (queensideCastling ? "Passed" : "Failed"));
    }
}

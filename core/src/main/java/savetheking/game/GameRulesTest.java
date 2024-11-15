package savetheking.game;

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
        King king = new King("white", new Point(0, 4));
        board.placePiece(king, new Point(0, 4));

        Rook rook1 = new Rook("black", new Point(1, 0));
        Rook rook2 = new Rook("black", new Point(1, 7));
        board.placePiece(rook1, new Point(1, 0));
        board.placePiece(rook2, new Point(1, 7));

        GameState gameState = GameState.getInstance();
        boolean isCheckmate = gameState.isCheckmate(king, board);

        System.out.println("Checkmate test: " + (isCheckmate ? "Passed" : "Failed"));
    }

    public static void testCastling() {
        Board board = new Board(8);
        King king = new King("white", new Point(0, 4));
        Rook rook = new Rook("white", new Point(0, 7));

        board.placePiece(king, new Point(0, 4));
        board.placePiece(rook, new Point(0, 7));

        // Use the king instance to check for castling eligibility
        boolean canCastle = king.canCastle(board, rook); // Assuming canCastle is now in King class

        System.out.println("Castling test: " + (canCastle ? "Passed" : "Failed"));
    }
}

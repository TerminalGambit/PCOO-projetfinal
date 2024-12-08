package savetheking.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Classe de test pour vérifier les règles spécifiques du jeu d'échecs.
 * Elle inclut des tests pour les situations suivantes :
 * - Roi en échec
 * - Échec et mat
 * - Roque (petit et grand)
 */
public class GameRulesTest {

    /**
     * Teste si le roi est correctement détecté en position d'échec.
     */
    @Test
    public void testKingInCheck() {
        Board board = new Board(8);
        King king = new King("white", new Point(0, 4));
        board.placePiece(king, new Point(0, 4));

        Rook attackingRook = new Rook("black", new Point(0, 0));
        board.placePiece(attackingRook, new Point(0, 0));

        GameState gameState = GameState.getInstance();
        boolean isInCheck = gameState.isInCheck(king, board);

        assertTrue(isInCheck, "Le roi devrait être en échec.");
    }

    /**
     * Teste si l'échec et mat est correctement détecté.
     */
    @Test
    public void testCheckmate() {
        Board board = new Board(8);
        King king = new King("white", new Point(0, 4)); // e1
        board.placePiece(king, new Point(0, 4));

        Rook rook1 = new Rook("black", new Point(0, 0)); // a1
        Rook rook2 = new Rook("black", new Point(1, 1)); // b2
        board.placePiece(rook1, new Point(0, 0));
        board.placePiece(rook2, new Point(1, 1));

        GameState gameState = GameState.getInstance();
        boolean isCheckmate = gameState.isCheckmate(king, board);

        assertTrue(isCheckmate, "Le roi devrait être en échec et mat (mate en échelle).");
    }

    /**
     * Teste si le roque (petit et grand) est correctement détecté.
     */
    @Test
    public void testCastling() {
        Board board = new Board(8);
        King king = new King("white", new Point(0, 4));
        Rook kingsideRook = new Rook("white", new Point(0, 7));
        Rook queensideRook = new Rook("white", new Point(0, 0));

        board.placePiece(king, new Point(0, 4));
        board.placePiece(kingsideRook, new Point(0, 7));
        board.placePiece(queensideRook, new Point(0, 0));

        List<Point> kingMoves = king.getPossibleMoves(board);

        assertTrue(kingMoves.contains(new Point(0, 6)), "Le roi devrait pouvoir faire un petit roque.");
        assertTrue(kingMoves.contains(new Point(0, 2)), "Le roi devrait pouvoir faire un grand roque.");
    }
}

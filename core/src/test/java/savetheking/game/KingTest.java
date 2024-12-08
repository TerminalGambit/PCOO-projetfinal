package savetheking.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de tests pour la classe King.
 * Vérifie les règles de déplacement du roi, y compris les cas limites
 * et les interactions avec d'autres pièces.
 */
public class KingTest {

    /**
     * Teste les déplacements du roi placé au centre du plateau.
     * Attendu : Tous les mouvements d'une case dans toutes les directions dans les limites du plateau.
     */
    @Test
    public void testKingCenterMoves() {
        Board board = new Board(8);
        King king = new King("white", new Point(4, 4));
        board.placePiece(king, king.getPosition());

        List<Point> moves = king.getPossibleMoves(board);

        assertFalse(moves.isEmpty(), "Les déplacements du roi au centre ne doivent pas être vides.");
        assertTrue(moves.contains(new Point(5, 4)), "Le roi doit pouvoir se déplacer en (5, 4).");
        assertTrue(moves.contains(new Point(3, 3)), "Le roi doit pouvoir se déplacer en (3, 3).");
    }

    /**
     * Teste les déplacements du roi placé au bord du plateau.
     * Attendu : Les déplacements sont limités par les bordures du plateau.
     */
    @Test
    public void testKingEdgeMoves() {
        Board board = new Board(8);
        King king = new King("black", new Point(0, 0));
        board.placePiece(king, king.getPosition());

        List<Point> moves = king.getPossibleMoves(board);

        assertFalse(moves.isEmpty(), "Les déplacements du roi au bord ne doivent pas être vides.");
        assertTrue(moves.contains(new Point(1, 0)), "Le roi doit pouvoir se déplacer en (1, 0).");
        assertTrue(moves.contains(new Point(0, 1)), "Le roi doit pouvoir se déplacer en (0, 1).");
        assertFalse(moves.contains(new Point(-1, -1)), "Le roi ne doit pas pouvoir sortir des limites.");
    }

    /**
     * Teste les déplacements du roi lorsqu'il est entouré de pièces alliées et adverses.
     * Attendu : Bloqué par les pièces alliées, mais peut capturer les pièces adverses.
     */
    @Test
    public void testKingBlockedMoves() {
        Board board = new Board(8);
        King king = new King("white", new Point(4, 4));
        board.placePiece(king, king.getPosition());
        board.placePiece(new Rook("white", new Point(4, 3)), new Point(4, 3)); // Pièce alliée à gauche
        board.placePiece(new Knight("black", new Point(4, 5)), new Point(4, 5)); // Pièce ennemie à droite

        List<Point> moves = king.getPossibleMoves(board);

        assertTrue(moves.contains(new Point(4, 5)), "Le roi doit pouvoir capturer une pièce ennemie.");
        assertFalse(moves.contains(new Point(4, 3)), "Le roi ne doit pas pouvoir se déplacer sur une pièce alliée.");
    }
}

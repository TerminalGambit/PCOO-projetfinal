package savetheking.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test JUnit pour la pièce "Tour" (Rook).
 * Cette classe vérifie les mouvements possibles de la tour dans divers scénarios.
 */
public class RookTest {

    /**
     * Vérifie les mouvements possibles de la tour lorsqu'elle est placée au centre du plateau.
     */
    @Test
    public void testRookCenterMoves() {
        Board board = new Board(8, 8);
        Rook rook = new Rook("white", new Point(4, 4));

        // Place la tour au centre du plateau
        board.placePiece(rook, new Point(4, 4));

        List<Point> moves = rook.getPossibleMoves(board);

        assertFalse(moves.isEmpty(), "Les mouvements de la tour ne doivent pas être vides.");
        assertTrue(moves.contains(new Point(4, 7)), "La tour doit pouvoir se déplacer en (4, 7).");
        assertTrue(moves.contains(new Point(0, 4)), "La tour doit pouvoir se déplacer en (0, 4).");
    }

    /**
     * Vérifie les mouvements possibles de la tour lorsqu'elle est placée au bord du plateau.
     */
    @Test
    public void testRookEdgeMoves() {
        Board board = new Board(8, 8);
        Rook rook = new Rook("black", new Point(0, 4));

        // Place la tour au bord du plateau
        board.placePiece(rook, new Point(0, 4));

        List<Point> moves = rook.getPossibleMoves(board);

        assertFalse(moves.isEmpty(), "Les mouvements de la tour au bord ne doivent pas être vides.");
        assertTrue(moves.contains(new Point(7, 4)), "La tour doit pouvoir se déplacer en (7, 4).");
        assertTrue(moves.contains(new Point(0, 7)), "La tour doit pouvoir se déplacer en (0, 7).");
    }

    /**
     * Vérifie les mouvements possibles de la tour lorsqu'elle est bloquée par d'autres pièces.
     */
    @Test
    public void testRookBlockedMoves() {
        Board board = new Board(8, 8);
        Rook rook = new Rook("white", new Point(3, 3));

        // Place la tour et des bloqueurs sur le plateau
        board.placePiece(rook, new Point(3, 3));
        board.placePiece(new Knight("white", new Point(3, 5)), new Point(3, 5)); // Bloqueur allié
        board.placePiece(new Pawn("black", new Point(3, 1), 1), new Point(3, 1)); // Adversaire capturable

        List<Point> moves = rook.getPossibleMoves(board);

        assertTrue(moves.contains(new Point(3, 1)), "La tour doit pouvoir capturer la pièce adverse en (3, 1).");
        assertFalse(moves.contains(new Point(3, 6)), "La tour ne doit pas pouvoir se déplacer au-delà du bloqueur allié.");
    }
}

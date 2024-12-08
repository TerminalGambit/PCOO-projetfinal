package savetheking.game;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitaire pour la classe Queen.
 * Vérifie le comportement et les mouvements possibles de la reine
 * dans divers scénarios sur le plateau d'échecs.
 */
public class QueenTest {

    @Test
    public void testQueenCenterMoves() {
        Board board = new Board(8);
        Queen queen = new Queen("white", new Point(4, 4));
        board.placePiece(queen, new Point(4, 4));

        List<Point> moves = queen.getPossibleMoves(board);

        assertFalse(moves.isEmpty(), "Les mouvements de la reine au centre ne doivent pas être vides.");
        assertTrue(moves.contains(new Point(5, 5)), "La reine doit pouvoir se déplacer en (5, 5).");
        assertTrue(moves.contains(new Point(0, 4)), "La reine doit pouvoir se déplacer en (0, 4).");
    }

    @Test
    public void testQueenEdgeMoves() {
        Board board = new Board(8);
        Queen queen = new Queen("black", new Point(0, 0));
        board.placePiece(queen, new Point(0, 0));

        List<Point> moves = queen.getPossibleMoves(board);

        assertFalse(moves.isEmpty(), "Les mouvements de la reine en bordure ne doivent pas être vides.");
        assertTrue(moves.contains(new Point(7, 7)), "La reine doit pouvoir se déplacer en (7, 7).");
    }

    @Test
    public void testQueenBlockedMoves() {
        Board board = new Board(8);
        Queen queen = new Queen("white", new Point(3, 3));
        board.placePiece(queen, new Point(3, 3));
        board.placePiece(new Rook("white", new Point(5, 5)), new Point(5, 5)); // Bloqueur allié
        board.placePiece(new Pawn("black", new Point(1, 1), 0), new Point(1, 1)); // Bloqueur ennemi

        List<Point> moves = queen.getPossibleMoves(board);

        assertTrue(moves.contains(new Point(1, 1)), "La reine doit pouvoir capturer une pièce ennemie.");
        assertFalse(moves.contains(new Point(6, 6)), "La reine ne doit pas pouvoir passer au-delà d'une pièce alliée.");
    }
}

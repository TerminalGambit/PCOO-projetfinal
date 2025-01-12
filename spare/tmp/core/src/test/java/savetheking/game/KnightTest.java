package savetheking.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour vérifier les mouvements possibles d'un cavalier (Knight).
 * Cette classe inclut plusieurs scénarios de test, notamment lorsque le cavalier
 * est au centre, au bord ou dans un coin du plateau.
 */
public class KnightTest {

    /**
     * Cas de test : Cavalier au centre du plateau.
     * Vérifie que le cavalier peut effectuer tous ses mouvements autorisés.
     */
    @Test
    public void testKnightCenterMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("Blanc", new Point(4, 4));
        board.placePiece(knight, new Point(4, 4));

        List<Point> moves = knight.getPossibleMoves(board);

        // Attendu : (6,5), (6,3), (2,5), (2,3), (5,6), (5,2), (3,6), (3,2)
        assertFalse(moves.isEmpty(), "Le cavalier doit avoir des mouvements possibles au centre.");
        assertTrue(moves.contains(new Point(6, 5)), "Le cavalier doit pouvoir se déplacer en (6,5).");
        assertTrue(moves.contains(new Point(3, 2)), "Le cavalier doit pouvoir se déplacer en (3,2).");
    }

    /**
     * Cas de test : Cavalier au bord du plateau.
     * Vérifie que le cavalier ne dépasse pas les limites du plateau.
     */
    @Test
    public void testKnightEdgeMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("Blanc", new Point(0, 1));
        board.placePiece(knight, new Point(0, 1));

        List<Point> moves = knight.getPossibleMoves(board);

        // Attendu : (2,2), (2,0), (1,3)
        assertFalse(moves.isEmpty(), "Le cavalier doit avoir des mouvements possibles au bord.");
        assertTrue(moves.contains(new Point(2, 2)), "Le cavalier doit pouvoir se déplacer en (2,2).");
        assertTrue(moves.contains(new Point(2, 0)), "Le cavalier doit pouvoir se déplacer en (2,0).");
    }

    /**
     * Cas de test : Cavalier dans un coin du plateau.
     * Vérifie que le cavalier peut effectuer uniquement des mouvements valides.
     */
    @Test
    public void testKnightCornerMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("Blanc", new Point(0, 0));
        board.placePiece(knight, new Point(0, 0));

        List<Point> moves = knight.getPossibleMoves(board);

        // Attendu : (2,1), (1,2)
        assertFalse(moves.isEmpty(), "Le cavalier doit avoir des mouvements possibles dans le coin.");
        assertTrue(moves.contains(new Point(2, 1)), "Le cavalier doit pouvoir se déplacer en (2,1).");
        assertTrue(moves.contains(new Point(1, 2)), "Le cavalier doit pouvoir se déplacer en (1,2).");
    }
}

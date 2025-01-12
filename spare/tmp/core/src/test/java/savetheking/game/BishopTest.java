package savetheking.game;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Bishop.
 * Vérifie les mouvements possibles dans différents scénarios.
 */
public class BishopTest {

    /**
     * Test des mouvements possibles du fou au centre du plateau.
     */
    @Test
    public void testBishopCenterMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("white", new Point(4, 4));
        board.placePiece(bishop, new Point(4, 4));

        List<Point> moves = bishop.getPossibleMoves(board);

        // Définir les mouvements attendus
        Set<Point> expectedMoves = new HashSet<>(Arrays.asList(
            new Point(5, 5), new Point(6, 6), new Point(7, 7),
            new Point(5, 3), new Point(6, 2), new Point(7, 1),
            new Point(3, 5), new Point(2, 6), new Point(1, 7),
            new Point(3, 3), new Point(2, 2), new Point(1, 1), new Point(0, 0)
        ));

        validateTest(moves, expectedMoves, "Mouvements du fou au centre");
    }

    /**
     * Test des mouvements possibles du fou en bordure du plateau.
     */
    @Test
    public void testBishopEdgeMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("black", new Point(0, 0));
        board.placePiece(bishop, new Point(0, 0));

        List<Point> moves = bishop.getPossibleMoves(board);

        // Définir les mouvements attendus
        Set<Point> expectedMoves = new HashSet<>(Arrays.asList(
            new Point(1, 1), new Point(2, 2), new Point(3, 3),
            new Point(4, 4), new Point(5, 5), new Point(6, 6), new Point(7, 7)
        ));

        validateTest(moves, expectedMoves, "Mouvements du fou en bordure");
    }

    /**
     * Test des mouvements possibles du fou avec des bloqueurs.
     */
    @Test
    public void testBishopBlockedMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("white", new Point(2, 2));
        board.placePiece(bishop, new Point(2, 2));

        // Ajout de pièces bloquantes
        board.placePiece(new Rook("white", new Point(4, 4)), new Point(4, 4)); // Bloqueur allié
        board.placePiece(new Rook("black", new Point(0, 0)), new Point(0, 0)); // Pièce ennemie

        List<Point> moves = bishop.getPossibleMoves(board);

        // Définir les mouvements attendus
        Set<Point> expectedMoves = new HashSet<>(Arrays.asList(
            new Point(3, 3), // Avant le bloqueur allié
            new Point(3, 1), new Point(4, 0), // Autre diagonale
            new Point(1, 3), new Point(0, 4), // Autre diagonale
            new Point(1, 1), new Point(0, 0) // Capture possible
        ));

        validateTest(moves, expectedMoves, "Mouvements du fou avec bloqueurs");
    }

    /**
     * Valide si les résultats du test correspondent aux attentes.
     *
     * @param actualMoves Liste des mouvements obtenus.
     * @param expectedMoves Ensemble des mouvements attendus.
     * @param testName Nom du test en cours.
     */
    private void validateTest(List<Point> actualMoves, Set<Point> expectedMoves, String testName) {
        Set<Point> actualMovesSet = new HashSet<>(actualMoves);
        assertEquals(expectedMoves, actualMovesSet, testName + " a échoué.");
    }
}

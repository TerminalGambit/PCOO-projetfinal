package savetheking.game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;

/**
 * Classe de test pour la pièce Fou (Bishop).
 * Vérifie que les mouvements possibles sont correctement calculés.
 */
public class BishopTest {

    /**
     * Point d'entrée pour exécuter tous les tests.
     *
     * @param args Arguments de ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        runTests();
    }

    /**
     * Exécute l'ensemble des cas de test.
     */
    public static void runTests() {
        testBishopCenterMoves();
        testBishopEdgeMoves();
        testBishopBlockedMoves();
    }

    /**
     * Vérifie les mouvements du fou depuis le centre du plateau.
     */
    private static void testBishopCenterMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("white", new Point(4, 4));
        board.placePiece(bishop, new Point(4, 4));

        System.out.println("Test des mouvements du fou depuis (4, 4) sur un plateau 8x8 :");
        List<Point> moves = bishop.getPossibleMoves(board);

        // Définir les mouvements attendus
        Set<Point> expectedMoves = new HashSet<Point>(Arrays.asList(
            new Point(5, 5), new Point(6, 6), new Point(7, 7),
            new Point(5, 3), new Point(6, 2), new Point(7, 1),
            new Point(3, 5), new Point(2, 6), new Point(1, 7),
            new Point(3, 3), new Point(2, 2), new Point(1, 1), new Point(0, 0)
        ));

        validateTest(moves, expectedMoves, "Mouvements du fou au centre");
    }

    /**
     * Vérifie les mouvements du fou depuis le bord du plateau.
     */
    private static void testBishopEdgeMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("black", new Point(0, 0));
        board.placePiece(bishop, new Point(0, 0));

        System.out.println("Test des mouvements du fou depuis (0, 0) sur un plateau 8x8 :");
        List<Point> moves = bishop.getPossibleMoves(board);

        // Définir les mouvements attendus
        Set<Point> expectedMoves = new HashSet<Point>(Arrays.asList(
            new Point(1, 1), new Point(2, 2), new Point(3, 3),
            new Point(4, 4), new Point(5, 5), new Point(6, 6), new Point(7, 7)
        ));

        validateTest(moves, expectedMoves, "Mouvements du fou au bord");
    }

    /**
     * Vérifie les mouvements du fou lorsqu'il est bloqué par d'autres pièces.
     */
    private static void testBishopBlockedMoves() {
        Board board = new Board(8);
        Bishop bishop = new Bishop("white", new Point(2, 2));
        board.placePiece(bishop, new Point(2, 2));

        // Ajout de pièces bloquantes
        board.placePiece(new Rook("white", new Point(4, 4)), new Point(4, 4)); // Bloqueur allié
        board.placePiece(new Rook("black", new Point(0, 0)), new Point(0, 0)); // Pièce ennemie

        System.out.println("Test des mouvements du fou depuis (2, 2) avec des bloqueurs :");
        List<Point> moves = bishop.getPossibleMoves(board);

        // Définir les mouvements attendus
        Set<Point> expectedMoves = new HashSet<Point>(Arrays.asList(
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
    private static void validateTest(List<Point> actualMoves, Set<Point> expectedMoves, String testName) {
        Set<Point> actualMovesSet = new HashSet<Point>(actualMoves);
        if (actualMovesSet.equals(expectedMoves)) {
            System.out.println(testName + " réussi.\n");
        } else {
            System.out.println(testName + " échoué.");
            System.out.println("Attendu : " + expectedMoves);
            System.out.println("Obtenu : " + actualMovesSet + "\n");
        }
    }
}

package savetheking.game;

import java.util.List;

/**
 * Classe de test pour la pièce "Tour" (Rook).
 * Cette classe vérifie les mouvements possibles de la tour dans différents scénarios.
 */
public class RookTest {

    public static void main(String[] args) {
        runTests();
    }

    /**
     * Méthode pour exécuter tous les tests de la classe.
     */
    public static void runTests() {
        testRookCenterMoves();
        testRookEdgeMoves();
        testRookBlockedMoves();
    }

    /**
     * Teste les mouvements de la tour placée au centre du plateau.
     */
    private static void testRookCenterMoves() {
        Board board = new Board(8, 8);
        Rook rook = new Rook("white", new Point(4, 4));

        // Place la tour au centre du plateau
        board.placePiece(rook, new Point(4, 4));

        System.out.println("Test des mouvements de la tour depuis (4, 4) sur un plateau 8x8 :");
        List<Point> moves = rook.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Attendu : Mouvements horizontaux et verticaux complets depuis (4, 4).\n");
    }

    /**
     * Teste les mouvements de la tour placée au bord du plateau.
     */
    private static void testRookEdgeMoves() {
        Board board = new Board(8, 8);
        Rook rook = new Rook("black", new Point(0, 4));

        // Place la tour au bord du plateau
        board.placePiece(rook, new Point(0, 4));

        System.out.println("Test des mouvements de la tour depuis (0, 4) sur un plateau 8x8 :");
        List<Point> moves = rook.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Attendu : Mouvements horizontaux et verticaux disponibles jusqu'aux limites du plateau.\n");
    }

    /**
     * Teste les mouvements de la tour avec des bloqueurs sur son chemin.
     */
    private static void testRookBlockedMoves() {
        Board board = new Board(8, 8);
        Rook rook = new Rook("white", new Point(3, 3));

        // Place la tour et des bloqueurs sur le plateau
        board.placePiece(rook, new Point(3, 3));
        board.placePiece(new Knight("white", new Point(3, 5)), new Point(3, 5)); // Bloqueur allié
        board.placePiece(new Pawn("black", new Point(3, 1), 1), new Point(3, 1));   // Adversaire capturable

        System.out.println("Test des mouvements de la tour depuis (3, 3) avec des bloqueurs sur le plateau :");
        List<Point> moves = rook.getPossibleMoves(board);
        printMoves(moves);
        System.out.println("Attendu : Bloquée par le cavalier allié ; peut capturer le pion adverse.\n");
    }

    /**
     * Imprime les mouvements possibles d'une pièce dans un format lisible.
     *
     * @param moves Liste des positions possibles.
     */
    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

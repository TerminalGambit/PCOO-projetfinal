package savetheking.game;

import java.util.List;

/**
 * Classe de test pour vérifier les mouvements possibles d'un cavalier (Knight).
 * Cette classe inclut plusieurs scénarios de test, notamment lorsque le cavalier
 * est au centre, au bord ou dans un coin du plateau.
 */
public class KnightTest {

    public static void main(String[] args) {
        // Exécution de tous les cas de test pour le cavalier
        runTests();
    }

    /**
     * Méthode principale pour exécuter tous les tests du cavalier.
     */
    private static void runTests() {
        testKnightCenterMoves();
        testKnightEdgeMoves();
        testKnightCornerMoves();
    }

    /**
     * Cas de test : Cavalier au centre du plateau.
     * Vérifie que le cavalier peut effectuer tous ses mouvements autorisés.
     */
    private static void testKnightCenterMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("Blanc", new Point(4, 4));
        board.placePiece(knight, new Point(4, 4));

        List<Point> moves = knight.getPossibleMoves(board);

        System.out.println("Mouvements possibles pour le cavalier à partir de (4,4) sur un plateau 8x8 :");
        printMoves(moves);
        System.out.println("Attendu : (6,5), (6,3), (2,5), (2,3), (5,6), (5,2), (3,6), (3,2)\n");
    }

    /**
     * Cas de test : Cavalier au bord du plateau.
     * Vérifie que le cavalier ne dépasse pas les limites du plateau.
     */
    private static void testKnightEdgeMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("Blanc", new Point(0, 1));
        board.placePiece(knight, new Point(0, 1));

        List<Point> moves = knight.getPossibleMoves(board);

        System.out.println("Mouvements possibles pour le cavalier à partir de (0,1) sur un plateau 8x8 :");
        printMoves(moves);
        System.out.println("Attendu : (2,2), (2,0), (1,3)\n");
    }

    /**
     * Cas de test : Cavalier dans un coin du plateau.
     * Vérifie que le cavalier peut effectuer uniquement des mouvements valides.
     */
    private static void testKnightCornerMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight("Blanc", new Point(0, 0));
        board.placePiece(knight, new Point(0, 0));

        List<Point> moves = knight.getPossibleMoves(board);

        System.out.println("Mouvements possibles pour le cavalier à partir de (0,0) sur un plateau 8x8 :");
        printMoves(moves);
        System.out.println("Attendu : (2,1), (1,2)\n");
    }

    /**
     * Méthode utilitaire pour afficher les mouvements dans un format standardisé.
     *
     * @param moves La liste des positions possibles pour le cavalier.
     */
    private static void printMoves(List<Point> moves) {
        for (Point move : moves) {
            System.out.print("(" + move.x + "," + move.y + ") ");
        }
        System.out.println();
    }
}

package savetheking.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour vérifier le comportement de la classe Point.
 * Teste principalement la méthode toChessNotation.
 */
public class PointTest {

    /**
     * Teste la conversion des coordonnées du coin inférieur gauche.
     */
    @Test
    public void testLowerLeftCorner() {
        Point point = new Point(0, 0); // Coin inférieur gauche
        String notation = point.toChessNotation(8);
        assertEquals("a8", notation, "La notation pour (0, 0) devrait être 'a8' sur un échiquier 8x8.");
    }

    /**
     * Teste la conversion des coordonnées du coin supérieur droit.
     */
    @Test
    public void testUpperRightCorner() {
        Point point = new Point(7, 7); // Coin supérieur droit
        String notation = point.toChessNotation(8);
        assertEquals("h1", notation, "La notation pour (7, 7) devrait être 'h1' sur un échiquier 8x8.");
    }

    /**
     * Teste la conversion des coordonnées du centre du plateau.
     */
    @Test
    public void testMiddleOfBoard() {
        Point point = new Point(4, 3); // Milieu de l'échiquier
        String notation = point.toChessNotation(8);
        assertEquals("d4", notation, "La notation pour (4, 3) devrait être 'd4' sur un échiquier 8x8.");
    }

    /**
     * Teste une position hors des limites pour vérifier le comportement de sécurité.
     */
    @Test
    public void testOutOfBounds() {
        Point point = new Point(-1, 8); // Position invalide
        assertThrows(IllegalArgumentException.class, () -> point.toChessNotation(8),
            "Une exception devrait être levée pour des coordonnées hors limites.");
    }
}

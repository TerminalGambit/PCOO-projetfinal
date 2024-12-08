package savetheking.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour l'implémentation de l'algorithme Minimax.
 * Vérifie la logique de prise de décision de l'IA dans divers scénarios.
 */
public class MinimaxTest {

    private Board board;
    private GameState gameState;
    private Minimax minimax;

    /**
     * Initialisation du plateau, de l'état du jeu, et de l'algorithme Minimax.
     */
    @BeforeEach
    public void setup() {
        board = new Board(8); // Plateau 8x8
        gameState = GameState.getInstance();
        minimax = new Minimax();
    }

    /**
     * Teste la capacité de l'algorithme à trouver un meilleur coup dans une situation simple.
     */
    @Test
    public void testGetBestMoveSimplePosition() {
        setupTestPosition(); // Position de test prédéfinie

        Move bestMove = minimax.getBestMove(board, "black");
        assertNotNull(bestMove, "Minimax devrait trouver un meilleur coup.");
        assertEquals(new Point(7, 0), bestMove.getStart(), "La Tour noire doit commencer en (7, 0).");
        assertEquals(new Point(7, 4), bestMove.getEnd(), "La Tour noire doit se déplacer en (7, 4).");
    }

    /**
     * Teste si l'algorithme détecte correctement une situation où aucun coup valide n'est disponible.
     */
    @Test
    public void testNoValidMoves() {
        // Crée une position où l'IA ne peut pas jouer
        board.placePiece(new King("white", new Point(0, 4)), new Point(0, 4));
        board.placePiece(new King("black", new Point(7, 4)), new Point(7, 4));

        Move bestMove = minimax.getBestMove(board, "black");
        assertNull(bestMove, "Aucun coup valide ne devrait être disponible pour l'IA.");
    }

    /**
     * Teste si Minimax gère correctement un plateau où il peut capturer une pièce.
     */
    @Test
    public void testCaptureMove() {
        setupTestPosition();
        // Ajout d'une pièce capturable
        board.placePiece(new Pawn("white", new Point(7, 3), -1), new Point(7, 3));

        Move bestMove = minimax.getBestMove(board, "black");
        assertNotNull(bestMove, "Minimax devrait trouver un coup valide.");
        assertEquals(new Point(7, 0), bestMove.getStart(), "La Tour noire doit commencer en (7, 0).");
        assertEquals(new Point(7, 3), bestMove.getEnd(), "La Tour noire doit capturer le Pion blanc en (7, 3).");
    }

    /**
     * Configure une position de test prédéfinie sur le plateau.
     */
    private void setupTestPosition() {
        board.placePiece(new King("white", new Point(0, 4)), new Point(0, 4));
        board.placePiece(new King("black", new Point(7, 4)), new Point(7, 4));
        board.placePiece(new Rook("white", new Point(0, 0)), new Point(0, 0));
        board.placePiece(new Rook("black", new Point(7, 0)), new Point(7, 0));
    }
}

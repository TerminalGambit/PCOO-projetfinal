package savetheking.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitaire pour la classe Pawn.
 * Vérifie les règles de mouvement des pions, y compris les mouvements standards,
 * captures, promotion, double mouvement initial et la règle du "en passant".
 */
public class PawnTest {

    /**
     * Test des mouvements standards du pion.
     * Vérifie que le pion avance correctement.
     */
    @Test
    public void testPawnStandardMoves() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(1, 4), 1);

        board.placePiece(pawn, new Point(1, 4));
        List<Point> moves = pawn.getPossibleMoves(board);

        assertTrue(moves.contains(new Point(2, 4)), "Le pion doit avancer d'une case.");
        assertTrue(moves.contains(new Point(3, 4)), "Le pion doit pouvoir avancer de deux cases depuis sa position initiale.");
    }

    /**
     * Test des mouvements de capture du pion.
     * Vérifie que le pion peut capturer correctement les pièces adverses.
     */
    @Test
    public void testPawnCaptureMoves() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(3, 3), 1);

        board.placePiece(pawn, new Point(3, 3));
        board.placePiece(new Rook("black", new Point(4, 4)), new Point(4, 4));
        board.placePiece(new Knight("black", new Point(4, 2)), new Point(4, 2));

        List<Point> moves = pawn.getPossibleMoves(board);

        assertTrue(moves.contains(new Point(4, 4)), "Le pion doit pouvoir capturer la pièce en (4,4).");
        assertTrue(moves.contains(new Point(4, 2)), "Le pion doit pouvoir capturer la pièce en (4,2).");
    }

    /**
     * Test du double mouvement initial du pion.
     * Vérifie que le pion peut avancer de deux cases depuis sa position de départ.
     */
    @Test
    public void testPawnDoubleMove() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(1, 4), 1);

        board.placePiece(pawn, new Point(1, 4));
        List<Point> moves = pawn.getPossibleMoves(board);

        assertTrue(moves.contains(new Point(2, 4)), "Le pion doit avancer d'une case.");
        assertTrue(moves.contains(new Point(3, 4)), "Le pion doit pouvoir avancer de deux cases depuis sa position initiale.");
    }

    /**
     * Test de la promotion du pion.
     * Vérifie que le pion peut atteindre la dernière rangée et être promu.
     */
    @Test
    public void testPawnPromotion() {
        Board board = new Board(8);
        Pawn pawn = new Pawn("white", new Point(6, 4), 1);

        board.placePiece(pawn, new Point(6, 4));
        List<Point> moves = pawn.getPossibleMoves(board);

        assertTrue(moves.contains(new Point(7, 4)), "Le pion doit pouvoir atteindre la dernière rangée pour être promu.");
    }

    /**
     * Test de la règle "en passant".
     * Vérifie que le pion peut capturer un adversaire selon les règles "en passant".
     */
    @Test
    public void testPawnEnPassant() {
        Board board = new Board(8);
        Pawn whitePawn = new Pawn("white", new Point(4, 4), 1); // Pion qui tentera le "en passant"
        Pawn blackPawn = new Pawn("black", new Point(6, 5), -1); // Pion qui déclenche le "en passant"

        board.placePiece(whitePawn, new Point(4, 4));
        board.placePiece(blackPawn, new Point(6, 5));

        // Simule le double mouvement du pion noir
        blackPawn.move(new Point(4, 5));
        GameState.getInstance().recordMove(blackPawn, new Point(6, 5), new Point(4, 5), false, false);

        List<Point> moves = whitePawn.getPossibleMoves(board);

        assertTrue(moves.contains(new Point(5, 5)), "Le pion doit pouvoir capturer en passant en (5,5).");
    }
}

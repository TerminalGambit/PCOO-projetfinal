package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un pion (Pawn) dans le mode Solo Chess.
 * Le pion peut avancer et capturer en diagonale selon les règles de Solo Chess.
 */
public class Pawn extends Piece {

    private final int direction; // +1 pour avancer vers le haut du plateau, -1 pour avancer vers le bas

    /**
     * Constructeur pour initialiser un pion avec une couleur et une position.
     *
     * @param color     La couleur du pion ("Blanc").
     * @param position  La position initiale du pion sur le plateau.
     * @param direction La direction du pion (+1 ou -1).
     */
    public Pawn(String color, Point position, int direction) {
        super(color, position);
        this.direction = direction;
    }

    /**
     * Retourne une liste des mouvements possibles pour le pion.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions où le pion peut se déplacer.
     */
    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();

        // Déplacement standard vers l'avant
        Point forwardMove = new Point(position.x + direction, position.y);
        if (board.isWithinBounds(forwardMove) && board.getTileAt(forwardMove) instanceof EmptyTile) {
            possibleMoves.add(forwardMove);
        }

        // Déplacements de capture en diagonale
        addCaptureMoves(board, possibleMoves);

        // Si le pion est noir (après 2 mouvements), il ne peut plus se déplacer
        if ("Noir".equals(this.color)) {
            possibleMoves.clear();
        }

        return possibleMoves;
    }

    /**
     * Ajoute les mouvements de capture possibles à la liste des mouvements.
     *
     * @param board          L'état actuel du plateau.
     * @param possibleMoves  La liste des mouvements possibles.
     */
    private void addCaptureMoves(Board board, List<Point> possibleMoves) {
        Point captureLeft = new Point(position.x + direction, position.y - 1);
        Point captureRight = new Point(position.x + direction, position.y + 1);

        if (board.isWithinBounds(captureLeft) && board.getTileAt(captureLeft) instanceof OccupiedTile) {
            Piece leftPiece = ((OccupiedTile) board.getTileAt(captureLeft)).getPiece();
            if (!leftPiece.getColor().equals(this.color)) {
                possibleMoves.add(captureLeft);
            }
        }
        if (board.isWithinBounds(captureRight) && board.getTileAt(captureRight) instanceof OccupiedTile) {
            Piece rightPiece = ((OccupiedTile) board.getTileAt(captureRight)).getPiece();
            if (!rightPiece.getColor().equals(this.color)) {
                possibleMoves.add(captureRight);
            }
        }
    }

    /**
     * Déplace le pion vers une nouvelle position et gère les règles de Solo Chess.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize   La taille du plateau.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        super.move(newPosition, boardSize); // Utilise la logique définie dans la classe parente
    }

    /**
     * Vérifie si le pion peut être promu.
     *
     * @param board L'état actuel du plateau.
     * @return `true` si le pion peut être promu, sinon `false`.
     */
    public boolean canPromote(Board board) {
        int promotionRow = "Blanc".equals(this.color) ? board.getRowCount() - 1 : 0;
        return this.position.x == promotionRow;
    }

    /**
     * Retourne une description textuelle du pion.
     *
     * @return Une chaîne décrivant le pion.
     */
    @Override
    public String toString() {
        return "Pion (" + color + ") à " + position;
    }
}

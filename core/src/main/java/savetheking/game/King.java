package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un roi (King) dans le mode Solo Chess.
 * Le roi peut se déplacer d'une case dans toutes les directions.
 */
public class King extends Piece {

    /**
     * Constructeur pour initialiser un roi avec une couleur et une position.
     *
     * @param color    La couleur du roi ("Blanc").
     * @param position La position initiale du roi sur le plateau.
     */
    public King(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne les mouvements possibles pour le roi.
     * Le roi peut se déplacer d'une case dans toutes les directions.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions possibles pour le roi.
     */
    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] moveOffsets = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},  // Déplacements verticaux et horizontaux
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Déplacements diagonaux
        };

        // Vérifie les mouvements normaux d'une case
        for (int[] offset : moveOffsets) {
            int newX = position.x + offset[0];
            int newY = position.y + offset[1];
            Point newPoint = new Point(newX, newY);

            if (isWithinBounds(newPoint, board.getRowCount())) {
                Tile tile = board.getTileAt(newPoint);
                if (tile instanceof EmptyTile || (tile instanceof OccupiedTile &&
                    !((OccupiedTile) tile).getPiece().getColor().equals(this.color))) {
                    possibleMoves.add(newPoint);
                }
            }
        }

        // Si le roi est noir (après 2 mouvements), il ne peut plus se déplacer
        if ("Noir".equals(this.color)) {
            possibleMoves.clear();
        }

        return possibleMoves;
    }

    /**
     * Déplace le roi vers une nouvelle position et gère les règles de Solo Chess.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize   La taille du plateau.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        super.move(newPosition, boardSize); // Utilise la logique définie dans la classe parente
    }

    /**
     * Retourne une description textuelle du roi.
     *
     * @return Une chaîne décrivant le roi avec sa position.
     */
    @Override
    public String toString() {
        return "Roi (" + color + ") à " + position;
    }
}

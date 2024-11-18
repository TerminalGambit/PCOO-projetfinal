package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un fou (bishop) dans le jeu d'échecs.
 * Le fou peut se déplacer en diagonale dans toutes les directions
 * jusqu'à rencontrer un obstacle ou les limites du plateau.
 */
public class Bishop extends Piece {

    /**
     * Constructeur pour initialiser un fou avec une couleur et une position.
     *
     * @param color La couleur du fou ("Blanc" ou "Noir").
     * @param position La position initiale du fou sur le plateau.
     */
    public Bishop(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne les mouvements possibles du fou sur le plateau.
     * Le fou peut se déplacer en diagonale dans toutes les directions jusqu'à ce qu'il rencontre une pièce
     * ou sorte des limites du plateau.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions possibles pour le fou.
     */
    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] directions = {
            {1, 1},   // Bas-droite
            {1, -1},  // Bas-gauche
            {-1, 1},  // Haut-droite
            {-1, -1}  // Haut-gauche
        };

        for (int[] direction : directions) {
            int newX = position.x;
            int newY = position.y;

            while (true) {
                newX += direction[0];
                newY += direction[1];
                Point newPoint = new Point(newX, newY);

                // Vérifie si la nouvelle position est dans les limites du plateau
                if (!newPoint.isWithinBounds(board.getRowCount())) {
                    break;
                }

                Tile tile = board.getTileAt(newPoint);

                if (tile instanceof EmptyTile) {
                    possibleMoves.add(newPoint);
                } else if (tile instanceof OccupiedTile) {
                    Piece pieceOnTile = ((OccupiedTile) tile).getPiece();
                    if (pieceOnTile.getColor().equals(this.color)) {
                        break; // Arrête si la case est occupée par une pièce alliée
                    }
                    possibleMoves.add(newPoint); // Peut capturer une pièce ennemie
                    break;
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Retourne les cases défendues par le fou.
     * Par défaut, les cases défendues sont identiques aux mouvements possibles.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions défendues par le fou.
     */
    @Override
    public List<Point> getDefendedTiles(Board board) {
        return getPossibleMoves(board);
    }

    /**
     * Déplace le fou vers une nouvelle position et marque qu'il a été déplacé.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize La taille du plateau.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            this.hasMoved = true; // Marque que la pièce a été déplacée
        } else {
            throw new IllegalArgumentException("Position hors limites : " + newPosition);
        }
    }

    /**
     * Retourne une description textuelle du fou, incluant sa position.
     *
     * @return Une chaîne décrivant le fou.
     */
    @Override
    public String toString() {
        return "Fou à " + position;
    }
}

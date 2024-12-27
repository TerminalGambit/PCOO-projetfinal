package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un fou (Bishop) dans le mode Solo Chess.
 * Le fou peut se déplacer en diagonale dans toutes les directions jusqu'à rencontrer un obstacle.
 */
public class Bishop extends Piece {

    /**
     * Constructeur pour initialiser un fou avec une couleur et une position.
     *
     * @param color    La couleur du fou ("Blanc").
     * @param position La position initiale du fou sur le plateau.
     */
    public Bishop(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne les mouvements possibles pour le fou.
     * Le fou peut se déplacer en diagonale dans toutes les directions jusqu'à ce qu'il rencontre une pièce
     * ou sorte des limites du plateau.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions possibles.
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

                // Vérifie si la position est dans les limites
                if (!isWithinBounds(newPoint, board.getRowCount())) {
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

        // Si la pièce est noire (après 2 mouvements), elle ne peut plus se déplacer
        if ("Noir".equals(this.color)) {
            possibleMoves.clear();
        }

        return possibleMoves;
    }

    /**
     * Déplace le fou vers une nouvelle position et gère les règles de Solo Chess.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize   La taille du plateau.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        super.move(newPosition, boardSize);
    }

    /**
     * Retourne une description textuelle du fou.
     *
     * @return Une chaîne décrivant le fou.
     */
    @Override
    public String toString() {
        return "Fou (" + color + ") à " + position;
    }
}

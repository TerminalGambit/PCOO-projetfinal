package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une tour dans Solo Chess.
 * La tour peut se déplacer horizontalement ou verticalement sur n'importe quel nombre de cases,
 * tant qu'il n'y a pas d'obstacles.
 */
public class Rook extends Piece {
    private int moveCount = 0; // Nombre de mouvements effectués par la tour

    /**
     * Constructeur pour initialiser une tour avec une couleur et une position.
     *
     * @param color    La couleur de la tour ("Blanc" ou "Noir").
     * @param position La position initiale de la tour sur le plateau.
     */
    public Rook(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne une liste des mouvements possibles pour la tour.
     * La tour peut se déplacer horizontalement et verticalement, dans toutes les directions,
     * jusqu'à ce qu'elle rencontre un obstacle ou la limite du plateau.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions où la tour peut se déplacer.
     */
    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] directions = {
            {1, 0},  // droite
            {-1, 0}, // gauche
            {0, 1},  // haut
            {0, -1}  // bas
        };

        for (int[] direction : directions) {
            int newX = position.x;
            int newY = position.y;

            while (true) {
                newX += direction[0];
                newY += direction[1];
                Point newPoint = new Point(newX, newY);

                // Vérifie si la position est dans les limites du plateau
                if (!board.isWithinBounds(newPoint)) {
                    break;
                }

                Tile tile = board.getTileAt(newPoint);

                if (tile instanceof EmptyTile) {
                    possibleMoves.add(newPoint);
                } else if (tile instanceof OccupiedTile) {
                    // Peut capturer une pièce à la position
                    possibleMoves.add(newPoint);
                    break;
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Déplace la tour vers une nouvelle position et augmente son compteur de mouvements.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize   La taille du plateau.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            moveCount++; // Incrémente le compteur de mouvements
            if (moveCount >= 2) {
                this.color = "Noir"; // Change la couleur après deux mouvements
            }
        } else {
            throw new IllegalArgumentException("Position hors limites : " + newPosition);
        }
    }

    /**
     * Retourne le nombre de mouvements effectués par la tour.
     *
     * @return Le nombre de mouvements.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Retourne une représentation textuelle de la tour, incluant sa position et son statut.
     *
     * @return Une chaîne de caractères décrivant la tour.
     */
    @Override
    public String toString() {
        return "Tour (" + color + ") à " + position + ", mouvements : " + moveCount;
    }
}

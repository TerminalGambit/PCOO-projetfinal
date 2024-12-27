package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une tour (Rook) dans le mode Solo Chess.
 * La tour peut se déplacer horizontalement ou verticalement jusqu'à rencontrer un obstacle.
 */
public class Rook extends Piece {

    /**
     * Constructeur pour initialiser une tour avec une couleur et une position.
     *
     * @param color    La couleur de la tour ("Blanc").
     * @param position La position initiale de la tour sur le plateau.
     */
    public Rook(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne une liste des mouvements possibles pour la tour.
     * La tour peut se déplacer horizontalement et verticalement, dans toutes les directions,
     * jusqu'à ce qu'elle rencontre un obstacle ou les limites du plateau.
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

        // Si la pièce est noire (après 2 déplacements), elle ne peut plus se déplacer
        if ("Noir".equals(this.color)) {
            possibleMoves.clear();
        }

        return possibleMoves;
    }

    /**
     * Déplace la tour vers une nouvelle position et gère les règles de Solo Chess.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize   La taille du plateau.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        super.move(newPosition, boardSize); // Utilise la logique définie dans la classe parente
    }

    /**
     * Retourne une représentation textuelle de la tour, incluant sa position.
     *
     * @return Une chaîne de caractères décrivant la tour.
     */
    @Override
    public String toString() {
        return "Tour (" + color + ") à " + position;
    }
}

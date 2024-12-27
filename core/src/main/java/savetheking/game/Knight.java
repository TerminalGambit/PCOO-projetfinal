package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un cavalier (Knight) dans le mode Solo Chess.
 * Le cavalier peut se déplacer en "L" (deux cases dans une direction et une case perpendiculairement).
 */
public class Knight extends Piece {

    /**
     * Constructeur pour initialiser un cavalier avec une couleur et une position.
     *
     * @param color    La couleur du cavalier ("Blanc").
     * @param position La position initiale du cavalier sur l'échiquier.
     */
    public Knight(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne une liste des positions où le cavalier peut se déplacer,
     * en tenant compte des limites de l'échiquier et des règles de Solo Chess.
     *
     * @param board L'état actuel de l'échiquier.
     * @return Une liste de positions possibles pour le cavalier.
     */
    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] moveOffsets = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] offset : moveOffsets) {
            int newX = position.x + offset[0];
            int newY = position.y + offset[1];
            Point newPoint = new Point(newX, newY);

            if (isWithinBounds(newPoint, board.getRowCount())) {
                Tile tile = board.getTileAt(newPoint);
                // Vérifie si la case est vide ou contient une pièce ennemie
                if (tile instanceof EmptyTile || (tile instanceof OccupiedTile &&
                    !((OccupiedTile) tile).getPiece().getColor().equals(this.color))) {
                    possibleMoves.add(newPoint);
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
     * Déplace le cavalier vers une nouvelle position et gère les règles de Solo Chess.
     * Utilise la logique héritée de la classe parente `Piece`.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize   La taille du plateau.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        super.move(newPosition, boardSize);
    }

    /**
     * Retourne une représentation textuelle du cavalier.
     *
     * @return Une chaîne décrivant le cavalier avec sa position.
     */
    @Override
    public String toString() {
        return "Cavalier (" + color + ") à " + position;
    }
}

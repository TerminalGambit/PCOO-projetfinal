package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un cavalier dans le jeu d'échecs.
 * Le cavalier peut se déplacer en "L" (deux cases dans une direction et une case perpendiculairement).
 * Cette classe hérite de la classe abstraite Piece.
 */
public class Knight extends Piece {

    /**
     * Constructeur pour initialiser un cavalier avec une couleur et une position.
     *
     * @param color La couleur du cavalier ("Blanc" ou "Noir").
     * @param position La position initiale du cavalier sur l'échiquier.
     */
    public Knight(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne une liste des positions où le cavalier peut se déplacer,
     * en tenant compte des limites de l'échiquier.
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

            if (newPoint.isWithinBounds(board.getRowCount()) &&
                newPoint.isWithinBounds(board.getColumnCount())) {
                Tile tile = board.getTileAt(newPoint);
                // Vérifie si la case est vide ou contient une pièce ennemie
                if (tile instanceof EmptyTile || (tile instanceof OccupiedTile &&
                    !((OccupiedTile) tile).getPiece().getColor().equals(this.color))) {
                    possibleMoves.add(newPoint);
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Retourne une liste des cases défendues par le cavalier.
     * Les cases défendues correspondent aux mêmes cases que celles des mouvements possibles.
     *
     * @param board L'état actuel de l'échiquier.
     * @return Une liste de positions défendues par le cavalier.
     */
    @Override
    public List<Point> getDefendedTiles(Board board) {
        return getPossibleMoves(board);
    }

    /**
     * Déplace le cavalier vers une nouvelle position si elle est dans les limites du plateau.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize La taille du plateau (supposé carré).
     * @throws IllegalArgumentException Si la position est hors limites.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            this.hasMoved = true; // Marque la pièce comme ayant été déplacée
        } else {
            throw new IllegalArgumentException("Position hors limites : " + newPosition);
        }
    }

    /**
     * Retourne une représentation textuelle du cavalier.
     *
     * @return Une chaîne décrivant le cavalier avec sa position.
     */
    @Override
    public String toString() {
        return "Cavalier en " + position;
    }
}

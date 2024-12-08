package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une reine (Queen) dans le jeu d'échecs.
 * La reine peut se déplacer horizontalement, verticalement ou en diagonale
 * sur n'importe quelle distance jusqu'à rencontrer un obstacle ou les limites du plateau.
 */
public class Queen extends Piece {

    /**
     * Constructeur pour initialiser une reine avec une couleur et une position.
     *
     * @param color La couleur de la reine ("Blanc" ou "Noir").
     * @param position La position initiale de la reine sur le plateau.
     */
    public Queen(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne les mouvements possibles de la reine sur le plateau.
     * La reine combine les mouvements de la tour et du fou, pouvant se déplacer
     * dans les directions horizontales, verticales et diagonales.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions possibles pour la reine.
     */
    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] directions = {
            {1, 0},  // Droite
            {-1, 0}, // Gauche
            {0, 1},  // Haut
            {0, -1}, // Bas
            {1, 1},  // Bas-droite
            {1, -1}, // Bas-gauche
            {-1, 1}, // Haut-droite
            {-1, -1} // Haut-gauche
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
     * Retourne les cases défendues par la reine.
     * Par défaut, les cases défendues sont identiques aux mouvements possibles.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions défendues par la reine.
     */
    @Override
    public List<Point> getDefendedTiles(Board board) {
        return getPossibleMoves(board);
    }

    /**
     * Déplace la reine vers une nouvelle position et marque qu'elle a été déplacée.
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
     * Retourne une description textuelle de la reine, incluant sa position.
     *
     * @return Une chaîne décrivant la reine.
     */
    @Override
    public String toString() {
        return "Reine à " + position;
    }
}

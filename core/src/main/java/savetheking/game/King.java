package savetheking.game;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe King représente le roi dans une partie d'échecs.
 * Elle définit les mouvements spécifiques du roi, y compris les mouvements normaux
 * et le roque (si les conditions sont respectées).
 */
public class King extends Piece {
    private boolean hasMoved = false; // Indique si le roi a déjà été déplacé

    /**
     * Constructeur pour initialiser un roi avec une couleur et une position.
     *
     * @param color La couleur du roi ("Blanc" ou "Noir").
     * @param position La position initiale du roi sur le plateau.
     */
    public King(String color, Point position) {
        super(color, position);
    }

    /**
     * Retourne la liste des mouvements possibles pour le roi.
     *
     * @param board Le plateau de jeu sur lequel le roi évolue.
     * @return Une liste de points représentant les mouvements possibles.
     */
    @Override
    public List<Point> getPossibleMoves(Board board) {
        List<Point> possibleMoves = new ArrayList<Point>();
        int[][] moveOffsets = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},  // Mouvements verticaux et horizontaux
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Mouvements diagonaux
        };

        // Vérifie les mouvements normaux d'une case
        for (int[] offset : moveOffsets) {
            int newX = position.x + offset[0];
            int newY = position.y + offset[1];
            Point newPoint = new Point(newX, newY);

            if (newPoint.isWithinBounds(board.getRowCount()) && newPoint.isWithinBounds(board.getColumnCount())) {
                Tile tile = board.getTileAt(newPoint);
                if (tile instanceof EmptyTile ||
                    (tile instanceof OccupiedTile && !((OccupiedTile) tile).getPiece().getColor().equals(this.color))) {
                    possibleMoves.add(newPoint);
                }
            }
        }

        // Ajoute les mouvements de roque, si applicables
        addCastlingMoves(board, possibleMoves);

        return possibleMoves;
    }

    /**
     * Ajoute les mouvements de roque aux mouvements possibles du roi, si les conditions sont remplies.
     *
     * @param board Le plateau de jeu.
     * @param possibleMoves La liste des mouvements possibles à laquelle les mouvements de roque seront ajoutés.
     */
    private void addCastlingMoves(Board board, List<Point> possibleMoves) {
        if (!hasMoved) { // Le roi ne doit pas avoir été déplacé
            // Roque côté roi
            Rook kingsideRook = board.getPieceAt(new Point(position.x, board.getColumnCount() - 1)) instanceof Rook
                ? (Rook) board.getPieceAt(new Point(position.x, board.getColumnCount() - 1))
                : null;

            if (kingsideRook != null && !kingsideRook.hasMoved()
                && board.isPathClear(position, kingsideRook.getPosition())
                && !board.isPositionUnderAttack(new Point(position.x, position.y + 1), this.color)
                && !board.isPositionUnderAttack(new Point(position.x, position.y + 2), this.color)) {
                possibleMoves.add(new Point(position.x, position.y + 2));
            }

            // Roque côté dame
            Rook queensideRook = board.getPieceAt(new Point(position.x, 0)) instanceof Rook
                ? (Rook) board.getPieceAt(new Point(position.x, 0))
                : null;

            if (queensideRook != null && !queensideRook.hasMoved()
                && board.isPathClear(position, queensideRook.getPosition())
                && !board.isPositionUnderAttack(new Point(position.x, position.y - 1), this.color)
                && !board.isPositionUnderAttack(new Point(position.x, position.y - 2), this.color)) {
                possibleMoves.add(new Point(position.x, position.y - 2));
            }
        }
    }

    /**
     * Retourne la liste des cases défendues par le roi.
     * Les cases défendues sont les mêmes que les mouvements possibles du roi.
     *
     * @param board Le plateau de jeu.
     * @return Une liste de points représentant les cases défendues.
     */
    @Override
    public List<Point> getDefendedTiles(Board board) {
        return getPossibleMoves(board);
    }

    /**
     * Déplace le roi vers une nouvelle position et marque qu'il a été déplacé.
     *
     * @param newPosition La nouvelle position cible du roi.
     * @throws IllegalArgumentException Si la nouvelle position est hors des limites du plateau.
     */
    @Override
    public void move(Point newPosition, int boardSize) {
        super.move(newPosition, boardSize);
        this.hasMoved = true;
    }

    /**
     * Indique si le roi a déjà été déplacé.
     *
     * @return `true` si le roi a été déplacé, sinon `false`.
     */
    @Override
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Retourne une représentation textuelle du roi.
     *
     * @return Une chaîne de caractères décrivant le roi.
     */
    @Override
    public String toString() {
        return "King at " + position;
    }
}

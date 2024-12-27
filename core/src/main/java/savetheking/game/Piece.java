package savetheking.game;

import java.util.List;

/**
 * Classe abstraite représentant une pièce d’échecs pour le mode Solo Chess.
 * Chaque pièce a une couleur, une position et des comportements spécifiques définis par ses sous-classes.
 */
public abstract class Piece {
    protected String color; // La couleur de la pièce ("Blanc" ou "Noir")
    protected Point position; // La position actuelle de la pièce sur le plateau
    protected int moveCount = 0; // Nombre de déplacements effectués par la pièce

    /**
     * Constructeur de base pour initialiser une pièce avec une couleur et une position.
     *
     * @param color    La couleur de la pièce (ex. "Blanc").
     * @param position La position initiale de la pièce sur le plateau.
     */
    public Piece(String color, Point position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Retourne la couleur actuelle de la pièce.
     *
     * @return La couleur de la pièce ("Blanc" ou "Noir").
     */
    public String getColor() {
        return color;
    }

    /**
     * Retourne la position actuelle de la pièce.
     *
     * @return La position actuelle de la pièce sous forme de `Point`.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Déplace la pièce vers une nouvelle position et met à jour la couleur après deux mouvements.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize   La taille du plateau.
     * @throws IllegalArgumentException Si la nouvelle position est hors des limites du plateau.
     */
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            this.moveCount++;

            // Change color to "Noir" after the second move
            if (this.moveCount >= 2 && "Blanc".equals(this.color)) {
                this.color = "Noir";
            }
        } else {
            throw new IllegalArgumentException("Position hors limites : " + newPosition);
        }
    }

    /**
     * Retourne le nombre de déplacements effectués par la pièce.
     *
     * @return Le nombre de déplacements.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Méthode abstraite à implémenter par chaque sous-classe pour déterminer les mouvements possibles.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions possibles où la pièce peut se déplacer.
     */
    public abstract List<Point> getPossibleMoves(Board board);

    /**
     * Vérifie si une position est dans les limites du plateau.
     *
     * @param point     La position à vérifier.
     * @param boardSize La taille du plateau (supposée carrée).
     * @return `true` si la position est dans les limites du plateau, sinon `false`.
     */
    protected boolean isWithinBounds(Point point, int boardSize) {
        return point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize;
    }

    /**
     * Retourne une représentation textuelle de la pièce.
     *
     * @return Une chaîne décrivant la pièce.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (" + color + ")";
    }
}

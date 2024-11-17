package savetheking.game;

import java.util.List;

/**
 * Classe abstraite représentant une pièce d’échecs.
 * Chaque pièce a une couleur, une position et des comportements spécifiques définis par ses sous-classes.
 * Cette classe fournit des méthodes générales pour gérer les mouvements, vérifier les limites du plateau
 * et les relations avec les autres pièces.
 */
public abstract class Piece {
    protected String color; // La couleur de la pièce (Blanc ou Noir)
    protected Point position; // La position actuelle de la pièce sur le plateau
    protected boolean hasMoved = false; // Indique si la pièce a été déplacée au moins une fois

    /**
     * Constructeur de base pour initialiser une pièce avec une couleur et une position.
     *
     * @param color La couleur de la pièce (ex. "Blanc" ou "Noir").
     * @param position La position initiale de la pièce sur le plateau.
     */
    public Piece(String color, Point position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Retourne la couleur de la pièce.
     *
     * @return La couleur de la pièce (ex. "Blanc" ou "Noir").
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
     * Déplace la pièce vers une nouvelle position si elle est dans les limites du plateau.
     * Met également à jour l'état indiquant que la pièce a été déplacée.
     *
     * @param newPosition La nouvelle position cible.
     * @param boardSize La taille du plateau (un plateau carré est supposé ici).
     * @throws IllegalArgumentException Si la nouvelle position est hors des limites du plateau.
     */
    public void move(Point newPosition, int boardSize) {
        if (isWithinBounds(newPosition, boardSize)) {
            this.position = newPosition;
            this.hasMoved = true;
        } else {
            throw new IllegalArgumentException("Position hors limites : " + newPosition);
        }
    }

    /**
     * Vérifie si la pièce a été déplacée au moins une fois.
     *
     * @return `true` si la pièce a été déplacée, sinon `false`.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Vérifie si la pièce n'a jamais été déplacée.
     *
     * @return `true` si la pièce n'a pas encore été déplacée, sinon `false`.
     */
    public boolean hasNotMoved() {
        return !hasMoved;
    }

    /**
     * Méthode abstraite à implémenter par chaque sous-classe pour déterminer les mouvements possibles.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions possibles où la pièce peut se déplacer.
     */
    public abstract List<Point> getPossibleMoves(Board board);

    /**
     * Méthode abstraite à implémenter par chaque sous-classe pour déterminer les cases défendues.
     * Les cases défendues sont celles où la pièce peut se déplacer ou attaquer.
     *
     * @param board L'état actuel du plateau.
     * @return Une liste des positions défendues par la pièce.
     */
    public abstract List<Point> getDefendedTiles(Board board);

    /**
     * Vérifie si une position est dans les limites du plateau.
     *
     * @param point La position à vérifier.
     * @param boardSize La taille du plateau (supposée carrée).
     * @return `true` si la position est dans les limites du plateau, sinon `false`.
     */
    protected boolean isWithinBounds(Point point, int boardSize) {
        return point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize;
    }

    /**
     * Retourne une représentation textuelle de la pièce, incluant son type et sa couleur.
     *
     * @return Une chaîne décrivant la pièce.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (" + color + ")";
    }

    /**
     * Vérifie si une pièce alliée occupe une position donnée sur le plateau.
     *
     * @param point La position à vérifier.
     * @param board L'état actuel du plateau.
     * @return `true` si une pièce alliée est présente à la position donnée, sinon `false`.
     */
    protected boolean isAlly(Point point, Board board) {
        Piece piece = board.getPieceAt(point);
        return piece != null && piece.getColor().equals(this.color);
    }
}

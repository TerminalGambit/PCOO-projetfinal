package savetheking.game;

/**
 * La classe abstraite Tile représente une case sur l'échiquier.
 * Elle fournit des méthodes pour vérifier si la case est défendue ou occupée et gère sa position.
 */
public abstract class Tile {
    protected Point position; // Position de la case sur l'échiquier
    protected boolean isDefended; // Indique si la case est défendue par une pièce

    /**
     * Constructeur pour initialiser une case avec une position donnée.
     * Par défaut, une case n'est pas défendue.
     *
     * @param position La position de cette case sur l'échiquier.
     */
    public Tile(Point position) {
        this.position = position;
        this.isDefended = false; // Par défaut, la case n'est pas défendue
    }

    /**
     * Constructeur par défaut. Utilisé pour initialiser sans position.
     * Par défaut, une case n'est pas défendue.
     */
    public Tile() {
        this.isDefended = false;
    }

    /**
     * Vérifie si cette case est occupée par une pièce.
     * Une case est considérée occupée si elle est une instance de {@link OccupiedTile}.
     *
     * @return true si la case est occupée, sinon false.
     */
    public boolean isOccupied() {
        return this instanceof OccupiedTile;
    }

    /**
     * Récupère la position de cette case sur l'échiquier.
     *
     * @return La position de la case sous forme d'un objet {@link Point}.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Vérifie si cette case est défendue.
     *
     * @return true si la case est défendue, sinon false.
     */
    public boolean isDefended() {
        return isDefended;
    }

    /**
     * Définit si cette case est défendue ou non.
     *
     * @param defended Le nouvel état défendu de la case.
     */
    public void setDefended(boolean defended) {
        this.isDefended = defended;
    }

    /**
     * Méthode abstraite pour définir une pièce sur cette case.
     * À implémenter dans les sous-classes.
     *
     * @param piece La pièce à placer sur cette case.
     */
    public abstract void setPiece(Piece piece);

    /**
     * Méthode abstraite pour récupérer la pièce située sur cette case.
     * À implémenter dans les sous-classes.
     *
     * @return La pièce située sur cette case ou null si aucune pièce n'est présente.
     */
    public abstract Piece getPiece();

    /**
     * Méthode abstraite pour retirer une pièce de cette case.
     * À implémenter dans les sous-classes.
     */
    public abstract void removePiece();
}

package savetheking.game;

/**
 * La classe EmptyTile représente une case sur l'échiquier qui ne contient pas de pièce.
 * Elle redéfinit les méthodes de gestion des pièces pour indiquer qu'aucune pièce ne peut être définie directement.
 */
public class EmptyTile extends Tile {

    /**
     * Constructeur pour initialiser une case vide à une position spécifique.
     *
     * @param position La position de cette case sur l'échiquier.
     */
    public EmptyTile(Point position) {
        super(position);
    }

    /**
     * Lance une exception car aucune pièce ne peut être directement placée sur une case vide.
     * La gestion de l'ajout d'une pièce doit être effectuée via la classe Board,
     * qui convertira cette case en une instance de {@link OccupiedTile}.
     *
     * @param piece La pièce à placer (non applicable pour EmptyTile).
     */
    @Override
    public void setPiece(Piece piece) {
        throw new UnsupportedOperationException("Impossible de placer une pièce directement sur une case vide. Utilisez la classe Board pour gérer la conversion en OccupiedTile.");
    }

    /**
     * Retourne null car aucune pièce n'est présente sur une case vide.
     *
     * @return null, indiquant qu'aucune pièce n'est présente.
     */
    @Override
    public Piece getPiece() {
        return null;
    }

    /**
     * Ne fait rien, car il n'y a aucune pièce à retirer sur une case vide.
     */
    @Override
    public void removePiece() {
        // Aucun effet, car la case est déjà vide
    }
}

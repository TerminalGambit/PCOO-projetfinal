package savetheking.game;

/**
 * La classe OccupiedTile représente une case sur l'échiquier qui contient actuellement une pièce.
 * Elle permet de récupérer, définir ou retirer la pièce occupant cette case.
 */
public class OccupiedTile extends Tile {
    private Piece piece; // La pièce occupant actuellement cette case

    /**
     * Constructeur pour initialiser une case occupée avec une position et une pièce.
     *
     * @param position La position de cette case sur l'échiquier.
     * @param piece La pièce occupant cette case.
     */
    public OccupiedTile(Point position, Piece piece) {
        super(position);
        this.piece = piece;
    }

    /**
     * Définit une pièce sur cette case occupée.
     *
     * @param piece La pièce à placer sur cette case.
     */
    @Override
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Récupère la pièce occupant actuellement cette case.
     *
     * @return La pièce occupant cette case.
     */
    @Override
    public Piece getPiece() {
        return piece;
    }

    /**
     * Retire la pièce de cette case, la rendant effectivement vide.
     */
    @Override
    public void removePiece() {
        this.piece = null;
    }
}

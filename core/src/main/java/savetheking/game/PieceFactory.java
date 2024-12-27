package savetheking.game;

/**
 * Classe pour créer des pièces d'échecs en fonction de leur type, couleur et position.
 */
public class PieceFactory {

    /**
     * Crée une pièce d'échecs en fonction du type, de la couleur et de la position donnés.
     *
     * @param type     Le type de la pièce (par exemple, "king", "queen").
     * @param color    La couleur de la pièce ("Blanc" ou "Noir").
     * @param position La position initiale de la pièce sur le plateau.
     * @return Une instance de Piece correspondant au type spécifié.
     * @throws IllegalArgumentException Si le type est invalide ou si un paramètre est null.
     */
    public static Piece createPiece(String type, String color, Point position) {
        // Validation des paramètres
        if (type == null || color == null || position == null) {
            throw new IllegalArgumentException("Le type, la couleur et la position ne peuvent pas être null.");
        }

        // Création en fonction du type de pièce
        if (type.equalsIgnoreCase("king")) {
            return new King(color, position);
        } else if (type.equalsIgnoreCase("queen")) {
            return new Queen(color, position);
        } else if (type.equalsIgnoreCase("bishop")) {
            return new Bishop(color, position);
        } else if (type.equalsIgnoreCase("rook")) {
            return new Rook(color, position);
        } else if (type.equalsIgnoreCase("knight")) {
            return new Knight(color, position);
        } else {
            throw new IllegalArgumentException("Type de pièce invalide : " + type);
        }
    }
}

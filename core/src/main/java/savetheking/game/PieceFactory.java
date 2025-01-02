package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * A factory class to create chess pieces.
 */
public class PieceFactory {

    /**
     * Creates a new chess piece based on the given type and attributes.
     *
     * @param type     The type of the piece (e.g., "Queen", "King").
     * @param color    The color of the piece ("White" or "Black").
     * @param position The initial position of the piece.
     * @return The created chess piece.
     */
    public Piece createPiece(String type, String color, Point position) {
        // Use correct path for textures based on your directory structure
        String texturePath = "pieces/" + color.toLowerCase().charAt(0) + type.toLowerCase().charAt(0) + ".png";
        Texture texture = new Texture(texturePath);

        return switch (type) {
            case "Queen" -> new Queen(color, position, texture);
            case "Bishop" -> new Bishop(color, position, texture);
            case "Knight" -> new Knight(color, position, texture);
            case "Rook" -> new Rook(color, position, texture);
            case "Pawn" -> new Pawn(color, position, texture);
            case "King" -> new King(color, position, texture);
            default -> throw new IllegalArgumentException("Unknown piece type: " + type);
        };
    }
}

package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Factory class for creating chess pieces.
 */
public class PieceFactory {
    /**
     * Creates a Piece based on its type, color, and position, including its texture.
     *
     * @param type     The type of the piece (e.g., "Rook", "Knight").
     * @param color    The color of the piece ("White" or "Black").
     * @param position The position of the piece.
     * @return The created Piece object, or null if type is invalid.
     */
    public static Piece createPiece(String type, String color, Point position) {
        if (type == null || color == null || position == null) {
            throw new IllegalArgumentException("Type, color, and position cannot be null.");
        }

        // Load the appropriate texture based on piece type and color
        Texture texture = loadTexture(type, color);

        switch (type.toLowerCase()) {
            case "rook":
                return new Rook(color, position, texture);
            case "knight":
                return new Knight(color, position, texture);
            case "bishop":
                return new Bishop(color, position, texture);
            case "queen":
                return new Queen(color, position, texture);
            case "king":
                return new King(color, position, texture);
            case "pawn":
                return new Pawn(color, position, texture);
            default:
                throw new IllegalArgumentException("Invalid piece type: " + type);
        }
    }

    /**
     * Loads the appropriate texture for a piece based on its type and color.
     *
     * @param type  The type of the piece.
     * @param color The color of the piece.
     * @return The loaded Texture.
     */
    private static Texture loadTexture(String type, String color) {
        String texturePath = "pieces/" + color.toLowerCase().charAt(0) + type.toLowerCase().charAt(0) + ".png";
        return new Texture(texturePath);
    }
}

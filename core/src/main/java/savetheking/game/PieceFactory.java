package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Factory class for creating chess pieces.
 */
public class PieceFactory {
    private static final boolean DEBUG_MODE = true; // Set to true for debugging

    /**
     * Creates a Piece based on its type, color, and position, including its texture.
     *
     * @param type     The type of the piece (e.g., "Rook", "Knight").
     * @param color    The color of the piece ("White" or "Black").
     * @param position The position of the piece.
     * @return The created Piece object.
     */
    public static Piece createPiece(String type, String color, Point position) {
        if (DEBUG_MODE) {
            System.out.println("Attempting to create piece:");
            System.out.println("Type: " + type + ", Color: " + color + ", Position: " + position);
        }

        if (type == null || color == null || position == null) {
            throw new IllegalArgumentException("Type, color, and position cannot be null.");
        }

        // Load the appropriate texture based on piece type and color
        Texture texture = loadTexture(type, color);

        // Use if-else to determine the type of piece
        if ("rook".equalsIgnoreCase(type)) {
            if (DEBUG_MODE) System.out.println("Creating a Rook...");
            return new Rook(color, position, texture);
        } else if ("knight".equalsIgnoreCase(type)) {
            if (DEBUG_MODE) System.out.println("Creating a Knight...");
            return new Knight(color, position, texture);
        } else if ("bishop".equalsIgnoreCase(type)) {
            if (DEBUG_MODE) System.out.println("Creating a Bishop...");
            return new Bishop(color, position, texture);
        } else if ("queen".equalsIgnoreCase(type)) {
            if (DEBUG_MODE) System.out.println("Creating a Queen...");
            return new Queen(color, position, texture);
        } else if ("king".equalsIgnoreCase(type)) {
            if (DEBUG_MODE) System.out.println("Creating a King...");
            return new King(color, position, texture);
        } else if ("pawn".equalsIgnoreCase(type)) {
            if (DEBUG_MODE) System.out.println("Creating a Pawn...");
            return new Pawn(color, position, texture);
        } else {
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
        String typeAbbreviation = getTypeAbbreviation(type);
        String texturePath = "pieces/" + color.toLowerCase().charAt(0) + typeAbbreviation + ".png";

        if (DEBUG_MODE) {
            System.out.println("Loading texture for piece:");
            System.out.println("Type Abbreviation: " + typeAbbreviation + ", Texture Path: " + texturePath);
        }

        return new Texture(texturePath);
    }

    /**
     * Maps the full type name to its corresponding abbreviation.
     *
     * @param type The full type name.
     * @return The abbreviation for the type.
     */
    private static String getTypeAbbreviation(String type) {
        if ("knight".equalsIgnoreCase(type)) {
            return "n"; // Use 'n' for knight
        } else if ("king".equalsIgnoreCase(type)) {
            return "k"; // Use 'k' for king
        } else if ("rook".equalsIgnoreCase(type)) {
            return "r";
        } else if ("bishop".equalsIgnoreCase(type)) {
            return "b";
        } else if ("queen".equalsIgnoreCase(type)) {
            return "q";
        } else if ("pawn".equalsIgnoreCase(type)) {
            return "p";
        } else {
            throw new IllegalArgumentException("Invalid piece type: " + type);
        }
    }
}

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
     * @param position The position of the piece (custom Point class).
     * @return The created Piece object.
     */
    public static Piece createPiece(String type, String color, Point position) {
        if (DEBUG_MODE) {
            System.out.println("[DEBUG] PieceFactory.createPiece called:");
            System.out.println("        Type: " + type + ", Color: " + color + ", Position: " + position);
        }

        if (type == null || color == null || position == null) {
            System.err.println("[ERROR] One or more parameters are null: Type=" + type + ", Color=" + color + ", Position=" + position);
            throw new IllegalArgumentException("Type, color, and position cannot be null.");
        }

        // Load the appropriate texture based on piece type and color
        Texture texture = loadTexture(type, color);

        if (texture == null) {
            System.err.println("[ERROR] Failed to load texture for " + type + " (" + color + ")");
            throw new IllegalArgumentException("Texture is null. Ensure that the texture files exist and are correctly defined in the assets.");
        }

        if (DEBUG_MODE) {
            System.out.println("[DEBUG] Texture loaded successfully for " + type + " (" + color + ")");
        }

        // Create specific pieces
        Piece piece;
        if ("rook".equalsIgnoreCase(type)) {
            piece = new Rook(color, position, texture);
        } else if ("knight".equalsIgnoreCase(type)) {
            piece = new Knight(color, position, texture);
        } else if ("bishop".equalsIgnoreCase(type)) {
            piece = new Bishop(color, position, texture);
        } else if ("queen".equalsIgnoreCase(type)) {
            piece = new Queen(color, position, texture);
        } else if ("king".equalsIgnoreCase(type)) {
            piece = new King(color, position, texture);
        } else if ("pawn".equalsIgnoreCase(type)) {
            piece = new Pawn(color, position, texture);
        } else {
            System.err.println("[ERROR] Invalid piece type: " + type);
            throw new IllegalArgumentException("Invalid piece type: " + type);
        }

        if (DEBUG_MODE) {
            System.out.println("[DEBUG] Piece created successfully: " + piece);
        }

        return piece;
    }

    /**
     * Loads the appropriate texture for a piece based on its type and color.
     *
     * @param type  The type of the piece.
     * @param color The color of the piece.
     * @return The loaded Texture or null if loading fails.
     */
    private static Texture loadTexture(String type, String color) {
        String typeAbbreviation = getTypeAbbreviation(type);
        String texturePath = "pieces/" + color.toLowerCase().charAt(0) + typeAbbreviation + ".png";

        if (DEBUG_MODE) {
            System.out.println("[DEBUG] Attempting to load texture from path: " + texturePath);
        }

        try {
            Texture texture = new Texture(texturePath);
            if (DEBUG_MODE) {
                System.out.println("[DEBUG] Successfully loaded texture from path: " + texturePath);
            }
            return texture;
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load texture from path: " + texturePath);
            e.printStackTrace();
            return null;
        }
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
            System.err.println("[ERROR] Invalid piece type abbreviation: " + type);
            throw new IllegalArgumentException("Invalid piece type: " + type);
        }
    }
}

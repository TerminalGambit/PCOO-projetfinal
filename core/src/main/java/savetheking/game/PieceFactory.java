package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Factory class for creating chess pieces.
 */
public class PieceFactory {
    private static final boolean DEBUG_MODE = true; // Set to true for debugging
    private final Renderer renderer; // Reference to the Renderer

    /**
     * Constructor for PieceFactory.
     *
     * @param renderer The Renderer instance to be used for rendering pieces.
     */
    public PieceFactory(Renderer renderer) {
        if (renderer == null) {
            throw new IllegalArgumentException("Renderer cannot be null.");
        }
        this.renderer = renderer;
    }

    /**
     * Creates a Piece based on its type, color, and position, including its texture.
     *
     * @param type     The type of the piece (e.g., "Rook", "Knight").
     * @param color    The color of the piece ("White" or "Black").
     * @param position The position of the piece.
     * @return The created Piece object.
     */
    public Piece createPiece(String type, String color, Point position) {
        if (DEBUG_MODE) {
            System.out.println("PieceFactory.createPiece called:");
            System.out.println("Type: " + type + ", Color: " + color + ", Position: " + position);
        }

        if (type == null || color == null || position == null) {
            throw new IllegalArgumentException("Type, color, and position cannot be null.");
        }

        // Load the appropriate texture based on piece type and color
        Texture texture = loadTexture(type, color);

        if (texture == null) {
            throw new IllegalArgumentException("Failed to load texture for " + type + " (" + color + ")");
        }

        if (DEBUG_MODE) {
            System.out.println("Texture loaded for " + type + " (" + color + ")");
        }

        // Create the piece
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
            throw new IllegalArgumentException("Invalid piece type: " + type);
        }

        // Notify the renderer about the new piece
        renderer.notifyNewPiece(piece);

        return piece;
    }

    /**
     * Loads the appropriate texture for a piece based on its type and color.
     *
     * @param type  The type of the piece.
     * @param color The color of the piece.
     * @return The loaded Texture.
     */
    private Texture loadTexture(String type, String color) {
        String typeAbbreviation = getTypeAbbreviation(type);
        String texturePath = "pieces/" + color.toLowerCase().charAt(0) + typeAbbreviation + ".png";
        System.out.println("Loading texture from path: " + texturePath);
        try {
            return new Texture(texturePath);
        } catch (Exception e) {
            System.err.println("Failed to load texture: " + e.getMessage());
            return null;
        }
    }

    /**
     * Maps the full type name to its corresponding abbreviation.
     *
     * @param type The full type name.
     * @return The abbreviation for the type.
     */
    private String getTypeAbbreviation(String type) {
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

package savetheking.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Factory class responsible for creating chess pieces dynamically.
 */
public class PieceFactory {

    /**
     * Creates a chess piece based on the given parameters.
     *
     * @param type     The type of the piece (e.g., "Queen", "Rook").
     * @param color    The color of the piece (e.g., "White", "Black").
     * @param position The initial position of the piece on the board.
     * @param texture  The texture representing the piece's image.
     * @return The created chess piece.
     */
    public Piece createPiece(String type, String color, Point position, Texture texture) {
        if (type == null || color == null || position == null) {
            throw new IllegalArgumentException("Piece parameters cannot be null.");
        }

        switch (type.toLowerCase()) {
            case "queen":
                return new Queen(color, position, texture);
            case "rook":
                return new Rook(color, position, texture);
            case "bishop":
                return new Bishop(color, position, texture);
            case "knight":
                return new Knight(color, position, texture);
            case "king":
                return new King(color, position, texture);
            case "pawn":
                return new Pawn(color, position, texture);
            default:
                throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }
}

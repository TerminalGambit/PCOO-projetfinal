package savetheking.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx; // Import Gdx for accessing delta time and other utilities
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The Main class serves as the entry point for the game.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Board board;
    private Renderer renderer;
    private Controller controller;
    private PlayingState playingState;

    @Override
    public void create() {
        int tileSize = 64; // Define the size of each tile
        board = new Board(8); // Create an 8x8 board
        batch = new SpriteBatch();
        renderer = new Renderer(board, tileSize); // Pass both the board and tileSize to the Renderer
        controller = new Controller(board);
        playingState = new PlayingState(board, controller, renderer); // Updated to match PlayingState constructor

        // Place initial pieces on the board
        PieceFactory pieceFactory = new PieceFactory();
        board.placePiece(pieceFactory.createPiece("Rook", "White", new Point(0, 0)), new Point(0, 0));
        board.placePiece(pieceFactory.createPiece("Knight", "White", new Point(0, 1)), new Point(0, 1));
        board.placePiece(pieceFactory.createPiece("Bishop", "White", new Point(0, 2)), new Point(0, 2));
        board.placePiece(pieceFactory.createPiece("Queen", "White", new Point(0, 3)), new Point(0, 3));
        board.placePiece(pieceFactory.createPiece("King", "White", new Point(0, 4)), new Point(0, 4));
        board.placePiece(pieceFactory.createPiece("Bishop", "White", new Point(0, 5)), new Point(0, 5));
        board.placePiece(pieceFactory.createPiece("Knight", "White", new Point(0, 6)), new Point(0, 6));
        board.placePiece(pieceFactory.createPiece("Rook", "White", new Point(0, 7)), new Point(0, 7));

        for (int i = 0; i < 8; i++) {
            board.placePiece(pieceFactory.createPiece("Pawn", "White", new Point(1, i)), new Point(1, i));
            board.placePiece(pieceFactory.createPiece("Pawn", "Black", new Point(6, i)), new Point(6, i));
        }

        board.placePiece(pieceFactory.createPiece("Rook", "Black", new Point(7, 0)), new Point(7, 0));
        board.placePiece(pieceFactory.createPiece("Knight", "Black", new Point(7, 1)), new Point(7, 1));
        board.placePiece(pieceFactory.createPiece("Bishop", "Black", new Point(7, 2)), new Point(7, 2));
        board.placePiece(pieceFactory.createPiece("Queen", "Black", new Point(7, 3)), new Point(7, 3));
        board.placePiece(pieceFactory.createPiece("King", "Black", new Point(7, 4)), new Point(7, 4));
        board.placePiece(pieceFactory.createPiece("Bishop", "Black", new Point(7, 5)), new Point(7, 5));
        board.placePiece(pieceFactory.createPiece("Knight", "Black", new Point(7, 6)), new Point(7, 6));
        board.placePiece(pieceFactory.createPiece("Rook", "Black", new Point(7, 7)), new Point(7, 7));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with a black background

        float deltaTime = Gdx.graphics.getDeltaTime(); // Fix for 'Cannot resolve symbol Gdx'
        playingState.update(deltaTime); // Update the current state
        playingState.render(batch); // Render the current state
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderer.dispose();
    }
}

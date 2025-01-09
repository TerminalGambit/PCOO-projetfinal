package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Renderer {
    private final Board board;
    private final int tileSize;
    private final SpriteBatch batch;
    private final Texture darkSquareTexture;
    private final Texture lightSquareTexture;
    private final OrthographicCamera camera;
    private final boolean boardDebugMode;
    private final boolean pieceDebugMode;

    public Renderer(Board board, int tileSize) {
        this.board = board;
        this.tileSize = tileSize;
        this.batch = new SpriteBatch();
        this.darkSquareTexture = new Texture("dark-green.png");
        this.lightSquareTexture = new Texture("light-white.png");
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, board.getColumnCount() * tileSize, board.getRowCount() * tileSize);
        this.boardDebugMode = false;
        this.pieceDebugMode = true;
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        renderBoardLayer();
        renderPieces();

        batch.end();
    }

    private void renderBoardLayer() {
        for (int row = 0; row < board.getRowCount(); row++) {
            for (int col = 0; col < board.getColumnCount(); col++) {
                boolean isDarkSquare = (row + col) % 2 == 1;
                Texture texture = isDarkSquare ? darkSquareTexture : lightSquareTexture;
                int screenX = col * tileSize;
                int screenY = (board.getRowCount() - row - 1) * tileSize;

                if (boardDebugMode) {
                    System.out.println("Rendering tile at grid (" + row + ", " + col +
                        "), screen (" + screenX + ", " + screenY + "), DarkSquare: " + isDarkSquare);
                }

                batch.draw(texture, screenX, screenY, tileSize, tileSize);
            }
        }
    }

    private void renderPieces() {
        for (Piece piece : board.getPieces()) {
            int screenX = (int) (piece.getPosition().getY() * tileSize);
            int screenY = (int) ((board.getRowCount() - piece.getPosition().getX() - 1) * tileSize);

            batch.draw(piece.getTexture(), screenX, screenY, tileSize, tileSize);

            if (pieceDebugMode) {
                System.out.println("Rendering piece: " + piece + " at grid (" + piece.getPosition().getX() + ", " + piece.getPosition().getY() +
                    "), screen (" + screenX + ", " + screenY + ")");
            }
        }
    }

    public void dispose() {
        batch.dispose();
        darkSquareTexture.dispose();
        lightSquareTexture.dispose();
    }
}

package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {
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

        try {
            System.out.println("Starting batch...");
            batch.begin();
            board.render(batch);
        } catch (Exception e) {
            System.err.println("Error during rendering: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (batch.isDrawing()) {
                System.out.println("Ending batch...");
                batch.end();
            }
        }
    }

    public void renderPiece(Piece piece) {
        Texture texture = piece.getTexture();
        if (texture != null) {
            int screenX = piece.getPosition().y * tileSize;
            int screenY = (board.getRowCount() - piece.getPosition().x - 1) * tileSize;
            System.out.println("Rendering piece: " + piece + " at (" + screenX + ", " + screenY + ")");
            batch.draw(texture, screenX, screenY, tileSize, tileSize);

            if (pieceDebugMode) {
                System.out.println("Rendered piece: " + piece + " with texture: " + texture);
            }
        } else {
            System.err.println("Texture is null for piece: " + piece);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        darkSquareTexture.dispose();
        lightSquareTexture.dispose();
    }
}

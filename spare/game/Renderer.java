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

        // Debug: Logging initialization
        System.out.println("[DEBUG] Initializing Renderer...");

        this.batch = new SpriteBatch();
        if (this.batch == null) {
            throw new IllegalStateException("[ERROR] SpriteBatch failed to initialize.");
        } else {
            System.out.println("[DEBUG] SpriteBatch initialized successfully.");
        }

        this.darkSquareTexture = loadTexture("dark-green.png");
        this.lightSquareTexture = loadTexture("light-white.png");

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, board.getColumnCount() * tileSize, board.getRowCount() * tileSize);

        this.boardDebugMode = false; // Adjust debug mode as needed
        this.pieceDebugMode = true;

        System.out.println("[DEBUG] Renderer initialized with tileSize: " + tileSize);
    }

    private Texture loadTexture(String path) {
        try {
            System.out.println("[DEBUG] Loading texture from path: " + path);
            Texture texture = new Texture(path);
            System.out.println("[DEBUG] Successfully loaded texture: " + path);
            return texture;
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load texture: " + path + ". Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        try {
            System.out.println("[DEBUG] Starting rendering batch...");
            batch.begin();

            if (darkSquareTexture == null || lightSquareTexture == null) {
                System.err.println("[ERROR] One or more square textures are null. Rendering may fail.");
            }

            System.out.println("[DEBUG] Rendering board...");
            board.render(batch);
            System.out.println("[DEBUG] Board rendered successfully.");
        } catch (Exception e) {
            System.err.println("[ERROR] Exception during rendering: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (batch.isDrawing()) {
                System.out.println("[DEBUG] Ending rendering batch...");
                batch.end();
            } else {
                System.err.println("[ERROR] Rendering batch was not drawing when ending. This may indicate an issue.");
            }
        }
    }

    public void renderPiece(Piece piece) {
        if (piece == null) {
            System.err.println("[ERROR] Attempted to render a null piece. Skipping...");
            return;
        }

        Texture texture = piece.getTexture();
        if (texture != null) {
            int screenX = piece.getPosition().y * tileSize;
            int screenY = (board.getRowCount() - piece.getPosition().x - 1) * tileSize;

            System.out.println("[DEBUG] Rendering piece: " + piece + " at (" + screenX + ", " + screenY + ")");
            try {
                batch.draw(texture, screenX, screenY, tileSize, tileSize);
                if (pieceDebugMode) {
                    System.out.println("[DEBUG] Rendered piece: " + piece + " with texture: " + texture);
                }
            } catch (Exception e) {
                System.err.println("[ERROR] Exception during rendering piece: " + piece + ". Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("[ERROR] Texture is null for piece: " + piece);
        }
    }

    @Override
    public void dispose() {
        System.out.println("[DEBUG] Disposing Renderer resources...");
        if (batch != null) {
            batch.dispose();
            System.out.println("[DEBUG] SpriteBatch disposed.");
        }
        if (darkSquareTexture != null) {
            darkSquareTexture.dispose();
            System.out.println("[DEBUG] Dark square texture disposed.");
        }
        if (lightSquareTexture != null) {
            lightSquareTexture.dispose();
            System.out.println("[DEBUG] Light square texture disposed.");
        }
    }
}

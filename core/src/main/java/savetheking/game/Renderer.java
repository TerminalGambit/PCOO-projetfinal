
package savetheking.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Renderer class is responsible for drawing the game board and pieces on the screen.
 */
public class Renderer {
    private final TiledMap tiledMap;
    private final int tileSize;
    private final SpriteBatch batch;
    private final BitmapFont debugFont;

    /**
     * Constructs a Renderer with the specified tiled map and tile size.
     *
     * @param tiledMap The game map to render.
     * @param tileSize The size of each tile in pixels.
     */
    public Renderer(TiledMap tiledMap, int tileSize) {
        this.tiledMap = tiledMap;
        this.tileSize = tileSize;
        this.batch = new SpriteBatch();
        this.debugFont = new BitmapFont(); // For debug purposes
    }

    /**
     * Renders the board layer.
     */
    public void renderBoardLayer() {
        batch.begin();

        TiledLayer boardLayer = null;

        // Retrieve the board layer
        for (TiledLayer layer : tiledMap.getLayers()) {
            if ("Board Layer".equalsIgnoreCase(layer.getLayerProperty("name"))) {
                boardLayer = layer;
                break;
            }
        }

        if (boardLayer == null) {
            System.out.println("Board Layer not found!");
            batch.end();
            return;
        }

        // Debug: Confirm board layer is being rendered
        System.out.println("Rendering Board Layer...");

        // Iterate through tiles and render them
        for (int x = 0; x < boardLayer.getWidth(); x++) {
            for (int y = 0; y < boardLayer.getHeight(); y++) {
                Tile tile = boardLayer.getTile(x, y);
                if (tile != null) {
                    Texture texture = new Texture("default-tile.png"); // Replace with actual tile texture path
                    int screenX = x * tileSize;
                    int screenY = y * tileSize;

                    // Debug: Output tile rendering info
                    System.out.println("Rendering tile at (" + x + ", " + y + ") to screen (" + screenX + ", " + screenY + ").");

                    // Render the tile using its method
                    tile.render(texture, batch);
                }
            }
        }

        batch.end();
    }
}

package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Renderer implements Observer {
    private Board board;

    public Renderer(Board board) {
        this.board = board;
    }

    @Override
    public void update() {
        // Called when the board or game state changes
        System.out.println("Renderer notified of changes.");
    }

    public void render(SpriteBatch batch) {
        // Use LibGDX to render the board and pieces
        System.out.println("Rendering the board...");
    }
}

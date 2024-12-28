package savetheking.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlayingState implements GameStateInterface {
    private final Board board;
    private final Renderer renderer;
    private final Controller controller;

    public PlayingState(Board board, Renderer renderer, Controller controller) {
        this.board = board;
        this.renderer = renderer;
        this.controller = controller;
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        controller.update(deltaTime);
        board.notifyObservers();
    }

    @Override
    public void render(SpriteBatch batch) {
        renderer.render(batch);
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            Vector2 boardCoordinates = screenToBoardCoordinates(mouseX, mouseY);

            if (boardCoordinates != null) {
                int row = (int) boardCoordinates.x;
                int col = (int) boardCoordinates.y;
                controller.handleInput(new Point(row, col));
            }
        }
    }

    private Vector2 screenToBoardCoordinates(int screenX, int screenY) {
        int tileSize = 64;
        int boardWidth = board.getRowCount() * tileSize;
        int boardHeight = board.getColumnCount() * tileSize;

        screenY = Gdx.graphics.getHeight() - screenY;

        if (screenX < 0 || screenX >= boardWidth || screenY < 0 || screenY >= boardHeight) {
            return null;
        }

        int row = screenY / tileSize;
        int col = screenX / tileSize;

        return new Vector2(row, col);
    }
}

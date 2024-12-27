package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PausedState implements GameStateInterface {
    @Override
    public void update(float deltaTime) {
        System.out.println("Updating PausedState...");
    }

    @Override
    public void render(SpriteBatch batch) {
        System.out.println("Rendering PausedState...");
    }
}

package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverState implements GameStateInterface {
    @Override
    public void update(float deltaTime) {
        System.out.println("Updating GameOverState...");
    }

    @Override
    public void render() {
        System.out.println("Rendering GameOverState...");
    }
}

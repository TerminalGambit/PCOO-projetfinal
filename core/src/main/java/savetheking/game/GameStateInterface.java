package savetheking.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameStateInterface {
    void update(float deltaTime);
    void render(SpriteBatch batch);
}

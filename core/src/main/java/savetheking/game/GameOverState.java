package savetheking.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class GameOverState implements GameStateInterface {
    private final Stage stage;
    private final Texture backgroundTexture;

    public GameOverState() {
        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("light-white.png"));
        createGameOverUI();
    }

    private void createGameOverUI() {
        // Add background image
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Create a table for UI elements
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Add "Game Over" message
        Label gameOverMessage = new Label("Game Over! The king is safe.", new Label.LabelStyle());
        gameOverMessage.setFontScale(2);
        table.add(gameOverMessage).pad(20).row();

        // Add "Reset Game" button
        TextButton resetButton = new TextButton("Reset Game", new TextButton.TextButtonStyle());
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exiting the game...");
                Gdx.app.exit();
            }
        });
        table.add(resetButton).pad(20);
    }

    @Override
    public void update(float deltaTime) {
        // No updates needed in Game Over State
    }

    //@Override
    public void render(SpriteBatch batch) {
        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);

        // Update and draw the stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    //@Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();
    }
}

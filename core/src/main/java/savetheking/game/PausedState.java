package savetheking.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Represents the paused state of the game.
 * Displays a menu with options to resume the game or quit.
 */
public class PausedState implements GameStateInterface {
    private Stage stage;
    private Texture backgroundTexture;
    private boolean isPaused;

    public PausedState() {
        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture(Gdx.files.internal("light-white.png"));
        isPaused = true; // Game is paused by default when entering this state
        createPauseMenu();
    }

    private void createPauseMenu() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create a centered window for the pause menu
        Window pauseWindow = new Window("Game Paused", skin);
        pauseWindow.setMovable(false);

        // Add a label with pause information
        Label pauseLabel = new Label("The game is currently paused.", skin);
        pauseWindow.add(pauseLabel).pad(10).row();

        // Add a "Resume" button
        TextButton resumeButton = new TextButton("Resume Game", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false; // Resume the game
                Gdx.input.setInputProcessor(null); // Clear input processor
            }
        });
        pauseWindow.add(resumeButton).pad(10).row();

        // Add a "Quit" button
        TextButton quitButton = new TextButton("Quit Game", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Quitting game...");
                Gdx.app.exit();
            }
        });
        pauseWindow.add(quitButton).pad(10);

        pauseWindow.pack();
        pauseWindow.setPosition(
            (Gdx.graphics.getWidth() - pauseWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - pauseWindow.getHeight()) / 2
        );

        stage.addActor(pauseWindow);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void enterState() {
        System.out.println("PausedState: Entering paused state...");
        isPaused = true;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float deltaTime) {
        if (isPaused) {
            // No game logic is updated in this state
        }
    }

    @Override
    public void render() {

    }

    //@Override
    public void render(SpriteBatch batch) {
        if (isPaused) {
            batch.begin();
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();

            stage.act();
            stage.draw();
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }
}

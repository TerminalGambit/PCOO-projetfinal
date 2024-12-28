package savetheking.game.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import savetheking.game.Main;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {

    public static void main(String[] args) {
        // Handles JVM setup for macOS and Windows, if needed
        if (StartupHelper.startNewJvmIfRequired()) return;

        // Launch the application with the configured settings
        createApplication();
    }

    /**
     * Creates and starts the application with the specified configuration.
     *
     * @return A new Lwjgl3Application instance.
     */
    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    /**
     * Sets the default configuration for the LWJGL3 application.
     *
     * @return A configured Lwjgl3ApplicationConfiguration instance.
     */
    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();

        // Application title
        configuration.setTitle("Save The King");

        // Enable VSync for smoother rendering and reduced tearing
        configuration.useVsync(true);

        // Set FPS limit to match monitor refresh rate
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);

        // Window dimensions
        configuration.setWindowedMode(800, 600);

        // Window icons (add paths to your custom icons)
        configuration.setWindowIcon(
            "libgdx128.png",
            "libgdx64.png",
            "libgdx32.png",
            "libgdx16.png"
        );

        return configuration;
    }
}

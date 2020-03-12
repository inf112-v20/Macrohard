package inf112.skeleton.app;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.cards.Deck;

public class Main extends InputAdapter {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRallyApplication";
        cfg.width = 1200;
        cfg.height = 840;

        new LwjglApplication(new RoboRallyApplication(), cfg);

    }
}
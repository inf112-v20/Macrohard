package inf112.skeleton.app;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main extends InputAdapter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "hello-world";
        cfg.width = 600;
        cfg.height = 600;

        new LwjglApplication(new BoardGraphic(), cfg);
    }
}
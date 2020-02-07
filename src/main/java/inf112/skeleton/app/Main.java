package inf112.skeleton.app;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.Scanner;

public class Main extends InputAdapter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "robo-rally";
        cfg.width = 900;
        cfg.height = 900;

        Player player = new Player(6, 6);
        new LwjglApplication(new BoardGraphic(player, new Board (player,12,12)), cfg);
    }
}
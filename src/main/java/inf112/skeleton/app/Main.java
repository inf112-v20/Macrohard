package inf112.skeleton.app;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inf112.skeleton.app.cards.Deck;

public class Main extends InputAdapter {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = 900;
        cfg.height = 900;

        new LwjglApplication(new RoboRally(), cfg);

        Deck deck = new Deck();
        System.out.println(deck.toString());
        deck.shuffle();
        System.out.println(deck.toString());

        Player player = new Player(0,0,Direction.NORTH);
        deck.dealHand(player);
        System.out.println(player.getHand());
        System.out.println(deck.getDeckSize());

        player.setHealthPoints(5);
        deck.dealHand(player);
        System.out.println(player.getHand());
        System.out.println(deck.getDeckSize());
    }
}
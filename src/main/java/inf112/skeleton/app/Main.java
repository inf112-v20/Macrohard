package inf112.skeleton.app;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.util.Scanner;

public class Main extends InputAdapter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "robo-rally";
        cfg.width = 600;
        cfg.height = 600;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of players: ");
        int numberOfPlayers = scanner.nextInt();

        Player[] players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            int row, column;
            System.out.println("Player " + (i + 1) + ", on which row do you want to place your piece? ");
            row = scanner.nextInt();
            System.out.println("On which column do you want to place your piece? ");
            column = scanner.nextInt();
            players[i] = new Player(row, column);
        }


        new LwjglApplication(new BoardGraphic(players), cfg);
    }
}
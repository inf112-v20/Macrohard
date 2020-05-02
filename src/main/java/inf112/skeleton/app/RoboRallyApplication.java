package inf112.skeleton.app;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.preferences.AppPreferences;
import inf112.skeleton.app.screens.*;

import java.awt.*;


public class RoboRallyApplication extends Game {

    public static int NUMBER_OF_PLAYERS = 0;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private PreferenceScreen preferenceScreen;
    private CreateGameScreen createGameScreen;

    private AppPreferences appPreferences;

    public final static int MAIN_MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int CREATE = 3;
    public static Music music;
    public static String currentSong;

    private boolean hasGame = false;
    public static int screenWidth, screenHeight;

    @Override
    public void create() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        initializeMusic();
        LoadingScreen loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
        appPreferences = new AppPreferences();
    }

    public static Skin getSkin() {
        return new Skin(Gdx.files.internal("assets/skins/commodore64/uiskin.json"));
    }

    private void initializeMusic() {
        currentSong = "Factory Swing";
        music = Gdx.audio.newMusic(Gdx.files.internal("data/Music/FactorySwing.wav"));
        music.setLooping(true);
        music.play();
    }

    public AppPreferences getPreferences() {
        return appPreferences;
    }

    public void changeScreen(int screen) {
        switch (screen) {
            case MAIN_MENU:
                menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                menuScreen.setAsInputProcessor();
                break;
            case PREFERENCES:
                if (preferenceScreen == null) {
                    preferenceScreen = new PreferenceScreen(this);
                }
                this.setScreen(preferenceScreen);
                preferenceScreen.setAsInputProcessor();
                break;
            case CREATE:
                createGameScreen = new CreateGameScreen(this);
                this.setScreen(createGameScreen);
                createGameScreen.setAsInputProcessor();

            default:
                break;
        }
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    public boolean hasGame() {
        return hasGame;
    }

    public void hasGame(boolean hasGame) {
        this.hasGame = hasGame;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        cfg.title = "RoboRallyApplication";
        cfg.width = screenSize.width;
        cfg.height = screenSize.height;
        cfg.fullscreen = true;

        new LwjglApplication(new RoboRallyApplication(), cfg);
    }

    public void createGame(String mapName, int nrOfPlayers) {
        String fileName = "assets/" + mapName.replaceAll("\\s+", "_") + ".tmx";
        TiledMapManager mapManager = new TiledMapManager(fileName);
        RoboRallyGame game = new RoboRallyGame(this, mapManager, nrOfPlayers);
        gameScreen = game.getGameScreen();
        setScreen(gameScreen);
        gameScreen.setAsInputProcessor();
    }
}

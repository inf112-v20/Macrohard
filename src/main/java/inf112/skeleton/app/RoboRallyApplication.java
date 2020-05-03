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

    public static int screenWidth, screenHeight, numberOfPlayers;
    public static AppPreferences appPreferences;
    public static Music music;
    public static String currentSong;

    public final static int MAIN_MENU_SCREEN = 0;
    public final static int PREFERENCE_SCREEN = 1;
    public final static int GAME_SCREEN = 2;
    public final static int CREATE_GAME_SCREEN = 3;

    private RoboRallyGame game;

    private GameScreen gameScreen;
    private PreferenceScreen preferenceScreen;
    private CreateGameScreen createGameScreen;

    @Override
    public void create() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        appPreferences = new AppPreferences();
        initializeSound();
        setScreen(new LoadingScreen(this));
    }

    public static Skin getSkin() {
        return new Skin(Gdx.files.internal("assets/skins/commodore64/uiskin.json"));
    }

    private void initializeSound() {
        currentSong = "Factory Swing";
        music = Gdx.audio.newMusic(Gdx.files.internal("data/Music/FactorySwing.wav"));
        music.setLooping(true);
        if (appPreferences.isMusicEnabled()) {
            music.setVolume(appPreferences.getMusicVolume());
            music.play();
        }
    }

    public AppPreferences getPreferences() {
        return appPreferences;
    }

    public void setScreen(int screen) {
        switch (screen) {
            case MAIN_MENU_SCREEN:
                MenuScreen menuScreen = new MenuScreen(this);
                setScreen(menuScreen);
                menuScreen.setAsInputProcessor();
                break;
            case PREFERENCE_SCREEN:
                if (preferenceScreen == null) {
                    preferenceScreen = new PreferenceScreen(this);
                }
                setScreen(preferenceScreen);
                preferenceScreen.setAsInputProcessor();
                break;
            case CREATE_GAME_SCREEN:
                if (createGameScreen == null) {
                    createGameScreen = new CreateGameScreen(this);
                }
                setScreen(createGameScreen);
                createGameScreen.setAsInputProcessor();
            case GAME_SCREEN:
                if (hasGame()) {
                    gameScreen = game.getGameScreen();
                    setScreen(gameScreen);
                    gameScreen.setAsInputProcessor();
                }
            default:
                break;
        }
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    public boolean hasGame() {
        return game != null;
    }

    public void createGame(String mapName, int nrOfPlayers) {
        String fileName = "assets/" + mapName.replaceAll("\\s+", "_") + ".tmx";
        TiledMapManager mapManager = new TiledMapManager(fileName);
        game = new RoboRallyGame(this, mapManager, nrOfPlayers);
        gameScreen = game.getGameScreen();
        setScreen(GAME_SCREEN);
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


}

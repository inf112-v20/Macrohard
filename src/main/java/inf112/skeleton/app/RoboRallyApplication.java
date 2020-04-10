package inf112.skeleton.app;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.skeleton.app.preferences.AppPreferences;
import inf112.skeleton.app.screens.LoadingScreen;
import inf112.skeleton.app.screens.GameScreen;
import inf112.skeleton.app.screens.MenuScreen;
import inf112.skeleton.app.screens.PreferenceScreen;

import java.util.ArrayList;
import java.util.Arrays;

public class RoboRallyApplication extends com.badlogic.gdx.Game {

    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private PreferenceScreen preferenceScreen;

    private AppPreferences appPreferences;

    public final static int MAIN_MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public static Music music;

    private boolean hasGame = false;

    @Override
    public void create() {
        initializeMusic();
        LoadingScreen loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
        appPreferences = new AppPreferences();
    }

    public Skin getSkin() {
        return new Skin(Gdx.files.internal("assets/skins/commodore64/uiskin.json"));
    }

    private void initializeMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("data/test1.wav"));
        music.setLooping(true);
        music.play();
    }

    public AppPreferences getPreferences(){
        return appPreferences;
    }

    public void changeScreen(int screen){
        switch(screen){
            case MAIN_MENU:
                menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                menuScreen.setAsInputProcessor();
                break;
            case PREFERENCES:
                if(preferenceScreen == null) {
                    preferenceScreen = new PreferenceScreen(this);
                }
                this.setScreen(preferenceScreen);
                preferenceScreen.setAsInputProcessor();
                break;
            case APPLICATION:
                if(gameScreen == null){
                    gameScreen = new GameScreen(this);
                }
                this.setScreen(gameScreen);
                gameScreen.setAsInputProcessor();
                break;

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
}

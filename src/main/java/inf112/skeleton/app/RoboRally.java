package inf112.skeleton.app;


import inf112.skeleton.app.preferences.AppPreferences;
import inf112.skeleton.app.screens.LoadingScreen;
import inf112.skeleton.app.screens.MainScreen;
import inf112.skeleton.app.screens.MenuScreen;
import inf112.skeleton.app.screens.PreferenceScreen;

public class RoboRally extends com.badlogic.gdx.Game {

    //private LoadingScreen loadingScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private PreferenceScreen preferenceScreen;

    private AppPreferences appPreferences;

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;

    private int currentScreen;

    @Override
    public void create() {
        LoadingScreen loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
        appPreferences = new AppPreferences();
    }

    public AppPreferences getPreferences(){
        return appPreferences;
    }

    public int getScreen(int screen){
        return currentScreen;
    }

    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(menuScreen == null){
                    menuScreen = new MenuScreen(this);
                }
                this.setScreen(menuScreen);
                menuScreen.setAsInputProcessor();
                currentScreen = MENU;
                break;
            case PREFERENCES:
                if(preferenceScreen == null) {
                    preferenceScreen = new PreferenceScreen(this);
                }
                this.setScreen(preferenceScreen);
                preferenceScreen.setAsInputProcessor();
                currentScreen = PREFERENCES;
                break;
            case APPLICATION:
                if(mainScreen == null){
                    mainScreen = new MainScreen(this);
                }
                this.setScreen(mainScreen);
                mainScreen.setAsInputProcessor();
                currentScreen = APPLICATION;
                break;

             default:
                 break;
        }
    }
}

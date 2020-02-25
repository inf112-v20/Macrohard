package inf112.skeleton.app;


import com.badlogic.gdx.Preferences;
import inf112.skeleton.app.preferences.AppPreferences;
import inf112.skeleton.app.screens.LoadingScreen;
import inf112.skeleton.app.screens.MainScreen;
import inf112.skeleton.app.screens.MenuScreen;
import inf112.skeleton.app.screens.PreferenceScreen;

public class Roborally extends com.badlogic.gdx.Game {

    private LoadingScreen loadingScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private PreferenceScreen preferenceScreen;

    private AppPreferences appPreferences;

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;

    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
        appPreferences = new AppPreferences();
    }

    public AppPreferences getPreferences(){
        return appPreferences;
    }

    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(menuScreen == null){
                    menuScreen = new MenuScreen(this);
                }
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
                if(mainScreen == null){
                    mainScreen = new MainScreen(this);
                }
                this.setScreen(mainScreen);
                mainScreen.setAsInputProcessor();
                break;
        }
    }
}

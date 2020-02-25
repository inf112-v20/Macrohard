package inf112.skeleton.app;


import inf112.skeleton.app.screens.LoadingScreen;
import inf112.skeleton.app.screens.MainScreen;
import inf112.skeleton.app.screens.MenuScreen;

public class RoboRally extends com.badlogic.gdx.Game {

    private LoadingScreen loadingScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;

    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
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
            case PREFERENCES: break;
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

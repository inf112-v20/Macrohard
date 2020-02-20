package inf112.skeleton.app;


import inf112.skeleton.app.screens.LoadingScreen;
import inf112.skeleton.app.screens.MenuScreen;

public class Roborally extends com.badlogic.gdx.Game {

    private LoadingScreen loadingScreen;
    private MenuScreen menuScreen;

    public final static int MENU = 0;

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
                    this.setScreen(menuScreen);
                }
                break;
        }
    }
}

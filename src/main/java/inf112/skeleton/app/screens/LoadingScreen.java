package inf112.skeleton.app.screens;

import com.badlogic.gdx.Screen;
import inf112.skeleton.app.RoboRally;

public class LoadingScreen implements Screen {

    private RoboRally parent;

    public LoadingScreen(RoboRally roborally){
        parent = roborally;
    }

    @Override
    public void show() {
        //Add info
    }

    @Override
    public void render(float v) {
        parent.changeScreen(RoboRally.MENU);
    }

    @Override
    public void resize(int i, int i1) {
        //Nothing yet
    }

    @Override
    public void pause() {
        //Nothing yet
    }

    @Override
    public void resume() {
        //Nothing yet
    }

    @Override
    public void hide() {
        //Nothing yet
    }

    @Override
    public void dispose() {
        //Nothing yet
    }
}

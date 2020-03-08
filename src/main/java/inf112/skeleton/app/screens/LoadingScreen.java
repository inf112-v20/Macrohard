package inf112.skeleton.app.screens;

import com.badlogic.gdx.Screen;
import inf112.skeleton.app.RoboRallyApplication;

public class LoadingScreen implements Screen {

    private RoboRallyApplication parent;

    public LoadingScreen(RoboRallyApplication roborally){
        parent = roborally;
    }

    @Override
    public void show() {
        //Add info
    }

    @Override
    public void render(float v) {
        parent.changeScreen(RoboRallyApplication.MENU);
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

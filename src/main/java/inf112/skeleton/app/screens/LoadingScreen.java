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

    }

    @Override
    public void render(float v) {
        parent.changeScreen(RoboRally.MENU);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

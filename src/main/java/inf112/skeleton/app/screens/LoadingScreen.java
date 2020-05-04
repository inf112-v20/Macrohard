package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRallyApplication;

public class LoadingScreen implements Screen {

    private RoboRallyApplication parent;
    private Stage stage;
    private Label loading;
    private ProgressBar progressBar;

    private float timePast;

    public LoadingScreen(RoboRallyApplication parent) {
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
        loading = new Label("Loading", RoboRallyApplication.getSkin());
        loading.setFontScale(0.8f);
        loading.setPosition(Gdx.graphics.getWidth() / 2f, (Gdx.graphics.getHeight() + 50) / 2f, Align.center);
        progressBar = new ProgressBar(0f, 1.0f, 0.01f, false, RoboRallyApplication.getSkin());
        progressBar.setSize(300, 20);
        progressBar.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
        stage.addActor(progressBar);
        stage.addActor(loading);
    }

    @Override
    public void show() {
        //Add info
    }

    @Override
    public void render(float v) {
        if (parent.manager.update()) {
            timePast += v;
            if (timePast > 0.2f) {
                parent.setScreen(RoboRallyApplication.MAIN_MENU_SCREEN);
            }
        }
        float progress = parent.manager.getProgress();
        StringBuilder s = new StringBuilder();
        for (float i = 0.33f; i < progress; i += 0.33f) {
            s.append(".");
        }
        loading.setText("Loading" + s.toString());

        progressBar.setValue(progress);
        stage.act();
        stage.draw();
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

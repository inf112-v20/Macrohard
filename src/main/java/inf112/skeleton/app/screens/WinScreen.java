package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.Player;

public class WinScreen implements Screen {

    private Stage stage = new Stage(new ScreenViewport());
    private Skin skin = new Skin(Gdx.files.internal("assets/skins/commodore64/uiskin.json"));
    private Player winner;

    public WinScreen(Player player) {
        winner = player;
    }

    @Override
    public void show() {
        Label winLabel = new Label("Player " + winner.getName() + " won!", skin);
        winLabel.setFontScale(2f);
        winLabel.setAlignment(Align.center);
        winLabel.setBounds(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0, 0);
        stage.addActor(winLabel);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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

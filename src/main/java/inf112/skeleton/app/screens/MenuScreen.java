package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRallyApplication;

public class MenuScreen implements Screen {

    private RoboRallyApplication parent;
    private Stage stage = new Stage(new ScreenViewport());
    private Skin skin = new Skin(Gdx.files.internal("assets/skins/commodore64/uiskin.json"));


    public MenuScreen(RoboRallyApplication roboRallyApplication) {
        parent = roboRallyApplication;
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        TextButton newGame = new TextButton("New Game", skin);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.setGameScreen(null);
                parent.changeScreen(RoboRallyApplication.APPLICATION);
                parent.hasGame(true);
            }
        });
        TextButton preferences = new TextButton("Preferences", skin);
        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(RoboRallyApplication.PREFERENCES);
            }
        });
        TextButton exit = new TextButton("Exit", skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.app.exit();
            }
        });

        Label titleLabel = new Label("RoboRally", skin);
        titleLabel.setFontScale(2f);
        table.add(titleLabel).colspan(2);
        table.row().pad(50, 15, 10, 0);
        if (parent.hasGame()) {
            TextButton resumeGame = new TextButton("Resume Game", skin);
            resumeGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    parent.changeScreen(RoboRallyApplication.APPLICATION);
                }
            });
            table.add(resumeGame).fillX().uniformX();
            table.row().pad(10, 15, 10, 0);
        }
        table.add(newGame).fillX().uniformX();
        table.row().pad(10, 15, 10, 0);
        table.add(preferences).fillX().uniformX();
        table.row().pad(10, 15, 10, 0);
        table.add(exit).fillX().uniformX();

        stage.addActor(table);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

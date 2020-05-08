package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRallyApplication;

public class CreateGameScreen implements Screen {

    private RoboRallyApplication parent;
    private Stage stage;

    public CreateGameScreen(RoboRallyApplication parent) {
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        stage.clear();
        stage.addActor(getCreateGameScreenTable());
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private Table getCreateGameScreenTable() {
        Table table = new Table();
        table.setFillParent(true);

        Skin skin = RoboRallyApplication.getSkin();

        SelectBox<String> mapSelectBox = new SelectBox<>(skin);
        mapSelectBox.setItems("Risky Exchange", "Dizzy Dash", "Island Hop", "Whirlwind Tour");

        SelectBox<Integer> playersSelectBox = new SelectBox<>(skin);
        playersSelectBox.setItems(2, 3, 4, 5, 6, 7, 8);

        CheckBox debugMode = new CheckBox(null, skin);

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.setScreen(RoboRallyApplication.MAIN_MENU_SCREEN);
            }
        });

        TextButton createButton = new TextButton("Create", skin);
        createButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(debugMode.isChecked()) {
                    RoboRallyApplication.debugMode = true;
                } else {
                    RoboRallyApplication.debugMode = false;
                }
                parent.createGame(mapSelectBox.getSelected(), playersSelectBox.getSelected());
            }
        });

        Label titleLabel = new Label("Create new game", skin);
        titleLabel.setFontScale(2f);

        //Adding elements to table
        table.add(titleLabel).colspan(2);
        table.row().pad(50, 10, 10, 10);
        table.add(new Label("Map", skin)).left();
        table.add(mapSelectBox).right();
        table.row().pad(10, 10, 10, 10);
        table.add(new Label("Players", skin)).left();
        table.add(playersSelectBox).right();
        table.row().pad(10, 10, 10, 10);
        table.add(new Label("Debug mode", skin)).left();
        table.add(debugMode).right();
        table.row().pad(100, 10, 100, 10);
        table.add(backButton);
        table.add(createButton);

        return table;
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

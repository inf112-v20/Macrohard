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
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        Skin skin = new Skin(Gdx.files.internal("assets/skins/commodore64/uiskin.json"));

        final SelectBox<String> mapSelectBox = new SelectBox<>(skin);
        mapSelectBox.setItems("Risky Exchange", "Dizzy Dash", "Island Hop", "Whirlwind Tour");

        final SelectBox<Integer> playersSelectBox = new SelectBox<>(skin);
        playersSelectBox.setItems(2, 3, 4, 5, 6, 7, 8);

        final SelectBox<String> difficultySelectBox = new SelectBox<>(skin);
        difficultySelectBox.setItems("Easy", "Medium", "Hard");

        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.setScreen(RoboRallyApplication.MAIN_MENU_SCREEN);
            }
        });

        final TextButton createButton = new TextButton("Create", skin);
        createButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.createGame(mapSelectBox.getSelected(), playersSelectBox.getSelected());
            }
        });

        Label titleLabel = new Label("Create new game", skin);
        titleLabel.setFontScale(2f);

        Label mapLabel = new Label("Map", skin);

        Label playersLabel = new Label("Players", skin);

        Label difficultyLabel = new Label("Difficulty", skin);

        //Add elements to table
        table.add(titleLabel).colspan(2);
        table.row().pad(50, 10, 10, 10);
        table.add(mapLabel).left();
        table.add(mapSelectBox);
        table.row().pad(10, 10, 10, 10);
        table.add(playersLabel).left();
        table.add(playersSelectBox);
        table.row().pad(10, 10, 10, 10);
        table.add(difficultyLabel).left();
        table.add(difficultySelectBox);
        table.row().pad(100, 10, 100, 10);
        table.add(backButton);
        table.add(createButton);

        //add table to stage
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

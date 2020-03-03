package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.RoboRally;

public class MainMenuScreen implements Screen {
    private RoboRally parent;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Skin skin;

    public MainMenuScreen(RoboRally roboRallyGame){
        parent = roboRallyGame;
        skin = new Skin(Gdx.files.internal("assets/skin/expee-ui.json"));

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        TextButton playButton = new TextButton("New Game", skin);
        TextButton PreferencesButton = new TextButton("Preferences", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent PlayGame, float x, float y) {
                //((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen());
                System.out.println("CLICKED");
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent QuitGame, float x, float y) {
                Gdx.app.exit();
            }
        });

        mainTable.add(playButton).fillX().uniformX();
        mainTable.row().pad(10, 0, 10, 0);
        mainTable.add(PreferencesButton).fillX().uniformX();
        mainTable.row();
        mainTable.add(exitButton).fillX().uniformX();

        stage.addActor(mainTable);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.*;
import inf112.skeleton.app.cards.*;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.managers.MainScreenInputManager;
import inf112.skeleton.app.managers.TiledMapManager;

import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen implements Screen {

    private RoboRallyApplication parent;

    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private MainScreenInputManager ip;

    private int tileSize = 60;
    private int gridSize = 12;

    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private ArrayList<PlayerGraphic> playerGraphics = new ArrayList<>();

    private Board board;

    private Stage stage;

    public GameScreen(RoboRallyApplication parent){

        stage = new Stage(new ScreenViewport());

        this.parent = parent;
        Player player1 = new Player(1,1, Direction.NORTH);
        Player player2 = new Player(9, 9, Direction.SOUTH);
        final ArrayList<Player> players = (ArrayList<Player>) Arrays.asList(player1, player2);
        board = new Board(players, 12,12);

        TiledMapManager handler = new TiledMapManager("assets/plsWork.tmx");

        map = handler.getMap();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(0, -tileSize*camera.zoom*2);

        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");

        Deck deck = new Deck();
        deck.shuffle();

        for (Player player : players) {
            deck.dealHand(player);
            TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
            playerLayer.setCell(player.getRow(), player.getCol(), playerCell);
            PlayerGraphic playerGraphic = new PlayerGraphic(player);
            playerGraphics.add(playerGraphic);
            stage.addActor(playerGraphic);
        }

        renderer = new OrthogonalTiledMapRenderer(map);

        PlayerHand hand = players.get(0).getHand();
        Card[] cards = hand.getPossibleHand();

        for (Card card : cards) {
            CardGraphic tempCard = new CardGraphic(card);
            stage.addActor(tempCard);
        }

        TextButton button = new TextButton("GO!", parent.getSkin());
        button.setBounds(750, 200, 100, 50);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                for (Player player : players) {
                    runProgram(player);
                }
            }
        });
        stage.addActor(button);

        ip = new MainScreenInputManager(parent);
    }

    public void runProgram(Player player) {
        for (int i = 0; i<player.getProgram().size(); i++) {
            Card card = player.getProgram().get(i);
            Direction dir = player.getDirection();
            board.execute(player, card);
            player.getGraphics().updatePlayerGraphic(card, dir);
        }
        player.getGraphics().animate();
        player.wipeProgram();
    }

    public void setAsInputProcessor() {
        //Gdx.input.setInputProcessor(ip);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        //Nothing yet
        RoboRallyApplication.music.play();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
        map.dispose();
        renderer.dispose();
        //music.dispose();
    }
}

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
import inf112.skeleton.app.managers.TiledMapManager;

public class GameScreen implements Screen {

    private RoboRallyApplication parent;

    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private int tileSize = 60;
    private int gridSize = 12;

    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private Board board;

    private Stage stage;

    public GameScreen(RoboRallyApplication parent){

        stage = new Stage(new ScreenViewport());

        this.parent = parent;
        final Player player = new Player(1,1, Direction.NORTH);
        board = new Board(player, 12,12);
        //map = new TmxMapLoader().load("assets/robomap.tmx");

        //TESTING

        TiledMapManager handler = new TiledMapManager("assets/plsWork.tmx");

        map = handler.getMap();

        //
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(0, -tileSize*camera.zoom*2);

        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");

        TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();

        playerLayer.setCell(player.getRow(), player.getCol(), playerCell);

        renderer = new OrthogonalTiledMapRenderer(map);

        Deck deck = new Deck();
        deck.shuffle();
        deck.dealHand(player);
        PlayerHand hand = player.getHand();
        final Card[] cards = hand.getPossibleHand();

        for (Card card : cards) {
            CardGraphic tempCard = new CardGraphic(card);
            stage.addActor(tempCard);
        }

        PlayerGraphic playerGraphic = new PlayerGraphic(player);
        stage.addActor(playerGraphic);

        TextButton button = new TextButton("GO!", parent.getSkin());
        button.setBounds(750, 200, 100, 50);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                runProgram(player, cards);
            }
        });
        stage.addActor(button);
    }

    public void runProgram(Player player, Card[] cards) {
        player.setProgram();

        // Create program of selected cards from hand
        for (Card card : cards) {
            if (card.handIndex == 1) {
                player.getProgram()[0] = card;
            } else if (card.handIndex == 2) {
                player.getProgram()[1] = card;
            } else if (card.handIndex == 3) {
                player.getProgram()[2] = card;
            } else if (card.handIndex == 4) {
                player.getProgram()[3] = card;
            } else if (card.handIndex == 5) {
                player.getProgram()[4] = card;
            } else {

            }
        }

        // Execute program
        for (int i = 0; i<player.getProgram().length; i++) {
            Card card = player.getProgram()[i];
            Direction dir = player.getDirection();
            board.execute(player, card);
            player.getGraphics().updatePlayerGraphic(card, dir);
        }
        player.getGraphics().animate();
    }

    public void setAsInputProcessor() {
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
    }
}

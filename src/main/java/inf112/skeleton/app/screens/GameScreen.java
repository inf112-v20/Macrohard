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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import inf112.skeleton.app.*;
import inf112.skeleton.app.cards.*;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.managers.MainScreenInputManager;
import inf112.skeleton.app.managers.TiledMapManager;

import java.awt.*;
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
        TiledMapTileLayer.Cell cell = handler.getCell(7,3,"FLOOR");
        TiledMapTileLayer.Cell cell1 = handler.getCell(1,0,0);
        TiledMapTileLayer.Cell cell3 = handler.getCell(2,0,0);
        TiledMapTileLayer.Cell cell4 = handler.getCell(3,0,0);
        TiledMapTileLayer.Cell cell5 = handler.getCell(4,0,0);
        TiledMapTileLayer.Cell cell6 = handler.getCell(5,0,0);
        TiledMapTileLayer.Cell cell7 = handler.getCell(6,0,0);

        System.out.println(Tile.getType(cell1));
        System.out.println(Tile.getType(cell3));
        System.out.println(Tile.getType(cell4));
        System.out.println(Tile.getType(cell5));
        System.out.println(Tile.getType(cell6));
        System.out.println(Tile.getType(cell7));


        System.out.println(Tile.getType(cell));
        //System.out.println(TileType.getType(cell2));
        TiledMapTileLayer floor = (TiledMapTileLayer) handler.getMap().getLayers().get("Player");
        System.out.println(floor.getCell(5,5) != null);
        //System.out.println(cell.setTile());
        System.out.println(handler.getMap().getLayers().get(0).getName());
        //System.out.println(handler.getTile(0,0,"FLOOR").getTileType());
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
        //Print-line for testing purposes

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

        ip = new MainScreenInputManager(parent, boardLayer, playerLayer, playerCell, player, board);
    }

    public void runProgram(Player player, Card[] cards) {
        player.setProgram();

        // Create program of selected cards from hand
        for (int j = 0; j<cards.length; j++) {
            if (cards[j].handIndex == 1) {
                player.getProgram()[0] = cards[j];
            }else if (cards[j].handIndex == 2) {
                player.getProgram()[1] = cards[j];
            }else if (cards[j].handIndex == 3) {
                player.getProgram()[2] = cards[j];
            }else if (cards[j].handIndex == 4) {
                player.getProgram()[3] = cards[j];
            }else if (cards[j].handIndex == 5) {
                player.getProgram()[4] = cards[j];
            } else { continue;}
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

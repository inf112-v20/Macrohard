package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.*;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.cards.PlayerHand;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.managers.MainScreenInputManager;
import inf112.skeleton.app.managers.TiledMapManager;

public class MainScreen implements Screen {

    private RoboRally parent;

    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private MainScreenInputManager ip;

    private int tileSize = 75;
    private int gridSize = 12;

    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private Board board;

    //Removed by request from Codacy
    //private Player player;
    //private TiledMapTileLayer.Cell playerCell;

    private Stage stage;

    public MainScreen(RoboRally parent){

        stage = new Stage(new ScreenViewport());

        this.parent = parent;
        Player player = new Player(1,1, Direction.NORTH);
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
        camera.setToOrtho(false, tileSize*gridSize+330, tileSize*gridSize+50);
        camera.zoom = 1.2f;
        camera.translate(tileSize*camera.zoom, -tileSize*camera.zoom);

        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");

        TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();

        Texture playerTexture = new Texture("./assets/player.png");
        TextureRegion playerTextureRegion = new TextureRegion(playerTexture);
        TextureRegion standardPlayerTextureRegion = playerTextureRegion.split(75, 75)[0][2];

        StaticTiledMapTile playerTile = new com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile(standardPlayerTextureRegion);
        playerCell.setTile(playerTile);
        playerLayer.setCell(player.getRow(), player.getCol(), playerCell);

        renderer = new OrthogonalTiledMapRenderer(map);

        Deck deck = new Deck();
        deck.shuffle();
        deck.dealHand(player);
        PlayerHand hand = player.getHand();
        Card[] cards = hand.getPossibleHand();

        for (int i = 0; i < cards.length; i++) {
             CardGraphic tempCard = new CardGraphic(cards[i]);
             stage.addActor(tempCard);
        }
 /*      player.setFinalHand();
        int cardIndex = 1;
        for (int j = 0; j<cards.length; j++) {
            if (cards[j].handIndex == cardIndex) {
                player.getFinalHand()[cardIndex-1] = cards[j];
                cardIndex++;
            }
        }
        */

       ip = new MainScreenInputManager(parent, boardLayer, playerLayer, playerCell, player, board);
    }

    public void setAsInputProcessor() {
        //Gdx.input.setInputProcessor(ip);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        //Nothing yet
        RoboRally.music.play();
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

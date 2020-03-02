package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.MainScreenInputProcessor;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRally;

public class MainScreen extends InputAdapter implements Screen {

    private RoboRally parent;

    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private MainScreenInputProcessor ip;

    private int tileSize = 75;
    private int gridSize = 12;

    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private Player player;
    private Board board;
    private TiledMapTileLayer.Cell playerCell;
    private int playerX, playerY;

    public MainScreen(RoboRally parent){
        this.parent = parent;

        map = new TmxMapLoader().load("assets/robomap.tmx");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, tileSize*gridSize, tileSize*gridSize);

        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        playerCell = new TiledMapTileLayer.Cell();

        Texture playerTexture = new Texture("./assets/player.png");
        TextureRegion playerTextureRegion = new TextureRegion(playerTexture);
        TextureRegion standardPlayerTextureRegion = playerTextureRegion.split(75, 75)[0][2];

        StaticTiledMapTile playerTile = new com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile(standardPlayerTextureRegion);
        playerCell.setTile(playerTile);
        playerLayer.setCell(playerX, playerY, playerCell);

        renderer = new OrthogonalTiledMapRenderer(map);

        ip = new MainScreenInputProcessor(parent, boardLayer, playerLayer, playerCell, playerX, playerY, camera);
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(ip);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched()) {
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }

        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
        map.dispose();
        renderer.dispose();
    }
}

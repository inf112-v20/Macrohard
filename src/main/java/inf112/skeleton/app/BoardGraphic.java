package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import javafx.scene.input.KeyCode;

public class BoardGraphic extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private Cell playerCell;
    private int playerX = 9;
    private int playerY = 9;

    @Override
    public void create() {

        Gdx.input.setInputProcessor(this);

        map = new TmxMapLoader().load("assets/robomap.tmx");
        camera = new OrthographicCamera();

        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        playerCell = new Cell();

        Texture playerTexture = new Texture("./assets/player.png");
        TextureRegion playerTextureRegion = new TextureRegion(playerTexture);
        TextureRegion standardPlayerTextureRegion = playerTextureRegion.split(300, 300)[0][2];

        StaticTiledMapTile playerTile = new com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile(standardPlayerTextureRegion);
        playerCell.setTile(playerTile);
        playerLayer.setCell(playerX, playerY, playerCell);
        camera.setToOrtho(false, 3600, 3600);
        camera.zoom = 6;
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public boolean keyUp(int keycode) {
        //Removes the player from previous location on the playerLayer
        playerLayer.setCell(playerX, playerY, boardLayer.getCell(playerX, playerY));

        //Change the players new coordinates according to the keycode
        switch(keycode){
            case Input.Keys.UP: playerY+=1; break;
            case Input.Keys.DOWN: playerY-=1; break;
            case Input.Keys.LEFT: playerX-=1; break;
            case Input.Keys.RIGHT: playerX+=1; break;
        }

        //Add the player onto the new coordinate
        playerLayer.setCell(playerX, playerY, playerCell);
        return true;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
}

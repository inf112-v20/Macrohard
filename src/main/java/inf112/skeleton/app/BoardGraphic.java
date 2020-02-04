package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

public class BoardGraphic implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    @Override
    public void create() {
      /*  batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);*/
        map = new TmxMapLoader().load("assets/coolMap.tmx");
        camera = new OrthographicCamera();
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) map.getLayers().get("board");
        TiledMapTileLayer playerLayer = (TiledMapTileLayer) map.getLayers().get("player");
        //Cell playerCell = boardLayer.getCell(5, 5);
        Cell playerCell = new Cell();

        Texture playerTexture = new Texture("./assets/player.png");
        TextureRegion playerTextureRegion = new TextureRegion(playerTexture);
        TextureRegion standardPlayerTextureRegion = playerTextureRegion.split(300, 300)[0][1];

        StaticTiledMapTile playerTile = new com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile(standardPlayerTextureRegion);
        playerCell.setTile(playerTile);
        playerLayer.setCell(0, 0, playerCell);
        camera.setToOrtho(false, 3600, 3600);
        camera.zoom = 6;
        renderer = new OrthogonalTiledMapRenderer(map);

        //TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
        //Gdx.input.setInputProcessor((InputProcessor) this);
    }


    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        /*batch.dispose();
        font.dispose();*/
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*batch.begin();
        font.draw(batch, "Hello World", 200, 200);
        batch.end();*/
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
package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class BoardGraphic implements ApplicationListener {

    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    @Override
    public void create() {

        map = new TmxMapLoader().load("assets/newMap.tmx");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 900, 900);
        camera.zoom = 1;

        TiledMapTileLayer boardLayer = (TiledMapTileLayer) map.getLayers().get("board");
        TiledMapTileLayer playerLayer = (TiledMapTileLayer) map.getLayers().get("player");
        Cell playerCell = new Cell();

        Texture playerTexture = new Texture("./assets/player.png");
        TextureRegion playerTextureRegion = new TextureRegion(playerTexture);
        TextureRegion standardPlayerTextureRegion = playerTextureRegion.split(75, 75)[0][1];

        StaticTiledMapTile playerTile = new com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile(standardPlayerTextureRegion);
        playerCell.setTile(playerTile);
        playerLayer.setCell(0, 0, playerCell);

        //camera.translate(192, 0);
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

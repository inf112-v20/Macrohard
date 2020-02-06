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

    private Player[] players;
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    public BoardGraphic(Player... players) {
        this.players = players;
    }

    @Override
    public void create() {

        map = new TmxMapLoader().load("assets/coolMap.tmx");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 3600, 3600);
        camera.zoom = 6;

        TiledMapTileLayer playerLayer = (TiledMapTileLayer) map.getLayers().get("player");
        Texture playerTexture = new Texture("./assets/player.png");
        TextureRegion playerTextureRegion = new TextureRegion(playerTexture);
        TextureRegion standardPlayerTextureRegion = playerTextureRegion.split(300, 300)[0][1];
        StaticTiledMapTile playerTile = new com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile(standardPlayerTextureRegion);

        Cell playerCell = new Cell();
        playerCell.setTile(playerTile);
        for (Player player : players) {
            playerLayer.setCell(player.getRow(), player.getCol(), playerCell);
        }

        renderer = new OrthogonalTiledMapRenderer(map);
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

package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class HelloWorld implements ApplicationListener {
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
        map = new TmxMapLoader().load("assets/robomap.tmx");
        camera = new OrthographicCamera();
        TiledMapTileLayer boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        camera.setToOrtho(false, 3600, 3600);
        camera.zoom = 6;
        renderer = new OrthogonalTiledMapRenderer(map);
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

package inf112.skeleton.app.managers;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/*
    Not very useful right now, but might be nice later.
 */
public class TiledMapManager {

    private final TiledMap map;
    private final MapProperties properties;
    private final int height;
    private final int width;

    public TiledMapManager(String fileName) {
        map = new TmxMapLoader().load(fileName);
        map.getLayers().get("LASERBEAMS").setVisible(false);
        properties = map.getProperties();
        height = properties.get("height", Integer.class);
        width = properties.get("width", Integer.class);
    }

    public TiledMap getMap() {
        return map;
    }

    public TiledMapTileLayer getLayer(String layerName) {
        return (TiledMapTileLayer) map.getLayers().get(layerName);
    }

    public TiledMapTileLayer.Cell getCell(String layerName, int row, int col) {
        return getLayer(layerName).getCell(col, row);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}

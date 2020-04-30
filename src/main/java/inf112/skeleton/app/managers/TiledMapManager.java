package inf112.skeleton.app.managers;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.skeleton.app.tiles.Tile;

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

    public void setLaserCell(boolean horizontal, int row, int col) {
        int id = horizontal ? 39 : 47;
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        if (getCell("PLAYERBEAMS", row, col) == null) {
            cell.setTile(map.getTileSets().getTile(id));
        } else {
            cell.setTile(map.getTileSets().getTile(40));
        }
        getLayer("PLAYERBEAMS").setCell(col, row, cell);
    }

    public void cleanPlayerLaserLayer() {
        for (int row = 0; row < height; row ++) {
            for (int col = 0; col < width; col ++) {
                if (getCell("PLAYERBEAMS", row, col) != null) {
                    getLayer("PLAYERBEAMS").setCell(col, row, null);
                }
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}

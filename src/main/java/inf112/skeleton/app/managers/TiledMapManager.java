package inf112.skeleton.app.managers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/*
    Not very useful right now, but might be nice later.
 */
public class TiledMapManager {

    private final TiledMap map;

    public TiledMapManager (String fileName){
        map = new TmxMapLoader().load(fileName);
    }

    public TiledMap getMap (){
        return map;
    }

    public TiledMapTileLayer getLayer(String layerName) {
        return (TiledMapTileLayer) map.getLayers().get(layerName);
    }

    public TiledMapTileLayer.Cell getCell(String layerName, int row, int col) {
        return getLayer(layerName).getCell(col, row);
    }

}

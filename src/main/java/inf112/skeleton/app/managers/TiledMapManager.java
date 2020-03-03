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
        return this.map;
    }

    //get cell using layer name
    public TiledMapTileLayer.Cell getCell (int x, int y, String layerName){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);
        return layer.getCell(x,y);
    }

    //get cell using layer id
    public TiledMapTileLayer.Cell getCell (int x, int y, int id){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(id);
        return layer.getCell(x,y);
    }

}

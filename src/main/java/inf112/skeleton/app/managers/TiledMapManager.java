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

}

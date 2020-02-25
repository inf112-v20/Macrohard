package inf112.skeleton.app;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MainScreenInputProcessor extends InputAdapter {

    private RoboRally parent;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private TiledMapTileLayer.Cell playerCell;
    private int playerX, playerY;

    public MainScreenInputProcessor(RoboRally parent, TiledMapTileLayer boardLayer, TiledMapTileLayer playerLayer, TiledMapTileLayer.Cell playerCell, int playerX, int playerY) {
        this.parent = parent;
        this.boardLayer = boardLayer;
        this.playerLayer = playerLayer;
        this.playerCell = playerCell;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public boolean keyDown(int keycode) {
        //Removes the player from previous location on the playerLayer
        playerLayer.setCell(playerX, playerY, boardLayer.getCell(playerX, playerY));

        //Change the players new coordinates according to the keycode
        switch(keycode){
            case Input.Keys.UP: playerY += 1; break;
            case Input.Keys.DOWN: playerY -= 1; break;
            case Input.Keys.LEFT: playerX -= 1; break;
            case Input.Keys.RIGHT: playerX += 1; break;
            case Input.Keys.ESCAPE: parent.changeScreen(RoboRally.MENU); break;
        }

        //Add the player onto the new coordinate
        playerLayer.setCell(playerX, playerY, playerCell);

        return true;
    }

}

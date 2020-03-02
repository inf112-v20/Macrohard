package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MainScreenInputProcessor extends InputAdapter {

    private RoboRally parent;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private TiledMapTileLayer.Cell playerCell;
    private int playerX, playerY;

    private OrthographicCamera camera;

    public MainScreenInputProcessor(RoboRally parent, TiledMapTileLayer boardLayer, TiledMapTileLayer playerLayer, TiledMapTileLayer.Cell playerCell, int playerX, int playerY, OrthographicCamera camera) {
        this.parent = parent;
        this.boardLayer = boardLayer;
        this.playerLayer = playerLayer;
        this.playerCell = playerCell;
        this.playerX = playerX;
        this.playerY = playerY;
        this.camera = camera;
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

    // WIP
    /*
    public boolean touchDragged(int x, int y, int pointer){
        x /= 50;
        y /= 50;
        camera.translate(-x, y);
        camera.update();
        if (Gdx.input.isTouched()) {
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }
        return true;
    }
     */

    public boolean scrolled(int amount){
        amount *= 10;
        camera.viewportWidth += amount;
        camera.viewportHeight += amount;
        camera.update();
        return true;
    }



}

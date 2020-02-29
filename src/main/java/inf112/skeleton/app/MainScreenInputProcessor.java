package inf112.skeleton.app;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MainScreenInputProcessor extends InputAdapter {

    private Player player;
    private RoboRally parent;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer.Cell playerCell;
    private Board board;

    public MainScreenInputProcessor(RoboRally parent, TiledMapTileLayer boardLayer, TiledMapTileLayer playerLayer, TiledMapTileLayer.Cell playerCell, Player player, Board board) {
        this.parent = parent;
        this.boardLayer = boardLayer;
        this.playerLayer = playerLayer;
        this.playerCell = playerCell;
        this.player = player;
        this.board = board;
    }

    public boolean keyDown(int keycode) {
        //Removes the player from previous location on the playerLayer
        playerLayer.setCell(player.getRow(), player.getCol(), boardLayer.getCell(player.getRow(), player.getCol()));

        //Change the players new coordinates according to the keycode
        switch(keycode){
            case Input.Keys.UP: board.move(player,player.getRow(),player.getCol()+1, Direction.NORTH); break;
            case Input.Keys.DOWN: board.move(player,player.getRow(),player.getCol()-1, Direction.SOUTH); break;
            case Input.Keys.LEFT: board.move(player,player.getRow()-1,player.getCol(), Direction.WEST); break;
            case Input.Keys.RIGHT: board.move(player,player.getRow()+1,player.getCol(), Direction.EAST); break;
            case Input.Keys.ESCAPE: parent.changeScreen(RoboRally.MENU); break;
        }
        // new position after move
        System.out.println("Row: " + player.getRow() + " Col: " + player.getCol() + " Direction:" + player.getDirection());

        //Add the player onto the new coordinate
        playerLayer.setCell(player.getRow(), player.getCol(), playerCell);

        return true;
    }

}

package inf112.skeleton.app.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.cards.RotationType;

public class MainScreenInputManager extends InputAdapter {

    private Player player;
    private RoboRallyApplication parent;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer.Cell playerCell;
    private Board board;

    public MainScreenInputManager(RoboRallyApplication parent, TiledMapTileLayer boardLayer, TiledMapTileLayer playerLayer, TiledMapTileLayer.Cell playerCell, Player player, Board board) {
        this.parent = parent;
        this.boardLayer = boardLayer;
        this.playerLayer = playerLayer;
        this.playerCell = playerCell;
        this.player = player;
        this.board = board;
    }

    public boolean keyDown(int keycode) {
        //Removes the player from previous location on the playerLayer
        playerLayer.setCell(player.getCol(), player.getRow(), boardLayer.getCell(player.getCol(), player.getRow()));

        //Change the players new coordinates according to the keycode
        //For manual testing purposes
        Direction oldDir = player.getDirection();
        /*switch(keycode){
            case Input.Keys.ESCAPE: parent.changeScreen(RoboRallyApplication.MAIN_MENU); break;
            case Input.Keys.A:
                board.execute(player, new RotationCard(1, RotationType.CLOCKWISE));
                player.getGraphics().updatePlayerGraphic(oldDir);
                break;
            case Input.Keys.B:
                board.execute(player, new RotationCard(1, RotationType.COUNTER_CLOCKWISE));
                player.getGraphics().updatePlayerGraphic(oldDir);
                break;
            case Input.Keys.C:
                board.execute(player, new RotationCard(1, RotationType.U_TURN));
                player.getGraphics().updatePlayerGraphic(oldDir);
                break;
            case Input.Keys.NUM_1:
                MovementCard card1 = new MovementCard(1, MovementType.ONE_FORWARD);
                board.execute(player, card1);
                player.getGraphics().updatePlayerGraphic(card1);
                break;
            case Input.Keys.NUM_2:
                MovementCard card2 = new MovementCard(1, MovementType.TWO_FORWARD);
                board.execute(player, card2);
                player.getGraphics().updatePlayerGraphic(card2);
                break;
            case Input.Keys.NUM_3:
                MovementCard card3 = new MovementCard(1, MovementType.THREE_FORWARD);
                board.execute(player, card3);
                player.getGraphics().updatePlayerGraphic(card3);
                break;
            case Input.Keys.NUM_4:
                MovementCard card4 = new MovementCard(1, MovementType.ONE_BACKWARD);
                board.execute(player, card4);
                player.getGraphics().updatePlayerGraphic(card4);
                break;
        }*/
        //Prints the new position after move in console
        System.out.println("Row: " + player.getRow() + " Col: " + player.getCol() + " Direction:" + player.getDirection());

        //Add the player onto the new coordinate
        playerLayer.setCell(player.getCol(), player.getRow(), playerCell);

        return true;
    }

}
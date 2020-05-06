package inf112.skeleton.app.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.cards.RotationType;
import inf112.skeleton.app.screens.GameScreen;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameScreenInputProcessor extends InputListener {

    private RoboRallyApplication parent;
    private Player player;
    private Board board;
    private GameScreen gameScreen;

    public GameScreenInputProcessor(RoboRallyApplication parent, Player client, Board board, GameScreen gameScreen) {
        this.parent = parent;
        this.player = client;
        this.board = board;
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        super.keyDown(event, keycode);
        switch (keycode) {
            case Input.Keys.ESCAPE:
                parent.setScreen(RoboRallyApplication.MAIN_MENU_SCREEN);
                return super.keyDown(event, keycode);
            case Input.Keys.UP:
                MovementCard movementCardUp = new MovementCard(0, MovementType.ONE_FORWARD);
                board.execute(player, movementCardUp);
                break;
            case Input.Keys.DOWN:
                MovementCard movementCardDown = new MovementCard(0, MovementType.ONE_BACKWARD);
                board.execute(player, movementCardDown);
                break;
            case Input.Keys.RIGHT:
                RotationCard rotationCardRight = new RotationCard(0, RotationType.CLOCKWISE);
                board.execute(player, rotationCardRight);
                break;
            case Input.Keys.LEFT:
                RotationCard rotationCardLeft = new RotationCard(0, RotationType.COUNTER_CLOCKWISE);
                board.execute(player, rotationCardLeft);
                break;
            case Input.Keys.G:
                board.rotateGears();
                break;
            case Input.Keys.E:
                board.rollConveyorBelts(true);
                board.rollConveyorBelts(true);
                break;
            case Input.Keys.B:
                board.fireBoardLasers();
                gameScreen.mapManager.getLayer("LASERBEAMS").setVisible(true);
                break;
            case Input.Keys.L:
                gameScreen.eraseLasers();
                gameScreen.drawLasers(board.firePlayerLasers());
                break;
            case Input.Keys.R:
                board.rollConveyorBelts(false);
                break;
            case Input.Keys.F:
                board.touchBoardElements();
                break;

        }
        gameScreen.updateGraphics();

        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

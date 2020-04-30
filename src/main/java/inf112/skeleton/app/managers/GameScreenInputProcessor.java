package inf112.skeleton.app.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.cards.RotationType;

public class GameScreenInputProcessor extends InputListener {

    RoboRallyApplication parent;
    Player player;
    Board board;

    public GameScreenInputProcessor(RoboRallyApplication parent, Player player, Board board) {
        this.parent = parent;
        this.player = player;
        this.board = board;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        super.keyDown(event, keycode);

        switch (keycode) {
            case Input.Keys.ESCAPE:
                parent.changeScreen(RoboRallyApplication.MAIN_MENU);
                return super.keyDown(event, keycode);
            case Input.Keys.UP:
                MovementCard movementCardUp = new MovementCard(0, MovementType.ONE_FORWARD);
                board.execute(player, movementCardUp);
                parent.getGameScreen().updateGraphics();
                //System.out.println(player);
                break;
            case Input.Keys.DOWN:
                MovementCard movementCardDown = new MovementCard(0, MovementType.ONE_BACKWARD);
                board.execute(player, movementCardDown);
                parent.getGameScreen().updateGraphics();
                break;
            case Input.Keys.RIGHT:
                Direction oldDirectionR = player.getDirection();
                RotationCard rotationCardRight = new RotationCard(0, RotationType.CLOCKWISE);
                board.execute(player, rotationCardRight);
                parent.getGameScreen().updateGraphics();
                //System.out.println(player);
                break;
            case Input.Keys.LEFT:
                Direction oldDirectionL = player.getDirection();
                RotationCard rotationCardLeft = new RotationCard(0, RotationType.COUNTER_CLOCKWISE);
                board.execute(player, rotationCardLeft);
                //player.getGraphics().animateRotation(oldDirectionL, player.getDirection());
                parent.getGameScreen().updateGraphics();
                //System.out.println(player);
                break;
        }

        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

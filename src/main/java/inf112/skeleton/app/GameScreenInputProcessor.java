package inf112.skeleton.app;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import inf112.skeleton.app.cards.*;
import inf112.skeleton.app.screens.GameScreen;

public class GameScreenInputProcessor extends InputListener {

    Player player;
    Board board;

    public GameScreenInputProcessor(Player player, Board board){
        this.player = player;
        this.board = board;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        super.keyDown(event, keycode);

        switch (keycode){

            case Input.Keys.UP:
                MovementCard movementCardUp = new MovementCard(0, MovementType.ONE_FORWARD);
                board.execute(player, movementCardUp);
                player.getGraphics().animateMove(player.getCol(), player.getRow(), 1);
                break;
            case Input.Keys.DOWN:
                MovementCard movementCardDown = new MovementCard(0, MovementType.ONE_BACKWARD);
                board.execute(player, movementCardDown);
                player.getGraphics().animateMove(player.getCol(), player.getRow(), 1);
                break;
            case Input.Keys.RIGHT:
                Direction oldDirectionR = player.getDirection();
                RotationCard rotationCardRight = new RotationCard(0, RotationType.CLOCKWISE);
                board.execute(player, rotationCardRight);
                player.getGraphics().animateRotation(oldDirectionR, player.getDirection());
                break;
            case Input.Keys.LEFT:
                Direction oldDirectionL = player.getDirection();
                RotationCard rotationCardLeft = new RotationCard(0, RotationType.COUNTER_CLOCKWISE);
                board.execute(player, rotationCardLeft);
                player.getGraphics().animateRotation(oldDirectionL, player.getDirection());
                break;
        }

        player.getGraphics().animate();

        return true;
    }
}

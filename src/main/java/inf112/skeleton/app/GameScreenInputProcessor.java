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
                player.getGraphics().animateMove(player.getCol(), player.getRow()+1, 1);
                MovementCard movementCard = new MovementCard(0, MovementType.ONE_FORWARD);
                board.execute(player, movementCard);
                player.getGraphics().animate();
                break;
            case Input.Keys.RIGHT:
                Direction oldDirection = player.getDirection();
                RotationCard rotationCard = new RotationCard(0, RotationType.CLOCKWISE);
                board.execute(player, rotationCard);

                player.getGraphics().animateRotation(oldDirection, player.getDirection());
                player.getGraphics().animate();
                break;
        }



        return true;
    }
}

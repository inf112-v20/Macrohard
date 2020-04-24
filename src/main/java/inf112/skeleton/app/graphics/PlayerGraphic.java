package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.screens.GameScreen;

public class PlayerGraphic extends Image {

    final float TILE_SIZE = GameScreen.TILE_SIZE;
    private final int START_Y_AXIS = 152;
    private final Player player;
    private Direction direction;
    private float degrees;


    SequenceAction sequenceAction = new SequenceAction();
    public boolean isVisible = true;

    public PlayerGraphic(Player player){
        super(new Texture("./assets/robots/robot" + player.getDirection().toString() + ".png"));

        this.player = player;
        player.setGraphic(this);
        direction = player.getDirection();
        degrees = 0f;

        setBounds(TILE_SIZE*player.getCol(), TILE_SIZE*player.getRow(), TILE_SIZE, TILE_SIZE);
        setOrigin(Align.center);
    }

    public Player getPlayer() {
        return player;
    }


    public void animateMove(int moves) {
        int row = player.getRow();
        int col = player.getCol();
        sequenceAction.addAction(Actions.delay(0,
                Actions.moveTo(TILE_SIZE * col, TILE_SIZE * row, 0.3f*Math.abs(moves))));
    }

    public void animateRotation() {
        Direction newDir = player.getDirection();
        degrees += Direction.getDegreesBetween(direction, newDir);
        sequenceAction.addAction(Actions.delay(0, Actions.rotateTo(degrees, 0.5f)));
        this.direction = newDir;
    }

    public void animateFall() {
        sequenceAction.addAction(Actions.fadeOut(0.5f));
        isVisible = false;
    }

    public void animateRespawn() {
        animateMove(1);
        sequenceAction.addAction(Actions.fadeIn(0.5f));
        isVisible = true;
    }

    public void animate(){
        addAction(sequenceAction);
    }

    public Direction getDirection() {
        return direction;
    }

}

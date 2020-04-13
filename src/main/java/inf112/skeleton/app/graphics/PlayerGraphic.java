package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.screens.GameScreen;

public class PlayerGraphic extends Image {

    final float PLAYER_WIDTH = GameScreen.TILE_SIZE;
    final float PLAYER_HEIGHT = GameScreen.TILE_SIZE;
    private final int START_Y_AXIS = 152;
    private final Player player;
    private Direction direction;
    private float degrees;

    SequenceAction sequenceAction = new SequenceAction();

    public PlayerGraphic(Player player){
        super(new Texture("./assets/robots/robot" + player.getDirection().toString() + ".png"));

        this.player = player;
        player.setGraphic(this);
        direction = player.getDirection();
        degrees = 0f;

        setBounds(PLAYER_WIDTH*player.getCol(), START_Y_AXIS + PLAYER_HEIGHT*player.getRow(), PLAYER_WIDTH, PLAYER_HEIGHT);
        setOrigin(Align.center);
    }

    public Player getPlayer() {
        return player;
    }

    public void updatePlayerGraphic(Card card, Direction oldDirection) {
        if (card instanceof MovementCard) {
            animateMoveDelayed(player.getCol(), player.getRow(), ((MovementCard) card).getMoveID());
        }
        else {
            animateRotationDelayed(oldDirection, player.getDirection());
        }
    }


    public void animateMoveDelayed(int newCol, int newRow, int moves) {
        sequenceAction.addAction(Actions.delay(1, Actions.moveTo(PLAYER_WIDTH*newCol, START_Y_AXIS + PLAYER_HEIGHT*newRow, 0.3f*Math.abs(moves))));
    }

    public void animateMove(int newCol, int newRow, int moves) {
        sequenceAction.addAction(Actions.delay(0, Actions.moveTo(PLAYER_WIDTH*newCol, START_Y_AXIS + PLAYER_HEIGHT*newRow, 0.3f*Math.abs(moves))));
    }

    public void animateRotationDelayed(Direction direction, Direction newDir) {
        this.direction = newDir;
        degrees += Direction.getDegreesBetween(direction, newDir);
        sequenceAction.addAction(Actions.delay(1, Actions.rotateTo(degrees, 0.5f)));
    }

    public void animateRotation(Direction direction, Direction newDir) {
        this.direction = newDir;
        degrees += Direction.getDegreesBetween(direction, newDir);
        sequenceAction.addAction(Actions.delay(0, Actions.rotateTo(degrees, 0.5f)));
    }

    public void animate(){
        addAction(sequenceAction);
    }

    public Direction getDirection() {
        return direction;
    }

    public void respawn() {
        setBounds(PLAYER_WIDTH*player.getCol(), START_Y_AXIS + PLAYER_HEIGHT*player.getRow(), PLAYER_WIDTH, PLAYER_HEIGHT);
        setOrigin(Align.center);
        sequenceAction.addAction(Actions.scaleTo(1, 1, 0.5f));
    }
}

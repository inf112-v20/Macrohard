package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.RotationCard;

public class PlayerGraphic extends Image {

    final float PLAYER_WIDTH = 60;
    final float PLAYER_HEIGHT = 60;
    private final Player player;

    SequenceAction sequenceAction = new SequenceAction();

    public PlayerGraphic(Player player){
        super(new Texture("./assets/robots/robot2.png"));

        this.player = player;
        player.setGraphic(this);

        setBounds(PLAYER_WIDTH*player.getCol(),
                (PLAYER_HEIGHT*player.getRow())+(PLAYER_HEIGHT*2),
                PLAYER_WIDTH,
                PLAYER_HEIGHT);
        setOrigin(Align.center);
    }

    public Player getPlayer() {
        return player;
    }

    public void updatePlayerGraphic(Card card, Direction oldDirection) {
        if (card instanceof MovementCard) {
            System.out.println("Animating move: " + card);
            animateMove(player.getCol(), player.getRow(), ((MovementCard) card).getMoveID());
        }
        else if (card instanceof RotationCard) {
            System.out.println("Animating rotation: " + card);
            animateRotation(oldDirection, player.getDirection());
        } else {
            System.out.println("This program-slot is empty");
        }
    }


    public void animateMove(int newCol, int newRow, int moves) {
        sequenceAction.addAction(Actions.delay(1, Actions.moveTo(PLAYER_WIDTH*newCol, PLAYER_HEIGHT*newRow + PLAYER_HEIGHT*2, 0.3f*Math.abs(moves))));
    }

    public void animateRotation(Direction direction, Direction newDir) {
        float degrees = (direction.ordinal() - newDir.ordinal())*90;
        if(degrees == 270){
            degrees = -90;
        }
        else if(degrees == -270){
            degrees = 90;
        }
        sequenceAction.addAction(Actions.delay(1, Actions.rotateBy(degrees, 0.5f)));
    }

    public void animate(){
        addAction(sequenceAction);
    }

}

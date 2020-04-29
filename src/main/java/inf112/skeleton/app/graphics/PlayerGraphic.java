package inf112.skeleton.app.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.screens.GameScreen;

public class PlayerGraphic extends Image {

    private final float TILE_SIZE = GameScreen.TILE_SIZE;

    private final Player player;

    private Direction direction;
    private float degrees;

    public boolean isVisible = true;

    public PlayerGraphic(Player player){
        super(new Texture("./assets/robots/robot" + player.getDirection().toString() + ".png"));

        this.player = player;
        player.setPlayerGraphic(this);

        direction = player.getDirection();
        degrees = 0f;

        setBounds(TILE_SIZE * player.getCol(), TILE_SIZE * player.getRow(), TILE_SIZE, TILE_SIZE);
        setOrigin(Align.center);
    }

    public void animateMove() {
        int row = player.getRow();
        int col = player.getCol();
        addAction(Actions.moveTo(TILE_SIZE * col, TILE_SIZE * row, 0.3f));
    }

    public void animateRotation() {
        Direction newDir = player.getDirection();
        degrees += Direction.getDegreesBetween(direction, newDir);
        addAction(Actions.rotateTo(degrees, 0.5f));
        this.direction = newDir;
    }

    public void animateFall() {
        addAction(Actions.delay(0.5f, Actions.fadeOut(0.5f)));
        isVisible = false;
    }

    public void animateDestruction() {
        addAction(Actions.fadeOut(0.2f));
        isVisible = false;
    }

    public void animateReboot() {
        animateMove();
        animateRotation();
        addAction(Actions.delay(0.5f, Actions.fadeIn(0.5f)));
        isVisible = true;
    }

}

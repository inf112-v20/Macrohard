package inf112.skeleton.app.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;

import java.io.File;

public class PlayerGraphic extends Image {

    final float PLAYER_WIDTH = 60;
    final float PLAYER_HEIGHT = 60;

    public PlayerGraphic(Player player){
        super(new Texture("./assets/robots/robot2.png"));
        setBounds(PLAYER_WIDTH*player.getCol(), (PLAYER_HEIGHT*player.getRow())+(PLAYER_HEIGHT*2), PLAYER_WIDTH, PLAYER_WIDTH);

        setOrigin(Align.center);
        player.setGraphic(this);
    }

    public void animateMove(int newCol, int newRow, int moves) {
        addAction(Actions.moveTo(PLAYER_WIDTH*newCol, PLAYER_HEIGHT*newRow + PLAYER_HEIGHT*2, 0.3f*moves));
    }

    public void animateRotation(Direction direction, Direction newDir) {
        float degrees = (direction.ordinal() - newDir.ordinal())*90;
        if(degrees == 270){
            degrees = -90;
        }
        if(degrees == -270){
            degrees = 90;
        }
        addAction(Actions.rotateBy(degrees, 0.5f));
        System.out.println(degrees);
    }
}

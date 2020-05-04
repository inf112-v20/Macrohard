package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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

    // ANIMATION
    private static final int HORIZONTAL_FRAME_COLS = 8, HORIZONTAL_FRAME_ROWS = 1;
    private static final int BACK_FRAME_COLS = 8, BACK_FRAME_ROWS = 1;
    private static final int FRONT_FRAME_COLS = 8, FRONT_FRAME_ROWS = 1;
    Animation<TextureRegion> idleHorizontalAnimation;
    Texture idleHorizontalSheet;

    Animation<TextureRegion> idleBackAnimation;
    Texture idleBackSheet;

    Animation<TextureRegion> idleFrontAnimation;
    Texture idleFrontSheet;

    float stateTime;

    private static float staticTimeModifier = 0;
    private float timeModifier;


    public PlayerGraphic(Player player) {
        super(new Texture("./assets/robots/robot" + player.getDirection().toString() + ".png"));
        this.player = player;
        player.setPlayerGraphic(this);
        direction = player.getDirection();
        degrees = 0f;

        staticTimeModifier += 0.30f;
        timeModifier = staticTimeModifier;

        String[] colors = {"", "_BLACK", "_GREEN", "_PURPLE", "_WHITE", "_YELLOW", "_BLUE2", "", "", "", ""};
        String colorModifier = colors[player.name()];

        idleHorizontalSheet = new Texture(Gdx.files.internal("./assets/sprites/robotSide" + colorModifier + ".png"));
        TextureRegion[][] idleHorizontalTR = TextureRegion.split(idleHorizontalSheet,
                idleHorizontalSheet.getWidth() / HORIZONTAL_FRAME_COLS,
                idleHorizontalSheet.getHeight() / HORIZONTAL_FRAME_ROWS);
        TextureRegion[] idleHorizontalFrames = new TextureRegion[HORIZONTAL_FRAME_COLS * HORIZONTAL_FRAME_ROWS];
        int index = 0;
        for(int i = 0; i < HORIZONTAL_FRAME_ROWS; i++){
            for(int j = 0; j < HORIZONTAL_FRAME_COLS; j++){
                idleHorizontalFrames[index++] = idleHorizontalTR[i][j];
            }
        }

        idleBackSheet = new Texture(Gdx.files.internal("./assets/sprites/robotBack" + colorModifier + ".png"));
        TextureRegion[][] idleBackTR = TextureRegion.split(idleBackSheet,
                idleBackSheet.getWidth() / BACK_FRAME_COLS,
                idleBackSheet.getHeight() / BACK_FRAME_ROWS);
        TextureRegion[] idleBackFrames = new TextureRegion[BACK_FRAME_COLS * BACK_FRAME_ROWS];
        index = 0;
        for(int i = 0; i < BACK_FRAME_ROWS; i++){
            for(int j = 0; j < BACK_FRAME_COLS; j++){
                idleBackFrames[index++] = idleBackTR[i][j];
            }
        }

        idleFrontSheet = new Texture(Gdx.files.internal("./assets/sprites/robotFront" + colorModifier + ".png"));
        TextureRegion[][] idleFrontTR = TextureRegion.split(idleFrontSheet,
                idleFrontSheet.getWidth() / FRONT_FRAME_COLS,
                idleFrontSheet.getHeight() / FRONT_FRAME_ROWS);
        TextureRegion[] idleFrontFrames = new TextureRegion[FRONT_FRAME_COLS * FRONT_FRAME_ROWS];
        index = 0;
        for(int i = 0; i < FRONT_FRAME_ROWS; i++){
            for(int j = 0; j < FRONT_FRAME_COLS; j++){
                idleFrontFrames[index++] = idleFrontTR[i][j];
            }
        }


        idleHorizontalAnimation = new Animation<>(0.25f, idleHorizontalFrames);
        idleBackAnimation = new Animation<>(0.25f, idleBackFrames);
        idleFrontAnimation = new Animation<>(0.25f, idleFrontFrames);
        stateTime = 0f;

        setBounds(TILE_SIZE * player.getCol(), TILE_SIZE * player.getRow(), TILE_SIZE, TILE_SIZE);
        setOrigin(Align.center);
    }

    public void updateAnimationFrame(float stateTime){
        stateTime += timeModifier;

        TextureRegion currentHorizontalFrame = idleHorizontalAnimation.getKeyFrame(stateTime, true);
        TextureRegion currentBackFrame = idleBackAnimation.getKeyFrame(stateTime, true);
        TextureRegion currentFrontFrame = idleFrontAnimation.getKeyFrame(stateTime, true);
        if(player.getDirection() == Direction.EAST){
            if(currentHorizontalFrame.isFlipY()){
                currentHorizontalFrame.flip(false, true);
            }
            if(!currentHorizontalFrame.isFlipX()){
                currentHorizontalFrame.flip(true, false);
            }
            setDrawable(new SpriteDrawable(new Sprite(currentHorizontalFrame)));
        }else if(player.getDirection() == Direction.WEST){
            if(currentHorizontalFrame.isFlipY()) {
                currentHorizontalFrame.flip(false, true);
            }
            if(currentHorizontalFrame.isFlipX()){
                currentHorizontalFrame.flip(true, false);
            }
            setDrawable(new SpriteDrawable(new Sprite(currentHorizontalFrame)));
        }
        else if(player.getDirection() == Direction.NORTH){
            setDrawable(new SpriteDrawable(new Sprite(currentBackFrame)));
        }
        else if(player.getDirection() == Direction.SOUTH){
            setDrawable(new SpriteDrawable(new Sprite(currentFrontFrame)));
        }

    }

    public void animateMove() {
        int row = player.getRow();
        int col = player.getCol();
        addAction(Actions.moveTo(TILE_SIZE * col, TILE_SIZE * row, 0.3f));
        if (player.isDestroyed()) {
            animateDestruction();
        }
    }

    public void animateRotation() {
        Direction newDir = player.getDirection();
        degrees += Direction.getDegreesBetween(direction, newDir);
        //addAction(Actions.rotateTo(degrees, 0.5f));
        this.direction = newDir;
    }

    public void animateFall() {
        addAction(Actions.delay(0.5f, Actions.fadeOut(0.5f)));
        isVisible = false;
    }

    public void animateDestruction() {
        addAction(Actions.fadeOut(0.5f));
        isVisible = false;
    }

    public void animateReboot() {
        animateMove();
        animateRotation();
        addAction(Actions.delay(0.5f, Actions.fadeIn(0.5f)));
        isVisible = true;
    }

}

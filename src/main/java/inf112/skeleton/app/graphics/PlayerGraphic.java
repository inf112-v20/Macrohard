package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
    private final String color;
    public boolean isVisible = true;

    // ANIMATION
    private static final String[] COLORS = new String[]{"", "_BLACK", "_GREEN", "_PURPLE", "_WHITE", "_YELLOW", "_BLUE2", "", "", "", ""};
    private static final int HORIZONTAL_FRAME_COLS = 8;
    private static final int HORIZONTAL_FRAME_ROWS = 1;
    private static final int BACK_FRAME_COLS = 8;
    private static final int BACK_FRAME_ROWS = 1;
    private static final int FRONT_FRAME_COLS = 8;
    private static final int FRONT_FRAME_ROWS = 1;

    private Animation<TextureRegion> idleHorizontalAnimation;
    private Animation<TextureRegion> idleBackAnimation;
    private Animation<TextureRegion> idleFrontAnimation;

    private static float staticTimeModifier = 0;
    private float timeModifier;


    public PlayerGraphic(Player player) {
        super(new Texture("./assets/robots/robot" + player.getDirection().toString() + ".png"));
        this.player = player;
        player.setPlayerGraphic(this);

        staticTimeModifier += 0.30f;
        timeModifier = staticTimeModifier;

        color = COLORS[player.getName()];
        TextureRegion[] idleHorizontalFrames = getAnimationFrames("Side", HORIZONTAL_FRAME_ROWS, HORIZONTAL_FRAME_COLS);
        TextureRegion[] idleBackFrames = getAnimationFrames("Back", BACK_FRAME_ROWS, BACK_FRAME_COLS);
        TextureRegion[] idleFrontFrames = getAnimationFrames("Front", FRONT_FRAME_ROWS, FRONT_FRAME_COLS);

        idleHorizontalAnimation = new Animation<>(0.25f, idleHorizontalFrames);
        idleBackAnimation = new Animation<>(0.25f, idleBackFrames);
        idleFrontAnimation = new Animation<>(0.25f, idleFrontFrames);

        setBounds(TILE_SIZE * player.getCol(), TILE_SIZE * player.getRow(), TILE_SIZE, TILE_SIZE);
        setOrigin(Align.center);
    }

    private TextureRegion[] getAnimationFrames(String orientation, int rows, int cols) {
        Texture idleSheet = new Texture(Gdx.files.internal("./assets/sprites/robot" + orientation + color + ".png"));
        TextureRegion[][] idleTR = TextureRegion.split(idleSheet, idleSheet.getWidth() / cols, idleSheet.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = idleTR[i][j];
            }
        }
        return frames;
    }

    public void updateAnimationFrame(float stateTime) {
        stateTime += timeModifier;
        TextureRegion currentFrame;
        if (player.getDirection() == Direction.EAST) {
            currentFrame = idleHorizontalAnimation.getKeyFrame(stateTime, true);
            if (currentFrame.isFlipY()) {
                currentFrame.flip(false, true);
            }
            if (!currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        } else if (player.getDirection() == Direction.WEST) {
            currentFrame = idleHorizontalAnimation.getKeyFrame(stateTime, true);
            if (currentFrame.isFlipY()) {
                currentFrame.flip(false, true);
            }
            if (currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        } else if (player.getDirection() == Direction.NORTH) {
            currentFrame = idleBackAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = idleFrontAnimation.getKeyFrame(stateTime, true);
        }
        setDrawable(new SpriteDrawable(new Sprite(currentFrame)));
    }

    public void animateMove() {
        int row = player.getRow();
        int col = player.getCol();
        addAction(Actions.moveTo(TILE_SIZE * col, TILE_SIZE * row, 0.3f));
        if (player.isDestroyed()) {
            animateDestruction();
        }
    }

    public void animateTakeDamage() {
        Color color = getColor();
        addAction(Actions.color(new Color(Color.RED), 0.3f));
        addAction(Actions.delay(0.5f, Actions.color(color, 0.2f)));
    }

    public void animateDestruction() {
        addAction(Actions.fadeOut(0.5f));
        isVisible = false;
    }

    public void animateReboot() {
        animateMove();
        addAction(Actions.delay(0.5f, Actions.fadeIn(0.5f)));
        isVisible = true;
    }

}

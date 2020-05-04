package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.cards.Card;

import java.io.File;
import java.util.Arrays;
import java.util.PriorityQueue;

public class CardGraphic extends Image {

    public final static int CARD_WIDTH = 100;
    public final static int CARD_HEIGHT = 100;
    public final static float CARD_ASPECT = 1.5f;
    public final static float CARD_MAX_SCALE = 1.2f;
    public final static int X_PADDING = 0;
    public final static int Y_PADDING = -150;
    public final static int VERTICAL_PROGRAM_ALIGNMENT = -80;
    public final static int HORIZONTAL_PROGRAM_ALIGNMENT = 450;
    public final static int VERTICAL_HAND_MARGIN = 75;

    public static PriorityQueue<Integer> registerIndices = new PriorityQueue<>(Arrays.asList(1, 2, 3, 4, 5));
    private static int xStartPosition = VERTICAL_HAND_MARGIN;
    private static BitmapFont.BitmapFontData fontData = new BitmapFont(Gdx.files.getFileHandle("./assets/fonts/arial.fnt",
            Files.FileType.Internal)).getData();
    private static Pixmap fontPixmap = new Pixmap(Gdx.files.internal(fontData.imagePaths[0]));
    private final static Color BLUE = new Color(0.1f, 0.7f, 0.9f, 0.3f);
    private final static Color RED = new Color(1f, 0f, 0f, 0.3f);

    private final Card[] clientProgram;
    private final Card card;
    private float initialX;

    private final File file;
    private Pixmap pixMap;
    private int registerIndex = -1;

    public CardGraphic(Player client, Card card) {
        super(new Texture("./assets/cards/" + card.getName() + ".png"));
        this.file = new File("./assets/cards/" + card.getName() + ".png");
        this.initialX = xStartPosition + (((CARD_WIDTH + X_PADDING) * client.getDamageTokens()) / 2f);
        this.clientProgram = client.getProgram();
        this.card = card;
        card.setCardGraphic(this);

        setBounds(initialX, Y_PADDING, CARD_WIDTH, CARD_HEIGHT * CARD_ASPECT);
        pixMap = getInitialPixMap();
        setDrawable(getSpriteDrawable(pixMap));

        addListener(new CardGraphicListener(this));

        xStartPosition += CARD_WIDTH + X_PADDING;
    }

    private SpriteDrawable getSpriteDrawable(Pixmap pixMap) {
        return new SpriteDrawable(new Sprite(new Texture(pixMap)));
    }

    private Pixmap getInitialPixMap() {
        if (pixMap != null) {
            pixMap.dispose();
        }
        Pixmap pixmap = new Pixmap(new FileHandle(file));

        int priority = card.getPriority();
        String priorityString = Integer.toString(priority);

        if (priorityString.length() == 2) {
            priorityString = "0" + priorityString;
        }

        for (int i = 0; i < priorityString.length(); i++) {
            BitmapFont.Glyph glyph = fontData.getGlyph(priorityString.charAt(i));
            pixmap.drawPixmap(fontPixmap, 120 + (i * 25), 30,
                    glyph.srcX, glyph.srcY, glyph.width, glyph.height);
        }
        return pixmap;
    }

    private Pixmap getHighlightedPixMap(int registerIndex, boolean locked) {
        pixMap = getInitialPixMap();

        // Draw highlight
        Color highlight = locked ? RED : BLUE;
        pixMap.setColor(highlight);
        pixMap.fillRectangle(0, 0, 320, 350);

        // Draw register index
        BitmapFont.Glyph glyph = fontData.getGlyph(("" + registerIndex).charAt(0));
        pixMap.drawPixmap(fontPixmap, 10, 10,
                glyph.srcX, glyph.srcY, glyph.width, glyph.height);

        return pixMap;
    }

    public void lock() {
        pixMap = getHighlightedPixMap(registerIndex, true);
        pixMap.setColor(new Color(1f, 0f, 0f, 0.3f));
        setDrawable(getSpriteDrawable(pixMap));
    }

    public void unlock() {
        pixMap = getHighlightedPixMap(registerIndex, false);
        setDrawable(getSpriteDrawable(pixMap));
    }

    private boolean notLocked() {
        return !card.isLocked();
    }

    public boolean isSelected() {
        return card.isSelected();
    }

    public void select() {
        if (registerIndices.isEmpty()) {
            return;
        }
        registerIndex = registerIndices.poll();
        card.select();
        clientProgram[registerIndex - 1] = card;
        pixMap = getHighlightedPixMap(registerIndex, false);
        setDrawable(getSpriteDrawable(pixMap));
    }

    public void deselect() {
        card.deselect();
        clientProgram[registerIndex - 1] = null;
        registerIndices.add(registerIndex);
        registerIndex = -1;
        pixMap = getInitialPixMap();
        setDrawable(getSpriteDrawable(pixMap));
    }

    public float getInitialX() {
        return initialX;
    }

    public static void reset(Player client) {
        xStartPosition = VERTICAL_HAND_MARGIN;
        int lockedCards = client.getNrOfLockedProgramRegisters();
        Integer[] indices = new Integer[5 - lockedCards];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i + 1;
        }
        registerIndices = new PriorityQueue<>(Arrays.asList(indices));
    }

    private static class CardGraphicListener extends ClickListener {

        private final CardGraphic parent;

        public CardGraphicListener(CardGraphic parent) {
            super();
            this.parent = parent;
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (parent.notLocked()) {
                if (parent.isSelected()) {
                    parent.deselect();
                } else {
                    parent.select();
                }
            }
            return true;
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            if (!parent.isSelected()) {
                parent.addAction(Actions.scaleTo(CARD_MAX_SCALE, CARD_MAX_SCALE, 0.2f));
                parent.addAction(Actions.moveBy(-((CARD_MAX_SCALE - 1) * 50), 0, 0.2f));
            }
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            if (!parent.isSelected()) {
                parent.addAction(Actions.moveTo(parent.getInitialX(), Y_PADDING, 0.1f));
                parent.addAction(Actions.scaleTo(1f, 1f, 0.1f));
            } else if (parent.notLocked()) {
                float yPlacement = (HORIZONTAL_PROGRAM_ALIGNMENT - Y_PADDING) - ((parent.registerIndex - 1f) * (CARD_HEIGHT * 1.2f));
                parent.addAction(Actions.moveTo(VERTICAL_PROGRAM_ALIGNMENT, yPlacement, 0.1f));
                parent.addAction(Actions.scaleTo(0.8f, 0.8f, 0.1f));
            }
        }
    }

}

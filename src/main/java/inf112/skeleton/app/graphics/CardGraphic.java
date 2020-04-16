package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
    public final static int Y_PADDING = 2;
    public final static int VERTICAL_PROGRAM_ALIGNMENT = 905;
    public final static int HORIZONTAL_PROGRAM_ALIGNMENT = 692;
    public final static int VERTICAL_HAND_MARGIN = 2;

    public static PriorityQueue<Integer> programIndices = new PriorityQueue<>(Arrays.asList(1, 2, 3, 4, 5));
    private static int xStartPosition = VERTICAL_HAND_MARGIN;

    private final Card card;
    private int cardIndex;
    private float initialX;

    private final File file;

    private boolean isSelected;

    public CardGraphic(final Card card){
        super(new Texture("./assets/cards/" + card.getName() + ".png"));
        this.card = card;
        this.file = new File("./assets/cards/" + card.getName() + ".png");
        this.initialX = xStartPosition;

        setBounds(xStartPosition, Y_PADDING, CARD_WIDTH, CARD_HEIGHT*CARD_ASPECT);
        addListener(new CardGraphicListener(this));

        xStartPosition += CARD_WIDTH + X_PADDING;

        FileHandle handle = Gdx.files.getFileHandle("./assets/fonts/arial.fnt",
                Files.FileType.Internal);
        BitmapFont font = new BitmapFont(handle);

        // ### PRIORITY ###
        BitmapFont.BitmapFontData data = font.getData();
        Pixmap fontPixmap = new Pixmap(Gdx.files.internal(data.imagePaths[0]));

        Pixmap pixmap = new Pixmap(new FileHandle(file));

        int priority = card.getPriority();
        String priorityAsString = Integer.toString(priority);

        if(priorityAsString.length() == 2){
            priorityAsString = "0" + priorityAsString;
        }

        for(int i = 0; i<priorityAsString.length(); i++){
            BitmapFont.Glyph partialPriorityGlyph = data.getGlyph(priorityAsString.charAt(i));
            pixmap.drawPixmap(fontPixmap, 120+(i*25), 30,
                    partialPriorityGlyph.srcX, partialPriorityGlyph.srcY, partialPriorityGlyph.width, partialPriorityGlyph.height);
        }

        Texture texture = new Texture(pixmap);
        setDrawable(new SpriteDrawable(new Sprite(texture)));

    }

    public Card getCard() {
        return card;
    }

    public File getFile() {
        return file;
    }

    public void reset() {
        xStartPosition = VERTICAL_HAND_MARGIN;
        programIndices = new PriorityQueue<>(Arrays.asList(1, 2, 3, 4, 5));
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        card.selected = selected;
        if (!selected) {
            programIndices.add(card.programIndex);
            card.programIndex = -1;
        }
        else if (!programIndices.isEmpty()) {
            cardIndex = programIndices.poll();
            card.setProgramIndex(cardIndex);
        }
    }

    public float getInitialX() {
        return initialX;
    }
}

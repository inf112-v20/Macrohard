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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import static inf112.skeleton.app.graphics.CardGraphic.*;


public class CardGraphicListener extends ClickListener {

    private final CardGraphic graphic;
    private final FileHandle fileHandle;

    private Texture texture;
    private Pixmap pixmap;
    private int registerIndex;
    private Pixmap fontPixmap;
    private BitmapFont.BitmapFontData fontData;

    public CardGraphicListener(CardGraphic graphic) {
        super();
        this.graphic = graphic;
        this.fileHandle = new FileHandle(graphic.getFile());
        this.pixmap = new Pixmap(fileHandle);
        this.texture = new Texture(pixmap);

        // FONT
        FileHandle handle = Gdx.files.getFileHandle("./assets/fonts/arial.fnt",
                Files.FileType.Internal);
        BitmapFont font = new BitmapFont(handle);
        this.fontData = font.getData();
        this.fontPixmap = new Pixmap(Gdx.files.internal(fontData.imagePaths[0]));
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (graphic.isSelected()) {
            graphic.setSelected(false);

            // Reset highlight
            pixmap = new Pixmap(fileHandle);
            texture = new Texture(pixmap);
        } else if (!CardGraphic.programIndices.isEmpty()) {
            registerIndex = CardGraphic.programIndices.peek();
            graphic.setSelected(true);

            BitmapFont.Glyph glyph = fontData.getGlyph(("" + registerIndex).charAt(0));

            // Draw the character onto our base pixel-map, with a padding of 10
            pixmap.drawPixmap(fontPixmap, 10, 10,
                    glyph.srcX, glyph.srcY, glyph.width, glyph.height);

            // Draw highlight
            pixmap.setColor(new Color(0.1f, 0.7f, 0.9f, 0.3f));
            pixmap.fillRectangle(0, 0, 320, 350);
        }
        // ### PRIORITY ###
        int priority = graphic.getCard().getPriority();
        String priorityAsString = Integer.toString(priority);

        for (int i = 0; i < priorityAsString.length(); i++) {
            BitmapFont.Glyph partialPriorityGlyph = this.fontData.getGlyph(priorityAsString.charAt(i));
            pixmap.drawPixmap(fontPixmap, 120 + (i * 25), 30,
                    partialPriorityGlyph.srcX, partialPriorityGlyph.srcY, partialPriorityGlyph.width, partialPriorityGlyph.height);
        }
        texture = new Texture(pixmap);

        // DRAW
        graphic.setDrawable(new SpriteDrawable(new Sprite(texture)));
        return true;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if (!graphic.isSelected()) {
            graphic.addAction(Actions.scaleTo(CARD_MAX_SCALE, CARD_MAX_SCALE, 0.2f));
            graphic.addAction(Actions.moveBy(-((CARD_MAX_SCALE - 1) * 50), 0, 0.2f));
        }
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        if (!graphic.isSelected()) {
            graphic.addAction(Actions.moveTo(graphic.getInitialX(), Y_PADDING, 0.1f));
            graphic.addAction(Actions.scaleTo(1f, 1f, 0.1f));
        } else {
            float yPlacement = HORIZONTAL_PROGRAM_ALIGNMENT - Y_PADDING;
            yPlacement -= (registerIndex - 1) * (CARD_HEIGHT * 1.2);
            graphic.addAction(Actions.moveTo(VERTICAL_PROGRAM_ALIGNMENT, yPlacement, 0.1f));
            graphic.addAction(Actions.scaleTo(0.8f, 0.8f, 0.1f));
        }
    }


}

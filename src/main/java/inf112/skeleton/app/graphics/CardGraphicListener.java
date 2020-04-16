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
    private int cardIndex;

    public CardGraphicListener(CardGraphic graphic) {
        super();
        this.graphic = graphic;
        this.fileHandle = new FileHandle(graphic.getFile());
        this.pixmap = new Pixmap(fileHandle);
        this.texture = new Texture(pixmap);
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if(graphic.isSelected()){
            graphic.setSelected(false);

            // Reset highlight
            pixmap = new Pixmap(fileHandle);
            texture = new Texture(pixmap);
        }
        else if (!CardGraphic.programIndices.isEmpty()){
            cardIndex = CardGraphic.programIndices.peek();
            graphic.setSelected(true);

            FileHandle handle = Gdx.files.getFileHandle("./assets/fonts/arial.fnt",
                    Files.FileType.Internal);
            BitmapFont font = new BitmapFont(handle);

            // get the glyph info
            BitmapFont.BitmapFontData data = font.getData();
            Pixmap fontPixmap = new Pixmap(Gdx.files.internal(data.imagePaths[0]));
            BitmapFont.Glyph glyph = data.getGlyph(("" + cardIndex).charAt(0));

            // Draw the character onto our base pixel-map, with a padding of 10
            pixmap.drawPixmap(fontPixmap, 10, 10,
                    glyph.srcX, glyph.srcY, glyph.width, glyph.height);

            // Draw highlight
            pixmap.setColor(new Color(0.1f, 0.7f, 0.9f, 0.3f));
            pixmap.fillRectangle(0, 0, 320, 350);
            texture = new Texture(pixmap);
        }
        graphic.setDrawable(new SpriteDrawable(new Sprite(texture)));
        return true;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
        if(!graphic.isSelected()) {
            graphic.addAction(Actions.scaleTo(CARD_MAX_SCALE, CARD_MAX_SCALE, 0.2f));
            graphic.addAction(Actions.moveBy(-((CARD_MAX_SCALE-1)*50), 0, 0.2f));
        }
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        if(!graphic.isSelected()) {
            graphic.addAction(Actions.moveTo(graphic.getInitialX(), 0, 0.1f));
            graphic.addAction(Actions.scaleTo(1f, 1f, 0.1f));
        }
        else{
            float yPlacement = HORIZONTAL_PROGRAM_ALIGNMENT - Y_PADDING;
            yPlacement -= (cardIndex-1) * (CARD_HEIGHT*1.2);
            graphic.addAction(Actions.moveTo(VERTICAL_PROGRAM_ALIGNMENT, yPlacement, 0.1f));
            graphic.addAction(Actions.scaleTo(0.8f, 0.8f, 0.1f));
        }
    }



}

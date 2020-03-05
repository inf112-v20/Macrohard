package inf112.skeleton.app;

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

import java.io.File;
import java.util.Arrays;

public class CardGraphic extends Image {

    static int statx = 100/2;
    static int initiatedCards = 0;

    static int[] indecies = {1,2,3,4,5,6,7,8,9};

    private final int CARD_WIDTH = 100;
    private final int CARD_HEIGHT = 150;
    private final float CARD_ASPECT = 1.5f;
    private final float CARD_MAX_SCALE = 1.2f;
    private final int PADDING = 10;

    Texture texture;
    Pixmap pixmap;
    Pixmap originalPixmap;

    private int cardIndex;
    private float initialXPosition;

    FileHandle fileHandle;

    private boolean isSelected;

    public CardGraphic(Card card){
        super(new Texture("./assets/cards/exampleCard.png"));
        initiatedCards++;

        initialXPosition = statx - CARD_WIDTH/2 + PADDING;

        if(card instanceof MovementCard){
            File png = new File("./assets/cards/movementCard.png");
            fileHandle = new FileHandle(png);
            pixmap = new Pixmap(fileHandle);
            originalPixmap = new Pixmap(fileHandle);
            texture = new Texture(pixmap);
            setDrawable(new SpriteDrawable(new Sprite(texture)));
        }else if (card instanceof RotationCard){
            File png = new File("./assets/cards/rotationCard.png");
            fileHandle = new FileHandle(png);
            pixmap = new Pixmap(fileHandle);
            originalPixmap = new Pixmap(fileHandle);
            texture = new Texture(pixmap);
            setDrawable(new SpriteDrawable(new Sprite(texture)));
        }

        setBounds(initialXPosition, PADDING, 100, 100*CARD_ASPECT);

        statx+=CARD_WIDTH+PADDING;
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Arrays.sort(indecies);

                if(isSelected){
                    isSelected = false;

                    int[] newIndecies = new int[indecies.length+1];
                    newIndecies[0] = cardIndex;
                    for(int i = 1; i<newIndecies.length; i++){
                        newIndecies[i] = indecies[i-1];
                    }
                    indecies = newIndecies;

                    texture = new Texture(originalPixmap);
                    setDrawable(new SpriteDrawable(new Sprite(texture)));

                    // Reset highlight
                    pixmap = new Pixmap(fileHandle);
                }else {
                    isSelected = true;

                    cardIndex = indecies[0];
                    int[] newIndecies = new int[indecies.length-1];
                    for(int i = 1; i<indecies.length; i++){
                        newIndecies[i-1] = indecies[i];
                    }
                    indecies = newIndecies;

                    FileHandle handle = Gdx.files.getFileHandle("./assets/fonts/arial.fnt",
                            Files.FileType.Internal);
                    BitmapFont font = new BitmapFont(handle);

                    // get the glypth info
                    BitmapFont.BitmapFontData data = font.getData();
                    Pixmap fontPixmap = new Pixmap(Gdx.files.internal(data.imagePaths[0]));
                    BitmapFont.Glyph glyph = data.getGlyph(("" + cardIndex).charAt(0));

                    // Draw the character onto our base pixmap, with a padding of 10
                    pixmap.drawPixmap(fontPixmap, 10, 10,
                            glyph.srcX, glyph.srcY, glyph.width, glyph.height);

                    // Draw highlight
                    pixmap.setColor(new Color(0.1f, 0.7f, 0.9f, 0.3f));
                    pixmap.fillRectangle(0, 0, 128, 193);

                    texture = new Texture(pixmap);
                    setDrawable(new SpriteDrawable(new Sprite(texture)));
                }
                return true;
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(!isSelected) {
                    addAction(Actions.scaleTo(CARD_MAX_SCALE, CARD_MAX_SCALE, 0.2f));
                    addAction(Actions.moveBy(-((CARD_MAX_SCALE-1)*50), 0, 0.2f));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                addAction(Actions.scaleTo(1, 1, 0.1f));
                if(!isSelected) {
                    addAction(Actions.moveTo(initialXPosition, PADDING, 0.1f));
                    addAction(Actions.scaleTo(1f, 1f, 0.1f));
                }else{
                    float xPlacement = Gdx.graphics.getWidth()-CARD_WIDTH-PADDING;
                    float yPlacement = Gdx.graphics.getHeight()-CARD_HEIGHT-PADDING;
                    yPlacement -= (cardIndex-1) * CARD_HEIGHT;
                    addAction(Actions.moveTo(xPlacement, yPlacement, 0.1f));
                }

            }
        });

    }



}

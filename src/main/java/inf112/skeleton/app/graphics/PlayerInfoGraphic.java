package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.screens.GameScreen;

import java.io.File;

public class PlayerInfoGraphic extends Image {

    private final Player player;

    private Pixmap fontPixmap;
    private Pixmap pixmap;
    private BitmapFont.BitmapFontData fontData;

    public PlayerInfoGraphic(Player player, GameScreen parent, MapProperties mapProperties) {
        super(new Texture("./assets/PlayerInfoBackground.png"));

        this.player = player;
        player.setInfoGraphic(this);

        int maxHeight = RoboRallyApplication.screenHeight;
        int boardWidth = (int) mapProperties.get("tilewidth") * (int) mapProperties.get("width");

        setBounds(boardWidth + 5, maxHeight - 341 - ((player.name()-1) * 110), 100, 100);
        resetPixmaps();
        updateValues();
    }

    public void resetPixmaps() {
        File file = new File("./assets/PlayerInfoBackground.png");
        this.pixmap = new Pixmap(new FileHandle(file));

        //FONT INIT
        FileHandle handle = Gdx.files.getFileHandle("./assets/fonts/ArialFull.fnt",
                Files.FileType.Internal);
        BitmapFont font = new BitmapFont(handle);
        this.fontData = font.getData();
        this.fontPixmap = new Pixmap(Gdx.files.internal(fontData.imagePaths[0]));
    }

    private void drawName() {
        String playerName = "PLAYER " + (player.name());
        for (int i = 0; i < playerName.length(); i++) {
            BitmapFont.Glyph partialStringGlyph = fontData.getGlyph(playerName.charAt(i));
            pixmap.drawPixmap(fontPixmap, 10 + 35 * i, 10,
                    partialStringGlyph.srcX, partialStringGlyph.srcY, partialStringGlyph.width, partialStringGlyph.height);
        }
    }

    private void drawLifeTokens() {
        String life = "LIFE  " + player.getLifeTokens();
        for (int i = 0; i < life.length(); i++) {
            BitmapFont.Glyph partialStringGlyph = fontData.getGlyph(life.charAt(i));
            pixmap.drawPixmap(fontPixmap, 10 + 35 * i, 200,
                    partialStringGlyph.srcX, partialStringGlyph.srcY, partialStringGlyph.width, partialStringGlyph.height);
        }
    }

    private void drawDamage() {
        String health = "DMG   " + player.getDamageTokens();
        for (int i = 0; i < health.length(); i++) {
            BitmapFont.Glyph partialStringGlyph = fontData.getGlyph(health.charAt(i));
            pixmap.drawPixmap(fontPixmap, 10 + 35 * i, 270,
                    partialStringGlyph.srcX, partialStringGlyph.srcY, partialStringGlyph.width, partialStringGlyph.height);
        }
    }

    private void drawFlag() {
        String life = "FLAG  " + player.getPreviousFlag();
        for (int i = 0; i < life.length(); i++) {
            BitmapFont.Glyph partialStringGlyph = fontData.getGlyph(life.charAt(i));
            pixmap.drawPixmap(fontPixmap, 10 + 35 * i, 340,
                    partialStringGlyph.srcX, partialStringGlyph.srcY, partialStringGlyph.width, partialStringGlyph.height);
        }
    }

    public void draw() {
        Texture texture = new Texture(pixmap);
        setDrawable(new SpriteDrawable(new Sprite(texture)));
    }

    public void updateValues() {
        resetPixmaps();
        drawName();
        drawLifeTokens();
        drawDamage();
        drawFlag();
        draw();
    }
}

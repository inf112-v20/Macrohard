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
import inf112.skeleton.app.Player;

import java.io.File;

public class PlayerInfoGraphic extends Image {

    private final Player player;

    static int playerNumber = -1;
    private final File file;

    public PlayerInfoGraphic(Player player){
        super(new Texture("./assets/PlayerInfoBackground.png"));
        playerNumber++;

        this.player = player;
        player.setInfoGraphic(this);
        this.file = new File("./assets/PlayerInfoBackground.png");
        setBounds(10 + (playerNumber * 130), 870, 120, 160);

        Pixmap pixmap = new Pixmap(new FileHandle(file));

        // ### FONT INIT ###
        FileHandle handle = Gdx.files.getFileHandle("./assets/fonts/ArialFull.fnt",
                Files.FileType.Internal);
        BitmapFont font = new BitmapFont(handle);
        BitmapFont.BitmapFontData data = font.getData();
        Pixmap fontPixmap = new Pixmap(Gdx.files.internal(data.imagePaths[0]));

        String playerName = "PLAYER " + (playerNumber+1);
        for(int i = 0; i<playerName.length(); i++){
            BitmapFont.Glyph partialStringGlyph = data.getGlyph(playerName.charAt(i));
            pixmap.drawPixmap(fontPixmap, 10 + 35*i, 10,
                    partialStringGlyph.srcX, partialStringGlyph.srcY, partialStringGlyph.width, partialStringGlyph.height);
        }

        String health = "DAMAGE " + player.getDamageTokens();
        for(int i = 0; i<playerName.length(); i++){
            BitmapFont.Glyph partialStringGlyph = data.getGlyph(health.charAt(i));
            pixmap.drawPixmap(fontPixmap, 10 + 35*i, 80,
                    partialStringGlyph.srcX, partialStringGlyph.srcY, partialStringGlyph.width, partialStringGlyph.height);
        }

        Texture texture = new Texture(pixmap);
        setDrawable(new SpriteDrawable(new Sprite(texture)));
    }
}

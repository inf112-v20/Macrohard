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

    static int initiatedPlayers = -1;
    private final int playerNumber;
    private File file;
    private Pixmap fontPixmap;
    private Pixmap pixmap;
    private BitmapFont.BitmapFontData fontData;

    public PlayerInfoGraphic(Player player){
        super(new Texture("./assets/PlayerInfoBackground.png"));
        initiatedPlayers++;
        playerNumber = initiatedPlayers;

        this.player = player;
        player.setInfoGraphic(this);
        setBounds(10 + (initiatedPlayers * 130), 870, 120, 160);
        resetPixmaps();
        updateValues();
    }

    public void resetPixmaps(){
        this.file = new File("./assets/PlayerInfoBackground.png");
        this.pixmap = new Pixmap(new FileHandle(file));

        //FONT INIT
        FileHandle handle = Gdx.files.getFileHandle("./assets/fonts/ArialFull.fnt",
                Files.FileType.Internal);
        BitmapFont font = new BitmapFont(handle);
        this.fontData = font.getData();
        this.fontPixmap = new Pixmap(Gdx.files.internal(fontData.imagePaths[0]));
    }

    public void drawName(){
        String playerName = "PLAYER " + (playerNumber+1);
        for(int i = 0; i<playerName.length(); i++){
            BitmapFont.Glyph partialStringGlyph = fontData.getGlyph(playerName.charAt(i));
            pixmap.drawPixmap(fontPixmap, 10 + 35*i, 10,
                    partialStringGlyph.srcX, partialStringGlyph.srcY, partialStringGlyph.width, partialStringGlyph.height);
        }
    }

    public void drawDamage(){
        String health = "DAMAGE " + player.getDamageTokens();
        for(int i = 0; i<health.length(); i++){
            BitmapFont.Glyph partialStringGlyph = fontData.getGlyph(health.charAt(i));
            pixmap.drawPixmap(fontPixmap, 10 + 35*i, 80,
                    partialStringGlyph.srcX, partialStringGlyph.srcY, partialStringGlyph.width, partialStringGlyph.height);
        }
    }

    public void draw(){
        Texture texture = new Texture(pixmap);
        setDrawable(new SpriteDrawable(new Sprite(texture)));
    }

    public void updateValues(){
        resetPixmaps();
        drawName();
        drawDamage();
        draw();
    }
}

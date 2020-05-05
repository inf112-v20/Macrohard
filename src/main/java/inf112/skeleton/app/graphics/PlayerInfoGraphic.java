package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;

import java.io.File;

public class PlayerInfoGraphic extends Table {

    private final Player player;
    private final Label lives;
    private final Label damage;
    private final Label powerDown;
    private final Label flag;

    private float timeInSeconds;

    public PlayerInfoGraphic(Player player, MapProperties mapProperties) {
        this.player = player;
        player.setInfoGraphic(this);

        int maxHeight = RoboRallyApplication.screenHeight;
        int boardWidth = (int) mapProperties.get("tilewidth") * (int) mapProperties.get("width");

        setBounds(boardWidth + 5, maxHeight - 341 - ((player.name() - 1) * 100), 400, 90);
        setBackground(new SpriteDrawable(new Sprite(new Texture("./assets/PlayerInfoBackground.png"))));
        setDebug(false);

        Skin skin = RoboRallyApplication.getSkin();
        Label name = new Label("Player " + player.name(), skin);
        name.setColor(Color.WHITE);
        add(name).colspan(2);
        row().padTop(15);
        add(new Label("Lives: ", skin));
        lives = new Label(Integer.toString(player.getLifeTokens()), skin);
        add(lives);
        add(new Label("Powerdown: ", skin)).padLeft(60);
        row();
        add(new Label("Damage: ", skin)).padLeft(5);
        damage = new Label(Integer.toString(player.getDamageTokens()), skin);
        add(damage);
        powerDown = new Label("No", skin);
        add(powerDown).uniformY();
        row();
        add(new Label("Flag: ", skin));
        flag = new Label(Integer.toString(player.getPreviousFlag()), skin);
        add(flag);
        align(Align.topLeft).pad(2, 5, 0, 0);
    }

    @Override
    public void act(float dt) {
        timeInSeconds += Gdx.graphics.getRawDeltaTime();
        float period = 0.8f;
        if (timeInSeconds > period) {
            if (player.isDestroyed()) {
                lives.setText(player.getLifeTokens());
            }
            if (!("" + player.getDamageTokens()).equals(damage.toString())) {
                damage.setText(player.getDamageTokens());
            }
            if (!("" + player.getPreviousFlag()).equals(flag.toString())) {
                flag.setText(player.getPreviousFlag());
            }
            if (player.announcedPowerDown && !powerDown.toString().equals("Announced")) {
                powerDown.setText("Announced");
            } else if (player.inPowerDown && !powerDown.toString().equals("Yes")) {
                powerDown.setText("Yes");
            } else if (!powerDown.toString().equals("No")) {
                powerDown.setText("No");
            }
        }
    }

}

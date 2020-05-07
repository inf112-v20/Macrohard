package inf112.skeleton.app.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;

public class PlayerInfoGraphic extends Table {

    private final Player player;
    private final Label lives;
    private final Label damage;
    private final Label powerDown;
    private final Label flag;

    private float timeInSeconds;

    public PlayerInfoGraphic(Player player, MapProperties mapProperties) {
        this.player = player;

        int maxHeight = RoboRallyApplication.screenHeight;
        int boardWidth = (int) mapProperties.get("tilewidth") * (int) mapProperties.get("width");

        setBounds(boardWidth + 5, maxHeight - 341 - ((player.getName() - 1) * 100), 400, 90);
        setBackground(new SpriteDrawable(new Sprite(new Texture("./assets/PlayerInfoBackground.png"))));

        Skin skin = RoboRallyApplication.getSkin();
        lives = new Label(Integer.toString(player.getLifeTokens()), skin);
        damage = new Label(Integer.toString(player.getDamageTokens()), skin);
        powerDown = new Label("No", skin);
        flag = new Label(Integer.toString(player.getPreviousFlag()), skin);
        formatTable(skin);
    }

    private void formatTable(Skin skin) {
        Label name = new Label("Player " + player.getName(), skin);
        add(name).colspan(2);
        row().padTop(15);
        add(new Label("Lives: ", skin));
        add(lives);
        add(new Label("Powerdown: ", skin)).padLeft(60);
        row();
        add(new Label("Damage: ", skin)).padLeft(5);
        add(damage);
        add(powerDown).uniformY();
        row();
        add(new Label("Flag: ", skin));
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

package inf112.skeleton.app.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.screens.GameScreen;

public class PowerDownButton extends ImageButton {

    private final static TextureRegionDrawable UNCHECKED = new TextureRegionDrawable(new TextureRegion(new Texture("./assets/buttons/powerDownRed.png")));
    private final static TextureRegionDrawable CHECKED = new TextureRegionDrawable(new TextureRegion(new Texture("./assets/buttons/powerDownGreen.png")));

    public PowerDownButton(Player client, GameScreen gameScreen) {
        super(UNCHECKED, UNCHECKED, CHECKED);
        setBounds(gameScreen.width - 120, -130, 120, 120);
        addListener(new PowerDownButtonListener(client));
        gameScreen.getGameStage().addActor(this);
    }

    private class PowerDownButtonListener extends ChangeListener {

        private Player client;

        public PowerDownButtonListener(Player client) {
            this.client = client;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            client.announcedPowerDown = !client.announcedPowerDown;
        }
    }
}

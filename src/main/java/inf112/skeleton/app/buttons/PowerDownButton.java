package inf112.skeleton.app.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.screens.GameScreen;

public class PowerDownButton extends ImageButton {

    private final static TextureRegionDrawable UNCHECKED = new TextureRegionDrawable(new TextureRegion(new Texture("./assets/buttons/powerDownRed.png")));
    private final static TextureRegionDrawable CHECKED = new TextureRegionDrawable(new TextureRegion(new Texture("./assets/buttons/powerDownGreen.png")));

    public PowerDownButton(GameScreen gameScreen) {
        super(UNCHECKED, UNCHECKED, CHECKED);
        addListener(new PowerDownButtonListener(gameScreen));
        gameScreen.getGameStage().addActor(this);
        setBounds(RoboRallyApplication.screenWidth - 800, -120, 100, 100);
        setVisible(false);
    }

    private static class PowerDownButtonListener extends ChangeListener {

        private GameScreen gameScreen;

        public PowerDownButtonListener(GameScreen gameScreen) {
            this.gameScreen = gameScreen;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            Player client = gameScreen.getClient();
            client.announcedPowerDown = !client.announcedPowerDown;
        }
    }
}

package inf112.skeleton.app.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.screens.GameScreen;

public class ContinuePowerDownWindow extends Window {


    public ContinuePowerDownWindow(GameScreen gameScreen) {
        super("Continue powerdown?", new Skin(Gdx.files.internal("assets/skins/expee/expee-ui.json")));

        setMovable(true);
        setResizable(true);
        setVisible(false);


        TextButton yes = new TextButton("Yes", getSkin());
        yes.addListener(new ButtonListener(this, gameScreen, true));

        TextButton no = new TextButton("No", getSkin());
        no.addListener(new ButtonListener(this, gameScreen, false));

        add(yes);
        add(no);

        pack();

        gameScreen.getGameStage().addActor(this);
        setPosition(RoboRallyApplication.screenWidth / 2f, RoboRallyApplication.screenHeight / 2f);
    }

    private static class ButtonListener extends ChangeListener {

        private Window parent;
        private GameScreen gameScreen;
        private boolean affirmative;

        public ButtonListener(Window parent, GameScreen gameScreen, boolean affirmative) {
            this.parent = parent;
            this.gameScreen = gameScreen;
            this.affirmative = affirmative;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            if (affirmative) {
                Player client = gameScreen.getClient();
                client.announcedPowerDown = true;
                client.inPowerDown = false;
            }
            gameScreen.closeWindow(parent);
        }
    }
}

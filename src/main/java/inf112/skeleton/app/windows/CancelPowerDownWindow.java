package inf112.skeleton.app.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.GameLoop;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;

public class CancelPowerDownWindow extends Window {


    public CancelPowerDownWindow(GameLoop gameLoop, Player client) {
        super("Cancel powerdown?", new Skin(Gdx.files.internal("assets/skins/expee/expee-ui.json")));

        setMovable(true);
        setResizable(true);
        setVisible(false);


        TextButton yes = new TextButton("Yes", getSkin());
        yes.addListener(new ButtonListener(gameLoop, client, true));

        TextButton no = new TextButton("No", getSkin());
        no.addListener(new ButtonListener(gameLoop, client, false));

        add(yes);
        add(no);

        pack();

        gameLoop.gameScreen.getGameStage().addActor(this);
        setPosition(RoboRallyApplication.screenWidth / 2f, RoboRallyApplication.screenHeight / 2f);
    }

    private class ButtonListener extends ChangeListener {

        private GameLoop gameLoop;
        private Player client;
        private boolean affirmative;

        public ButtonListener(GameLoop gameLoop, Player client, boolean affirmative) {
            this.gameLoop = gameLoop;
            this.client = client;
            this.affirmative = affirmative;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            if (affirmative) {
                client.announcedPowerDown = false;
            }
            gameLoop.phase++;
        }
    }
}
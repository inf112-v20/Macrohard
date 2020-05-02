package inf112.skeleton.app.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.screens.GameScreen;

public class RebootWindow extends Window {

    public RebootWindow(GameScreen gameScreen) {
        super("Choose reboot-direction", new Skin(Gdx.files.internal("assets/skins/expee/expee-ui.json")));

        setMovable(true);
        setResizable(true);
        setVisible(false);


        for (String value : new String[]{"NORTH", "EAST", "SOUTH", "WEST"}) {
            add(getImageButton(gameScreen, value));
        }

        pack();
        gameScreen.getGameStage().addActor(this);
        setPosition(RoboRallyApplication.screenWidth / 2f, RoboRallyApplication.screenHeight / 2f);
    }

    private ImageButton getImageButton(GameScreen gameScreen, String value) {
        Texture image = new Texture("./assets/robots/robot" + value + ".png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(image));
        ImageButton imageButton = new ImageButton(drawable);
        imageButton.addListener(new RebootButton(this, gameScreen, Direction.fromString(value)));
        return imageButton;
    }

    private static class RebootButton extends ChangeListener {

        private Window parent;
        private GameScreen gameScreen;
        private Direction direction;

        public RebootButton(Window parent, GameScreen gameScreen, Direction direction) {
            this.parent = parent;
            this.gameScreen = gameScreen;
            this.direction = direction;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
            Player client = gameScreen.getClient();
            client.setDirection(direction);
            client.reboot();
            client.getPlayerGraphic().animateReboot();
            gameScreen.closeWindow(parent);
        }
    }
}

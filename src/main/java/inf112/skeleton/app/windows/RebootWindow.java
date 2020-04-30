package inf112.skeleton.app.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.GameLoop;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;

public class RebootWindow extends Window {

    private Player client;


    public RebootWindow(GameLoop gameLoop, Player client) {
        super("Choose reboot-direction", new Skin(Gdx.files.internal("assets/skins/expee/expee-ui.json")));
        this.client = client;

        setMovable(true);
        setResizable(true);
        setVisible(false);


        for (String value : new String[]{"NORTH", "EAST", "SOUTH", "WEST"}) {
            add(getImageButton(gameLoop, value));
        }

        pack();
        gameLoop.gameScreen.getGameStage().addActor(this);
        setPosition(RoboRallyApplication.screenWidth / 2f, RoboRallyApplication.screenHeight / 2f);
    }

    private ImageButton getImageButton(GameLoop gameLoop, String value) {
        Texture image = new Texture("./assets/robots/robot" + value + ".png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(image));
        ImageButton imageButton = new ImageButton(drawable);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Direction direction = Direction.fromString(value);
                client.setDirection(direction);
                client.reboot();
                client.getPlayerGraphic().animateReboot();
                gameLoop.phase ++;
            }
        });
        return imageButton;
    }

}

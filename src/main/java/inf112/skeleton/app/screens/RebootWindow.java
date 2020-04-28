package inf112.skeleton.app.screens;

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

public class RebootWindow extends Window {

    private Player rebootPlayer;
    private GameScreen parent;


    public RebootWindow(GameScreen parent, Player rebootPlayer) {
        super("Choose reboot-direction", new Skin(Gdx.files.internal("assets/skins/expee/expee-ui.json")));
        this.parent = parent;
        this.rebootPlayer = rebootPlayer;

        setMovable(false);
        setResizable(false);
        setBounds(400, 600, 300, 100);

        for (String value : new String[]{"NORTH", "EAST", "SOUTH", "WEST"}) {
            add(getImageButton(value));
        }

        parent.getGameStage().addActor(this);
    }

    private ImageButton getImageButton(String value) {
        Texture image = new Texture("./assets/robots/robot" + value + ".png");
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(image));
        ImageButton imageButton = new ImageButton(drawable);
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Direction direction = Direction.fromString(value);
                rebootPlayer.setDirection(direction);
                rebootPlayer.reboot();
                rebootPlayer.getGraphics().animateReboot();
                parent.closeRebootWindow();
            }
        });
        return imageButton;
    }
}

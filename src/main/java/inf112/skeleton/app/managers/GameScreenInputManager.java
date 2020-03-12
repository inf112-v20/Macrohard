package inf112.skeleton.app.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import inf112.skeleton.app.RoboRallyApplication;

public class GameScreenInputManager extends InputListener {

    private RoboRallyApplication parent;

    public GameScreenInputManager(RoboRallyApplication parent) {
        this.parent = parent;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        if (keycode == Input.Keys.ESCAPE) parent.changeScreen(RoboRallyApplication.MAIN_MENU);
        return super.keyDown(event, keycode);
    }

}

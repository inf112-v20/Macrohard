package inf112.skeleton.app.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.GameLoop;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.screens.GameScreen;

public class ProgramButton extends TextButton {

    public ProgramButton(GameLoop gameLoop, GameScreen gameScreen) {
        super("LOCK PROGRAM", RoboRallyApplication.getSkin());
        setColor(Color.DARK_GRAY);
        setBounds(gameScreen.width - 350, -105, 200, 60);
        addListener(new ProgramButtonChangeListener(this, gameLoop));
        setVisible(false);
        gameScreen.getGameStage().addActor(this);
    }

    private class ProgramButtonChangeListener extends ChangeListener {

        private ProgramButton parent;
        private GameLoop gameLoop;

        public ProgramButtonChangeListener(ProgramButton parent, GameLoop gameLoop) {
            this.parent = parent;
            this.gameLoop = gameLoop;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            if (gameLoop.client.hasCompleteProgram()) {
                gameLoop.phase++;
                parent.setVisible(false);
            }
        }
    }
}

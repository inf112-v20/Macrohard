package inf112.skeleton.app.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.screens.GameScreen;

public class ProgramButton extends TextButton {

    public ProgramButton(GameScreen gameScreen) {
        super("LOCK PROGRAM", RoboRallyApplication.getSkin());
        setColor(Color.DARK_GRAY);
        addListener(new ProgramButtonChangeListener(gameScreen));

        gameScreen.getGameStage().addActor(this);
        setBounds(RoboRallyApplication.screenWidth - 660, -100, 220, 60);
        setVisible(false);
    }

    private static class ProgramButtonChangeListener extends ChangeListener {

        private GameScreen gameScreen;

        public ProgramButtonChangeListener(GameScreen gameScreen) {
            this.gameScreen = gameScreen;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            if (gameScreen.getClient().hasCompleteProgram()) {
                gameScreen.discardUnselectedCards();
                gameScreen.incrementPhase();
            }
        }
    }
}

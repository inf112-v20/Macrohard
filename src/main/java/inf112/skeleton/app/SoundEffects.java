package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import inf112.skeleton.app.preferences.AppPreferences;

public class SoundEffects {

    public final static Sound FIRE_LASERS = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/laserbeam.wav"));
    public final static Sound FALLING_ROBOT = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/FallingRobot.wav"));
    public final static Sound CONVEYOR_BELT = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/ConveyorBelt.wav"));
    public final static Sound ROTATE_GEARS = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/Gear.wav"));
    public final static Sound CHECKPOINT = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/Checkpoint.wav"));


    public static void rotateGears() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            ROTATE_GEARS.play(RoboRallyApplication.appPreferences.getSoundVolume());
        }
    }

    public static void fireLasers() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            FIRE_LASERS.play(RoboRallyApplication.appPreferences.getSoundVolume());
        }
    }

    public static void rollConveyorBelts() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            CONVEYOR_BELT.play(RoboRallyApplication.appPreferences.getSoundVolume());
        }
    }
}

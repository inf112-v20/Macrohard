package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundEffects {

    public final static Sound FIRE_LASERS = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/laserbeam.wav"));
    public final static Sound FALLING_ROBOT = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/FallingRobot.wav"));
    public final static Sound ROTATE_GEARS = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/Gear.wav"));
    public final static Sound CHECKPOINT = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/Checkpoint.wav"));

}
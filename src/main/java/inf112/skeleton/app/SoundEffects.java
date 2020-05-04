package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
import inf112.skeleton.app.cards.RotationCard;

public class SoundEffects {

    public final static Sound FIRE_LASERS = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/laserbeam.wav"));
    public final static Sound FALLING_ROBOT = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/FallingRobot.wav"));
    public final static Sound CONVEYOR_BELT = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/ConveyorBelt.wav"));
    public final static Sound ROTATE_GEARS = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/Gear.wav"));
    public final static Sound CHECKPOINT = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/Checkpoint.wav"));
    public final static Sound ROTATE = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/Move.wav"));
    public final static Sound MOVE1 = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/move1.wav"));
    public final static Sound DAMAGE = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/Damage.wav"));
    private static final Sound MOVE2 = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/move2.wav"));
    private static final Sound REPAIR = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/repair.wav"));


    public static void damage() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            DAMAGE.play(RoboRallyApplication.appPreferences.getSoundVolume() - 0.4f);
        }
    }

    public static void repair() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            REPAIR.play(RoboRallyApplication.appPreferences.getSoundVolume() - 0.4f);
        }
    }

    public static void move(Card card) {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            if (card instanceof RotationCard) {
                ROTATE.play(RoboRallyApplication.appPreferences.getSoundVolume());
            } else {
                MovementCard mCard = (MovementCard) card;
                if (mCard.getMoveID() == MovementType.ONE_BACKWARD.getMoveID()
                        || mCard.getMoveID() == MovementType.ONE_FORWARD.getMoveID()) {
                    MOVE1.play(RoboRallyApplication.appPreferences.getSoundVolume() - 0.4f);
                } else {
                    MOVE2.play(RoboRallyApplication.appPreferences.getSoundVolume());
                }
            }
        }
    }

    public static void checkpoint() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            CHECKPOINT.play(RoboRallyApplication.appPreferences.getSoundVolume());
        }
    }

    public static void fallingRobot() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            FALLING_ROBOT.play(RoboRallyApplication.appPreferences.getSoundVolume() - 0.2f);
        }
    }

    public static void rotateGears() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            ROTATE_GEARS.play(RoboRallyApplication.appPreferences.getSoundVolume());
        }
    }

    public static void fireLasers() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            FIRE_LASERS.play(RoboRallyApplication.appPreferences.getSoundVolume() - 0.2f);
        }
    }

    public static void rollConveyorBelts() {
        if (RoboRallyApplication.appPreferences.isSoundEffectsEnabled()) {
            CONVEYOR_BELT.play(RoboRallyApplication.appPreferences.getSoundVolume());
        }
    }
}

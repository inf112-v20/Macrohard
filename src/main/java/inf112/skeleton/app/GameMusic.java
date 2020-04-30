package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public  class GameMusic {
    public final static Music FactorySwing = Gdx.audio.newMusic(Gdx.files.internal("data/Music/FactorySwing.wav"));
    public final static Music NorwegianSteel = Gdx.audio.newMusic(Gdx.files.internal("data/Music/NorwegianSteel.wav"));
    public final static Music NullpointerException = Gdx.audio.newMusic(Gdx.files.internal("data/Music/NullpointerException.wav"));
    public final static Music RobotBoogaloo = Gdx.audio.newMusic(Gdx.files.internal("data/Music/RobotBoogaloo.wav"));
    public final static Music ShortCuiricut = Gdx.audio.newMusic(Gdx.files.internal("data/Music/ShortCuiricut.wav"));

}
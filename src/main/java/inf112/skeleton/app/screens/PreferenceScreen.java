package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.RoboRallyApplication;

public class PreferenceScreen implements Screen {

    private RoboRallyApplication parent;
    private Stage stage;

    public static boolean isCheckedMusic = true;
    private float volume;

    public PreferenceScreen(RoboRallyApplication parent) {
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
        volume = parent.getPreferences().getMusicVolume();
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        stage.clear();
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);

        Skin skin = new Skin(Gdx.files.internal("assets/skins/commodore64/uiskin.json"));

        final SelectBox<String> selectBox = new SelectBox<>(skin);
        selectBox.setItems("Factory Swing", "Short Circuit", "Nullpointer Exception", "Norwegian Steel", "Robot Boogaloo");
        selectBox.addListener(event -> {
            String song = selectBox.getSelected();
            switch (song) {
                case "Factory Swing":
                    if (RoboRallyApplication.currentSong.equals(song)) {
                        break;
                    }
                    changeSong(song, "data/Music/FactorySwing.wav");
                    break;

                case "Short Circuit":
                    if (RoboRallyApplication.currentSong.equals(song)) {
                        break;
                    }
                    changeSong(song, "data/Music/ShortCircuit.wav");
                    break;

                case "Nullpointer Exception":
                    if (RoboRallyApplication.currentSong.equals(song)) {
                        break;
                    }
                    changeSong(song, "data/Music/NullpointerException.wav");
                    break;

                case "Norwegian Steel":
                    if (RoboRallyApplication.currentSong.equals(song)) {
                        break;
                    }
                    changeSong(song, "data/Music/NorwegianSteel.wav");
                    break;

                case "Robot Boogaloo":
                    if (RoboRallyApplication.currentSong.equals(song)) {
                        break;
                    }
                    changeSong(song, "data/Music/RobotBoogaloo.wav");
                    break;
            }
            return false;
        });

        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(event -> {
            if (isCheckedMusic) {
                volume = volumeMusicSlider.getValue();
            } else {
                volume = 0;
            }
            parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
            return false;
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(event -> {
            boolean enabled = musicCheckbox.isChecked();
            isCheckedMusic = enabled;
            if (!enabled) {
                RoboRallyApplication.music.dispose();
            } else {
                RoboRallyApplication.music.play();
            }
            parent.getPreferences().setMusicEnabled(enabled);
            return false;
        });

        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSoundSlider.setValue(parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener(event -> {
            parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
            return false;
        });

        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
        soundCheckbox.addListener(event -> {
            boolean enabled = soundCheckbox.isChecked();
            parent.getPreferences().setSoundEffectsEnabled(enabled);
            return false;
        });

        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.setScreen(RoboRallyApplication.MAIN_MENU_SCREEN);
            }
        });

        Label titleLabel = new Label("Preferences", skin);
        titleLabel.setFontScale(2f);
        Label songLabel = new Label("Song", skin);
        Label volumeMusicLabel = new Label("Music Volume", skin);
        Label musicOnOffLabel = new Label("Music", skin);
        Label volumeSoundLabel = new Label("Sound Volume", skin);
        Label soundOnOffLabel = new Label("Sound", skin);

        //Add elements to table
        table.add(titleLabel).colspan(2);
        table.row().pad(50, 10, 10, 10);
        table.add(songLabel).left();
        table.add(selectBox);
        table.row().pad(10, 10, 10, 10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10, 10, 10, 10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10, 10, 10, 10);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider);
        table.row().pad(10, 10, 10, 10);
        table.add(soundOnOffLabel).left();
        table.add(soundCheckbox);
        table.row().pad(25, 10, 10, 10);
        table.add(backButton).colspan(2);

        //add table to stage
        stage.addActor(table);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void render(float v) {
        // = music;
        RoboRallyApplication.music.setVolume(volume);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        //Nothing yet
    }

    @Override
    public void pause() {
        //Nothing yet
    }

    @Override
    public void resume() {
        //Nothing yet
    }

    @Override
    public void hide() {
        //Nothing yet
    }

    @Override
    public void dispose() {
        //Nothing yet
    }

    private void changeSong(String song, String filepath) {
        RoboRallyApplication.currentSong = song;
        RoboRallyApplication.music.stop();
        RoboRallyApplication.music = Gdx.audio.newMusic(Gdx.files.internal(filepath));
        RoboRallyApplication.music.setLooping(true);
        RoboRallyApplication.music.play();
    }
}

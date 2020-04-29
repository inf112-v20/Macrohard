package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.*;
import inf112.skeleton.app.cards.*;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.managers.GameScreenInputProcessor;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.tiles.Hole;

import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen implements Screen {

    private static final int CARD_GRAPHIC_HEIGHT =  150;

    public final RoboRallyApplication parent;
    public final int width, height;
    private final Viewport gamePort;
    private final OrthographicCamera gameCamera;

    public TiledMapManager mapHandler;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private GameScreenInputProcessor inputProcessor;

    public final static int TILE_SIZE = 60;
    private float timeInSeconds = 0f;

    private Board board;
    public final RebootWindow rebootWindow;

    private Stage gameStage;
    private GameLoop gameLoop;
    private final ArrayList<Player> players;

    public GameScreen(final RoboRallyApplication parent, int width, int height){
        this.parent = parent;
        this.width = width;
        this.height = height;

        // ---- INITIALISATION ----

        // Initialise players
        Player player1 = new Player(0, 1, Direction.EAST, false);
        Player player2 = new Player(0, 1, Direction.EAST, true);
        Player player3 = new Player(0, 2, Direction.EAST, true);
        Player player4 = new Player(0, 2, Direction.EAST, true);
        Player player5 = new Player(0, 2, Direction.EAST, true);
        players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4, player5));

        // Initialise board
        TiledMapManager handler = new TiledMapManager("assets/riskyExchange.tmx");
        mapHandler = handler;
        map = handler.getMap();
        renderer = new OrthogonalTiledMapRenderer(map);
        board = new Board(players, handler);

        // Initialise board-view
        gameCamera = new OrthographicCamera();
        renderer.setView(gameCamera);

        MapProperties properties = map.getProperties();
        int tileSize = (Integer) properties.get("tilewidth");
        int boardHeight = (Integer) properties.get("height");

        gamePort = new FitViewport(width, boardHeight * tileSize + CARD_GRAPHIC_HEIGHT, gameCamera);

        gameStage = new Stage(gamePort);

        // ---- GRAPHICS ----
        for (Player player : players) {
            PlayerGraphic playerGraphic = new PlayerGraphic(player);
            gameStage.addActor(playerGraphic);
        }

        // --- INPUT ----
        inputProcessor = new GameScreenInputProcessor(parent, player1, board);
        gameStage.addListener(inputProcessor);

        rebootWindow = new RebootWindow(this, player1);
        rebootWindow.setVisible(false);
        gameStage.addActor(rebootWindow);

        TextButton fireLaser = new TextButton("LASER", parent.getSkin());
        fireLaser.setBounds(width - (width / 4f), 504, 150, 50);
        fireLaser.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                mapHandler.getLayer("LASERBEAMS").setVisible(true);
                board.fireBoardLasers();
                SoundEffects.FIRE_LASERS.play(parent.getPreferences().getSoundVolume());
                mapHandler.getLayer("LASERBEAMS").setVisible(false);
            }
        });
        gameStage.addActor(fireLaser);

        TextButton reboot = new TextButton("REBOOT", parent.getSkin());
        reboot.setBounds(width - (width / 4f), 452, 150, 50);
        reboot.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                for (Player player : players) {
                    if (player.isDestroyed() && !player.isDead()) {
                        player.reboot();
                        player.getGraphics().animateReboot();
                    }
                }
                updatePlayerGraphics();
            }
        });
        gameStage.addActor(reboot);

        TextButton change = new TextButton("CHANGE", parent.getSkin());
        change.setBounds(width - (width / 4f), 400, 150, 50);
        change.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                int index = players.indexOf(inputProcessor.getPlayer());
                int newIndex = (index + 1) % players.size();
                inputProcessor.setPlayer(players.get(newIndex));
            }
        });
        gameStage.addActor(change);

        TextButton button = new TextButton("PROGRAM", parent.getSkin());
        button.setBounds(width - (width / 4f), 348, 150, 50);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                players.get(0).lockInProgram();
            }
        });
        gameStage.addActor(button);
        int buttonX = (width - (width / 4)) - 52;
        gameLoop = new GameLoop(board, this, buttonX);

    }

    public void updatePlayerGraphics() {
        for (Player player : players) {
            PlayerGraphic graphic = player.getGraphics();
            if (graphic.isVisible) {
                graphic.animateMove();
                graphic.animateRotation();
                if (board.getTile(player) instanceof Hole) {
                    graphic.animateFall();
                    SoundEffects.FALLING_ROBOT.play(parent.getPreferences().getSoundVolume());
                }
                else if (player.isDestroyed()) {
                    graphic.animateDestruction();
                }
            }

        }
    }

    public void runProgram(Player player, int registerIndex) {
        Card card = player.getProgram()[registerIndex];
        board.execute(player, card);
        updatePlayerGraphics();
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void show() {
        //RoboRallyApplication.music.play();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameCamera.position.set(width / 2f - 150, gamePort.getWorldHeight() / 2 - CARD_GRAPHIC_HEIGHT, 0);
        renderer.setView(gameCamera);
        renderer.render();
        gameStage.act();
        gameStage.draw();

        // Comment this out to disable GameLoop
        timeInSeconds += Gdx.graphics.getRawDeltaTime();
        float period = 0.8f;
        if (timeInSeconds > period) {
            timeInSeconds -= period;
            gameLoop.tick();
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
    }

    public void addStageActor(Image actor) {
        gameStage.addActor(actor);
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void clearCards(ArrayList<CardGraphic> cardsOnScreen) {
        for (CardGraphic cardGraphic : cardsOnScreen) {
            cardGraphic.reset();
            cardGraphic.remove();
        }
    }

    public TextButton[] powerDownOptions() {
        if (players.get(0).inPowerDown) {
            TextButton continuePowerDown = new TextButton("CONTINUE", parent.getSkin());
            continuePowerDown.setBounds(1500, 130, 200, 50);
            TextButton stopPowerDown = new TextButton("STOP", parent.getSkin());
            stopPowerDown.setBounds(1500, 70, 200, 50);
            stopPowerDown.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    stopPowerDown.setText("STOPPING..");
                    stopPowerDown.setColor(Color.RED);
                    players.get(0).inPowerDown = false;
                    players.get(0).continuePowerDown = false;
                    gameLoop.powerDownInput = true;
                }
            });
            continuePowerDown.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    continuePowerDown.setText("CONTINUING..");
                    continuePowerDown.setColor(Color.RED);
                    players.get(0).continuePowerDown = true;
                    gameLoop.powerDownInput = true;
                }
            });
            gameStage.addActor(stopPowerDown);
            gameStage.addActor(continuePowerDown);
            return (new TextButton[]{continuePowerDown, stopPowerDown});
        } else {
            TextButton continueGame = new TextButton("CONTINUE", parent.getSkin());
            continueGame.setBounds(1500, 130, 200, 50);
            TextButton powerDown = new TextButton("POWER DOWN", parent.getSkin());
            powerDown.setBounds(1500, 70, 200, 50);
            continueGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    continueGame.setText("CONTINUING..");
                    continueGame.setColor(Color.RED);
                    gameLoop.powerDownInput = true;
                }
            });
            powerDown.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (players.get(0).getDamageTokens() == 0) {
                        powerDown.setBounds(1500,70,300,50);
                        powerDown.setText("ILLEGAL, NO DAMAGE TAKEN");
                        gameLoop.powerDownInput = true;
                    }
                    powerDown.setText("REPAIRING..");
                    powerDown.setColor(Color.RED);
                    players.get(0).inPowerDown = true;
                    gameLoop.powerDownInput = true;

                }
            });
            gameStage.addActor(continueGame);
            gameStage.addActor(powerDown);
            return (new TextButton[]{continueGame, powerDown});
        }
    }

    public void powerDown() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        if (players.get(0).inPowerDown) {
            Texture green = new Texture("./assets/buttons/powerDownGreen.png");
            style.checked = new TextureRegionDrawable(new TextureRegion(green));
            ImageButton powerDown = new ImageButton(style.checked);
            powerDown.setBounds(1500, 200, 200, 200);
            gameStage.addActor(powerDown);
        } else {
            Texture red = new Texture("./assets/buttons/powerDownRed.png");
            style.checked = new TextureRegionDrawable(new TextureRegion(red));
            ImageButton powerDown = new ImageButton(style.checked);
            powerDown.setBounds(1500, 200, 200, 200);
            gameStage.addActor(powerDown);
        }

    }

    public void openRebootWindow() {
        rebootWindow.setVisible(true);
    }

    public void closeRebootWindow() {
        rebootWindow.setVisible(false);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

}

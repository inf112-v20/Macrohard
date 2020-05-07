package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RoboRallyApplication;
import inf112.skeleton.app.RoboRallyGame;
import inf112.skeleton.app.buttons.PowerDownButton;
import inf112.skeleton.app.buttons.ProgramButton;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.graphics.PlayerInfoGraphic;
import inf112.skeleton.app.managers.GameScreenInputProcessor;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.tiles.Tile;
import inf112.skeleton.app.windows.CancelPowerDownWindow;
import inf112.skeleton.app.windows.ContinuePowerDownWindow;
import inf112.skeleton.app.windows.RebootWindow;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameScreen implements Screen {

    public final static int TILE_SIZE = 60;
    private static final int CARD_GRAPHIC_HEIGHT = 150;

    private final RoboRallyGame game;

    private final Viewport gamePort;
    private final OrthographicCamera gameCamera;
    private final Stage gameStage;
    private final OrthogonalTiledMapRenderer renderer;

    private ProgramButton programButton;
    private PowerDownButton powerDownButton;

    private RebootWindow rebootWindow;
    private ContinuePowerDownWindow continuePowerDownWindow;
    private CancelPowerDownWindow cancelPowerDownWindow;

    private final TiledMapManager mapManager;
    private final ArrayList<Player> players;
    private Player client;

    private float timeInSeconds = 0f;
    private float stateTime = 0f;


    public GameScreen(RoboRallyGame game, TiledMapManager mapManager) {
        this.game = game;
        this.mapManager = mapManager;
        this.players = game.getPlayers();
        this.client = game.getClient();

        TiledMap map = mapManager.getMap();
        MapProperties properties = map.getProperties();
        int boardHeight = (Integer) properties.get("height");
        int boardWidth = (Integer) properties.get("width");

        // Initialise board-view
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCamera = new OrthographicCamera(boardWidth, boardHeight);
        renderer.setView(gameCamera);
        gamePort = new FitViewport(1920, 1080, gameCamera);
        gameStage = new Stage(gamePort);
        gameCamera.translate(-(boardWidth * TILE_SIZE) / 3f, -(CARD_GRAPHIC_HEIGHT));

        // Initialise stage actors
        for (Player player : players) {
            PlayerGraphic playerGraphic = new PlayerGraphic(player);
            PlayerInfoGraphic playerInfoGraphic = new PlayerInfoGraphic(player, properties);
            gameStage.addActor(playerGraphic);
            gameStage.addActor(playerInfoGraphic);
        }

        // Initialise buttons
        programButton = new ProgramButton(this);
        powerDownButton = new PowerDownButton(this);

        // Initialise pop-up windows
        rebootWindow = new RebootWindow(this);
        continuePowerDownWindow = new ContinuePowerDownWindow(this);
        cancelPowerDownWindow = new CancelPowerDownWindow(this);

    }

    /**
     * Update the graphical elements of the board in accordance with the current phase of the game.
     *
     * @param phase the current phase of the game
     */
    private void updateScreenGraphics(int phase) {
        // --- BUTTONS ---
        boolean inputPhase = phase < 2 && !client.inPowerDown;
        Color color = client.hasCompleteProgram() ? Color.GREEN : Color.DARK_GRAY;
        programButton.setColor(color);
        programButton.setVisible(inputPhase);
        powerDownButton.setVisible(inputPhase);

        // --- PLAYER MOVEMENT AND INFO-GRAPHICS ---
        if (phase > 2) {
            for (Player player : players) {
                PlayerGraphic graphic = player.getPlayerGraphic();
                if (phase < 8 && graphic.isVisible) {
                    graphic.animateMove();
                }
            }
        }

        // --- POP-UP WINDOWS AND CARD-GRAPHICS ---
        switch (phase) {
            case -2:
                if (client.inPowerDown) {
                    continuePowerDownWindow.setVisible(true);
                }
            case 0:
                if (!client.inPowerDown) {
                    for (Card card : client.getCards()) {
                        if (!card.isLocked()) {
                            gameStage.addActor(new CardGraphic(client, card));
                        }
                    }
                }
                break;
            case 10:
                eraseLasers();
                break;
            case 11:
                if (client.isDestroyed() && !client.isDead()) {
                    rebootWindow.setVisible(true);
                } else {
                    incrementPhase();
                    incrementPhase();
                }
                break;
            case 12:
                if (client.announcedPowerDown) {
                    cancelPowerDownWindow.setVisible(true);
                } else {
                    incrementPhase();
                }
                break;
            case 13:
                wipeProgram();
                CardGraphic.reset(client);
                client.wipeProgram();
                if (!client.announcedPowerDown) {
                    powerDownButton = new PowerDownButton(this);
                }
                break;
        }

    }

    /**
     * Draws the beams of the player lasers from a ArrayList of lists of tiles,
     * where each list of tiles represent the beam of a single laser.
     * Also makes the static board lasers visible.
     *
     * @param playerLaserBeams is an ArrayList containing lists of tiles,
     *                         where every list of tiles represents a laser beam
     */
    public void drawLasers(ArrayList<LinkedList<Tile>> playerLaserBeams) {
        mapManager.getLayer("LASERBEAMS").setVisible(true);
        for (LinkedList<Tile> laserBeam : playerLaserBeams) {
            Direction direction = laserBeam.getFirst().getPlayer().getDirection();
            boolean horizontal = direction == Direction.EAST || direction == Direction.WEST;
            for (Tile tile : laserBeam) {
                mapManager.setLaserCell(horizontal, tile.getRow(), tile.getCol());
            }
        }
    }

    public void eraseLasers() {
        mapManager.getLayer("LASERBEAMS").setVisible(false);
        mapManager.cleanPlayerLaserLayer();
    }

    public void discardUnselectedCards() {
        for (Card card : client.getCards()) {
            if (!card.isSelected()) {
                card.getCardGraphic().remove();
            }
        }
    }

    private void wipeProgram() {
        for (Card card : client.getProgram()) {
            if (card != null && !card.isLocked()) {
                card.getCardGraphic().remove();
            }
        }
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getClient() {
        return client;
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(gameStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();
        for (Player player : players) {
            player.getPlayerGraphic().updateAnimationFrame(stateTime);
        }

        renderer.setView(gameCamera);
        renderer.render();
        gameStage.act();
        gameStage.draw();

        timeInSeconds += Gdx.graphics.getRawDeltaTime();
        float period = 0.8f;
        if (timeInSeconds > period) {
            int phase = game.getPhase();
            timeInSeconds -= period;
            game.tick();
            updateScreenGraphics(phase);
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapManager.getMap().dispose();
        renderer.dispose();
    }

    public void incrementPhase() {
        game.incrementPhase();
    }

    public void closeWindow(Window window) {
        window.setVisible(false);
        game.incrementPhase();
    }

    /**
     * This method is used to update the player-graphics independently
     * of the game loop.
     */
    public void updateGraphics() {
        for (Player player : players) {
            PlayerGraphic graphic = player.getPlayerGraphic();
            if (graphic.isVisible) {
                graphic.animateMove();
            }
        }
    }
}

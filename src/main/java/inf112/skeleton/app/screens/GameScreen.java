package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import inf112.skeleton.app.*;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.graphics.PlayerInfoGraphic;
import inf112.skeleton.app.managers.GameScreenInputProcessor;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.tiles.Hole;
import inf112.skeleton.app.tiles.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class GameScreen implements Screen {

    private static final int CARD_GRAPHIC_HEIGHT = 150;
    private static final int CARD_GRAPHIC_SELECTED_WIDTH = 80;
    private static final int PADDING = 5;

    public final RoboRallyApplication parent;
    public final int width, height;
    public final int buttonXPosition;

    private final Viewport gamePort;
    private final OrthographicCamera gameCamera;
    private final MapProperties properties;

    public TiledMapManager mapHandler;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public final static int TILE_SIZE = 60;
    private float timeInSeconds = 0f;

    private Board board;

    private Stage gameStage;
    private GameLoop gameLoop;
    private final ArrayList<Player> players;

    private float stateTime;

    public GameScreen(final RoboRallyApplication parent, int width, int height) {
        this.parent = parent;
        this.width = width;
        this.height = height;

        // ---- INITIALISATION ----

        // Initialise players
        Player player1 = new Player(0, 1, Direction.EAST);
        Player player2 = new Player(0, 1, Direction.EAST);
        Player player3 = new Player(0, 1, Direction.EAST);
        Player player4 = new Player(0, 1, Direction.EAST);

        players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));

        // Initialise board
        TiledMapManager handler = new TiledMapManager("assets/riskyExchange.tmx");
        mapHandler = handler;
        map = handler.getMap();
        renderer = new OrthogonalTiledMapRenderer(map);
        board = new Board(players, handler);

        // Initialise board-view
        gameCamera = new OrthographicCamera(width, height);
        renderer.setView(gameCamera);

        this.properties = map.getProperties();
        int tileSize = (Integer) properties.get("tilewidth");
        int boardHeight = (Integer) properties.get("height");

        gamePort = new ExtendViewport(width, boardHeight * tileSize + CARD_GRAPHIC_HEIGHT, gameCamera);

        gameStage = new Stage(gamePort);

        // ---- GRAPHICS ----
        for (Player player : players) {
            PlayerGraphic playerGraphic = new PlayerGraphic(player);
            PlayerInfoGraphic playerInfoGraphic = new PlayerInfoGraphic(player, this);
            gameStage.addActor(playerGraphic);
            gameStage.addActor(playerInfoGraphic);
        }

        // --- INPUT ----
        GameScreenInputProcessor inputProcessor = new GameScreenInputProcessor(parent, player1, board);
        gameStage.addListener(inputProcessor);

        // --- BUTTONS ---
        buttonXPosition = width - (150 + CARD_GRAPHIC_SELECTED_WIDTH + PADDING);

        gameLoop = new GameLoop(board, this);

        gameCamera.translate(-85, -(CARD_GRAPHIC_HEIGHT + 5));
        stateTime = 0f;
    }

    public void updateGraphics() {
        for (Player player : players) {
            PlayerGraphic graphic = player.getPlayerGraphic();
            if (graphic.isVisible) {
                graphic.animateMove();
                graphic.animateRotation();
                if (board.getTile(player) instanceof Hole) {
                    graphic.animateFall();
                    SoundEffects.FALLING_ROBOT.play(parent.getPreferences().getSoundVolume());
                } else if (player.isDestroyed()) {
                    graphic.animateDestruction();
                }
            }
            player.getInfoGraphic().updateValues();
        }
    }

    public void clearCards(ArrayList<CardGraphic> cardsOnScreen) {
        for (CardGraphic cardGraphic : cardsOnScreen) {
            if (!cardGraphic.getCard().isLocked) {
                cardGraphic.reset();
                cardGraphic.remove();
            }
        }
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
        Gdx.gl.glClearColor(0.5f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();

        for(Player player : players){
            player.getPlayerGraphic().updateAnimationFrame(stateTime);
        }
        renderer.setView(gameCamera);
        renderer.render();
        gameStage.act();
        gameStage.draw();

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

    public Stage getGameStage() {
        return gameStage;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getHeight() {
        return height;
    }

    public MapProperties getMapProperties() {
        return this.properties;
    }

    public void drawPlayerLasers(ArrayList<LinkedList<Tile>> firePlayerLasers) {
        for (LinkedList<Tile> laserBeam : firePlayerLasers) {
            Direction direction = laserBeam.getFirst().getPlayer().getDirection();
            boolean horizontal = direction == Direction.EAST || direction == Direction.WEST;
            for (Tile tile : laserBeam) {
                mapHandler.setLaserCell(horizontal, tile.getRow(), tile.getCol());
            }
        }
    }

    public void erasePlayerLasers() {
        mapHandler.cleanPlayerLaserLayer();
    }
}

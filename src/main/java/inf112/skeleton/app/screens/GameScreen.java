package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.*;
import inf112.skeleton.app.cards.*;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.managers.GameScreenInputProcessor;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.tiles.Hole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameScreen implements Screen {

    public final RoboRallyApplication parent;
    private final GameScreenInputProcessor inputProcessor;

    public TiledMapManager mapHandler;
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    public final static int TILE_SIZE = 50;
    private float timeInSeconds = 0f;
    private float period = 0.8f;
    private TiledMapTileLayer playerLayer;

    private ArrayList<PlayerGraphic> playerGraphics = new ArrayList<>();

    private Board board;

    private Stage stage;
    private GameLoop gameLoop;
    private final ArrayList<Player> players;

    public GameScreen(final RoboRallyApplication parent){
    this.parent = parent;
        // ---- INITIALISATION ----
        stage = new Stage(new ScreenViewport());

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
        board = new Board(players, handler);

        // Initialise board-view
        camera = new OrthographicCamera();
        camera.zoom += 0.19;
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - 300);
        renderer = new OrthogonalTiledMapRenderer(map);

        // Initialise tile-layers
        playerLayer = mapHandler.getLayer("PLAYERS");

        // Place players on Player-layer
        for (Player value : players) {
            TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
            playerLayer.setCell(value.getRow(), value.getCol(), playerCell);
        }

        // ---- GRAPHICS ----
        for (Player player : players) {
            PlayerGraphic playerGraphic = new PlayerGraphic(player);
            playerGraphics.add(playerGraphic);
            stage.addActor(playerGraphic);
            stage.addActor(player.getPlayerInfoGraphic());
        }

        // --- INPUT ----
        inputProcessor = new GameScreenInputProcessor(parent, player1, board);
        stage.addListener(inputProcessor);

        //Initialise buttons
        int buttonX = ((Integer) map.getProperties().get("width") * TILE_SIZE) + 250;
        TextButton laserOn = new TextButton("LASER ON/OFF", parent.getSkin());
        laserOn.setBounds(buttonX, 600, 200, 50);
        laserOn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                boolean on = !mapHandler.getLayer("LASERBEAMS").isVisible();
                mapHandler.getLayer("LASERBEAMS").setVisible(on);
                if (on) {
                    board.fireLasers();
                }

            }
        });
        stage.addActor(laserOn);

        TextButton conveyor = new TextButton("CONVEYOR", parent.getSkin());
        conveyor.setBounds(buttonX, 452, 150, 50);
        conveyor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                board.rollConveyorBelts(false);
                updatePlayerGraphics();
            }
        });
        stage.addActor(conveyor);

        TextButton conveyorExpress = new TextButton("EXPRESS", parent.getSkin());
        conveyorExpress.setBounds(buttonX, 400, 150, 50);
        conveyorExpress.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                board.rollConveyorBelts(true);
                updatePlayerGraphics();
            }
        });
        stage.addActor(conveyorExpress);

        TextButton button = new TextButton("PROGRAM", parent.getSkin());
        button.setBounds(buttonX, 348, 150, 50);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                players.get(0).setProgram();
                lockInProgram(players.get(0), players.get(0).getHand());
            }
        });
        stage.addActor(button);

        TextButton changePlayer = new TextButton("CHANGE", parent.getSkin());
        changePlayer.setBounds(buttonX, 296, 150, 50);
        changePlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                int index = (players.indexOf(inputProcessor.getPlayer()) + 1) % players.size();
                inputProcessor.setPlayer(players.get(index));
            }
        });
        stage.addActor(changePlayer);

        TextButton respawn = new TextButton("RESPAWN", parent.getSkin());
        respawn.setBounds(buttonX, 244, 150, 50);
        respawn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                for (Player player : players) {
                    if (player.hasQueuedRespawn) {
                        player.reSpawn(player.getDirection());
                    }
                }
                updatePlayerGraphics();
            }
        });
        stage.addActor(respawn);

        gameLoop = new GameLoop(board, this);

    }

    public void updatePlayerGraphics() {
        for (Player player : players) {
            player.getGraphics().animateMove(player.getCol(), player.getRow(), 1);
            player.getGraphics().animateRotation(player.getGraphics().getDirection(), player.getDirection());
            if (board.getTile(player) instanceof Hole) {
                player.getGraphics().addAction(Actions.scaleTo(0.01f, 0.01f, 1f));
            }
            player.getGraphics().animate();
        }
    }

    public void lockInProgram(Player player, Card[] cards){
        // Create program of selected cards from hand
        for (Card card : cards) {
            if (card.isInProgramRegister()) {
                player.getProgram()[card.programIndex - 1] = card;
            }
        }
    }

    public void lockRandomProgram(Player player) {
        player.setProgram();
        Random rand = new Random();
        int[] ranval = new int[9 - player.getDamageTokens()];
        for (int j = 0; j < ranval.length; j++) ranval[j] = j;
        for (int i = ranval.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = ranval[i];
            ranval[i] = ranval[index];
            ranval[index] = temp;
        }
        for (int i = 0; i<5; i++) {
                if (player.getHand()[i] != null) {
                    player.getProgram()[i] = player.getHand()[i];
                } else {
                    player.getProgram()[i] = null;
                }
        }
    }

    public void runProgram(Player player, int programIndex) {
        Card card = player.getProgram()[programIndex];
        board.execute(player, card);
        updatePlayerGraphics();
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        //RoboRallyApplication.music.play();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Comment this out to disable GameLoop
        timeInSeconds += Gdx.graphics.getRawDeltaTime();
        if (timeInSeconds > period) {
            timeInSeconds -= period;
            gameLoop.tick();
        }

        renderer.setView(camera);
        renderer.render();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
        stage.addActor(actor);
    }

    public void clearCards(ArrayList<CardGraphic> cardsOnScreen) {
        for (CardGraphic cardGraphic : cardsOnScreen) {
            cardGraphic.reset();
            cardGraphic.remove();
        }
    }

}

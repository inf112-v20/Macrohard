package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
import java.util.Random;

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
    private float period = 0.8f;

    private ArrayList<PlayerGraphic> playerGraphics = new ArrayList<>();

    private Board board;

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
            playerGraphics.add(playerGraphic);
            gameStage.addActor(playerGraphic);
            gameStage.addActor(player.getPlayerInfoGraphic());
        }

        // --- INPUT ----
        inputProcessor = new GameScreenInputProcessor(parent, player1, board);
        gameStage.addListener(inputProcessor);

        TextButton change = new TextButton("CHANGE", parent.getSkin());
        change.setBounds(width - (width / 4), 400, 150, 50);
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
        button.setBounds(width - (width / 4), 348, 150, 50);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                players.get(0).createEmptyProgram();
                lockInProgram(players.get(0), players.get(0).getHand());
            }
        });
        gameStage.addActor(button);

        gameLoop = new GameLoop(board, this);

    }

    public void updatePlayerGraphics() {
        for (Player player : players) {
            PlayerGraphic graphic = player.getGraphics();
            if (graphic.isVisible) {
                graphic.animateMove(1);
                graphic.animateRotation();
                if (board.getTile(player) instanceof Hole) {
                    graphic.animateFall();
                }
                graphic.animate();
            }

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
        player.createEmptyProgram();
        Random rand = new Random();
        int[] randomValue = new int[9 - player.getDamageTokens()];
        for (int j = 0; j < randomValue.length; j++) randomValue[j] = j;
        for (int i = randomValue.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = randomValue[i];
            randomValue[i] = randomValue[index];
            randomValue[index] = temp;
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

        // Comment this out to disable GameLoop
        timeInSeconds += Gdx.graphics.getRawDeltaTime();
        if (timeInSeconds > period) {
            timeInSeconds -= period;
            gameLoop.tick();
        }

        gameCamera.position.set(width / 2 - 150, gamePort.getWorldHeight() / 2 - CARD_GRAPHIC_HEIGHT, 0);
        renderer.setView(gameCamera);
        renderer.render();

        gameStage.act();
        gameStage.draw();
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

    public void clearCards(ArrayList<CardGraphic> cardsOnScreen) {
        for (CardGraphic cardGraphic : cardsOnScreen) {
            cardGraphic.reset();
            cardGraphic.remove();
        }
    }

}

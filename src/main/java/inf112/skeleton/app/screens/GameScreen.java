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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameScreen implements Screen {

    private final GameScreenInputProcessor inputProcessor;
    private TiledMapTileLayer.Cell playerCell;
    private RoboRallyApplication parent;

    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;

    private int tileSize = 60;
    private int gridSize = 12;
    private float timeInSeconds = 0f;
    private float period = 0.5f;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;

    private ArrayList<PlayerGraphic> playerGraphics = new ArrayList<>();

    private Board board;

    private Stage stage;
    private GameLoop gameLoop;
    private final ArrayList<Player> players;
    public int clientPlayerIndex = 0;

    public GameScreen(final RoboRallyApplication parent){

        // ---- INITIALISATION ----
        stage = new Stage(new ScreenViewport());
        this.parent = parent;

        // Initialise players
        Player player1 = new Player(1, 1, Direction.NORTH, false);
        Player player2 = new Player(1, 2, Direction.NORTH, true);
        Player player3 = new Player(1, 3, Direction.NORTH, true);
        Player player4 = new Player(1, 4, Direction.NORTH, true);
        players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));

        // Initialise board
        TiledMapManager handler = new TiledMapManager("assets/plsWork.tmx");
        map = handler.getMap();
        board = new Board(players, handler);

        // Initialise board-view
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(0, -tileSize*camera.zoom*2);
        renderer = new OrthogonalTiledMapRenderer(map);

        // Initialise tile-layers
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");

        // Place players on Player-layer
        for (int i = 0; i < players.size(); i++) {
            TiledMapTileLayer.Cell playerCell = new TiledMapTileLayer.Cell();
            if(i == clientPlayerIndex){
                this.playerCell = playerCell;
            }
            playerLayer.setCell(players.get(i).getRow(), players.get(i).getCol(), playerCell);
        }

        // ---- GRAPHICS ----
        for (Player player : players) {
            PlayerGraphic playerGraphic = new PlayerGraphic(player);
            playerGraphics.add(playerGraphic);
            stage.addActor(playerGraphic);
        }

        // --- INPUT ----
        inputProcessor = new GameScreenInputProcessor(parent, player1, board);
        stage.addListener(inputProcessor);

        //Initialise buttons
        TextButton conveyor = new TextButton("CONVEYOR", parent.getSkin());
        conveyor.setBounds(750, 452, 150, 50);
        conveyor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                board.rollConveyorBelts(false);
                updatePlayerGraphics();
            }
        });
        stage.addActor(conveyor);

        TextButton conveyorExpress = new TextButton("EXPRESS", parent.getSkin());
        conveyorExpress.setBounds(750, 400, 150, 50);
        conveyorExpress.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                board.rollConveyorBelts(true);
                updatePlayerGraphics();
            }
        });
        stage.addActor(conveyorExpress);

        TextButton button = new TextButton("PROGRAM", parent.getSkin());
        button.setBounds(750, 348, 150, 50);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                players.get(0).setProgram();
                lockInProgram(players.get(0), players.get(0).getHand().getDealtHand());
            }
        });
        stage.addActor(button);

        TextButton changePlayer = new TextButton("CHANGE", parent.getSkin());
        changePlayer.setBounds(750, 296, 150, 50);
        changePlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                int index = (players.indexOf(inputProcessor.getPlayer()) + 1) % players.size();
                inputProcessor.setPlayer(players.get(index));
            }
        });
        stage.addActor(changePlayer);

        gameLoop = new GameLoop(board, this);

    }

    public void updatePlayerGraphics() {
        for (Player player : players) {
            player.getGraphics().animateMove(player.getCol(), player.getRow(), 1);
            player.getGraphics().animateRotation(player.getGraphics().getDirection(), player.getDirection());
            player.getGraphics().animate();
        }
    }

    public void lockInProgram(Player player, Card[] cards){
        // Create program of selected cards from hand
        for (Card card : cards) {
            if (card.handIndex == 1) {
                player.getProgram()[0] = card;
            } else if (card.handIndex == 2) {
                player.getProgram()[1] = card;
            } else if (card.handIndex == 3) {
                player.getProgram()[2] = card;
            } else if (card.handIndex == 4) {
                player.getProgram()[3] = card;
            } else if (card.handIndex == 5) {
                player.getProgram()[4] = card;
            }
        }
        player.hasChosenCards = true;
        System.out.println(Arrays.toString(player.getProgram()
        ));
    }

    public void lockRandomProgram(Player player) {
        player.setProgram();
        Random rand = new Random();
        int[] ranval =new int[player.getHealthPoints()];
        for (int j = 0; j < ranval.length; j++) ranval[j] = j;
        for (int i = ranval.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = ranval[i];
            ranval[i] = ranval[index];
            ranval[index] = temp;
        }
        for (int i = 0; i<5; i++) {
                player.getProgram()[i] = player.getHand().getDealtHand()[i];
        }
    }

    public void runProgram(Player player, int cardNumber) {
        Card card = player.getProgram()[cardNumber];
        Direction dir = player.getDirection();
        board.execute(player, card);
        player.getGraphics().updatePlayerGraphic(card, dir);
        player.getGraphics().animate();
       /* if (player.isNPC) {
            System.out.println("NPC Played Card: " + player.getProgram()[cardNumber]
            );
        }*/
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        RoboRallyApplication.music.play();
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

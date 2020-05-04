package inf112.skeleton.app;

import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.managers.GameScreenInputProcessor;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.screens.GameScreen;
import inf112.skeleton.app.screens.WinScreen;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class RoboRallyGame {

    public static int currentProgramRegister = 0;

    private final RoboRallyApplication parent;
    private final GameScreen gameScreen;
    private final Board board;

    private PriorityQueue<Player> movementPriority;
    private ArrayList<Player> players;
    private Player client;
    private Deck deck;

    private final int nrOfFlags;
    private int phase = 0;

    public RoboRallyGame(RoboRallyApplication parent, TiledMapManager mapManager, int nrOfPlayers) {
        this.parent = parent;

        // Initialize players
        players = new ArrayList<>(nrOfPlayers);
        for (int i = 0; i < nrOfPlayers; i ++) {
            players.add(i, new Player(0, 0, Direction.EAST));
        }
        client = players.get(0);

        board = new Board(players, mapManager);
        nrOfFlags = board.getNumberOfFlags();
        gameScreen = new GameScreen(this, mapManager, parent.getScreenWidth(), parent.getScreenWidth());
        movementPriority = new PriorityQueue<>();
        deck = new Deck(true);

        GameScreenInputProcessor inputProcessor = new GameScreenInputProcessor(parent, client, board, gameScreen);
        gameScreen.getGameStage().addListener(inputProcessor);
    }

    // Assumes the non-NPC player is always the first element of the players ArrayList
    public void tick() {
        if (players.size() == 1) {
            parent.setScreen(new WinScreen(players.get(0)));
        }
        switch (phase) {
            case -2:
                if (!client.inPowerDown) {
                    phase ++;
                }
                break;
            case -1:
                for (Player player : players) {
                    if (player.announcedPowerDown) {
                        //Player starts this round in Power Down, discarding all damage tokens
                        player.powerDown();
                    } else if (player.inPowerDown) {
                        //Player is finished powering down, CPUs decide if they want to continue
                        player.inPowerDown = !player.equals(client) && player.getDamageTokens() > 4;
                    }
                }
                phase ++;
                break;
            case 0:
                // Check if new deck is needed
                int cardsNeededInDeck = 0;
                for (Player player : players) {
                    if (!player.inPowerDown) {
                        cardsNeededInDeck += player.getHandSize();
                    }
                }
                if (deck.getDeckSize() < cardsNeededInDeck) {
                    deck = new Deck(true);
                }

                // Deal hand to all non-powered down players
                for (Player player : players) {
                    if (!player.inPowerDown) {
                        deck.dealHand(player);
                    }
                }
                // Skip next phase if client is in Power Down
                if (client.inPowerDown) {
                    phase += 2;
                }
                // Else wait for client to lock program
                else {
                    phase++;
                }
                break;
            case 1:
                // Run this phase until program is locked by client
                break;
            case 2:
                // Lock in programs and announce Power Down for computer players
                for (Player player : players) {
                    if (!player.inPowerDown) {
                        if (!player.equals(client)) {
                            player.lockInRandomProgram();
                            if (player.getDamageTokens() > 4) {
                                player.announcedPowerDown = true;
                            }
                        }
                        player.discardUnselectedCards();
                    }
                }
                phase++;
                break;

            case 3:
                // Decide the order in which program cards will be played
                for (Player player : players) {
                    if (!player.inPowerDown && !player.isDestroyed()) {
                        movementPriority.add(player);
                    }
                }
                phase++;
                break;

            case 4:
                // Execute program cards in order. If last player played his card on current programRegister, continue.
                if (!movementPriority.isEmpty() && players.stream().anyMatch(player -> !(player.inPowerDown || player.isDestroyed()))) {
                    Player player = movementPriority.poll();
                    if (player != null) {
                        board.execute(player, player.getProgram()[currentProgramRegister]);
                        SoundEffects.move();
                        if (player.isDestroyedInFall()) {
                            SoundEffects.fallingRobot();
                            player.clearDestroyInFall();
                        }
                        removeDeadAndDestructedPlayers();
                    }
                } else {
                    phase++;
                }
                break;

            case 5:
                // Board elements move, starting with all conveyor belts
                if (board.rollConveyorBelts(false)) { SoundEffects.rollConveyorBelts(); }
                phase++;
                break;
            case 6:
                // Then express belts move
                if (board.rollConveyorBelts(true)) { SoundEffects.rollConveyorBelts(); }
                phase++;
                break;
            case 7:
                // And gears rotate
                if (board.rotateGears()) { SoundEffects.rotateGears(); }
                phase++;
                break;
            case 8:
                // Board- and player-lasers fire
                int damageTokens = client.getDamageTokens();
                board.fireBoardLasers();
                gameScreen.drawLasers(board.firePlayerLasers());
                SoundEffects.fireLasers();
                if (client.getDamageTokens() > damageTokens) {
                    SoundEffects.damage();
                }
                phase++;
                break;
            case 9:
                //Players that are not destroyed during phase touches flags and repair sites
                int flag = client.getPreviousFlag();
                board.touchBoardElements();
                if (client.getPreviousFlag() > flag) {
                    SoundEffects.checkpoint();
                }
                for (Player player : players) {
                    if (player.getPreviousFlag() == nrOfFlags) {
                        parent.setScreen(new WinScreen(player));
                    }
                }
                phase++;
                break;
            case 10:
                // Clean up
                // Increment programRegister and reset gameLoop values.
                // if on last programRegister or all players are destroyed, do full round cleanup
                if (currentProgramRegister == 4 || players.stream().allMatch(Player::isDestroyed)) {
                    phase++;
                } else {
                    currentProgramRegister++;
                    phase = 3;
                }
                break;
            case 13:
                for (Player player : players) {
                    if (!player.equals(client)) {
                        if (!player.inPowerDown) {
                            player.wipeProgram();
                        }
                        // Reboot destroyed players if they still have more life tokens
                        // Cancel Power Down if announced this turn
                        if (!player.equals(client) && player.isDestroyed() && !player.isDead()) {
                            player.setDirection(Direction.any());
                            player.reboot();
                            player.getPlayerGraphic().animateReboot();
                            if (player.announcedPowerDown) {
                                player.announcedPowerDown = false;
                            }
                        }
                    }
                }

                currentProgramRegister = 0;
                phase = -2;
                break;
            default:
        }
    }

    private void removeDeadAndDestructedPlayers() {
        int index = 0;
        while (index < players.size()) {
            Player player = players.get(index);
            if (player.isDead()) {
                players.remove(player);
                movementPriority.remove(player);
                board.getPlayers().remove(player);
                gameScreen.getPlayers().remove(player);
            } else if (player.isDestroyed()) {
                movementPriority.remove(player);
            }
            index++;
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getClient() {
        return client;
    }

    public void incrementPhase() {
        phase ++;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public int getPhase() {
        return phase;
    }
}
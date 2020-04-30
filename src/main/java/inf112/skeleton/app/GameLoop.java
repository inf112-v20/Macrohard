package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import inf112.skeleton.app.buttons.PowerDownButton;
import inf112.skeleton.app.buttons.ProgramButton;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.screens.GameScreen;
import inf112.skeleton.app.screens.WinScreen;
import inf112.skeleton.app.tiles.Flag;
import inf112.skeleton.app.tiles.RepairSite;
import inf112.skeleton.app.tiles.Tile;
import inf112.skeleton.app.windows.CancelPowerDownWindow;
import inf112.skeleton.app.windows.ContinuePowerDownWindow;
import inf112.skeleton.app.windows.RebootWindow;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GameLoop {

    public static int currentProgramRegister = 0;

    public final GameScreen gameScreen;
    private final Board board;

    private ArrayList<Player> players;
    public Player client;
    private ArrayList<CardGraphic> cardGraphics;
    private PriorityQueue<Player> movementPriority;
    private Deck deck;
    public int phase = 0;
    private ProgramButton programButton;
    private PowerDownButton powerDownButton;

    private final RebootWindow rebootWindow;
    private ContinuePowerDownWindow continuePowerDown;
    private CancelPowerDownWindow cancelPowerDownWindow;

    public GameLoop(Board board, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.board = board;
        this.players = board.getPlayers();
        this.client = players.get(0);
        this.movementPriority = new PriorityQueue<>();
        this.cardGraphics = new ArrayList<>(9);
        this.deck = new Deck(true);

        programButton = new ProgramButton(this, gameScreen);

        rebootWindow = new RebootWindow(gameScreen.getGameStage(), client);
        continuePowerDown = new ContinuePowerDownWindow(this, client);
        cancelPowerDownWindow = new CancelPowerDownWindow(this, client);
    }

    // Assumes the non-NPC player is always the first element of the players ArrayList
    public void tick() {
        if (players.size() == 1) {
            gameScreen.parent.setScreen(new WinScreen(players.get(0)));
        }
        switch (phase) {
            case -2:
                if (client.inPowerDown) {
                    continuePowerDown.setVisible(true);
                } else {
                    phase++;
                }
                break;
            case -1:
                continuePowerDown.setVisible(false);
                for (Player player : players) {
                    if (player.announcedPowerDown) {
                        //Player starts this round in Power Down, discarding all damage tokens
                        player.announcedPowerDown = false;
                        player.inPowerDown = true;
                        player.setDamageTokens(0);
                    } else if (player.inPowerDown) {
                        //Player is finished powering down, CPUs decide if they want to continue
                        player.inPowerDown = !player.equals(client) && player.getDamageTokens() > 4;
                    }
                }
                phase++;
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
                // Else draw cards on screen and wait for client to lock program
                else {
                    for (Card card : client.getHand()) {
                        CardGraphic cardGraphic = new CardGraphic(client, card);
                        cardGraphics.add(cardGraphic);
                        gameScreen.getGameStage().addActor(cardGraphic);
                    }
                    powerDownButton = new PowerDownButton(client, gameScreen);
                    programButton.setVisible(true);
                    phase++;
                }
                break;
            case 1:
                // Runs this phase until program is locked by client
                if (!client.hasCompleteProgram()) {
                    programButton.setColor(Color.DARK_GRAY);
                } else {
                    programButton.setColor(Color.GREEN);
                }
                break;
            case 2:
                programButton.setVisible(false);
                powerDownButton.setVisible(false);

                // Lock in programs and announce Power Down for computer players
                for (Player player : players.subList(1, players.size())) {
                    if (!player.inPowerDown) {
                        player.lockInRandomProgram();
                        if (player.getDamageTokens() > 4) {
                            player.announcedPowerDown = true;
                        }
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
                        gameScreen.updateGraphics();
                        removeDeadAndDestructedPlayers();
                    }
                } else {
                    phase++;
                }
                break;

            case 5:
                // Board elements move, starting with all conveyor belts
                board.rollConveyorBelts(false);
                gameScreen.updateGraphics();
                phase++;
                break;
            case 6:
                // Then express belts move
                board.rollConveyorBelts(true);
                gameScreen.updateGraphics();
                phase++;
                break;
            case 7:
                // And gears rotate
                board.rotateGears();
                gameScreen.updateGraphics();
                SoundEffects.ROTATE_GEARS.play(gameScreen.parent.getPreferences().getSoundVolume());
                phase++;
                break;
            case 8:
                // Board- and player-lasers fire
                gameScreen.mapHandler.getLayer("LASERBEAMS").setVisible(true);
                board.fireBoardLasers();
                gameScreen.drawPlayerLasers(board.firePlayerLasers());
                SoundEffects.FIRE_LASERS.play(gameScreen.parent.getPreferences().getSoundVolume());
                gameScreen.updateGraphics();
                phase++;
                break;
            case 9:
                //Players that are not destroyed during phase touches flags and repair sites
                for (Player player : players) {
                    if (!player.isDestroyed()) {
                        Tile tile = board.getTile(player);
                        if (tile instanceof Flag) {
                            Flag flag = (Flag) tile;
                            player.setArchiveMarker(flag);
                            if (player.getNextFlag() == flag.getNumber()) {
                                player.touchFlag();
                                player.getInfoGraphic().updateValues();
                                if (player.getPreviousFlag() == board.getNumberOfFlags()) {
                                    gameScreen.parent.setScreen(new WinScreen(player));
                                }
                            }
                        } else if (tile instanceof RepairSite) {
                            player.setArchiveMarker(tile);
                            player.repair();
                        }
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
                gameScreen.mapHandler.getLayer("LASERBEAMS").setVisible(false);
                gameScreen.erasePlayerLasers();
                break;
            case 11:
                if (client.isDestroyed() && !client.isDead()) {
                    rebootWindow.setVisible(true);
                } else {
                    rebootWindow.setVisible(false);
                    if (client.announcedPowerDown) {
                        cancelPowerDownWindow.setVisible(true);
                    } else {
                        phase++;
                    }

                }
                break;
            case 12:
                cancelPowerDownWindow.setVisible(false);
                for (Player player : players) {
                    player.discardHandAndWipeProgram();

                    // Reboot destroyed players if they still have more life tokens
                    // Cancel Power Down if announced this turn
                    if (player.isDestroyed() && !player.isDead()) {
                        player.setDirection(Direction.any());
                        player.reboot();
                        player.getPlayerGraphic().animateReboot();
                        if (player.announcedPowerDown) {
                            player.announcedPowerDown = false;
                        }
                    }
                }
                gameScreen.clearCards(cardGraphics);
                currentProgramRegister = 0;
                phase = -2;
                break;
            default:
                System.out.println("phase index did an oopsie :)");
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

}
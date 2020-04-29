package inf112.skeleton.app;


import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.screens.GameScreen;
import inf112.skeleton.app.screens.WinScreen;
import inf112.skeleton.app.tiles.Flag;
import inf112.skeleton.app.tiles.RepairSite;
import inf112.skeleton.app.tiles.Tile;

import java.util.*;

public class GameLoop {

    private final GameScreen gameScreen;
    private final Board board;

    private ArrayList<Player> players;
    private Player client;
    private ArrayList<CardGraphic> cardGraphics;
    private PriorityQueue<Player> movementPriority;
    private Deck deck;
    public int phase = 0;
    private int currentProgramRegister = 0;
    private TextButton[] powerDownStatus;
    public boolean buttonsDisplayed = false;
    public boolean powerDownInput = false;
    private boolean cardsDisplayed = false;
    private boolean canClean = false;
    private boolean canPlay = false;
    private boolean roundOver = false;

    public GameLoop(Board board, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.board = board;
        this.players = board.getPlayers();
        this.client = players.get(0);
        this.movementPriority = new PriorityQueue<>();
        this.cardGraphics = new ArrayList<>(9);
        this.deck = new Deck(true);

    }

    // Assumes the non-NPC player is always the first element of the players ArrayList
    public void tick() {
        if (players.size() == 1) {
            gameScreen.parent.setScreen(new WinScreen(players.get(0)));
        }
        switch (phase) {
            case 0:
                gameScreen.powerDown();
                // Check if new deck is needed
                int cardsNeededInDeck = 0;
                for (Player player : players) {
                    cardsNeededInDeck += player.getHandSize();
                }
                if (deck.getDeckSize() < cardsNeededInDeck) {
                    deck = new Deck(true);
                }

                // Deal hand to all players or discard all damageTokens if in Power Down
                for (Player player : players) {
                    if (!player.inPowerDown) {
                        deck.dealHand(player);
                    }
                    else {
                        player.setDamageTokens(0);
                    }
                }


                phase++;
                break;

            case 1:
                // Draw cards on screen and lock in the programs for all NPC's
                if (!cardsDisplayed && !client.inPowerDown) {
                    for (Card card : client.getHand()) {
                        CardGraphic cardGraphic = new CardGraphic(card);
                        gameScreen.addStageActor(cardGraphic);
                        cardGraphics.add(cardGraphic);
                    }
                    cardsDisplayed = true;
                    }
                for (Player player : players.subList(1, players.size())) {
                    if (!player.inPowerDown) {
                        player.lockInRandomProgram();
                    }
                }
                if (client.hasLockedInProgram() || client.inPowerDown) {
                    phase++;
                }
                break;

            case 2:
                // Decide the order in which program cards will be played
                if (currentProgramRegister < 5) {
                    for (Player player : players) {
                        if (!player.inPowerDown && !player.isDestroyed() && player.getProgram()[currentProgramRegister] != null) {
                            movementPriority.add(player);
                        }
                    }
                    canPlay = true;
                    phase ++;
                    break;
                }
                break;

            case 3:
                // Execute program cards in order. If last player played his card on current programRegister, continue.
                if (canPlay && currentProgramRegister < 5 && !movementPriority.isEmpty()) {
                    Player player = movementPriority.poll();
                    gameScreen.runProgram(player, currentProgramRegister);
                    if (player.isDead()) {
                        remove(player);
                    }
                    if (movementPriority.isEmpty()) {
                        canPlay = false;
                        phase++;
                    }
                }
                // if none of the players are able to move, continue.
                if (players.stream().allMatch(player -> player.inPowerDown || player.isDestroyed())) {
                   currentProgramRegister = 4;
                    phase++;

                }
                break;

            case 4:
                // Board elements move, starting with all conveyor belts
                if (movementPriority.isEmpty()) {
                    board.rollConveyorBelts(false);
                    gameScreen.updateGraphics();
                    phase ++;
                }
                break;
            case 5:
                // Express belts move
                board.rollConveyorBelts(true);
                gameScreen.updateGraphics();
                phase ++;
                break;
            case 6:
                // Gears rotate
                board.rotateGears();
                gameScreen.updateGraphics();
                SoundEffects.ROTATE_GEARS.play(gameScreen.parent.getPreferences().getSoundVolume());
                phase++;
                break;
            case 7:
                // Board lasers fire
                gameScreen.mapHandler.getLayer("LASERBEAMS").setVisible(true);
                board.fireBoardLasers();
                board.firePlayerLasers();
                SoundEffects.FIRE_LASERS.play(gameScreen.parent.getPreferences().getSoundVolume());
                gameScreen.updateGraphics();
                phase++;
                break;
            case 8:
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
                        }
                        if (tile instanceof RepairSite) {
                            player.setArchiveMarker(tile);
                            player.repair();
                        }
                    }
                }
                canClean = true;
                phase++;
                break;
            case 9:
                // Clean up
                // Increment programRegister and reset gameLoop values.
                // if on last programRegister, do full round cleanup
                if (currentProgramRegister == 4) {
                    roundOver = true;
                    phase ++;
                } else {
                    currentProgramRegister ++;
                    phase = 2;
                    canClean = false;
                }
                gameScreen.mapHandler.getLayer("LASERBEAMS").setVisible(false);
                break;

            case 10:
                if (canClean && roundOver &&!powerDownInput) {
                    if (client.isDestroyed() && !client.isDead() && !buttonsDisplayed) {
                        buttonsDisplayed = true;
                        gameScreen.openRebootWindow();
                    } else if(!buttonsDisplayed) {
                        buttonsDisplayed = true;
                        powerDownStatus = gameScreen.powerDownOptions();
                    }
                    }else {
                    phase++;
                }
                break;
            case 11:
                if (gameScreen.rebootWindow.isVisible()) {
                    gameScreen.closeRebootWindow();
                } else {
                    powerDownStatus[0].remove();
                    powerDownStatus[1].remove();
                }
                gameScreen.closeRebootWindow();
                for (Player player : players) {
                    player.discardHandAndWipeProgram();

                    //Reboot destroyed players if they still have more life tokens
                    if (player.isDestroyed() && !player.isDead()) {
                        player.setDirection(Direction.any());
                        player.reboot();
                        player.getPlayerGraphic().animateReboot();
                    }
                }
                // NPC announce power down if it has a certain amount of damage tokens
                for (Player player : players.subList(1, players.size())) {
                    if (player.inPowerDown && player.getDamageTokens() < 4) {
                        player.inPowerDown = false;
                    }
                    if (player.getDamageTokens() > 4) {
                        player.inPowerDown = true;
                    }
                }

                gameScreen.clearCards(cardGraphics);
                buttonsDisplayed = false;
                powerDownInput = false;
                cardsDisplayed = false;
                canClean = false;
                currentProgramRegister = 0;
                phase = 0;
                break;
            default:
                System.out.println("phase index did an oopsie :)");
        }
    }

    private void remove(Player player) {
        players.remove(player);
        board.getPlayers().remove(player);
        gameScreen.getPlayers().remove(player);
    }

}
package inf112.skeleton.app;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.screens.GameScreen;
import inf112.skeleton.app.tiles.Flag;
import inf112.skeleton.app.tiles.RepairSite;
import inf112.skeleton.app.tiles.Tile;

import java.util.*;

public class GameLoop {

    private final Sound laserSound = Gdx.audio.newSound(Gdx.files.internal("data/Sounds/laserbeam.wav"));
    private final GameScreen gameScreen;
    private final Board board;

    private ArrayList<Player> players;
    private Player client;
    private ArrayList<CardGraphic> cardGraphics;
    private PriorityQueue<Player> movementPriority;
    private Deck deck;
    private int buttonX;
    private int phase = 0;
    private int currentProgramRegister = 0;

    private boolean cardsDisplayed = false;
    private boolean canClean = false;
    private boolean canPlay = false;
    private boolean roundOver = false;

    public GameLoop(Board board, GameScreen gameScreen, int buttonX) {
        this.gameScreen = gameScreen;
        this.board = board;
        this.buttonX = buttonX;
        this.players = board.getPlayers();
        this.client = players.get(0);
        this.movementPriority = new PriorityQueue<>();
        this.cardGraphics = new ArrayList<>(9);
        this.deck = new Deck(true);

    }

    // Assumes the non-NPC player is always the first element of the players ArrayList
    public void tick() {
        switch (phase) {
            case 0:
                // display powerdown button or continue powerdown button
                gameScreen.setPowerdown(buttonX);
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
                    } else {
                        player.setDamageTokens(0);
                    }
                }

                // NPC announce power down if it has a certain ammount of damage tokens
                for (Player player : players.subList(1, players.size())) {
                    if (player.getDamageTokens() >= 4) {
                        player.announcedPowerDown = true;
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
                        gameScreen.lockRandomProgram(player);
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
                        if (!player.inPowerDown) {
                            movementPriority.add(player);
                        }
                    }
                   // movementPriority.addAll(players);
                    canPlay = true;
                    phase++;
                    break;
                }
                break;

            case 3:
                // Execute program cards in order. If last player played his card on current programRegister, continue.
                if (canPlay && currentProgramRegister < 5 && !movementPriority.isEmpty()) {
                    Player player = movementPriority.poll();
                    gameScreen.runProgram(player, currentProgramRegister);
                    if (movementPriority.isEmpty()) {
                        canPlay = false;
                        phase++;
                    }
                }
                break;

            case 4:
                // Board elements move, starting with all conveyor belts
                if (movementPriority.isEmpty() && !canPlay) {
                    board.rollConveyorBelts(false);
                    gameScreen.updatePlayerGraphics();
                    phase++;
                }
                break;
            case 5:
                // Express belts move
                board.rollConveyorBelts(true);
                gameScreen.updatePlayerGraphics();
                phase++;
                break;
            case 6:
                // Gears rotate
                board.rotateGears();
                gameScreen.updatePlayerGraphics();
                phase++;
                break;
            case 7:
                // Board lasers fire
                gameScreen.mapHandler.getLayer("LASERBEAMS").setVisible(true);
                board.fireLasers();
                laserSound.play(gameScreen.parent.getPreferences().getSoundVolume());
                phase++;
                break;
            case 8:
                for (Player player : players) {
                    Tile tile = board.getTile(player);
                    if (tile instanceof Flag) {
                        player.setArchiveMarker(tile);
                    }
                    if (tile instanceof RepairSite) {
                        player.setArchiveMarker(tile);
                        if (player.getDamageTokens() > 0) {
                            player.setDamageTokens(player.getDamageTokens()-1);
                        }
                    }
                    player.getPlayerInfoGraphic().updateValues();
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
                    phase++;
                } else {
                    currentProgramRegister++;
                    phase = 2;
                    canClean = false;
                }
                gameScreen.mapHandler.getLayer("LASERBEAMS").setVisible(false);
                break;

            case 10:
                if (canClean && roundOver) {
                    for (Player player : players) {
                        player.clearHand();
                        if (player.hasQueuedRespawn) {
                            player.reSpawn(player.getDirection());
                        }
                        // Player chose to not continue power down
                        if (player.announcedPowerDown && player.inPowerDown && !player.continuePowerDown) {
                            player.announcedPowerDown = false;
                            player.inPowerDown = false;
                        }
                        // Player announced power down this turn and will enter power down next turn
                        if (player.announcedPowerDown) {
                            player.inPowerDown = true;
                        }



                    }
                    gameScreen.updatePlayerGraphics();
                    gameScreen.clearCards(cardGraphics);
                    cardsDisplayed = false;
                    currentProgramRegister = 0;
                    canClean = false;
                    phase = 0;
                    break;
                }
            default:
                System.out.println("phase index did an oopsie :)");
        }
    }

}

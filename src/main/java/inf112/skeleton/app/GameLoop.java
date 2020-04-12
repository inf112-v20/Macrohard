package inf112.skeleton.app;


import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.screens.GameScreen;

import java.util.*;

import static java.util.Comparator.comparing;

public class GameLoop {

    private final GameScreen gameScreen;
    private Board board;
    ArrayList<Player> players;
    ArrayList<CardGraphic> cardImages;
    ArrayList<Player> priority;
    Deck deck;
    private int phase;
    private int programRegister = 0;
    private int playersPlayed = 0;

    private boolean cardsShown;
    private boolean canClean;
    private boolean canPlay;
    private boolean roundOver;

    public GameLoop(Board board, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.board = board;
        this.players = board.getPlayers();
        this.cardImages = new ArrayList<>(9);
        this.deck = new Deck(true);
        phase = 0;
    }

    // Assumes the non-NPC player is always the first element of the players ArrayList
    public void tick() {
       switch (phase) {

            case 0:
                int cardsNeeded = 0;
                for (Player player : players) {
                    cardsNeeded += player.getHandSize();
                }
                if (deck.getDeckSize() < cardsNeeded) {
                    deck = new Deck(true);
                }

                // Deal hand to all players.
                for(Player player : players){
                    deck.dealHand(player);
                }
                phase ++;
                break;

           case 1:
               // Draw cards on screen and lock in program for NPC's
               if (!cardsShown) {
                   for (Card card : players.get(0).getHand().getHand()) {
                       CardGraphic cardGraphic = new CardGraphic(card);
                       gameScreen.addStageActor(cardGraphic);
                       cardImages.add(cardGraphic);
                   }
                   cardsShown = true;
                   for (int j = 1; j < players.size(); j++) {
                       gameScreen.lockRandomProgram((players.get(j)));
                   }
               }
               if (players.get(0).hasChosenCards) {
                   phase++;
                   break;
               }
               break;

            case 2:
                // Decide the order in which program cards will be played
                if (players.get(0).getProgram() != null && programRegister <5) {
                    priority = priorityHandler();
                    canPlay = true;
                    phase ++;
                    break;
                }
                break;

           case 3:
               // Execute program cards in order. If last player played his card on current programRegister, continue.
               if (canPlay && programRegister < 5 && !priority.isEmpty()) {
                       gameScreen.runProgram(priority.get(playersPlayed), programRegister);
                       phase ++;
                       break;
               }
               break;

           case 4:
               if (playersPlayed < priority.size()-1) {
                   playersPlayed++;
                   phase --;
                   break;

               } else {
                   priority.clear();
                   canPlay = false;
                   phase++;
                   break;
               }

           case 5:
               // Board elements move
               if(priority.isEmpty() && !canPlay) {
                   board.rollConveyorBelts(false);
                   gameScreen.updatePlayerGraphics();
                   board.rollConveyorBelts(true);
                   gameScreen.updatePlayerGraphics();
                   gameScreen.mapHandler.getLayer("LASERBEAMS").setVisible(true);
                   board.fireLasers();
               canClean = true;
               phase ++;
               break; } break;


           case 6:
               // Increment programRegister and reset gameLoop values.
               // if on last programRegister, do full round cleanup
               if (programRegister == 4) {
                   roundOver = true;
                   phase++;
               } else {
                   programRegister++;
                   playersPlayed = 0;
                   phase = 2;
                   canClean = false;
               }
               gameScreen.mapHandler.getLayer("LASERBEAMS").setVisible(false);
               break;

           case 7:
        if (canClean && roundOver) {
            for (Player player : players) {
                player.clearHand();
            }
            playersPlayed = 0;
            gameScreen.clearCards(cardImages);
            cardsShown = false;
            programRegister = 0;
            canClean = false;
            players.get(0).hasChosenCards = false;
            phase = 0;
            break;
        }
           default:
               System.out.println("phase index did an oopsie :)");
        }
        }



    // returns an ArrayList with Players sorted in descending order by cardpriority in current programregister
    // Player associated with Card that has highest priority goes first.
    public ArrayList<Player> priorityHandler() {
        for (Player player : players) {
            player.programRegister = programRegister;
        }
        ArrayList<Player> playersCopy = new ArrayList<>(players);
        Collections.sort(playersCopy, comparing(Player::getPriorityOfCardOnCurrentProgramRegister));
        return playersCopy;
    }
}

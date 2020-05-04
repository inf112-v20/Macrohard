package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player;
    Deck deck;
    Board board;

    @Before
    public void setUp() {
        player = new Player(5, 5, Direction.any());
        deck = new Deck(true);
        board = new Board(10, 10, player);
        player.setArchiveMarker(board.getTile(4, 4));
        deck.dealHand(player);
    }

    @Test
    public void playerLockingRandomProgramYieldsCompleteProgram() {
        player.lockInRandomProgram();
        assertTrue(player.hasCompleteProgram());
    }

    @Test
    public void playerIsDeadAfterBeingDestroyedThreeTimes() {
        player.destroy();
        player.destroy();
        player.destroy();
        assertTrue(player.isDead());
    }

    @Test
    public void poweringDownResetsDamageTokens() {
        player.applyDamage(1);
        player.applyDamage(1);
        player.powerDown();
        assertEquals(0, player.getDamageTokens());
    }

    @Test
    public void handSizeAndNumberOfLockedProgramRegistersAreDeterminedByDamageTaken() {
        assertEquals(9, player.getHandSize());
        assertEquals(0, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(8, player.getHandSize());
        assertEquals(0, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(7, player.getHandSize());
        assertEquals(0, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(6, player.getHandSize());
        assertEquals(0, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(5, player.getHandSize());
        assertEquals(0, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(4, player.getHandSize());
        assertEquals(1, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(3, player.getHandSize());
        assertEquals(2, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(2, player.getHandSize());
        assertEquals(3, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(1, player.getHandSize());
        assertEquals(4, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertEquals(0, player.getHandSize());
        assertEquals(5, player.getNrOfLockedProgramRegisters());
        player.applyDamage(1);
        assertTrue(player.isDestroyed());
    }

    @Test
    public void rebootingPlacesPlayerOnArchiveMarkerAndGivesTwoDamageTokens() {
        player.destroy();
        player.reboot();
        assertEquals(2, player.getDamageTokens());
        assertFalse(player.isDestroyed());
        assertEquals(player.archiveMarker, board.getTile(player));
    }

    @Test
    public void cardsNotSelectedAreDiscarded() {
        player.lockInRandomProgram();
        player.discardUnselectedCards();
        assertEquals(player.getProgram().length, player.getCards().size());
    }

    @Test
    public void wipingProgramRetainsLockedCardsAccordingToDamageTaken() {
        player.lockInRandomProgram();
        Card cardInProgramRegisterFive = player.getProgram()[4];
        player.applyDamage(5);
        player.wipeProgram();
        assertEquals(cardInProgramRegisterFive, player.getProgram()[4]);

        deck.dealHand(player);
        player.lockInRandomProgram();
        Card cardInProgramRegisterFour = player.getProgram()[3];
        player.applyDamage(1);
        player.wipeProgram();
        assertEquals(cardInProgramRegisterFive, player.getProgram()[4]);
        assertEquals(cardInProgramRegisterFour, player.getProgram()[3]);

        deck.dealHand(player);
        player.lockInRandomProgram();
        Card cardInProgramRegisterThree = player.getProgram()[2];
        player.applyDamage(1);
        player.wipeProgram();
        assertEquals(cardInProgramRegisterFive, player.getProgram()[4]);
        assertEquals(cardInProgramRegisterFour, player.getProgram()[3]);
        assertEquals(cardInProgramRegisterThree, player.getProgram()[2]);

        deck.dealHand(player);
        player.lockInRandomProgram();
        Card cardInProgramRegisterTwo = player.getProgram()[1];
        player.applyDamage(1);
        player.wipeProgram();
        assertEquals(cardInProgramRegisterFive, player.getProgram()[4]);
        assertEquals(cardInProgramRegisterFour, player.getProgram()[3]);
        assertEquals(cardInProgramRegisterThree, player.getProgram()[2]);
        assertEquals(cardInProgramRegisterTwo, player.getProgram()[1]);

        deck.dealHand(player);
        player.lockInRandomProgram();
        Card[] lockedProgram = player.getProgram();
        player.applyDamage(1);
        player.wipeProgram();
        assertSame(lockedProgram, player.getProgram());
    }

}
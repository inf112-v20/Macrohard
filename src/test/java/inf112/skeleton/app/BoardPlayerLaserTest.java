package inf112.skeleton.app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardPlayerLaserTest {

    private Board board;
    private Player player1;
    private Player player2;
    private Player player3;
    private int player1NoDamageTaken;
    private int player2NoDamageTaken;
    private int player3NoDamageTaken;


    @Before
    public void setUp() {
        player1 = new Player(3, 3, Direction.EAST);
        player2 = new Player(3, 4, Direction.WEST);
        player3 = new Player(3, 5, Direction.EAST);
        player1NoDamageTaken = player1.getDamageTokens();
        player2NoDamageTaken = player2.getDamageTokens();
        player3NoDamageTaken = player3.getDamageTokens();
        board = new Board(10, 10, player1, player2, player3);
    }

    @Test
    public void playersShootingEachOtherYieldsOneDamageTokenEach() {
        board.firePlayerLasers();

        assertEquals(player1NoDamageTaken + 1, player1.getDamageTokens());
        assertEquals(player2NoDamageTaken + 1, player2.getDamageTokens());
    }

    @Test
    public void noDamageTokensDealtWhenWallBetweenPlayerLasers() {
        board.erectWall(player1.getDirection(), player1.getRow(), player1.getCol());
        board.firePlayerLasers();

        assertEquals(player1NoDamageTaken, player1.getDamageTokens());
        assertEquals(player2NoDamageTaken, player2.getDamageTokens());
    }

    @Test
    public void destroyedPlayersDoesNotFireLasers() {
        player1.destroy();
        board.firePlayerLasers();

        assertEquals(player2NoDamageTaken, player2.getDamageTokens());
    }

    @Test
    public void playerLasersDoesNotFireThroughMultiplePlayers() {
        board.firePlayerLasers();
        assertEquals(player3NoDamageTaken, player3.getDamageTokens());
    }

    @Test
    public void wallsBehindPlayersDoesNotStopPlayerLaser() {
        board.erectWall(player1.getDirection().opposite(), player1.getRow(), player1.getCol());
        board.erectWall(player2.getDirection().opposite(), player2.getRow(), player2.getCol());
        board.firePlayerLasers();

        assertEquals(player1NoDamageTaken + 1, player1.getDamageTokens());
        assertEquals(player2NoDamageTaken + 1, player2.getDamageTokens());
    }


}
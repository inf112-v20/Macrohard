package inf112.skeleton.app;

import inf112.skeleton.app.tiles.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class BoardPlayerLaserTest {

    private ArrayList<LinkedList<Tile>> playerLasers;
    private Board board;
    private Player player1;
    private Player player2;
    private Player player3;
    private int player1NoDamageTaken;
    private int player2NoDamageTaken;
    private int player3NoDamageTaken;


    @Before
    public void setUp() {
        player1 = new Player(3,3,Direction.EAST);
        player2 = new Player(3,4,Direction.WEST);
        player3 = new Player(3,5,Direction.EAST);
        player1NoDamageTaken = player1.getDamageTokens();
        player2NoDamageTaken = player2.getDamageTokens();
        player3NoDamageTaken = player3.getDamageTokens();
        board = new Board(10,10,player1,player2,player3);
    }

    @Test
    public void playersShootingEachOtherYieldsOneDamageTokenEach() {
        playerLasers = board.firePlayerLasers();
        assertEquals(player1NoDamageTaken+1, player1.getDamageTokens());
        assertEquals(player2NoDamageTaken+1,player2.getDamageTokens());
    }

    @Test
    public void noDamageTokensDealtWhenWallBetweenPlayerLasers(){
        board.getTile(player1).getWalls().add(player1.getDirection());
        //board.getTile(player2).getWalls().add(player2.getDirection());
        playerLasers = board.firePlayerLasers();
        assertEquals(player1NoDamageTaken,player1.getDamageTokens());
        assertEquals(player2NoDamageTaken,player2.getDamageTokens());
    }

    @Test
    public void destroyedPlayersDoesNotFireLasers() {
        player1.destroy();
        playerLasers = board.firePlayerLasers();
        assertEquals(player2NoDamageTaken,player2.getDamageTokens());
    }

    @Test
    public void playerLasersDoesNotFireThroughMultiplePlayers(){
        playerLasers = board.firePlayerLasers();
        assertEquals(player3NoDamageTaken, player3.getDamageTokens());
    }

    @Test
    public void wallsBehindPlayersDoesNotStopPlayerLaser(){
        board.getTile(player1).getWalls().add(player1.getDirection().opposite());
        board.getTile(player2).getWalls().add(player2.getDirection().opposite());
        playerLasers = board.firePlayerLasers();
        assertEquals(player1NoDamageTaken+1, player1.getDamageTokens());
        assertEquals(player2NoDamageTaken+1,player2.getDamageTokens());
    }


}
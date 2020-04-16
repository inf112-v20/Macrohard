package inf112.skeleton.app;

import inf112.skeleton.app.tiles.Laser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardLaserTest {

    private Board board;
    private Laser laser;
    private Laser doubleLaser;

    private Player targetPlayer;
    private Player shieldedPlayer;

    private final int laserRow = 2;
    private int col = 0;

    @Before
    public void setUp() {
        laser = new Laser(laserRow, col, 1, Direction.EAST);
        doubleLaser = new Laser(laserRow, col ++, 2, Direction.EAST);
        targetPlayer = new Player(laserRow, col ++, Direction.any());
        shieldedPlayer = new Player(laserRow, col ++, Direction.any());
        board = new Board(10, 10, targetPlayer, shieldedPlayer);
        board.getLasers().add(laser);
    }

    @Test
    public void firingOneLaserOnceGivesOneDamageTokenToTargetPlayer() {
        board.fireLasers();

        assertEquals(laser.getDamage(), targetPlayer.getDamageTokens());
    }

    @Test
    public void firingDoubleLaserOnceGivesTwoDamageTokensToTargetPlayer() {
        board.getLasers().remove(laser);
        board.getLasers().add(doubleLaser);
        board.fireLasers();

        assertEquals(doubleLaser.getDamage(), targetPlayer.getDamageTokens());
    }

    @Test
    public void firingOneLaserTwiceGivesTwoDamageTokensToTargetPlayer() {
        board.fireLasers();
        board.fireLasers();

        assertEquals((laser.getDamage() * 2), targetPlayer.getDamageTokens());
    }

    @Test
    public void targetPlayerShieldsNextPlayerFromDamage() {
        int shieldedPlayerDamageTokens = shieldedPlayer.getDamageTokens();
        board.fireLasers();

        assertEquals(shieldedPlayerDamageTokens, shieldedPlayer.getDamageTokens());
    }

    @Test
    public void damageGivenToShieldedPlayerWhenRemovingTargetPlayerFromBoardBeforeFiringLaser() {
        board.getTile(targetPlayer).setPlayer(null);
        board.fireLasers();

        assertEquals(laser.getDamage(), shieldedPlayer.getDamageTokens());
    }

    @Test
    public void erectingWallBetweenLaserAndTargetPlayerShieldsTargetPlayerFromDamage() {
        int targetPlayerDamageTokens = targetPlayer.getDamageTokens();
        board.getTile(laser).getWalls().add(laser.getDirection());
        board.fireLasers();

        assertEquals(targetPlayerDamageTokens, targetPlayer.getDamageTokens());
    }

    @Test
    public void erectingWallBehindLaserDoesNotObstructLaserBeam() {
        int targetPlayerDamageTokens = targetPlayer.getDamageTokens();
        board.getTile(laserRow, laser.getCol()).getWalls().add(laser.getDirection().opposite());
        board.fireLasers();

        assertNotEquals(targetPlayerDamageTokens,
                targetPlayer.getDamageTokens());
    }


}
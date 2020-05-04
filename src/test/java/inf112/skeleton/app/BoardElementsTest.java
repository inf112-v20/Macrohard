package inf112.skeleton.app;

import inf112.skeleton.app.tiles.*;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class BoardElementsTest {

Player player;
Hole hole;
Gear gearClockWise;
Gear gearCounterClockWise;
RepairSite repairSite;
Flag flag;
Board board;


    @Before
    public void setUp() {
        player = new Player(5,5,Direction.any());
        hole = new Hole(4,5);
        gearClockWise = new Gear(5,6,true);
        gearCounterClockWise = new Gear(5,7,false);
        repairSite = new RepairSite(5,4);
        flag = new Flag(1,5,8);
        board = new Board(10,10,player);

    }

    @Test
    public void walkingIntoHoleDestroysRobot() {
        board.layTile(hole);
        int maxLifeTokens = player.getLifeTokens();
        board.stepOne(player,Direction.SOUTH);
        assertTrue(player.isDestroyed());
        assertEquals(maxLifeTokens-1, player.getLifeTokens());
    }

    @Test
    public void standingOnClockWiseGearRotatesRobotAccordingly() {
        board.layTile(gearClockWise);
        board.stepOne(player,Direction.EAST);
        Direction previousDirection = player.getDirection();
        board.rotateGears();
        assertEquals(player.getDirection(), previousDirection.turnClockwise());
    }

    @Test
    public void standingOnCounterClockWiseGearRotatesRobotAccordingly() {
        board.layTile(gearCounterClockWise);
        board.stepOne(player,Direction.EAST);
        board.stepOne(player,Direction.EAST);
        Direction previousDirection = player.getDirection();
        board.rotateGears();
        assertEquals(player.getDirection(), previousDirection.turnCounterClockwise());
    }

    @Test
    public void walkingOnFlagUpdatesPlayerParameters(){
        board.layTile(flag);
        board.stepOne(player,Direction.EAST);
        board.stepOne(player,Direction.EAST);
        board.stepOne(player,Direction.EAST);
        board.touchBoardElements();
        assertEquals(flag.getNumber(), player.getPreviousFlag());
        assertEquals(board.getTile(flag.getRow(),flag.getCol()), player.archiveMarker);
    }

    @Test
    public void walkingOnRepairSiteWithNoDamageTokensUpdatesOnlyArchiveMarker(){
        board.layTile(repairSite);
        board.stepOne(player,Direction.WEST);
        int noDamageTaken = player.getDamageTokens();
        board.touchBoardElements();
        assertEquals(noDamageTaken, player.getDamageTokens());
        assertEquals(board.getTile(repairSite.getRow(), repairSite.getCol()), player.archiveMarker);

    }

    @Test
    public void walkingOnRepairSiteWithDamageTokensRemovesOneAndUpdatesArchiveMarker() {
        board.layTile(repairSite);
        board.stepOne(player,Direction.WEST);
        int tokensBeforeDamage = player.getDamageTokens();
        player.applyDamage(1);
        board.touchBoardElements();
        assertEquals(tokensBeforeDamage, player.getDamageTokens());
        assertEquals(board.getTile(repairSite.getRow(), repairSite.getCol()), player.archiveMarker);
    }

}
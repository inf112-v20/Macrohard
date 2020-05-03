package inf112.skeleton.app;

import inf112.skeleton.app.tiles.Flag;
import inf112.skeleton.app.tiles.Gear;
import inf112.skeleton.app.tiles.Hole;
import inf112.skeleton.app.tiles.RepairSite;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
        repairSite = new RepairSite(5,8);
        flag = new Flag(1,5,9);
        board = new Board(10,10,player);
    }

    @Test
    public void dockPlayers() {


    }

    @Test
    public void rotateGears() {

    }
}
package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.PlayerHand;
import inf112.skeleton.app.graphics.PlayerGraphic;

import java.util.ArrayList;

public class Player {

    public boolean hasChosenCards;
    public boolean isNPC;
    private int row;
    private int col;
    private Direction direction;
    public PlayerHand hand;
    private int healthPoints;
    private PlayerGraphic playerGraphic;
    private ArrayList<Card> program;

    public Player(int row, int col, Direction direction, boolean isNPC) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.isNPC = isNPC;
        this.healthPoints = 9;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setProgram() {
        hand.setProgram();
    }

    public void setHealthPoints (int hp) { this.healthPoints = hp;}

    public Direction getDirection () {
        return this.direction;
    }

    public int getRowTrajectory() {
        return direction.getRowTrajectory();
    }

    public int getColumnTrajectory() {
        return direction.getColumnTrajectory();
    }

    public PlayerHand getHand() {
        return this.hand;
    }

    public Card[] getProgram() {
        return hand.getProgram();
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }


    public void setGraphic(PlayerGraphic playerGraphic) {
        this.playerGraphic = playerGraphic;
    }

    public PlayerGraphic getGraphics() {
        return this.playerGraphic;
    }
    public boolean programHasNull() {
        return hand.hasNull(getProgram());
    }

}

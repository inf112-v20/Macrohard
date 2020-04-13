package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.PlayerHand;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.tiles.Tile;
import org.jetbrains.annotations.NotNull;

public class Player implements Comparable<Player> {

    public boolean isNPC;
    private int row;
    private int col;
    private Tile archiveMarker;
    private Direction direction;
    public PlayerHand hand;

    private int damageTokens;
    private int lifeTokens;

    private PlayerGraphic playerGraphic;
    public int programRegister = 0;
    public boolean hasQueuedRespawn = false;


    public Player(int row, int col, Direction direction, boolean isNPC) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.isNPC = isNPC;
        this.damageTokens = 0;
        this.lifeTokens = 3;
    }

    public Tile getArchiveMarker() {
        return archiveMarker;
    }

    public void setArchiveMarker(Tile archiveMarker) {
        this.archiveMarker = archiveMarker;
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

    public void stepIn(Direction direction) {
        setRow(row + direction.getRowTrajectory());
        setCol(col + direction.getColumnTrajectory());
    }

    private void reSpawn() {
        setRow(archiveMarker.getRow());
        setCol(archiveMarker.getCol());
    }

    public void reSpawn(Direction direction) {
        reSpawn();
        setDirection(direction);
        hasQueuedRespawn = false;

        playerGraphic.respawn();
    }

    public Direction getDirection () {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public PlayerHand getHand() {
        return this.hand;
    }

    public Card[] getProgram() {
        return hand.getProgram();
    }

    public boolean hasLockedInProgram() { return getProgram() != null; }

    public void setProgram() {
        hand.setProgram();
    }

    public int getDamageTokens() {
        return damageTokens;
    }

    public int getHandSize() { return 9 - damageTokens; }

    public PlayerGraphic getGraphics() {
        return this.playerGraphic;
    }

    public void setGraphic(PlayerGraphic playerGraphic) {
        this.playerGraphic = playerGraphic;
    }

    public void clearHand() {
        hand.clear();
    }

    public void rotateClockwise() {
        setDirection(getDirection().turnClockwise());
    }

    public void rotateCounterClockwise() {
        setDirection(getDirection().turnCounterClockwise());
    }

    public int getPriorityOfCardOnCurrentProgramRegister(){
        if (getProgram() != null && programRegister < 5) {
            if (getProgram()[programRegister] != null) {
                return getProgram()[programRegister].getPriority();
            } else {
                getGraphics().animate();
                return -1;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Player{" +
                "row=" + row +
                ", col=" + col +
                ", damage=" + damageTokens +
                '}';
    }

    public void applyDamage(int damage) {
        this.damageTokens += damage;
        if (this.damageTokens > 9) {
            looseLife();
            this.damageTokens = 0;
        }
    }

    public void looseLife() {
        lifeTokens --;
    }

    public void queueRespawn() {
        if (lifeTokens > 0) {
            this.hasQueuedRespawn = true;
        }
    }

    @Override
    public int compareTo(@NotNull Player otherPlayer) {
        return getProgram()[programRegister].compareTo(otherPlayer.getProgram()[programRegister]);
    }
}

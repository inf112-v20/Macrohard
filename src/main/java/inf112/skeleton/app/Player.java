package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.graphics.PlayerInfoGraphic;
import inf112.skeleton.app.tiles.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Player implements Comparable<Player> {

    private int row;
    private int col;
    private Direction direction;

    private Tile archiveMarker;
    public Card[] program;
    public Card[] hand;

    public boolean inPowerDown = false;
    public boolean announcedPowerDown = false;
    private boolean destroyed;

    private int damageTokens;
    private int lifeTokens;
    private int previousFlag;

    private final int name;

    private PlayerInfoGraphic infoGraphic;
    private PlayerGraphic playerGraphic;

    public Player(int row, int col, Direction direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.damageTokens = 0;
        this.lifeTokens = 3;
        this.program = new Card[5];
        this.name = ++RoboRallyApplication.NUMBER_OF_PLAYERS;
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

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setArchiveMarker(Tile archiveMarker) {
        this.archiveMarker = archiveMarker;
    }

    public void stepIn(Direction direction) {
        setRow(row + direction.getRowTrajectory());
        setCol(col + direction.getColumnTrajectory());
    }

    public boolean hasCompleteProgram() {
        for (Card card : getProgram()) {
            if (card == null) { return false; }
        }
        return true;
    }

    public int getDamageTokens() {
        return damageTokens;
    }

    public int getLifeTokens() {
        return lifeTokens;
    }

    public int getHandSize() {
        return 9 - damageTokens;
    }

    public PlayerGraphic getPlayerGraphic() {
        return playerGraphic;
    }

    public void setPlayerGraphic(PlayerGraphic playerGraphic) {
        this.playerGraphic = playerGraphic;
    }

    public PlayerInfoGraphic getInfoGraphic() {
        return infoGraphic;
    }

    public void setInfoGraphic(PlayerInfoGraphic infoGraphic) {
        this.infoGraphic = infoGraphic;
    }

    public void rotateClockwise() {
        setDirection(getDirection().turnClockwise());
    }

    public void rotateCounterClockwise() {
        setDirection(getDirection().turnCounterClockwise());
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public Card[] getHand() {
        return hand;
    }

    public Card[] getProgram() {
        return program;
    }

    public void lockInRandomProgram() {
        ArrayList<Card> shuffledHand = new ArrayList<>(Arrays.asList(hand));
        Collections.shuffle(shuffledHand);
        int bound = Math.min(program.length, shuffledHand.size());
        for (int i = 0; i < bound; i++) {
            program[i] = shuffledHand.get(i);
        }
    }

    public void discardHandAndWipeProgram() {
        hand = null;
        int lockedCards = Math.max(getDamageTokens() - 4, 0);
        for (int i = 0; i < program.length - lockedCards; i++) {
            program[i] = null;
        }
    }

    public void applyDamage(int damage) {
        damageTokens += damage;
        if (damageTokens > 9) {
            destroy();
        }
    }

    public void setDamageTokens(int newDamageTokens) {
        damageTokens = newDamageTokens;
    }

    public void destroy() {
        lifeTokens--;
        damageTokens = 0;
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isDead() {
        return lifeTokens <= 0;
    }

    public void reboot() {
        setRow(archiveMarker.getRow());
        setCol(archiveMarker.getCol());
        archiveMarker.setPlayer(this);

        destroyed = false;
        damageTokens = 2;
    }

    public void repair() {
        // Discard one damage token if there exist at least one damage token
        if (damageTokens > 0) {
            damageTokens--;
        }
    }

    @Override
    public int compareTo(@NotNull Player otherPlayer) {
        return getProgram()[GameLoop.currentProgramRegister].compareTo(otherPlayer.getProgram()[GameLoop.currentProgramRegister]);
    }

    @Override
    public String toString() {
        return "Player{" +
                "row=" + row +
                ", col=" + col +
                ", damage=" + damageTokens +
                '}';
    }

    public int name() {
        return name;
    }

    public int getNextFlag() {
        return previousFlag + 1;
    }

    public void touchFlag() {
        previousFlag++;
    }

    public int getPreviousFlag() {
        return previousFlag;
    }
}

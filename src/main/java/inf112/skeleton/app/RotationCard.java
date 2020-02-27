package inf112.skeleton.app;

public class RotationCard extends Card {

    private final Type direction;

    public RotationCard(int priority, Type direction) {
        super(priority);
        this.direction = direction;
    }

}

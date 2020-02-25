package inf112.skeleton.app;

public class MovementCard implements Card {
    private int priority;
    private Type type;

    public MovementCard(int priority, Type type){
        this.priority = priority;
        this.type = type;
    }

    @Override
    public Type getType(Card card) {
        return this.type;
    }


    @Override
    public String toString(){
        return ("priority: " + priority + ". Type: " + type);
    }
}

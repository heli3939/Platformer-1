import bagel.Input;

public class Hammer extends GameEntity{
    private final static String HAMMER_IMG = "res/hammer.png";
    public Hammer(int x, int y) {
        super(HAMMER_IMG, x, y);
    }
    @Override
    public void UpdatePostition(Input input, Platform[] platforms, Ladder[] ladders) {

    }
}
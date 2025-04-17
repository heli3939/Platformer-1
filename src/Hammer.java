import bagel.Input;

// hammer is a game entity
public class Hammer extends GameEntity{
    private final static String HAMMER_IMG = "res/hammer.png";

    // constructor for a hammer
    public Hammer(int x, int y) {
        super(HAMMER_IMG, x, y);
    }
    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer, Donkey donkey, Barrel[] barrels) {
        // no falling motion requried
    }
}
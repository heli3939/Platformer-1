import bagel.Input;

// platform is a game entity
public class Platform extends GameEntity{
    private final static  String PLATFORM_IMG = "res/platform.png";

    // constructor for a platform
    public Platform(int x, int y) {
        super(PLATFORM_IMG, x, y);
    }

    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer, Donkey donkey, Barrel[] barrels) {
        //  platform don't need to move or adjust position
    }
}
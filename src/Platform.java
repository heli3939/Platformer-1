import bagel.Input;

public class Platform extends GameEntity{
    private final static  String PLATFORM_IMG = "res/platform.png";
    public Platform(int x, int y) {
        super(PLATFORM_IMG, x, y);
    }

    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer, Donkey donkey) {

    }
}
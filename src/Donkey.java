public class Donkey extends GameEntity{
    private final static String DONKEY_IMG = "res/donkey_kong.png";
    public Donkey(int x, int y) {
        super(DONKEY_IMG, x, y);
    }

    @Override
    public void UpdatePostition(Platform[] platforms) {

    }
}
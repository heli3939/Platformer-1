public class Barrel extends GameEntity{
    private final static String BARREL_IMG = "res/barrel.png";
    public Barrel(int x, int y) {
        super(BARREL_IMG, x, y);
    }

    @Override
    public void UpdatePostition(Platform[] platforms){

    }
}
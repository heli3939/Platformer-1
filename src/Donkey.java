import bagel.Input;

public class Donkey extends GameEntity{
    private final static String DONKEY_IMG = "res/donkey_kong.png";
    public Donkey(int x, int y) {
        super(DONKEY_IMG, x, y);
    }
    private double v_y = 0;

    @Override
    public void UpdatePostition(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer) {
        v_y =  Math.min(ShadowDonkeyKong.VMAXFALL_B_D, v_y + ShadowDonkeyKong.GRAVITY);
        y += (int) v_y;
        int donkeyBtm = (int) this.getBoundingBox().bottom();
        int donkeyHeight = (int) (this.getBoundingBox().top() - this.getBoundingBox().bottom());
        for (Platform platform : platforms) {
            int platformTop = (int) platform.getBoundingBox().top();
            int platformBtm = (int) platform.getBoundingBox().bottom();
            if (this.isCollide(platform) && donkeyBtm >= platformTop && donkeyBtm <= platformBtm) {
                y = platformTop + donkeyHeight / 2;
                v_y = 0;

                break;
            }
        }
    }
}
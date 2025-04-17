import bagel.Input;

public class Barrel extends GameEntity{
    private final static String BARREL_IMG = "res/barrel.png";
    private double v_y = 0;
    private boolean hasScored = false;

    public Barrel(int x, int y) {
        super(BARREL_IMG, x, y);
    }


    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer, Donkey donkey, Barrel[] barrels) {
        v_y =  Math.min(ShadowDonkeyKong.VMAXFALL_B_D, v_y + ShadowDonkeyKong.GRAVITY);
        y += (int) v_y;
        int barrelBtm = (int) this.getBoundingBox().bottom();
        int barrelHeight = (int) (this.getBoundingBox().top() - this.getBoundingBox().bottom());
        for (Platform platform : platforms) {
            int platformTop = (int) platform.getBoundingBox().top();
            int platformBtm = (int) platform.getBoundingBox().bottom();
            if (this.isCollide(platform) && barrelBtm >= platformTop && barrelBtm <= platformBtm) {
                y = platformTop + barrelHeight / 2;
                v_y = 0;
                break;
            }
        }
    }
}
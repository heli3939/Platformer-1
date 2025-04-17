import bagel.Input;

// donkeykong is a game entity
public class Donkey extends GameEntity{
    private final static String DONKEY_IMG = "res/donkey_kong.png";
    // constructor for donkey
    public Donkey(int x, int y) {
        super(DONKEY_IMG, x, y);
    }
    private double v_y = ShadowDonkeyKong.VFINAL;

    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer, Donkey donkey, Barrel[] barrels) {
        // falling down setting to correct position of donkey
        v_y =  Math.min(ShadowDonkeyKong.VMAXFALL_B_D, v_y + ShadowDonkeyKong.GRAVITY);
        y += (int) v_y;
        int donkeyBtm = (int) this.getBoundingBox().bottom();
        int donkeyHeight = (int) (this.getBoundingBox().top() - this.getBoundingBox().bottom());
        // ensure bottom of donkey are on the top of the platform (collide or fall from air)
        for (Platform platform : platforms) {
            int platformTop = (int) platform.getBoundingBox().top();
            int platformBtm = (int) platform.getBoundingBox().bottom();
            if (this.isCollide(platform) && donkeyBtm >= platformTop && donkeyBtm <= platformBtm) {
                y = platformTop + donkeyHeight / 2;
                v_y = ShadowDonkeyKong.VFINAL; // stop moving after set
                break;
            }
        }
    }
}
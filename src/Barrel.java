import bagel.Input;

// barrel is a game entity
public class Barrel extends GameEntity{
    private final static String BARREL_IMG = "res/barrel.png";
    private double v_y = ShadowDonkeyKong.VFINAL; // vertical velocity

    // constructor for a barrel
    public Barrel(int x, int y) {
        super(BARREL_IMG, x, y);
    }

    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders,
                         Hammer hammer, Donkey donkey, Barrel[] barrels) {
        // falling down setting to correct position of barrel
        v_y =  Math.min(ShadowDonkeyKong.VMAXFALL_B_D, v_y + ShadowDonkeyKong.GRAVITY);
        y += (int) v_y;
        int barrelBtm = (int) this.getBoundingBox().bottom();
        int barrelHeight = (int) (this.getBoundingBox().top() - this.getBoundingBox().bottom());
        // ensure bottom of barrel are on the top of the platform (collide or fall from air)
        for (Platform platform : platforms) {
            int platformTop = (int) platform.getBoundingBox().top();
            int platformBtm = (int) platform.getBoundingBox().bottom();
            if (this.isCollide(platform) && barrelBtm >= platformTop && barrelBtm <= platformBtm) {
                y = platformTop + barrelHeight / 2;
                v_y = ShadowDonkeyKong.VFINAL; // stop moving after set
                break;
            }
        }
    }
}
import java.util.Properties;

public class Mario extends GameEntity{
    private final static String MARIOL_IMG = "res/mario_left.png";
    private final static String MARIOR_IMG = "res/mario_right.png";

    private double v_y = 0;

    public Mario(int x, int y) {
        super(MARIOR_IMG, x, y);
    }

    @Override
    public void UpdatePostition(Platform[] platforms) {
        v_y =  Math.min(ShadowDonkeyKong.VMAXFALL, v_y + ShadowDonkeyKong.GRAVITY);
        y += (int) v_y;
        int marioBtm = (int) this.getBoundingBox().bottom();
        int marioHeight = (int) (this.getBoundingBox().top() - this.getBoundingBox().bottom());
        for (Platform platform : platforms) {
            int platformTop = (int) platform.getBoundingBox().top();
            int platformBtm = (int) platform.getBoundingBox().bottom();
            if (this.isCollide(platform) && marioBtm >= platformTop && marioBtm <= platformBtm){
                y = platformTop + marioHeight / 2;
                v_y = 0;
                break;
            }
        }
    }

}

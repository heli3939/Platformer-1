import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class Mario extends GameEntity{
    private final static String MARIOL_IMG = "res/mario_left.png";
    private final static String MARIOR_IMG = "res/mario_right.png";

    private final static Image marioL = new Image(MARIOL_IMG);
    private final static Image marioR = new Image(MARIOR_IMG);
    private Image  currentImage = marioR;

    private double v_y = 0;

    public Mario(int x, int y) {
        super(MARIOR_IMG, x, y);
    }

    @Override
    public void UpdatePostition(Platform[] platforms, Input input) {
        v_y =  Math.min(ShadowDonkeyKong.VMAXFALL_MARIO, v_y + ShadowDonkeyKong.GRAVITY);
        y += (int) v_y;
        int currentPlatformTop = 0;
        int marioBtm = (int) this.getBoundingBox().bottom();
        int marioHeight = (int) (this.getBoundingBox().top() - this.getBoundingBox().bottom());
        for (Platform platform : platforms) {
            int platformTop = (int) platform.getBoundingBox().top();
            int platformBtm = (int) platform.getBoundingBox().bottom();
            if (this.isCollide(platform) && marioBtm >= platformTop && marioBtm <= platformBtm){
                y = platformTop + marioHeight / 2;
                v_y = 0;
                currentPlatformTop = (int) platform.getBoundingBox().top();
                break;
            }
        }
        if (input.isDown(Keys.SPACE) && this.getBoundingBox().bottom() == currentPlatformTop){
            v_y =  ShadowDonkeyKong.VINIT;
            y += (int) v_y;
        }
        if (input.isDown(Keys.RIGHT)){
            x += ShadowDonkeyKong.SPEED_LR;
            currentImage = marioR;
        }
        if (input.isDown(Keys.LEFT)){
            x -= ShadowDonkeyKong.SPEED_LR;
            currentImage = marioL;
        }
        currentImage.draw(x, y);
    }

}

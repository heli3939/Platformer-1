import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class Mario extends GameEntity{
    private final static String MARIOL_IMG = "res/mario_left.png";
    private final static String MARIOR_IMG = "res/mario_right.png";

    private final static String MARIOLH_IMG = "res/mario_hammer_left.png";
    private final static String MARIORH_IMG = "res/mario_hammer_right.png";

    private final static Image marioL = new Image(MARIOL_IMG);
    private final static Image marioR = new Image(MARIOR_IMG);

    private final static Image marioLH = new Image(MARIOLH_IMG);
    private final static Image marioRH = new Image(MARIORH_IMG);


    private Image  currentImage = marioR;

    private boolean hasHammer = false;
    private boolean hitDonkey = false;

    private double v_y = 0;

    public Mario(int x, int y) {
        super(MARIOR_IMG, x, y);
    }

    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer, Donkey donkey) {
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
        boolean onLadder = false;
        for (Ladder ladder: ladders){
            int ladderL = (int) ladder.getBoundingBox().left();
            int ladderR = (int) ladder.getBoundingBox().right();
            int ladderBtm = (int) ladder.getBoundingBox().bottom();
            if (x >= ladderL && x <= ladderR &&
                    (marioBtm == ladderBtm || this.isCollide(ladder))){
                onLadder = true;
            }
        }
        if (onLadder){
            v_y = 0;
            if (input.isDown(Keys.UP)) {
                y -= ShadowDonkeyKong.SPEED_CLIMB;
            } else if (input.isDown(Keys.DOWN)) {
                y += ShadowDonkeyKong.SPEED_CLIMB;
            }
        }
        if (!hasHammer && this.isCollide(hammer)){
            hasHammer = true;
            hammer.x = -1000;
            System.out.println("Hammer collected!");
        }
        if (input.isDown(Keys.SPACE) && this.getBoundingBox().bottom() == currentPlatformTop){
            v_y =  ShadowDonkeyKong.VINIT;
            y += (int) v_y;
        }
        if (this.isCollide(donkey)){
            if (hasHammer){
                hitDonkey = true;
                ShadowDonkeyKong.isWin = true;
                ShadowDonkeyKong.gameScreen = ShadowDonkeyKong.GAME_ENDING;
            }
        }
        if (input.isDown(Keys.RIGHT)){
            x += ShadowDonkeyKong.SPEED_LR;
            if (hasHammer) {
                currentImage = marioRH;
            }
            else{
                currentImage = marioR;
            }
        }
        if (input.isDown(Keys.LEFT)){
            x -= ShadowDonkeyKong.SPEED_LR;
            if (hasHammer) {
                currentImage = marioLH;
            }
            else{
                currentImage = marioL;
            }
        }
        currentImage.draw(x, y);
    }

}

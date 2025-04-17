import bagel.Image;
import bagel.Input;
import bagel.Keys;

// mario is a game entity
public class Mario extends GameEntity{
    // store image path for mario face left and right
    private final static String MARIOL_IMG = "res/mario_left.png";
    private final static String MARIOR_IMG = "res/mario_right.png";

    // store image path for mario face left and right with a hammer
    private final static String MARIOLH_IMG = "res/mario_hammer_left.png";
    private final static String MARIORH_IMG = "res/mario_hammer_right.png";

    // store image path for background image and create its image object
    private final static String BG_IMG = "res/background.png";
    private final static Image Bg = new Image(BG_IMG);

    // create image object for mario face left and right
    private final static Image marioL = new Image(MARIOL_IMG);
    private final static Image marioR = new Image(MARIOR_IMG);

    // create image object for mario face left and right  with a hammer
    private final static Image marioLH = new Image(MARIOLH_IMG);
    private final static Image marioRH = new Image(MARIORH_IMG);

    // mario face right without a hammer at the start
    private Image  currentImage = marioR;
    private boolean hasHammer = false;

    // vertical velocity
    private double v_y = ShadowDonkeyKong.VFINAL;

    // constructor for mario
    public Mario(int x, int y) {
        super(MARIOR_IMG, x, y);
    }

    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders,
                         Hammer hammer, Donkey donkey, Barrel[] barrels) {
        // falling down setting to correct position of barrel
        v_y = Math.min(ShadowDonkeyKong.VMAXFALL_MARIO, v_y + ShadowDonkeyKong.GRAVITY);
        y += (int) v_y;
        // store y-corrd for top of current platform top to prevent multiple jumps
        int currentPlatformTop = 0;
        int marioBtm = (int) this.getBoundingBox().bottom();
        int marioHeight = (int) (this.getBoundingBox().bottom() - this.getBoundingBox().top());
        // ensure bottom of mario are on the top of the platform (collide or fall from air)
        for (Platform platform : platforms) {
            int platformTop = (int) platform.getBoundingBox().top();
            int platformBtm = (int) platform.getBoundingBox().bottom();
            if (this.isCollide(platform) && marioBtm >= platformTop && marioBtm <= platformBtm) {
                y = platformTop - marioHeight / 2;
                v_y = 0;
                currentPlatformTop = (int) platform.getBoundingBox().top();
                break;
            }
        }

        // implement climbing ladder
        boolean onLadder = false;
        for (Ladder ladder : ladders) {
            int ladderL = (int) ladder.getBoundingBox().left();
            int ladderR = (int) ladder.getBoundingBox().right();
            int ladderBtm = (int) ladder.getBoundingBox().bottom();
            // mario can start climb the ladder only when it's in between and touch theladder
            if (x >= ladderL && x <= ladderR &&
                    (marioBtm == ladderBtm || this.isCollide(ladder))) {
                onLadder = true;
            }
        }
        // ladder prevent mario from falling, so previous gravity setting not apply here
        if (onLadder) {
            v_y = 0;
            // climb up change y-coord
            if (input.isDown(Keys.UP)) {
                y -= ShadowDonkeyKong.SPEED_CLIMB;
            }
            // climb down
            else if (input.isDown(Keys.DOWN)) {
                y += ShadowDonkeyKong.SPEED_CLIMB;
            }
        }

        // collect hammer if haven't
        if (!hasHammer && this.isCollide(hammer)) {
            // collect hammer now
            hasHammer = true;
            // hammer image in the air disappear
            hammer.x = ShadowDonkeyKong.OUTOFSCREEN;
            // printout message
            System.out.println("Hammer collected!");
        }

        // implement jumping and count reward score while jumping across the barrel
        if (input.isDown(Keys.SPACE) && this.getBoundingBox().bottom() == currentPlatformTop) {
            v_y = ShadowDonkeyKong.VINIT;
            y += (int) v_y;
            // count and reward score for jump across
            for (Barrel barrel : barrels) {
                int barrelTop = (int) barrel.getBoundingBox().top();
                int barrelL = (int) barrel.getBoundingBox().left();
                int barrelR = (int) barrel.getBoundingBox().right();
                if (marioBtm > barrelTop  && this.x >= barrelL && this.x <= barrelR) {
                    ScoreCounter.addScore(ShadowDonkeyKong.JUMPSCORE);
                    System.out.println("Jumping!");
                }
            }
        }
            // mario touch donkey
            if (this.isCollide(donkey)) {
                // defeat it and win the game if with hammer
                if (hasHammer) {
                    ShadowDonkeyKong.isWin = true;
                }
                ShadowDonkeyKong.gameScreen = ShadowDonkeyKong.GAME_ENDING;
            }

            // mario touch the barrel
            for (Barrel barrel : barrels) {
                if (this.isCollide(barrel)) {
                    // defeat the barrel with hammer and gain score and print message
                    if (hasHammer) {
                        barrel.x = ShadowDonkeyKong.OUTOFSCREEN;
                        System.out.println("Barrel destroyed!");
                        ScoreCounter.addScore(ShadowDonkeyKong.BARRELSCORE);
                    }
                    // loss if without a hammer
                    else {
                        ShadowDonkeyKong.isWin = false;
                        ShadowDonkeyKong.gameScreen = ShadowDonkeyKong.GAME_ENDING;
                    }
                }
            }
            // right motion for mario
            if (input.isDown(Keys.RIGHT)) {
                x += ShadowDonkeyKong.SPEED_LR;
                // prevent go out of boundary
                x = (int) Math.min(x, Bg.getWidth());
                // switch image according to hammer status
                if (hasHammer) {
                    currentImage = marioRH;
                } else {
                    currentImage = marioR;
                }
            }
            // left motion for mario
            if (input.isDown(Keys.LEFT)) {
                x -= ShadowDonkeyKong.SPEED_LR;
                // prevent go out of boundary
                x = Math.max(0, x);
                // switch image according to hammer status
                if (hasHammer) {
                    currentImage = marioLH;
                } else {
                    currentImage = marioL;
                }
            }
            // display current image of mario at correct position
            currentImage.draw(x, y);
        }
}

import bagel.Input;

public class Ladder extends GameEntity {
    private final static String LADDER_IMG = "res/ladder.png";

    private double v_y = 0;

    public Ladder(int x, int y) {
        super(LADDER_IMG, x, y);
    }

    @Override
    public void UpdatePostition(Platform[] platforms, Input input) {
        int ladderHeight = (int) (this.getBoundingBox().bottom() -
                this.getBoundingBox().top());
        int platformHeight = (int) (platforms[0].getBoundingBox().bottom() -
                platforms[0].getBoundingBox().top());
        for (int i=0; i<platforms.length-1; i++) {
            int higherBtm = (int) platforms[i+1].getBoundingBox().bottom();
            int higherTop = (int) platforms[i+1].getBoundingBox().top();

            if (this.isCollide(platforms[i])) {
                y = higherBtm+ladderHeight / 2 - 29;
                break;
            }
        }
    }


}


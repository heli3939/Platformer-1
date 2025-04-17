import bagel.Input;

// barrel is a game entity
public class Ladder extends GameEntity {
    private final static String LADDER_IMG = "res/ladder.png";

    // constructor for a ladder
    public Ladder(int x, int y) {
        super(LADDER_IMG, x, y);
    }

    @Override
    public void Updating(Input input, Platform[] platforms, Ladder[] ladders,
                         Hammer hammer, Donkey donkey, Barrel[] barrels) {
        // get top of ladder allign with top of platform above them
        int ladderHeight = (int) (this.getBoundingBox().bottom() -
                this.getBoundingBox().top());
        int platformHeight = (int) (platforms[0].getBoundingBox().bottom() -
                platforms[0].getBoundingBox().top());
        for (int i=0; i<platforms.length-1; i++) {
            // bottom of the platform above the ladder
            int higherBtm = (int) platforms[i+1].getBoundingBox().bottom();
            if (this.isCollide(platforms[i])) {
                y = higherBtm + ladderHeight / 2 - (platformHeight - 1);
                break;
            }
        }
    }


}


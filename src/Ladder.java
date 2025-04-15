import bagel.Input;

public class Ladder extends GameEntity{
    private final static String LADDER_IMG = "res/ladder.png";

    private double v_y = 0;
    public Ladder(int x, int y) {
        super(LADDER_IMG, x, y);
    }

    @Override
    public void UpdatePostition(Platform[] platforms, Input input){

    }

}
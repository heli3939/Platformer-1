public class Timer {
    private int currentFrame = 0;
    private int maxFrame;

    public Timer(int maxFrame) {
        this.maxFrame = maxFrame;
    }

    public void Update(){
        currentFrame++;
    }

    int RemainingTime(){
        return (maxFrame - currentFrame) / 60;
    }
}

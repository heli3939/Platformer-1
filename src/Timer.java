public class Timer {
    // initialize timer
    private int currentFrame = 0;
    private int maxFrame;

    // constructor for timer
    public Timer(int maxFrame) {
        this.maxFrame = maxFrame;
    }

    // increase frame count for every frame has passed
    public void Update(){
        currentFrame++;
    }

    // calculate remaining time based on frames spending
    int RemainingTime(){
        return (maxFrame - currentFrame) / 60;
    }
}

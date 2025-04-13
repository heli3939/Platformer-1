public class ScoreCounter {
    private static int currentScore = 0;

    public ScoreCounter(int currentScore) {
        this.currentScore = currentScore;
    }

    public static int getCurrentScore() {
        return currentScore;
    }

}

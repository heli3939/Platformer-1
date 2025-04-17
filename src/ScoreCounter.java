public class ScoreCounter {
    // initial score setting
    private static int currentScore = 0;

    // constructor for score counter
    public ScoreCounter(int currentScore) {
        this.currentScore = currentScore;
    }

    // getter for current score
    public static int getCurrentScore() {
        return currentScore;
    }

    // add score to score counter
    public static void addScore(int scoreAdd){
        currentScore += scoreAdd;
    }
}

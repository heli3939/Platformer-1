public class ScoreCounter {
    private static int currentScore = 0;

    public ScoreCounter(int currentScore) {
        this.currentScore = currentScore;
    }

    public static int getCurrentScore() {
        return currentScore;
    }

    public static void addScore(int scoreAdd){
        currentScore += scoreAdd;
    }

    public static void JumpOver (Mario mario, Barrel[] barrels){
        int marioBtm = (int) mario.getBoundingBox().bottom();
        for (Barrel barrel : barrels) {
            int barrelTop = (int) barrel.getBoundingBox().bottom();
            int barrelL = (int) barrel.getBoundingBox().left();
            int barrelR = (int) barrel.getBoundingBox().right();
            if (marioBtm < barrelTop && mario.x <= barrelR && mario.x >= barrelL){
                addScore(ShadowDonkeyKong.JUMPSCORE);
                System.out.println("jump over");
            }
        }
    }

}

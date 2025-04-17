import bagel.*;

import java.util.Properties;

/**
 * The main class for the Shadow Donkey Kong game.
 * This class extends {@code AbstractGame} and is responsible for managing game initialization,
 * updates, rendering, and handling user input.
 *
 * It sets up the game world, initializes characters, platforms, ladders, and other game objects,
 * and runs the game loop to ensure smooth gameplay.
 */
public class ShadowDonkeyKong extends AbstractGame {

    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;

    // Switch between three different screens
    public static final String HOME_SCREEN = "HOMESCREEN";
    public static final String GAMEPLAY_SCREEN = "GAMEPLAYSCREEN";
    public static final String GAME_ENDING = "GAMEENDING";

    // determine current game status for different end screen
    public static boolean isWin = false;

    // initialize the screen
    public static String gameScreen = HOME_SCREEN;

    // arrays to store platform objects, x-coord and y-coord
    private Platform[] platforms;
    private int[] platformX;
    private int[] platformY;

    // arrays to store barrel objects, x-coord and y-coord
    private Barrel[] barrels;
    private int[] barrelX;
    private int[] barrelY;

    // arrays to store ladder objects, x-coord and y-coord
    private Ladder[] ladders;
    private int[] ladderX;
    private int[] ladderY;

    // object for otehr game entity
    private Mario mario;
    private Hammer hammer;
    private Donkey donkey;

    // object for timer and score counter
    private Timer timer;
    private ScoreCounter scoreCounter;

    // background image
    private final Image BACKGROUND;

    // use to ensure we only record final remaining time once
    private int finalTime = 0;

    // constants for motion property from task description
    static public final double GRAVITY = 0.2;
    static public final double JUMPHEIGHT = -62.5;
    static public final double VFINAL = 0;
    static public final double VINIT = -5;
    static public final double VMAXFALL_MARIO = 10;
    static public final double VMAXFALL_B_D = 5;
    static public final double SPEED_LR = 3.5;
    static public final int SPEED_CLIMB = 2;
    static public final int SCOREPERSEC = 3;

    // score gain reward
    static public final int JUMPSCORE = 30;
    static public final int BARRELSCORE = 100;

    // point outside of screen for some icons to disappear
    static public final int OUTOFSCREEN = -10000;

    // continue message on end screen is 100 pixel above the bottom
    public final int CONTINUEPOSITION = 100;

    // constructor for the game
    public ShadowDonkeyKong(Properties gameProps, Properties messageProps) {
        // create window for the game with title
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("home.title"));
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;

        // create background image object
        BACKGROUND = new Image(GAME_PROPS.getProperty("backgroundImage"));;

        // store platform objects and x-, y- coord from app prop
        String[] platformCoord = GAME_PROPS.getProperty("platforms").split(";");
        int numPlatform = platformCoord.length;
        platformX = new int[numPlatform];
        platformY = new int[numPlatform];
        platforms = new Platform[numPlatform];
        for (int i=0; i<numPlatform; i++) {
            // store platform objects and x-, y- coord from app prop
            String [] pc = platformCoord[i].split(",");
            platformX[i] = Integer.parseInt(pc[0]);
            platformY[i] = Integer.parseInt(pc[1]);
            platforms[i] = new Platform(platformX[i], platformY[i]);
        }

        // store barrel objects and x-, y- coord from app prop
        int numBarrel = Integer.parseInt(GAME_PROPS.getProperty("barrel.count"));
        barrelX = new int[numBarrel];
        barrelY = new int[numBarrel];
        barrels = new Barrel[numBarrel];
        for (int i=0; i<numBarrel; i++){
            // store barrel objects and x-, y- coord from app prop
            String [] bc =  GAME_PROPS.getProperty("barrel." + (i+1)).split(",");
            barrelX[i] = Integer.parseInt(bc[0]);
            barrelY[i] = Integer.parseInt(bc[1]);
            barrels[i] = new Barrel(barrelX[i], barrelY[i]);
        }

        // store ladder objects and x-, y- coord from app prop
        int numLadder = Integer.parseInt(GAME_PROPS.getProperty("ladder.count"));
        ladderX = new int[numLadder];
        ladderY = new int[numLadder];
        ladders = new Ladder[numLadder];
        for (int i=0; i<numLadder; i++){
            // store ladder objects and x-, y- coord from app prop
            String [] lc =  GAME_PROPS.getProperty("ladder." + (i+1)).split(",");
            ladderX[i] = Integer.parseInt(lc[0]);
            ladderY[i] = Integer.parseInt(lc[1]);
            ladders[i] = new Ladder(ladderX[i], ladderY[i]);
        }

        // create object for other game entity with initial position
        mario = new Mario(Integer.parseInt(GAME_PROPS.getProperty("mario.start.x")),
                Integer.parseInt(GAME_PROPS.getProperty("mario.start.y")));
        hammer = new Hammer(Integer.parseInt(GAME_PROPS.getProperty("hammer.start.x")),
                Integer.parseInt(GAME_PROPS.getProperty("hammer.start.y")));
        donkey = new Donkey(Integer.parseInt(GAME_PROPS.getProperty("donkey.x")),
                Integer.parseInt(GAME_PROPS.getProperty("donkey.y")));

        // create object for timer and score counter
        timer = new Timer(Integer.parseInt(GAME_PROPS.getProperty("gamePlay.maxFrames")));
        scoreCounter = new ScoreCounter(0);
    }

    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the gameplay.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {
        // quit the game when press ESC
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        // time passes every frame
        timer.Update();
        // switch between three different screens
        if (gameScreen.equals(HOME_SCREEN)){
            HomeScreen(input);
        } else if (gameScreen.equals(GAMEPLAY_SCREEN)) {
            GamePlayScreen(input);
        } else if (gameScreen.equals(GAME_ENDING)) {
            GameEnding(input);
        }
    }

    // ensure we start a new game after finishing an old game and back to home
    // reset all properties to initial status
    private void ResetGame (){
        mario = new Mario(Integer.parseInt(GAME_PROPS.getProperty("mario.start.x")),
                Integer.parseInt(GAME_PROPS.getProperty("mario.start.y")));
        hammer = new Hammer(Integer.parseInt(GAME_PROPS.getProperty("hammer.start.x")),
                Integer.parseInt(GAME_PROPS.getProperty("hammer.start.y")));
        donkey = new Donkey(Integer.parseInt(GAME_PROPS.getProperty("donkey.x")),
                Integer.parseInt(GAME_PROPS.getProperty("donkey.y")));
        for (int i = 0; i < barrels.length; i++) {
            barrels[i] = new Barrel(barrelX[i], barrelY[i]);
        }
        for (int i = 0; i < ladders.length; i++) {
            ladders[i] = new Ladder(ladderX[i], ladderY[i]);
        }
        scoreCounter = new ScoreCounter(0);
        timer = new Timer(Integer.parseInt(GAME_PROPS.getProperty("gamePlay.maxFrames")));
        isWin = false;
    }

    // implementation of game ending screen
    private void GameEnding (Input input){
        DrawBG();
        // display game end status message
        final Font fontEndStatus = new Font(GAME_PROPS.getProperty("font"),
                Integer.parseInt(GAME_PROPS.getProperty("gameEnd.status.fontSize")));
        String StatusMsg = MESSAGE_PROPS.getProperty("gameEnd.lost");
        // different message for win and loss
        if (isWin){
            StatusMsg = MESSAGE_PROPS.getProperty("gameEnd.won");
        }

        // display game end score message
        fontEndStatus.drawString(StatusMsg,
                ((Integer.parseInt(GAME_PROPS.getProperty("window.width")) -
                        fontEndStatus.getWidth(StatusMsg)) / 2),
                Integer.parseInt(GAME_PROPS.getProperty("gameEnd.status.y")));
        final Font fontEndScore = new Font(GAME_PROPS.getProperty("font"),
                Integer.parseInt(GAME_PROPS.getProperty("gameEnd.scores.fontSize")));
        final String SCOREDISPLAY = MESSAGE_PROPS.getProperty("gameEnd.score");

        // record exact time game ends for calculating game score
        if (finalTime == 0){
            finalTime = timer.RemainingTime();
        }
        // final score adds reward calculating from remaining time
        // on score we got for different actions in game
        int finalScore = scoreCounter.getCurrentScore() + finalTime * SCOREPERSEC;
        // display game score message on screen
        final String ScoreDisplay = SCOREDISPLAY + finalScore;
        fontEndScore.drawString(ScoreDisplay,
                ((Integer.parseInt(GAME_PROPS.getProperty("window.width")) -
                        fontEndScore.getWidth(ScoreDisplay)) / 2),
                Integer.parseInt(GAME_PROPS.getProperty("gameEnd.scores.y")));
        // display continue message on screen
        final String CONTINUE = MESSAGE_PROPS.getProperty("gameEnd.continue");
        fontEndStatus.drawString(CONTINUE,
                ((Integer.parseInt(GAME_PROPS.getProperty("window.width")) -
                        fontEndStatus.getWidth(CONTINUE)) / 2),
                Integer.parseInt(GAME_PROPS.getProperty("window.height")) - CONTINUEPOSITION);
        // go back to home and start a new game after press space
        if (input.isDown(Keys.SPACE)){
            ResetGame();
            gameScreen = HOME_SCREEN;
        }

    }

    // draw background at appropriate position in the middle of screen
    private void DrawBG (){
        double x = Integer.parseInt(GAME_PROPS.getProperty("window.width")) / 2.0;
        double y = Integer.parseInt(GAME_PROPS.getProperty("window.height")) / 2.0;
        BACKGROUND.draw(x, y);
    }

    // implementation of game play screen
    private void GamePlayScreen (Input input){
        DrawBG();
        // draw platforms
        for (int i=0; i< platforms.length; i++) {
            platforms[i].drawImage();
        }
        // draw barrels and set them to correct position
        for (int i=0; i< barrels.length; i++) {
            barrels[i].drawImage();
            barrels[i].Updating(input, platforms, ladders, hammer, donkey, barrels);
        }
        // draw ladders and set them to correct position
        for (int i=0; i< ladders.length; i++) {
            ladders[i].drawImage();
            ladders[i].Updating(input, platforms, ladders, hammer, donkey, barrels);
        }
        // draw other game entities and set them to correct position
        mario.drawImage();
        mario.Updating(input, platforms, ladders, hammer, donkey, barrels);
        hammer.drawImage();
        donkey.drawImage();
        donkey.Updating(input, platforms, ladders, hammer, donkey, barrels);

        // display timer on top left
        final Font fontScoreTime = new Font(GAME_PROPS.getProperty("font"),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.fontSize")));
        fontScoreTime.drawString("TIME LEFT " + timer.RemainingTime(),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.x")),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.y")) + 30);

        // display score counter above timer
        fontScoreTime.drawString("SCORE " + ScoreCounter.getCurrentScore(),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.x")),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.y")));
        if (timer.RemainingTime() == 0){
            isWin = false;
            gameScreen = GAME_ENDING;
        }
    }

    // implementation of homescreen
    private void HomeScreen (Input input){
        DrawBG();
        // display title of game in the middle
        final Font fontHomeTitle = new Font(GAME_PROPS.getProperty("font"),
                Integer.parseInt(GAME_PROPS.getProperty("home.title.fontSize")));
        fontHomeTitle.drawString(GAME_PROPS.getProperty("home.title"),
                (Integer.parseInt(GAME_PROPS.getProperty("window.width"))-
                        fontHomeTitle.getWidth(GAME_PROPS.getProperty("home.title"))) / 2,
                Integer.parseInt(GAME_PROPS.getProperty("home.title.y")));
        // display prompt of game in the middle
        final Font fontHomePrompt = new Font(GAME_PROPS.getProperty("font"),
                Integer.parseInt(GAME_PROPS.getProperty("home.prompt.fontSize")));
        fontHomePrompt.drawString(GAME_PROPS.getProperty("home.prompt"),
                (Integer.parseInt(GAME_PROPS.getProperty("window.width"))-
                        fontHomePrompt.getWidth(GAME_PROPS.getProperty("home.prompt"))) / 2,
                Integer.parseInt(GAME_PROPS.getProperty("home.prompt.y")));
        // switch to gameplay screen after press Enter
        if (input.wasPressed(Keys.ENTER)) {
            gameScreen = GAMEPLAY_SCREEN;
        }
    }


    /**
     * The main entry point of the Shadow Donkey Kong game.
     *
     * This method loads the game properties and message files, initializes the game,
     * and starts the game loop.
     *
     * @param args Command-line arguments (not used in this game).
     */
    public static void main(String[] args) {
        Properties gameProps = IOUtils.readPropertiesFile("res/app.properties");
        Properties messageProps = IOUtils.readPropertiesFile("res/message_en.properties");
        ShadowDonkeyKong game = new ShadowDonkeyKong(gameProps, messageProps);
        game.run();
    }
}

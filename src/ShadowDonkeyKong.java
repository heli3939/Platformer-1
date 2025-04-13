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
    private final String HOME_SCREEN = "HOMESCREEN";
    private final String GAMEPLAY_SCREEN = "GAMEPLAYSCREEN";
    private final String GAME_ENDING = "GAMEENDING";
    

    private String gameScreen = HOME_SCREEN; // initialize the screen

    private Platform[] platforms;
    private int[] platformX;
    private int[] platformY;

    private Barrel[] barrels;
    private int[] barrelX;
    private int[] barrelY;

    private Ladder[] ladders;
    private int[] ladderX;
    private int[] ladderY;

    private Mario mario;
    private Hammer hammer;
    private Donkey donkey;

    private Timer timer;
    private ScoreCounter scoreCounter;

    private final Image BACKGROUND;

    public ShadowDonkeyKong(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("home.title"));
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;

        BACKGROUND = new Image(GAME_PROPS.getProperty("backgroundImage"));;

        String[] platformCoord = GAME_PROPS.getProperty("platforms").split(";");
        int numPlatform = platformCoord.length;
        platformX = new int[numPlatform];
        platformY = new int[numPlatform];
        platforms = new Platform[numPlatform];
        for (int i=0; i<numPlatform; i++) {
            String [] pc = platformCoord[i].split(",");
            platformX[i] = Integer.parseInt(pc[0]);
            platformY[i] = Integer.parseInt(pc[1]);
            platforms[i] = new Platform(platformX[i], platformY[i]);
        }

        int numBarrel = Integer.parseInt(GAME_PROPS.getProperty("barrel.count"));
        barrelX = new int[numBarrel];
        barrelY = new int[numBarrel];
        barrels = new Barrel[numBarrel];
        for (int i=0; i<numBarrel; i++){
            String [] bc =  GAME_PROPS.getProperty("barrel." + (i+1)).split(",");
            barrelX[i] = Integer.parseInt(bc[0]);
            barrelY[i] = Integer.parseInt(bc[1]);
            barrels[i] = new Barrel(barrelX[i], barrelY[i]);
        }

        int numLadder = Integer.parseInt(GAME_PROPS.getProperty("ladder.count"));
        ladderX = new int[numLadder];
        ladderY = new int[numLadder];
        ladders = new Ladder[numLadder];
        for (int i=0; i<numLadder; i++){
            String [] lc =  GAME_PROPS.getProperty("ladder." + (i+1)).split(",");
            ladderX[i] = Integer.parseInt(lc[0]);
            ladderY[i] = Integer.parseInt(lc[1]);
            ladders[i] = new Ladder(ladderX[i], ladderY[i]);
        }

        mario = new Mario(Integer.parseInt(GAME_PROPS.getProperty("mario.start.x")),
                Integer.parseInt(GAME_PROPS.getProperty("mario.start.y")));
        hammer = new Hammer(Integer.parseInt(GAME_PROPS.getProperty("hammer.start.x")),
                Integer.parseInt(GAME_PROPS.getProperty("hammer.start.y")));
        donkey = new Donkey(Integer.parseInt(GAME_PROPS.getProperty("donkey.x")),
                Integer.parseInt(GAME_PROPS.getProperty("donkey.y")));

        timer = new Timer(Integer.parseInt(GAME_PROPS.getProperty("gamePlay.maxFrames")));
        scoreCounter = new ScoreCounter(0);
    }
    
    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the gameplay.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {
        timer.Update();
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        if (gameScreen.equals(HOME_SCREEN)){
            HomeScreen(input);
        } else if (gameScreen.equals(GAMEPLAY_SCREEN)) {
            GamePlayScreen(input);
        } else if (gameScreen.equals(GAME_ENDING)) {
            // add later
        }
    }

    private void DrawBG (){
        double x = Integer.parseInt(GAME_PROPS.getProperty("window.width")) / 2.0;
        double y = Integer.parseInt(GAME_PROPS.getProperty("window.height")) / 2.0;
        BACKGROUND.draw(x, y);
    }


    private void GamePlayScreen (Input input){
        DrawBG();
        for (int i=0; i< platforms.length; i++) {
            platforms[i].drawImage();
        }
        for (int i=0; i< barrels.length; i++) {
            barrels[i].drawImage();
        }
        for (int i=0; i< ladders.length; i++) {
            ladders[i].drawImage();
        }
        mario.drawImage();
        hammer.drawImage();
        donkey.drawImage();

        final Font fontScoreTime = new Font(GAME_PROPS.getProperty("font"),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.fontSize")));
        fontScoreTime.drawString("TIME LEFT " + timer.RemainingTime(),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.x")),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.y")) + 30);
        fontScoreTime.drawString("SCORE " + ScoreCounter.getCurrentScore(),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.x")),
                Integer.parseInt(GAME_PROPS.getProperty("gamePlay.score.y")));
    }

    private void HomeScreen (Input input){
        DrawBG();
        final Font fontHomeTitle = new Font(GAME_PROPS.getProperty("font"),
                Integer.parseInt(GAME_PROPS.getProperty("home.title.fontSize")));
        fontHomeTitle.drawString(GAME_PROPS.getProperty("home.title"),
                (Integer.parseInt(GAME_PROPS.getProperty("window.width"))-
                        fontHomeTitle.getWidth(GAME_PROPS.getProperty("home.title"))) / 2,
                Integer.parseInt(GAME_PROPS.getProperty("home.title.y")));
        final Font fontHomePrompt = new Font(GAME_PROPS.getProperty("font"),
                Integer.parseInt(GAME_PROPS.getProperty("home.prompt.fontSize")));
        fontHomePrompt.drawString(GAME_PROPS.getProperty("home.prompt"),
                (Integer.parseInt(GAME_PROPS.getProperty("window.width"))-
                        fontHomePrompt.getWidth(GAME_PROPS.getProperty("home.prompt"))) / 2,
                Integer.parseInt(GAME_PROPS.getProperty("home.prompt.y")));
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

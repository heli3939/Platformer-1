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

    // images used in this game
    private final Image HomeScreenBG = new Image("res/background.png");

    public ShadowDonkeyKong(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("home.title"));
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
    }



    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the gameplay.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        if (gameScreen.equals(HOME_SCREEN)){
            HomeScreen(input);
        } else if (gameScreen.equals(GAMEPLAY_SCREEN)) {
            // add later
        } else if (gameScreen.equals(GAME_ENDING)) {
            // add later
        }
    }


    private void HomeScreen (Input input){
        HomeScreenBG.draw( Integer.parseInt(GAME_PROPS.getProperty("window.width")) / 2.0,
                Integer.parseInt(GAME_PROPS.getProperty("window.height")) / 2.0);
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
package com.procode.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.screens.MiniSettingScreen;
import com.procode.game.screens.PlayScreen;
import com.procode.game.sprites.Bird;
import com.procode.game.tools.Gamepad;
import com.procode.game.tools.ImageFunctions;

public class HUD implements Disposable {
    public Stage stage;
    // values that get updated dynamically
    private static Integer score;

    // what is shown on the HUD
    private Image healthBar;
    public ImageButton pauseBtn, playBtn;
    private Image scoreBackground;        // visual for when displaying the score
    private Label scoreLabel, scoreTextLabel;
    public Gamepad gamepad;               // displays the gamepad on the screen

    // visuals
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public static int state;
    public int bird_State = 0;
    public static final int GAME_PLAY = 0, GAME_PAUSE = 1, GAME_QUIT = 4;
    public static MiniSettingScreen settingScreen;

    public HUD(SuperBirdGame game){
        score = 0;
        stage = new Stage(game.viewport, game.batch);

        Gdx.input.setInputProcessor(stage);

        // table is used for organizing the displays
        Table leftTable = new Table();
        leftTable.left();
        leftTable.setFillParent(true);

        Table scoreTable = new Table();
        scoreTable.left();
        scoreTable.setFillParent(true);

        // for changing the fonts when displaying score
        Skin skin = new Skin(Gdx.files.internal("comic-ui.json"));
        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 50;
        font = fontGenerator.generateFont(fontParameter);

        Label.LabelStyle style = new Label.LabelStyle();
        style = skin.get(Label.LabelStyle.class);
        style.font = font;

        //set the state of the game and the bird
        state = GAME_PLAY;
        bird_State = 0;

        // displays
        healthBar = new Image(ImageFunctions.resize("screen icons//bird health 6.png", SuperBirdGame.GAME_WIDTH /7, SuperBirdGame.GAME_HEIGHT /5));
        pauseBtn = ImageFunctions.resizeImageButton("screen icons//pause button.png", SuperBirdGame.GAME_WIDTH /35, SuperBirdGame.GAME_HEIGHT /25);
        scoreBackground = new Image(ImageFunctions.resize("screen icons//score-backgroundTwo.png", SuperBirdGame.GAME_WIDTH /4, SuperBirdGame.GAME_HEIGHT/12));
        scoreTextLabel = new Label("SCORE: ", style);
        scoreLabel = new Label(score + " ", style);

        leftTable.add(pauseBtn).padBottom(SuperBirdGame.GAME_HEIGHT /2 + SuperBirdGame.GAME_HEIGHT /3).padLeft(SuperBirdGame.GAME_WIDTH /60);
        leftTable.add(healthBar).padBottom(SuperBirdGame.GAME_HEIGHT /2 + SuperBirdGame.GAME_HEIGHT /4);

        //--Nikko: (Changeable) I have placed the score to be in the middle of the screen as opposed to the right as the user doesn't have to look very far to see their score
        leftTable.add(scoreTextLabel).padBottom((int) (SuperBirdGame.GAME_HEIGHT /1.1)).padLeft((int) (SuperBirdGame.GAME_WIDTH /4.5));
        leftTable.add(scoreLabel).padBottom((int) (SuperBirdGame.GAME_HEIGHT /1.1));
        scoreTable.add(scoreBackground).padBottom(SuperBirdGame.GAME_HEIGHT/2 + SuperBirdGame.GAME_HEIGHT/3 + SuperBirdGame.GAME_HEIGHT/15)
                .padLeft(SuperBirdGame.GAME_WIDTH/2 - SuperBirdGame.GAME_WIDTH/8);

        //set the sate of the PauseBtn
        setPauseBtn();

        // gamepad
        gamepad = new Gamepad(game);

        stage.addActor(gamepad.upArrow);
        stage.addActor(gamepad.downArrow);
        stage.addActor(gamepad.leftArrow);
        stage.addActor(gamepad.rightArrow);
        stage.addActor(gamepad.upLeft);
        stage.addActor(gamepad.upRight);
        stage.addActor(gamepad.downLeft);
        stage.addActor(gamepad.downRight);
        stage.addActor(gamepad.shootButton);
        stage.addActor(gamepad.resizeButton);

        stage.addListener(gamepad.clickListener);

        //Display table to screen
        stage.addActor(scoreTable);
        stage.addActor(leftTable);

        //set up the mini settings for the PlayScreen
        settingScreen = new MiniSettingScreen(stage);
        settingScreen.setContainerVisible(false);
        settingScreen.setSettingsContainerVisible(false);

        //Display table to screen
        stage.addActor(settingScreen.getActor());
        stage.addActor(settingScreen.getSettingsActor());

    }
    public void setPauseBtn(){
        pauseBtn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(state == GAME_PLAY)
                    state = GAME_PAUSE;
                else if(state == GAME_PAUSE)
                    state = GAME_PLAY;

                return true;
            }
        });
    }

    //GAME_PLAY, GAME_PAUSE, GAME_QUIT
    public void setState(int gameState){
        state = gameState;
    }

    public void updateHealthBar(int currentHealth){
        Texture newHealth = ImageFunctions.resize("screen icons//bird health " + String.valueOf(currentHealth) + ".png", SuperBirdGame.GAME_WIDTH /7, SuperBirdGame.GAME_HEIGHT /5);
        healthBar.setDrawable(new TextureRegionDrawable(new TextureRegion(newHealth)));
    }

    public void updatePoints(int point){
        score += point;
        scoreLabel.setText(score);
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}

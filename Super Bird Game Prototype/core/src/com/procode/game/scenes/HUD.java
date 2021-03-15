package com.procode.game.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.procode.game.screens.PlayScreen;
import com.procode.game.sprites.Bird;
import com.procode.game.tools.Gamepad;
import com.procode.game.tools.ImageFunctions;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JTextField;

public class HUD implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Table table;

    // values that get updated dynamically
    private static Integer score;

    // what is shown on the HUD
    private Image healthBar;
    private Image pauseBtn;
    private Image scoreBackground;        // visual for when displaying the score
    private Label scoreLabel, scoreTextLabel;
    public Gamepad gamepad;               // displays the gamepad on the screen

    // visuals
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public static int state;
    public int bird_State = 0;
    private final int GAME_PLAY = 0, GAME_PAUSE = 1;

    public HUD(SuperBirdGame game){
        score = 0;
        viewport = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        // table is used for organizing the displays
        Table leftTable = new Table();
        leftTable.left();
        leftTable.setFillParent(true);

        Table rightTable = new Table();
        rightTable.right();
        rightTable.setFillParent(true);

        Table bottomTable = new Table();
        bottomTable.bottom();
        bottomTable.setFillParent(true);

        // for changing the fonts when displaying score
        Skin skin = new Skin(Gdx.files.internal("comic-ui.json"));
        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;
        font = fontGenerator.generateFont(fontParameter);

        Label.LabelStyle style = new Label.LabelStyle();
        style = skin.get(Label.LabelStyle.class);


        style.font = font;

        //set the state of the game and the bird
        state = GAME_PLAY;
        bird_State = 0;

        // displays
        healthBar = new Image(ImageFunctions.resize("screen icons//bird health 6.png", SuperBirdGame.ANDROID_WIDTH/7, SuperBirdGame.ANDROID_HEIGHT/5));
        pauseBtn = new Image(ImageFunctions.resize("screen icons//pause button.png", SuperBirdGame.ANDROID_WIDTH/35, SuperBirdGame.ANDROID_HEIGHT/25));
        scoreBackground = new Image(ImageFunctions.resize("screen icons//score-backgroundTwo.png", SuperBirdGame.ANDROID_WIDTH/4, SuperBirdGame.ANDROID_HEIGHT/7));
        scoreTextLabel = new Label("SCORE: ", style);
        //scoreTextLabel.setFontScale(0.5f);
        scoreLabel = new Label(score + " ", style);
        //scoreLabel.setFontScale(0.5f);

        leftTable.add(pauseBtn).padBottom(SuperBirdGame.ANDROID_HEIGHT/2 + SuperBirdGame.ANDROID_HEIGHT/3).padLeft(SuperBirdGame.ANDROID_WIDTH/60);
        leftTable.add(healthBar).padBottom(SuperBirdGame.ANDROID_HEIGHT/2 + SuperBirdGame.ANDROID_HEIGHT/4);

//        rightTable.add(scoreTextLabel).padBottom(SuperBirdGame.ANDROID_HEIGHT/2 + SuperBirdGame.ANDROID_HEIGHT/4).padRight(SuperBirdGame.ANDROID_WIDTH/60);
//        rightTable.add(scoreLabel).padBottom(SuperBirdGame.ANDROID_HEIGHT/2 + SuperBirdGame.ANDROID_HEIGHT/4).padRight(SuperBirdGame.ANDROID_WIDTH/60);

        //--Nikko: (Changeable) I have placed the score to be in the middle of the screen as opposed to the right as the user doesn't have to look very far to see their score
        leftTable.add(scoreTextLabel).padBottom((int) (SuperBirdGame.ANDROID_HEIGHT/1.1)).padLeft((int) (SuperBirdGame.ANDROID_WIDTH/4.5));
        leftTable.add(scoreLabel).padBottom((int) (SuperBirdGame.ANDROID_HEIGHT/1.1));
        scoreBackground.setPosition((SuperBirdGame.ANDROID_HEIGHT/2),  (SuperBirdGame.ANDROID_WIDTH/2));

        //set the sate of the PauseBtn
        setPauseBtn();

        // gamepad
        gamepad = new Gamepad(game);

        stage.addActor(gamepad.upArrow);
        stage.addActor(gamepad.downArrow);
        stage.addActor(gamepad.leftArrow);
        stage.addActor(gamepad.rightArrow);
        stage.addActor(gamepad.shootButton);

        //Display table to screen
        stage.addActor(scoreBackground);
        stage.addActor(leftTable);
        stage.addActor(rightTable);
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

    public boolean getShootStateBtn(){
        return gamepad.shoot;
    }


    //--Nikko: Might change this to update as I can just use one method to update the healthbar and score label--//
    public void updateHealthBar(int currentHealth){
        Texture newHealth = ImageFunctions.resize("screen icons//bird health " + String.valueOf(currentHealth) + ".png", SuperBirdGame.ANDROID_WIDTH/7, SuperBirdGame.ANDROID_HEIGHT/5);
        healthBar.setDrawable(new TextureRegionDrawable(new TextureRegion(newHealth)));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

package com.procode.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.sprites.Bird;
import com.procode.game.tools.ImageFunctions;

public class HUD implements Disposable {
    //Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Update Values
    private static Integer score;
    private int health;

    //What is shown on screen
    private Image healthBar;              // display the Health Bar
    private Image pauseBtn;               // display the Pause Button
    private Label scoreLabel;             // display the score
    private Label livesLabel, lives;      // displays the number of lives

    public HUD(SuperBirdGame game){
        //Initialize Values
        score = 0;

        viewport = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        //Table is used for organizing the displays
        Table table = new Table();
        table.top(); //Puts the displays on the top
        table.setFillParent(true); //Fit to screen

        //The Displays
        healthBar = new Image(ImageFunctions.resize("screen icons//bird health 6.png", SuperBirdGame.ANDROID_WIDTH/7, SuperBirdGame.ANDROID_HEIGHT/5));
        pauseBtn = new Image(ImageFunctions.resize("screen icons//pause button.png", SuperBirdGame.ANDROID_WIDTH/35, SuperBirdGame.ANDROID_HEIGHT/25));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(1.5f);
        livesLabel = new Label("LIVES:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel.setFontScale(1.5f);
        lives = new Label(String.format("%02d", Bird.numLives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lives.setFontScale(1.5f);


        //Adding the displays to the screen  //--Change these to be more dynamic to the phone's screen res--/
        table.add(pauseBtn).padBottom(120).padLeft(20);
        table.add(healthBar).expandX().padTop(10).padRight(900).padBottom(60);
        table.add(livesLabel).padBottom(150);
        table.add(lives).expandX().padBottom(150);
        table.add(scoreLabel).padBottom(50);



        //Display table to screen
        stage.addActor(table);


    }

    //Updates the healthbar with parameter of current health (gets called every time it gets hit)
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

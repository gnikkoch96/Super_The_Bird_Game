package com.procode.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

public class HUD implements Disposable {
    //Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Update Values
    private static Integer score;
    private int health;

    //What is shown on screen
    private Image healthBar;              //Displays the Health Bar
    private Image pauseBtn;
    private Label scoreLabel;             //Displays the score

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
        healthBar = new Image(ImageFunctions.resize("screen icons//bird health 1.png", SuperBirdGame.ANDROID_WIDTH/7, SuperBirdGame.ANDROID_HEIGHT/5));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(2);


        //Adding the displays to the screen
        table.add(pauseBtn).padBottom(120).padLeft(20);
        table.add(healthBar).expandX().padTop(10).padRight(900).padBottom(60);              //--Change these to be more dynamic--/
        table.add(scoreLabel).expandX().padTop(10).padRight(50).padBottom(60);


        //Display table to screen
        stage.addActor(table);


    }

    //Updates the healthbar with parameter of current health (gets called every time it gets hit)
    //--Nikko: Might change this to update as I can just use one method to update the healthbar and score label--//
    public void updateHealthBar(int currentHealth){
       //Use currentHealth value and concatenate to find the respective image (i.e. "health(" + currentHealth + ").png)
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

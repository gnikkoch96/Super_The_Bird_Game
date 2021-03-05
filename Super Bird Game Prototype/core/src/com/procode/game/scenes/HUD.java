package com.procode.game.scenes;

import com.badlogic.gdx.Game;
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
import com.procode.game.tools.Gamepad;
import com.procode.game.tools.ImageFunctions;

public class HUD implements Disposable {
    public Stage stage;
    private Viewport viewport;

    // values that get updated dynamically
    private static Integer score;

    // what is shown on the HUD
    private Image healthBar;
    private Image pauseBtn;
    private Label scoreLabel, scoreTextLabel;
    public Gamepad gamepad;               // displays the gamepad on the screen

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

        // displays
        healthBar = new Image(ImageFunctions.resize("screen icons//bird health 6.png", SuperBirdGame.ANDROID_WIDTH/7, SuperBirdGame.ANDROID_HEIGHT/5));
        pauseBtn = new Image(ImageFunctions.resize("screen icons//pause button.png", SuperBirdGame.ANDROID_WIDTH/35, SuperBirdGame.ANDROID_HEIGHT/25));
        scoreTextLabel = new Label("SCORE: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreTextLabel.setFontScale(2.5f);
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(2.5f);

        leftTable.add(pauseBtn).padBottom(SuperBirdGame.ANDROID_HEIGHT/2 + SuperBirdGame.ANDROID_HEIGHT/3).padLeft(SuperBirdGame.ANDROID_WIDTH/60);
        leftTable.add(healthBar).padBottom(SuperBirdGame.ANDROID_HEIGHT/2 + SuperBirdGame.ANDROID_HEIGHT/4);

        rightTable.add(scoreTextLabel).padBottom(SuperBirdGame.ANDROID_HEIGHT/2 + SuperBirdGame.ANDROID_HEIGHT/4).padRight(SuperBirdGame.ANDROID_WIDTH/60);
        rightTable.add(scoreLabel).padBottom(SuperBirdGame.ANDROID_HEIGHT/2 + SuperBirdGame.ANDROID_HEIGHT/4).padRight(SuperBirdGame.ANDROID_WIDTH/60);

        // gamepad
        gamepad = new Gamepad(game);
        stage.addActor(gamepad.upArrow);
        stage.addActor(gamepad.downArrow);
        stage.addActor(gamepad.leftArrow);
        stage.addActor(gamepad.rightArrow);
        stage.addActor(gamepad.shootButton);

        //Display table to screen
        stage.addActor(leftTable);
        stage.addActor(rightTable);

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

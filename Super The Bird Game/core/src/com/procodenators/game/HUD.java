package com.procode.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;

public class HUD {
    //Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Update Values
    private static Integer score;
    private int health;

    //What is shown on screen
    private Image healthBar;              //Displays the Health Bar
    private Label scoreLabel;             //Displays the score


    public HUD(SpriteBatch sb){
        //Initialize Values
        score = 0;
        health = 6;                         //can be changed if upgraded
        viewport = new FitViewport(SuperBirdGame.DESKTOP_WIDTH, SuperBirdGame.DESKTOP_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //Table is used for organizing the displays
        Table table = new Table();
        table.top(); //Puts the displays on the top
        table.setFillParent(true); //Fit to screen

        //The Display
        healthBar = new Image(resize("bird_health//health(full).png", 200, 150));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(2);
        //Adding the displays to the screen
        table.add(healthBar).expandX().padTop(10).padRight(800);
        table.add(scoreLabel).expandX().padTop(10).padRight(50);

        //Display table to screen
        stage.addActor(table);

    }

    public Texture resize(String path, int newWidth, int newHeight) {
        Pixmap original = new Pixmap(Gdx.files.internal(path));
        Pixmap resized = new Pixmap(newWidth, newHeight, original.getFormat());
        resized.drawPixmap(original,
                0, 0, original.getWidth(), original.getHeight(),
                0, 0, resized.getWidth(), resized.getHeight());
        Texture output = new Texture(resized);
        original.dispose();
        resized.dispose();
        return output;
    }
}

package com.procode.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

public class LoadingScreen extends Game implements Screen {

    private Pixmap pixmap;
    private TextureRegionDrawable drawable;
    private ProgressBar.ProgressBarStyle progressBarStyle;
    private Stage stage;
    private ProgressBar healthBar;
    private Skin skin;
    private SuperBirdGame game;


    private Texture background;
    public LoadingScreen(SuperBirdGame g){

        game = g;
        skin = new Skin(Gdx.files.internal("comic-ui.json"));


        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knob = drawable;

        Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        stage = new Stage();

        healthBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
        healthBar.setValue(1.0f);
        healthBar.setAnimateDuration(0.25f);
        healthBar.setBounds(10, 10, 100, 20);
        stage.addActor(healthBar);
    }

    @Override
    public void show() {

        background = ImageFunctions.resize("screen icons//superbird.png", SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);

        stage = new Stage();

        healthBar = new ProgressBar(0.0f, 5.0f, 0.10f, false, skin);
        healthBar.setValue(0.0f);
        healthBar.setAnimateDuration(0.25f);
        healthBar.setBounds(10, 10, SuperBirdGame.ANDROID_WIDTH/4, 20);
        //healthBar.setPosition(500,500);
        healthBar.setPosition(500,SuperBirdGame.ANDROID_HEIGHT/4);
        stage.addActor(healthBar);


    }

    private float updateBar =0;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1F, 1F, 1F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateBar += delta;
        healthBar.setValue(updateBar);

        if(updateBar > 5.5f)
            game.setScreen(new PlayScreen(game));
        game.batch.begin();
        //render the stage and draw it
        //game.batch.draw(background, 0, 0);
        game.batch.draw(background,0,0);
        game.batch.end();
        stage.draw();
        stage.act();
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        healthBar.setSize(width/2, height/4 -200);
        healthBar.setPosition(width/4,height/4 );
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

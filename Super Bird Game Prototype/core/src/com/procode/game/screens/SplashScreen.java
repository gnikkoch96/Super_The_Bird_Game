package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;


public class SplashScreen implements Screen {

    private Texture splashtexture;
    private Image splashimage;
    private Stage splashstage;
    private float WIDTH,HEIGHT;
    private SuperBirdGame game;
    private Viewport viewport;
    private float nextPage;

    public SplashScreen(SuperBirdGame g) {
        game = g;
    }

    @Override
    public void show() {

        viewport = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT, new OrthographicCamera());

        splashtexture = new Texture(Gdx.files.internal("screen icons/procode.png"));
        splashtexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        splashimage = new Image(splashtexture);
        splashimage.setSize(game.ANDROID_WIDTH,game.ANDROID_HEIGHT);

        OrthographicCamera camera = new OrthographicCamera(game.ANDROID_WIDTH, game.ANDROID_HEIGHT);
        camera.position.x = (float) game.ANDROID_WIDTH/2;
        camera.position.y = (float) game.ANDROID_HEIGHT/2;

        splashstage = new Stage(viewport,game.batch);
        splashstage.addActor(splashimage);

        splashimage.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.fadeIn(0.25F),Actions.delay(0.25F), Actions.fadeOut(1F)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        splashstage.act();
        splashstage.draw();

        game.batch.begin();
        //render the stage and draw it
        //game.batch.draw(background, 0, 0);
        game.batch.draw(splashtexture,0,0);

        nextPage += delta;

        if(nextPage > 4.5){
            game.setScreen(new LoginScreen(game));
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        splashstage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        splashtexture.dispose();
        splashstage.dispose();
    }
}
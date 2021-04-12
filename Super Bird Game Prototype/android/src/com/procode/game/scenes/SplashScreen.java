package com.procode.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.screens.LoginScreen;


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
        splashtexture = new Texture(Gdx.files.internal("screen icons/procode.png"));
        splashtexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        splashimage = new Image(splashtexture);
        splashimage.setSize(game.GAME_WIDTH,game.GAME_HEIGHT);

        splashstage = new Stage(game.viewport,game.batch);
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
        game.viewport.update(width, height);
        game.camera.position.set(game.GAME_WIDTH/2, game.GAME_HEIGHT/2, 0);
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
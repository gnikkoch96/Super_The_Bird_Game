package com.procode.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.procode.game.scenes.HUD;
import com.procode.game.sprites.Bird;

public class SuperBirdGame extends ApplicationAdapter {
	public static final int DESKTOP_WIDTH = 1300;
	public static final int DESKTOP_HEIGHT = 680;
	public static final String DEKSTOP_TITLE = "Super Bird Game";

//	private SpriteBatch batch;
//
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//	}
//
//	@Override
//	public void render () {
//		//Clears the Screen
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//		batch.begin();
//		batch.end();
//	}
//
//	@Override
//	public void dispose () {
//		batch.dispose();
//	}

	private GameController gc;
	private HUD hud;

	SpriteBatch batch;
	Texture bg;

	Bird bird;

	@Override
	public void create () {
		gc = new GameController();
		batch = new SpriteBatch();
		bg = new Texture("Background//bg.png");
		bird = new Bird();
		hud = new HUD(batch);
	}

	@Override
	public void render () {
		gc.handleInput(); // Testing purposes only to see if it worked
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(bg, 0, 0);
		batch.draw(bird.getIdlebird()[0], 200, 150);
		batch.end();
		hud.stage.draw();
	}

	@Override
	public void dispose () {
		batch.dispose();
		bg.dispose();
	}
}

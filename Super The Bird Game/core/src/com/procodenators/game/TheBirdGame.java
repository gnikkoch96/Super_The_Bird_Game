package com.procodenators.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TheBirdGame extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	public static final String TITLE = "The Bird Game";

	private GameController gc;

	SpriteBatch batch;
	Texture bg;

	Bird bird;
	Texture[] idlebird;
	
	@Override
	public void create () {
		gc = new GameController();
		batch = new SpriteBatch();
		bg = new Texture("Background//bg.png");
		bird = new Bird();
		idlebird = bird.getIdlebird();
	}

	@Override
	public void render () {
		gc.handleInput(); // Testing purposes only to see if it worked
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(bg, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bg.dispose();
	}
}

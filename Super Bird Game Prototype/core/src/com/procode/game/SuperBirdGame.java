package com.procode.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.procode.game.scenes.HUD;
import com.procode.game.screens.LoginScreen;
import com.procode.game.screens.PlayScreen;
import com.procode.game.sprites.Bird;

public class SuperBirdGame extends Game {
	// desktop configurations
	public static final int DESKTOP_WIDTH = 1000;
	public static final int DESKTOP_HEIGHT = 700;
	public static final String DESKTOP_TITLE = "Super Bird Game";
	public SpriteBatch batch;

	// android configurations
	public static int ANDROID_WIDTH, ANDROID_HEIGHT;
	public static int num;															// nikko: what does this do?

	public SuperBirdGame(){}
	public SuperBirdGame(int width, int height){
		this.ANDROID_WIDTH = width;
		this.ANDROID_HEIGHT = height;
		System.out.println("width: " + width);
		System.out.println("height: " + height);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new LoginScreen(this));
		System.out.println("Android Height: " + ANDROID_HEIGHT);
		System.out.println("Android Width: " + ANDROID_WIDTH);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
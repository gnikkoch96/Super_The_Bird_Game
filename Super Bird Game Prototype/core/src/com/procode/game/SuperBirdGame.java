package com.procode.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.procode.game.scenes.HUD;
import com.procode.game.screens.LoadingScreen;
import com.procode.game.screens.LoginScreen;
import com.procode.game.screens.PlayScreen;
import com.procode.game.screens.SplashScreen;
import com.procode.game.sprites.Bird;

public class SuperBirdGame extends Game {
	public static final int DESKTOP_WIDTH = 1920;
	public static final int DESKTOP_HEIGHT = 1080;
	public static final String DESKTOP_TITLE = "Super Bird Game";
	public SpriteBatch batch;

	// android configurations
	public static int ANDROID_WIDTH, ANDROID_HEIGHT;
	public static int num;															// nikko: what does this do?

	// collision bits (used to manage the collision detections)
	public static final short BIRD_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short BIRDSPIT_BIT = 8;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around Assetmanager to those the classes that need it.
	We will use it in the static context to save time for now. */
	public static AssetManager manager;

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

		// asset manager stuff
		manager = new AssetManager();
		manager.load("audio/sound/spit.wav", Sound.class);
		manager.load("audio/sound/bird_dead.wav", Sound.class);
		manager.load("audio/sound/bird_dead_sad.wav", Sound.class);
		manager.load("audio/sound/bird_scream_loud.wav", Sound.class);
		manager.load("audio/sound/bird_scream_normal.wav", Sound.class);
		manager.load("audio/sound/spitCollision.mp3", Sound.class);
		manager.finishLoading();

		this.setScreen(new SplashScreen(this));

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
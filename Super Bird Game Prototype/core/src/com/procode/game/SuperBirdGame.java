package com.procode.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.screens.SplashScreen;

public class SuperBirdGame extends Game {
	public static final String DESKTOP_TITLE = "Super Bird Game";
	public static int DESKTOP_WIDTH = 1280;
	public static int DESKTOP_HEIGHT = 720;

	public static int GAME_WIDTH = 1920;
	public static int GAME_HEIGHT = 1080;

	public static OrthographicCamera camera;
	public static Viewport viewport;
	public static float aspectRatio;

	public SpriteBatch batch;

	// android configurations
	public static int ANDROID_WIDTH, ANDROID_HEIGHT;
	public static int num;															// nikko: what does this do?


	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around Assetmanager to those the classes that need it.
	We will use it in the static context to save time for now. */
	public static AssetManager manager;

	public SuperBirdGame(){
		// do nothing
	}

	public SuperBirdGame(int width, int height){
		this.ANDROID_WIDTH = width;
		this.ANDROID_HEIGHT = height;
	}

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);

		aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		viewport = new FillViewport(GAME_WIDTH * aspectRatio, GAME_HEIGHT, camera);
		viewport.apply();

		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);

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
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(GAME_WIDTH/2, GAME_HEIGHT/2, 0);
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
package com.procode.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.scenes.BaseScene;
import com.procode.game.scenes.HUD;
import com.procode.game.screens.HomeScreen;
import com.procode.game.screens.LoginScreen;
import com.procode.game.screens.PlayScreen;
import com.procode.game.screens.Scoreboard;
import com.procode.game.screens.SettingsScreen;
import com.procode.game.screens.PlayScreen;
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

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around Assetmanager to those the classes that need it.
	We will use it in the static context to save time for now. */
	public static AssetManager manager;

	public SuperBirdGame(){}

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
		manager.load("audio/sound/bird_flap.mp3", Sound.class);
		manager.load("audio/sound/bird_dead.wav", Sound.class);
		manager.load("audio/sound/bird_dead_sad.wav", Sound.class);
		manager.load("audio/sound/bird_scream_loud.wav", Sound.class);
		manager.load("audio/sound/bird_scream_normal.wav", Sound.class);
		//manager.load("audio/sound/spitCollision.mp3", Sound.class);
		manager.load("audio/sound/mechaBirdSpin.mp3", Sound.class);
		manager.load("audio/sound/mechaBirdLaser.mp3", Sound.class);
//		manager.load("audio/sound/spitCollision.mp3", Sound.class);
		manager.load("audio/sound/mecha_dead.wav", Sound.class);
		manager.load("audio/music/music.mp3", Music.class);
		manager.finishLoading();


		this.setScreen(new SplashScreen(this));
		//this.setScreen(new PlayScreen(this));
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
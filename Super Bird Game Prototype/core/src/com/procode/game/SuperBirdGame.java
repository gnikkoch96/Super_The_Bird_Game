package com.procode.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.procode.game.scenes.HUD;
import com.procode.game.screens.PlayScreen;
import com.procode.game.sprites.Bird;

public class SuperBirdGame extends Game {
	public static final int DESKTOP_WIDTH = 1300;
	public static final int DESKTOP_HEIGHT = 680;
	public static final String DEKSTOP_TITLE = "Super Bird Game";
	public SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new PlayScreen(this));
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

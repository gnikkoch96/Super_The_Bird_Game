package com.procode.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.procode.game.SuperBirdGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SuperBirdGame.DESKTOP_WIDTH;
		config.height = SuperBirdGame.DESKTOP_HEIGHT;
		config.title = SuperBirdGame.DEKSTOP_TITLE;

		new LwjglApplication(new SuperBirdGame(SuperBirdGame.DESKTOP_WIDTH,SuperBirdGame.DESKTOP_HEIGHT), config);
	}
}

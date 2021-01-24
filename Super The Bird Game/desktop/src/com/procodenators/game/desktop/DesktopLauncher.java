package com.procodenators.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.procodenators.game.TheBirdGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = TheBirdGame.WIDTH;
		config.height = TheBirdGame.HEIGHT;
		config.title = TheBirdGame.TITLE;
		new LwjglApplication(new TheBirdGame(), config);
	}
}

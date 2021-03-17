package com.procode.game;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import android.view.Window;
import android.view.WindowManager;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.procode.game.SuperBirdGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display screensize = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		screensize.getSize(size);
		int width = size.x;
		int height = size.y;
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		//sp.ANDROID_HEIGHT = 100;
		//sp.setHeight(100);
		//initialize(new SuperBirdGame(width, height), config);
		initialize(new Test());
	}
}

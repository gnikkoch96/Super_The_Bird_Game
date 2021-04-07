package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

public class Background {

    private Texture background,background_sky, background_hills, background_clouds, background_mountains;

    public Background(){
        //Gdx.graphics returns the size of the android screen
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        background_sky = ImageFunctions.resize("background stuff/bg sky.png", SuperBirdGame.GAME_WIDTH,SuperBirdGame.GAME_HEIGHT);
        background_hills = ImageFunctions.resize("background stuff/bg hills.png", SuperBirdGame.GAME_WIDTH,SuperBirdGame.GAME_HEIGHT);
        background_clouds = ImageFunctions.resize("background stuff/clouds.png", SuperBirdGame.GAME_WIDTH,SuperBirdGame.GAME_HEIGHT);
        background_mountains = ImageFunctions.resize("background stuff/bg mountains.png", SuperBirdGame.GAME_WIDTH,SuperBirdGame.GAME_HEIGHT);
    }


    public Texture getBackgroundSky(){
        return background_sky;
    }

    public Texture getBackground_hills(){
        return background_hills;
    }

    public Texture getBackgroundClouds(){
        return background_clouds;
    }

    public Texture getBackgroundMountains(){
        return background_mountains;
    }

    public Texture getBackground(){
        return background;
    }
}

package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.procode.game.tools.Animation;
import com.procode.game.tools.ImageFunctions;

import java.util.ArrayList;
import java.util.List;

public class Bird {
    private Animation birdAnimation; // takes in an animation class to allow for changing of animation played and other settings
    private float healthCount;//Nikko

    public Bird() {
        birdAnimation = new Animation();
        healthCount = 6; //Nikko
        birdAnimation.setAnimation("Bird_Animations//idle bird ", 192, 120, 1, 4, 0, .25f);
    }

    // updates the bird every frame
    public void update(float deltaTime){
        birdAnimation.updateFrame(deltaTime);
    }

    // gets the current image of the bird
    public Texture getBirdImage() {
        return birdAnimation.getCurrImg();
    }
}

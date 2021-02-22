package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.procode.game.tools.Animation;
import com.procode.game.tools.ImageFunctions;

import java.util.ArrayList;
import java.util.List;

public class Bird {
    private static final int BIRD_WIDTH = 240;
    private static final int BIRD_HEIGHT = 150;

    private Animation birdAnimation; // takes in an animation class to allow for changing of animation played and other settings
    private float healthCount;
    private Vector2 position;
    private Vector2 velocity;

    public Bird(int x, int y) {
        birdAnimation = new Animation();
        healthCount = 6;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);

        // sets the current animation to the idle bird
        birdAnimation.setAnimation("bird animations//idle bird ", BIRD_WIDTH, BIRD_HEIGHT, 1, 4, 0, .25f);
    }

    // updates the bird every frame
    public void update(float deltaTime){
        birdAnimation.updateFrame(deltaTime);
    }

    // gets the current image of the bird
    public Texture getBirdImage() {
        return birdAnimation.getCurrImg();
    }
    public Vector2 getPosition(){return this.position;}     //gets the position of the bird
}
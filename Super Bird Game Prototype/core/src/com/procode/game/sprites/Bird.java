package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.tools.Animation;

public class Bird {
    private static int BirdWidth = 240;
    private static int BirdHeight = 150;

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
        birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, 0, .25f);
    }

    // updates the bird every frame
    public void update(float deltaTime){ birdAnimation.updateFrame(deltaTime);
    }

    // gets the current image of the bird
    public Texture getBirdImage(){
        return birdAnimation.getCurrImg();
    }

    //gets the position of the bird
    public Vector2 getPosition(){
        return this.position;
    }

    //sets the new position of the bird
    public void setPosition(float newX, float newY){
        position.x = newX;
        position.y = newY;
    }

    //gets the width and height of the bird
    public Vector2 getBirdSize(){
        return new Vector2(BirdWidth, BirdHeight);
    }

    @Override
    public void dispose() {

    }
}

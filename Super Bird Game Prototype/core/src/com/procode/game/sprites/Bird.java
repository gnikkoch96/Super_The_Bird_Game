package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;
import java.lang.Math;

public class Bird implements Disposable {
    private static int BirdWidth;
    private static int BirdHeight;

    private Animation birdAnimation; // takes in an animation class to allow for changing of animation played and other settings
    private Vector2 position;
    private float healthCount;
    private Vector2 velocity;

    public Bird(int x, int y, int birdWidth, int birdHeight) {
        birdAnimation = new Animation();
        healthCount = 6;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);

        BirdWidth = (int) birdWidth;
        BirdHeight = (int) birdHeight;

        // sets the current animation to the idle bird
        birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f);
    }

    // updates the bird every frame
    public void update(float deltaTime){ birdAnimation.updateFrame(deltaTime); }

    // gets the current image of the bird
    public Texture getBirdImage(){
        return birdAnimation.getCurrImg();
    }

    //gets the position of the bird
    public Vector2 getPosition(){
        return this.position;
    }

    //sets the new position of the bird
    public void movePosition(float newX, float newY){

        if(position.x + newX >= 0 && (position.x + BirdWidth + newX) <= SuperBirdGame.ANDROID_WIDTH) {
            position.x += (newX);
        }
        if(position.y + newY >= 0 && (position.y + BirdHeight + newY) <= SuperBirdGame.ANDROID_HEIGHT) {
            position.y += newY;
        }
    }

    //gets the width and height of the bird
    public Vector2 getBirdSize(){
        return new Vector2(BirdWidth, BirdHeight);
    }

    public void setBirdSize(int width, int height){

        //repositions the bird of the new size makes it go out of bounds
        if((position.x + width) > SuperBirdGame.ANDROID_WIDTH) {
            position.x = SuperBirdGame.ANDROID_WIDTH - width;
        }
        if((position.y + height) > SuperBirdGame.ANDROID_HEIGHT) {
            position.y = SuperBirdGame.ANDROID_HEIGHT - height;
        }

        BirdWidth = width;
        BirdHeight = height;
    }

    @Override
    public void dispose() {
        birdAnimation.dispose();
    }
}

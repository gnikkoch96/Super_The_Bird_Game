package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.tools.Animation;

public class Bird implements Disposable {
    private static int BirdWidth = 240;
    private static int BirdHeight = 150;

    public enum State {IDLE, SHOOT, DAMAGED, DEAD};  // different states of the bird
    private Animation birdAnimation; // takes in an animation class to allow for changing of animation played and other settings
    private float healthCount;
    private Vector2 position;
    private Vector2 velocity;

    public Bird(int x, int y, int birdWidth, int birdHeight) {
        birdAnimation = new Animation();
        healthCount = 6;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);

        BirdWidth = birdWidth;
        BirdHeight = birdHeight;

        // sets the current animation to the idle bird
        birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
    }

    // updates the bird every frame
    public void update(float deltaTime){
        if(birdAnimation.isAnimFinished() && !birdAnimation.getIsLoop()){ // play animation once
            //reset to idle animation again
            switchAnimations(Bird.State.IDLE);
        }else{
            birdAnimation.updateFrame(deltaTime);
        }

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


    //--TEST--//
    public void switchAnimations(State playerState){
        switch(playerState){
            case IDLE:
                birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
                break;
            case SHOOT:
                birdAnimation.setAnimation("bird animations//shoot bird ", BirdWidth, BirdHeight, 1, 3, .35f, false);
                break;
            case DAMAGED:
                birdAnimation.setAnimation("bird animations//damage bird ", BirdWidth, BirdHeight, 1, 3, .45f, false);
                break;
            case DEAD:
                birdAnimation.setAnimation("bird animations//dead bird ", BirdWidth, BirdHeight, 1, 5, .45f, false);
                break;
        }
    }

    //--TEST--//
    public void shoot() {
        switchAnimations(Bird.State.SHOOT);
    }

    @Override
    public void dispose() {

    }


}

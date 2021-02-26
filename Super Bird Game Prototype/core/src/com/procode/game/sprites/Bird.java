package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.Animation;

public class Bird implements Disposable {
    private static int BirdWidth = 240;
    private static int BirdHeight = 150;

    public enum State {IDLE, SHOOT, DAMAGED, DEAD};  // different states of the bird
    private Animation birdAnimation; // takes in an animation class to allow for changing of animation played and other settings
    private int healthCount;
    private Vector2 position;
    private Vector2 velocity;
    private boolean isDead;         //checks to see if the bird is dead
    private int numLives;           //number of lives the bird has (default is 3)

    public Bird(int x, int y, int birdWidth, int birdHeight) {
        birdAnimation = new Animation();
        healthCount = 6;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);
        isDead = false;
        numLives = 3;
        BirdWidth = birdWidth;
        BirdHeight = birdHeight;

        // sets the current animation to the idle bird
        birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
    }

    // updates the bird every frame
    public void update(float deltaTime){
        if(birdAnimation.isAnimFinished() && !birdAnimation.getIsLoop()){ // play animation once
            //reset to the IDLE animation
            switchAnimations(Bird.State.IDLE);
        }else{//continue updating the frame
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
        switchAnimations(State.SHOOT);
    }

    public void deadBird(HUD hud){
        if(numLives <= 0){
            //Play Game Over Screen
        }else{
            numLives--; //reduces a life
            switchAnimations(State.DEAD);
            //reset the healthbar, healthcount (basically everything related to the bird)
            healthCount = 6;
            hud.updateHealthBar(healthCount);
        }
    }

    public void damagedBird(HUD hud){
        switchAnimations(State.DAMAGED);
        this.healthCount--;                     //reduced the health if bird is damaged
        if(this.healthCount <= 0){
            this.deadBird(hud);
        }
        hud.updateHealthBar(this.healthCount);  //updates the healthbar to display the change in health
    }

    @Override
    public void dispose() {

    }


}

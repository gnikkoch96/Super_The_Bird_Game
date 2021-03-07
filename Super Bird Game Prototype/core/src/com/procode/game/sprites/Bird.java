package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.Animation;

public class Bird implements Disposable {
    private static final long INVINCIBLE_DURATION = 2000; // value can be changed
    private static long timeVar;  // used for the invincible property

    public enum State {IDLE, SHOOT, DAMAGED, DEAD}
    private Animation birdAnimation;  // takes in an animation class to allow for changing of animation played and other settings
    private Vector2 position;
    private Vector2 velocity;
    private int healthCount;
    private int BirdWidth;
    private int BirdHeight;

    // state variables (used to prevent animations from interfering with each other)
    private State currentState;
    private State previousState;

    // boolean vars for bird (used in the damagedBird())
    private boolean isDead;
    private boolean isInvincible;

    public Bird(int x, int y, int birdWidth, int birdHeight) {
        birdAnimation = new Animation();
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);

        isDead = false;
        isInvincible = false;

        healthCount = 6;
        BirdWidth = birdWidth;
        BirdHeight = birdHeight;
        currentState = State.IDLE;
        previousState = currentState;

        // sets the current animation to the idle bird
        birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
    }

    // gets the current image of the bird
    public Texture getBirdImage(){
        return birdAnimation.getCurrImg();
    }

    // gets the position of the bird
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

    // gets the width and height of the bird
    public Vector2 getBirdSize(){
        return new Vector2(BirdWidth, BirdHeight);
    }

    // returns the status of the invincibility of the bird (used to re-enable the bird's collision detection)
    public boolean getInvincible() {return this.isInvincible;}

    // can only set invincible after a certain period of time has passed
    public void setInvincible(boolean isInvincible){
        if(isInvincible){
            this.isInvincible = true;
        }else{ // only change to non-invincible when x (or INVINCIBLE_DURATION) seconds pass after getting damaged
            if(System.currentTimeMillis() >= timeVar + INVINCIBLE_DURATION){
                this.isInvincible = false;
            }
        }

    }


    // updates the bird every frame
    public void update(float deltaTime){
        if(birdAnimation.isAnimFinished() && !birdAnimation.getIsLoop()){ // plays an animation only once (in this case SHOOT, DEAD, and DAMAGED)
            //reset to the IDLE animation
            previousState = currentState;
            currentState = State.IDLE;
            switchAnimations(State.IDLE);
            birdAnimation.setAnimationEnded(false); // fixes the transition issue
        }else{//continue updating the frame
            birdAnimation.updateFrame(deltaTime);
            setInvincible(false);
        }

    }

    public void switchAnimations(State playerState){
        switch(playerState){
            case IDLE:
                birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
                break;
            case SHOOT:
                birdAnimation.setAnimation("bird animations//shoot bird ", BirdWidth, BirdHeight, 1, 3, 0.55f, false);
                break;
            case DAMAGED:
                birdAnimation.setAnimation("bird animations//damage bird ", BirdWidth, BirdHeight, 1, 8, .45f, false);
                break;
            case DEAD:
                birdAnimation.setAnimation("bird animations//dead bird ", BirdWidth, BirdHeight, 1, 7, .50f, false);
                break;
        }
    }

    public void shoot() {
        previousState = currentState;
        currentState = State.SHOOT;

        if(previousState != State.SHOOT){ // to allow the shoot animation to properly display before shooting again
            switchAnimations(State.SHOOT);
        }
    }

    public void deadBird(HUD hud){
        previousState = currentState;
        currentState = State.DEAD;

        switchAnimations(State.DEAD);

        // set the screen to game over screen
    }

    public void damagedBird(HUD hud){
        previousState = currentState;
        currentState = State.DAMAGED;

        if(!this.isInvincible){ // bird can only get damaged when it isn't invincible
            timeVar = System.currentTimeMillis(); // update time var to current time value every time the bird gets damaged
            setInvincible(true);
            switchAnimations(State.DAMAGED);
            this.healthCount--;
            if(this.healthCount <= 0){
                this.deadBird(hud);
            }
            hud.updateHealthBar(this.healthCount);
        }

    }


    @Override
    public void dispose() {

    }


}

package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.Animation;

public class Bird implements Disposable {
    public static int numLives;           // number of lives the bird has (default is 3) //--Nikko: I made this static so that it can be accessible in the HUD.class

    private static final long INVINCIBLE_DURATION = 2000; // invincible for 2 seconds (can be changed)
    private static long timeVar;                         // used for the invincible property
    private static int BirdWidth = 240;
    private static int BirdHeight = 150;

    public enum State {IDLE, SHOOT, DAMAGED, DEAD};  // different states of the bird
    private Animation birdAnimation; // takes in an animation class to allow for changing of animation played and other settings
    private Vector2 position;
    private Vector2 velocity;
    private int healthCount;

    // state variables
    private State currentState;
    private State previousState;

    // boolean vars for bird
    private boolean isDead;         // checks to see if the bird is dead
    private boolean isInvincible;    // when the bird gets damaged, they are invincible for xx seconds

    public Bird(int x, int y, int birdWidth, int birdHeight) {
        birdAnimation = new Animation();
        healthCount = 6;
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);
        isDead = false;
        isInvincible = false;
        numLives = 3;
        BirdWidth = birdWidth;
        BirdHeight = birdHeight;
        currentState = State.IDLE;
        previousState = currentState;

        // sets the current animation to the idle bird
        birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
    }

    // updates the bird every frame
    public void update(float deltaTime){
        Gdx.app.log("Animation Ended: ", String.valueOf(birdAnimation.isAnimFinished()));
        if(birdAnimation.isAnimFinished() && !birdAnimation.getIsLoop()){ // play animation once
            //reset to the IDLE animation
            previousState = currentState;
            currentState = State.IDLE;
            switchAnimations(State.IDLE);
            birdAnimation.setAnimationEnded(false);             // fixes the transition issue
        }else{//continue updating the frame
            birdAnimation.updateFrame(deltaTime);
            setInvincible(false);
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

    public boolean getInvincible() {return this.isInvincible;}


    // isInvincible is the value to set to the bird's isInvincible variable
    public void setInvincible(boolean isInvincible){
        if(isInvincible){
            this.isInvincible = true;
        }else{ // only change to non-invincible when x seconds pass after getting damaged
            if(System.currentTimeMillis() >= timeVar + INVINCIBLE_DURATION){          // invincibleDuration time has passed
                this.isInvincible = false;
            }
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
                birdAnimation.setAnimation("bird animations//damage bird ", BirdWidth, BirdHeight, 1, 3, .50f, false);
                break;
            case DEAD:
                birdAnimation.setAnimation("bird animations//dead bird ", BirdWidth, BirdHeight, 1, 5, .45f, false);
                break;
        }
    }

    public static int counter = 0;
    public void shoot() {
        previousState = currentState;   // updates prev state to current state before update
        currentState = State.SHOOT;
        Gdx.app.log("prev state #" + Integer.toString(counter++), String.valueOf(previousState));
        if(previousState != State.SHOOT){
            switchAnimations(State.SHOOT);
        }
    }

    public void deadBird(HUD hud){
        previousState = currentState;
        currentState = State.DEAD;
        if(numLives <= 0){
            // play game over screen (i.e. setScreen(new GameOverScreen(...))
        }else{
            numLives--; //reduces a life
            switchAnimations(State.DEAD);

            //reset the healthbar, healthcount
            healthCount = 6;
            hud.updateHealthBar(healthCount);
        }
    }

    public void damagedBird(HUD hud){
        previousState = currentState;
        currentState = State.DAMAGED;

        if(!this.isInvincible){                         // bird can only get damaged when it isn't invincible
            timeVar = System.currentTimeMillis();       //  update time var to current time value everytime the bird gets damaged
            setInvincible(true);
            switchAnimations(State.DAMAGED);
            this.healthCount--;                         //reduced the health if bird is damaged
            if(this.healthCount <= 0){
                this.deadBird(hud);
            }
            hud.updateHealthBar(this.healthCount);      //updates the healthbar to display the change in health
        }

    }

    @Override
    public void dispose() {

    }


}

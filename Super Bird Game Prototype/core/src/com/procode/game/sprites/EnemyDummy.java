package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Hitbox;

public class EnemyDummy implements Disposable {
    private static final long INVINCIBLE_DURATION = 2000; // value can be changed
    private static long timeVar;  // used for the invincible property

    public enum State {IDLE, SHOOT, DAMAGED, DEAD, SPIN}
    private Animation birdAnimation, shootAnimation, spinAnimation;  // takes in an animation class to allow for changing of animation played and other settings
    private static int BirdWidth;
    private static int BirdHeight;
    private Vector2 position;
    private int healthCount;
    private Vector2 velocity;

    public Hitbox hitbox;

    // state variables (used to prevent animations from interfering with each other)
    private State currentState;
    private State previousState;

    // boolean vars for bird (used in the damagedBird())
    private boolean isDead;
    private boolean isInvincible;

    public EnemyDummy(int x, int y, int birdWidth, int birdHeight) {
        birdAnimation = new Animation();
        shootAnimation = new Animation();
        spinAnimation = new Animation();
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);

        isDead = false;
        isInvincible = false;

        healthCount = 6;
        BirdWidth = (int) birdWidth;
        BirdHeight = (int) birdHeight;
        hitbox = new Hitbox(this.position, BirdWidth, BirdHeight);
        currentState = State.SPIN;


        // sets the current animation to the idle bird
        birdAnimation.setAnimation("mecha bird animations//mecha bird idle ", BirdWidth, BirdHeight, 1, 3, .25f, true);
        shootAnimation.setAnimation("bird animations//shoot bird ", BirdWidth, BirdHeight, 1, 3, 0.1f, false);
        spinAnimation.setAnimation("mecha bird animations//mecha bird spin ", BirdWidth, BirdHeight, 1, 8, .15f, true);
    }


    // gets the current image of the bird
    public Texture getBirdImage(){

        if(currentState == State.IDLE)
            return birdAnimation.getCurrImg();
        else if(currentState == State.SHOOT)
            return shootAnimation.getCurrImg();
        else if(currentState == State.SPIN)
            return spinAnimation.getCurrImg();
        return birdAnimation.getCurrImg();
    }

    // gets the position of the bird
    public Vector2 getPosition(){
        return this.position;
    }

    //sets the new position of the bird
    public void movePosition(float newX, float newY){

        if(position.x + newX >= 0 && (position.x + BirdWidth + newX) <= SuperBirdGame.GAME_WIDTH) {
            position.x += (newX);
        }
        if(position.y + newY >= 0 && (position.y + BirdHeight + newY) <= SuperBirdGame.GAME_HEIGHT) {
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
        hitbox.update(this.position);
       /*if(birdAnimation.isAnimFinished() && !birdAnimation.getIsLoop()){ // plays an animation only once (in this case SHOOT, DEAD, and DAMAGED)
            //reset to the IDLE animation
            previousState = currentState;
            currentState = State.IDLE;
            switchAnimations(State.IDLE);
            birdAnimation.setAnimationEnded(false); // fixes the transition issue
        }else{//continue updating the frame
            birdAnimation.updateFrame(deltaTime);
            setInvincible(false);
        }*/
        if(currentState == State.SHOOT) {
            shootAnimation.updateFrame(deltaTime);

            //shootAnimation.setAnimationEnded(false);
            if(shootAnimation.animationEnded == true) {
                currentState = State.IDLE;
                shootAnimation.setAnimFinished();

            }
        }else if(currentState == State.IDLE) {
            birdAnimation.updateFrame(deltaTime);
        }else if(currentState == State.SPIN)
            spinAnimation.updateFrame(deltaTime);

        setInvincible(false);

    }

    public void switchAnimations(Bird.State playerState){
        switch(playerState){
            case IDLE:
                birdAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
                break;
            case SHOOT:
                birdAnimation.setAnimation("bird animations//shoot bird ", BirdWidth, BirdHeight, 1, 3, 0.1f, false);
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
        System.out.println("Shooting");
        previousState = currentState;
        currentState = State.SHOOT;


    }

    public void deadBird(HUD hud){
        previousState = currentState;
        currentState = State.DEAD;

        switchAnimations(Bird.State.DEAD);

        // set the screen to game over screen
    }

    public void damagedBird(HUD hud){
        previousState = currentState;
        currentState = State.DAMAGED;

        if(!this.isInvincible){ // bird can only get damaged when it isn't invincible
            timeVar = System.currentTimeMillis(); // update time var to current time value every time the bird gets damaged
            setInvincible(true);
            switchAnimations(Bird.State.DAMAGED);
            this.healthCount--;
            if(this.healthCount <= 0){
                this.deadBird(hud);
            }
            hud.updateHealthBar(this.healthCount);
        }

    }


    public void setBirdSize(int width, int height){

        //repositions the bird of the new size makes it go out of bounds
        if((position.x + width) > SuperBirdGame.GAME_WIDTH) {
            position.x = SuperBirdGame.GAME_WIDTH - width;
        }
        if((position.y + height) > SuperBirdGame.GAME_HEIGHT) {
            position.y = SuperBirdGame.GAME_HEIGHT - height;
        }

        BirdWidth = width;
        BirdHeight = height;
    }

    @Override
    public void dispose() {
        birdAnimation.dispose();
    }
}

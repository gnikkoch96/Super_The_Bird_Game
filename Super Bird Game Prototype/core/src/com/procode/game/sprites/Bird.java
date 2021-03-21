package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Hitbox;

import java.lang.Math;

public class Bird implements Disposable {
    private static final long INVINCIBLE_DURATION = 2000; // value can be changed
    private static long timeVar;  // used for the invincible property
    private static int BirdWidth;
    private static int BirdHeight;

    // states
    public enum State {IDLE, SHOOT, DAMAGED, DEAD}
    private Animation currentAnimation, idleAnimation, shootAnimation, damageAnimation, deadAnimation;

    // state variables (used to prevent animations from interfering with each other)
    private State currentState;
    private State previousState;

    // boolean vars for bird (used in the damagedBird())
    private boolean isDead;
    private boolean isInvincible;

    // bird properties
    private int healthCount;
    private Vector2 position;
    private Vector2 velocity;
    private Hitbox hitbox;

    // audio related
    private Sound spitSound;
    private Sound damageSoundNormal, damageSoundLoud;
    private Sound deadSound, deadSoundSad;

    // projectiles (w/ memory management)
    private final Array<BirdSpit> activeSpits = new Array<BirdSpit>(); // active spits is defined as in the screen and hasn't made contact with anything yet
    private final Pool<BirdSpit> spitPool = new Pool<BirdSpit>(){ // spit pool
        @Override
        protected BirdSpit newObject() {
            return new BirdSpit();
        }
    };


    public Bird(int x, int y, int birdWidth, int birdHeight) {
        position = new Vector2(x,y);
        velocity = new Vector2(0,0);

        isDead = false;
        isInvincible = false;

        healthCount = 6;
        BirdWidth = (int) birdWidth;
        BirdHeight = (int) birdHeight;
        hitbox = new Hitbox(this.position, BirdWidth, BirdHeight);
        hitbox.hitboxBit = SuperBirdGame.BIRD_BIT;
        currentState = State.IDLE;
        previousState = currentState;

        // getting sounds
        spitSound = SuperBirdGame.manager.get("audio/sound/spit.wav", Sound.class);
        damageSoundNormal = SuperBirdGame.manager.get("audio/sound/bird_scream_normal.wav", Sound.class);
        damageSoundLoud = SuperBirdGame.manager.get("audio/sound/bird_scream_loud.wav", Sound.class);
        deadSound = SuperBirdGame.manager.get("audio/sound/bird_dead.wav", Sound.class);
        deadSoundSad = SuperBirdGame.manager.get("audio/sound/bird_dead_sad.wav", Sound.class);

        // initializes animation variables (memory management)
        idleAnimation = new Animation();
        shootAnimation = new Animation();
        damageAnimation = new Animation();
        deadAnimation = new Animation();
        idleAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
        shootAnimation.setAnimation("bird animations//shoot bird ", BirdWidth, BirdHeight, 1, 3, .1f, false);
        damageAnimation.setAnimation("bird animations//damage bird ", BirdWidth, BirdHeight, 1, 8, .45f, false);
        deadAnimation.setAnimation("bird animations//dead bird ", BirdWidth, BirdHeight, 1, 7, .50f, false);

        currentAnimation = idleAnimation; // idle is always the first state the bird is in
    }

    // gets the current image of the bird
    public Texture getBirdImage(){return currentAnimation.getCurrImg();}

    // gets the position of the bird
    public Vector2 getPosition(){
        return this.position;
    }

    //--Nikko: I used this to locate where the spits should be rendering from
    public static int getBirdWidth(){return BirdWidth;}
    public static int getBirdHeight(){return BirdHeight;}

    // gets the width and height of the bird
    public Vector2 getBirdSize(){
        return new Vector2(BirdWidth, BirdHeight);
    }

    // returns the status of the invincibility of the bird (used to re-enable the bird's collision detection)
    public boolean getInvincible() {return this.isInvincible;}

    //sets the new position of the bird
    public void movePosition(float newX, float newY){

        if(position.x + newX >= 0 && (position.x + BirdWidth + newX) <= SuperBirdGame.ANDROID_WIDTH) {
            position.x += (newX);
        }
        if(position.y + newY >= 0 && (position.y + BirdHeight + newY) <= SuperBirdGame.ANDROID_HEIGHT) {
            position.y += newY;
        }
    }

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
        // update hitbox
        hitbox.update(this.position);

        if(currentAnimation.animationEnded == true){ // transitions from non-idle animation back to idle
            currentAnimation.setAnimFinished();
            switchAnimations(State.IDLE);
            currentState = State.IDLE;
        }else{ // updates the idle animation
            currentAnimation.updateFrame(deltaTime);
            setInvincible(false);
        }

        // projectiles
        for(BirdSpit spits : activeSpits){
            spits.update(deltaTime);
        }
    }

    public void switchAnimations(State playerState){
        switch(playerState){
            case IDLE:
                currentAnimation = idleAnimation;
                break;
            case SHOOT:
                currentAnimation = shootAnimation;
                break;
            case DAMAGED:
                currentAnimation = damageAnimation;
                break;
            case DEAD:
                currentAnimation = deadAnimation;
                break;
        }
    }

    public void shoot() {
        previousState = currentState;
        currentState = State.SHOOT;

        if(previousState != State.SHOOT && previousState == State.IDLE){ // to prevent overlapping of animations
            // plays sound
            spitSound.play();

            switchAnimations(State.SHOOT);

            // create spit
            BirdSpit item = spitPool.obtain();
            item.init(this.position.x, this.position.y);
            activeSpits.add(item);
        }
    }

    public void renderBullets(SpriteBatch batch){ //--Nikko: Might need to make this better
        for(BirdSpit spits: activeSpits){
            spits.render(batch);
        }
    }

    public void deadBird(HUD hud){
        previousState = currentState;
        currentState = State.DEAD;

        switchAnimations(State.DEAD);

        deadSoundSad.play();

        // set the screen to game over screen

    }

    public void damageBird(HUD hud){
        previousState = currentState;
        currentState = State.DAMAGED;

        if(!this.isInvincible){ // bird can only get damaged when it isn't invincible
            if(this.healthCount == 2){
//                damageSoundLoud.play();
            }else{
                damageSoundNormal.play();
            }

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
        currentAnimation.dispose();
        deadAnimation.dispose();
        idleAnimation.dispose();
        shootAnimation.dispose();
        damageAnimation.dispose();
        spitSound.dispose();
        deadSound.dispose();
        deadSoundSad.dispose();
        damageSoundNormal.dispose();
        damageSoundLoud.dispose();
    }


}

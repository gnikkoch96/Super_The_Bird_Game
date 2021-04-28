package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.screens.SettingsScreen;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Enemy;
import com.procode.game.tools.Hitbox;
import com.procode.game.tools.ParticleEffectComponent;

import java.util.List;

public class Bird implements Disposable {
    private static final long INVINCIBLE_DURATION = 2000; // value can be changed
    public static long timeVar;  // used for the invincible property
    private static int BirdWidth;
    private static int BirdHeight;
    private static int ShrunkBirdWidth;
    private static int ShrunkBirdHeight;
    private static int OriginalBirdWidth;
    private static int OriginalBirdHeight;

    //volume changes
    private float volume;

    // states
    public enum State {IDLE, SHOOT, DAMAGED, DEAD}
    private Animation currentAnimation, idleAnimation, shootAnimation, damageAnimation,
            deadAnimation, invincibleIdleAnimation, invincibleShootAnimation;

    private Animation shrunkResizeTransitionAnimation, shrunkIdleAnimation, shrunkShootAnimation, shrunkDamageAnimation,
            shrunkInvincibleIdleAnimation, shrunkInvincibleShootAnimation;

    // state variables (used to prevent animations from interfering with each other)
    private State currentState;
    private State previousState;

    // boolean vars for bird (used in the damagedBird())
    private boolean dead;
    public boolean isInvincible;

    // bird properties
    private Vector2 position;
    private Vector2 hitboxBoundsOffset;
    private Vector2 hitboxPosOffset;
    private int healthCount;
    public Hitbox hitbox;

    // audio related
    private Sound spitSound, flapSound;
    private Sound damageSoundNormal, damageSoundLoud;
    private Sound deadSound, deadSoundSad;

    // projectiles (w/ memory management)
    private final Array<BirdSpit> activeSpits = new Array<BirdSpit>(); // active spits is defined as in the screen and hasn't made contact with anything yet
    private final Pool<BirdSpit> spitPool;

    // shrunk effects for projectiles
    private float currentTime; // keeps track of current delta time so bird can only shoot every .2 seconds
    private float rateOfFireTime;
    private float lastTimeShot;

    // particles
    private ParticleEffectComponent spitParticles;

    // enabled when the bird is shrunk
    public boolean isShrunk;
    private boolean shrinking;
    private boolean growing;

    private float speedOffset;
    public boolean isFlapping;

    public Bird(int x, int y, int birdWidth, int birdHeight, final Camera gameCamera) {
        currentAnimation = new Animation();
        shootAnimation = new Animation();
        position = new Vector2(x,y);
        dead = false;
        isInvincible = false;
        isShrunk = false;
        shrinking = false;
        growing = false;
        speedOffset = 1;
        isFlapping = false;

        currentTime = 0;
        rateOfFireTime = .23f;
        lastTimeShot = 0;

        healthCount = 6;
        BirdWidth = (int) birdWidth;
        BirdHeight = (int) birdHeight;
        OriginalBirdHeight = BirdHeight;
        OriginalBirdWidth = BirdWidth;
        ShrunkBirdHeight = (int) birdHeight / 2;
        ShrunkBirdWidth = (int) birdWidth / 2;
        spitPool = new Pool<BirdSpit>(){ // spit pool (16 by default)
            @Override
            protected BirdSpit newObject() {
                return new BirdSpit(gameCamera);
            }
        };
        spitParticles = new ParticleEffectComponent("effects/spitimpact3.p", "bird animations");
        this.hitboxPosOffset = new Vector2((float) (x + (int)(BirdWidth/8)), (float) (y +  (this.BirdHeight/10)));
        this.hitboxBoundsOffset = new Vector2((int) (BirdWidth/3), ((int)(this.BirdHeight) - (this.BirdHeight/4)));
        hitbox = new Hitbox(this.hitboxPosOffset, (int) this.hitboxBoundsOffset.x, (int) this.hitboxBoundsOffset.y, gameCamera);

        currentState = State.IDLE;
        previousState = currentState;

        // getting sounds
        spitSound = SuperBirdGame.manager.get("audio/sound/spit.wav", Sound.class);
        flapSound = SuperBirdGame.manager.get("audio/sound/bird_flap.mp3", Sound.class);
        damageSoundNormal = SuperBirdGame.manager.get("audio/sound/bird_scream_normal.wav", Sound.class);
        damageSoundLoud = SuperBirdGame.manager.get("audio/sound/bird_scream_loud.wav", Sound.class);
        deadSound = SuperBirdGame.manager.get("audio/sound/bird_dead.wav", Sound.class);
        deadSoundSad = SuperBirdGame.manager.get("audio/sound/bird_dead_sad.wav", Sound.class);

        // initializes animation variables (memory management)
        idleAnimation = new Animation();
        shootAnimation = new Animation();
        damageAnimation = new Animation();
        deadAnimation = new Animation();
        invincibleIdleAnimation = new Animation();
        invincibleShootAnimation = new Animation();

        // shrunk animations
        shrunkIdleAnimation = new Animation();
        shrunkShootAnimation = new Animation();
        shrunkDamageAnimation = new Animation();
        shrunkInvincibleIdleAnimation = new Animation();
        shrunkInvincibleShootAnimation = new Animation();
        shrunkResizeTransitionAnimation = new Animation();

        idleAnimation.setAnimation("bird animations//idle bird ", BirdWidth, BirdHeight, 1, 4, .25f, true);
        shootAnimation.setAnimation("bird animations//shoot bird ", BirdWidth, BirdHeight, 1, 3, .1f, false);
        damageAnimation.setAnimation("bird animations//damage bird ", BirdWidth, BirdHeight, 1, 8, .45f, false);
        deadAnimation.setAnimation("bird animations//dead bird ", BirdWidth, BirdHeight, 1, 7, .50f, false);
        invincibleIdleAnimation.setAnimation("bird animations//invincible animations//idle//invincible ", BirdWidth, BirdHeight, 1, 8, 0.25f, true);
        invincibleShootAnimation.setAnimation("bird animations//invincible animations//shoot//invincible shoot ", BirdWidth, BirdHeight, 1, 6, .1f, false);

        shrunkResizeTransitionAnimation.setAnimation("bird animations//dead bird ", BirdWidth, BirdHeight, 2,4, .15f, false);
        shrunkIdleAnimation.setAnimation("bird animations//idle bird ", BirdWidth/2, BirdHeight/2, 1, 4, .25f, true);
        shrunkShootAnimation.setAnimation("bird animations//shoot bird ", BirdWidth/2, BirdHeight/2, 1, 3, .1f, false);
        shrunkDamageAnimation.setAnimation("bird animations//damage bird ", BirdWidth/2, BirdHeight/2, 1, 8, .45f, false);
        shrunkInvincibleIdleAnimation.setAnimation("bird animations//invincible animations//idle//invincible ", BirdWidth/2, BirdHeight/2, 1, 8, 0.25f, true);
        shrunkInvincibleShootAnimation.setAnimation("bird animations//invincible animations//shoot//invincible shoot ", BirdWidth/2, BirdHeight/2, 1, 6, .1f, false);

        currentAnimation = idleAnimation; // idle is always the first state the bird is in
    }

    // gets each of the activeSpits
    public Array<BirdSpit> getActiveSpits(){ return this.activeSpits;}

    // gets each of the activeParticles
    public ParticleEffectComponent getSpitParticles(){return this.spitParticles;}

    // gets the current image of the bird
    public Texture getBirdImage(){return currentAnimation.getCurrImg();}

    // determines if the bird is dead or not
    public boolean isDead(){return dead;}

    // gets the position of the bird
    public Vector2 getPosition(){
        return this.position;
    }

    //sets the new position of the bird
    public void movePosition(float newX, float newY){
        if(position.x + (newX * speedOffset) >= 0 && (position.x + BirdWidth + (newX * speedOffset)) <= SuperBirdGame.GAME_WIDTH) {
            position.x += (newX * speedOffset);
        }
        if(position.y + (newY * speedOffset) >= 0 && (position.y + BirdHeight + (newY * speedOffset)) <= SuperBirdGame.GAME_HEIGHT) {
            position.y += (newY * speedOffset);
        }
    }


    // gets the width and height of the bird
    public Vector2 getBirdSize(){
        return new Vector2(BirdWidth, BirdHeight);
    }

    // returns the status of the invincibility of the bird (used to re-enable the bird's collision detection)
    public boolean getInvincible() {return this.isInvincible;}

    // returns current state of the bird
    public State getCurrentState(){return this.currentState;}

    // updates the bird every frame
    public void update(float deltaTime){
        //this method captures the changes on the volume from the settings
        setVolume();
        currentTime = deltaTime;

        if(currentAnimation.animationEnded == true){ // transitions from non-idle animation back to idle

            if(shrinking || growing){
                shrinking = false;
                growing = false;
            }

            currentAnimation.setAnimFinished();
            previousState = currentState;
            //keep running if state is not dead
            if(currentState != State.DEAD) {
                if (!this.isInvincible) {
                    switchAnimations(State.IDLE);
                } else { // is currently invincible
                    if (previousState == State.SHOOT) {
                        if(isShrunk){
                            currentAnimation = shrunkInvincibleShootAnimation;
                        }
                        else {
                            currentAnimation = invincibleShootAnimation;
                        }
                    } else {
                        if(isShrunk){
                            currentAnimation = shrunkInvincibleIdleAnimation;
                        }
                        else {
                            currentAnimation = invincibleIdleAnimation;
                        }
                    }
                }

                currentState = State.IDLE;
            }
        }else{ // updates the idle animation

                setInvincible(false);
                if (!this.isInvincible) { // switch to non-invincible animations
                    if (currentState == State.SHOOT) {
                        switchAnimations(State.SHOOT);
                    } else if (currentState == State.IDLE) {
                        switchAnimations(State.IDLE);
                    }
                }

                //stops the dead animation from updating after reaching the final frame
           if(currentState != State.DEAD)
            currentAnimation.updateFrame(deltaTime);
           else
               if (currentAnimation.getCurrFrameIndex() != 6)
                   currentAnimation.updateFrame(deltaTime);
        }

        // updates bird hitbox
        this.hitboxPosOffset.set((float) (position.x + (int)(BirdWidth/8)), (float) (position.y +  (this.BirdHeight / 10)));
        this.hitbox.update(this.hitboxPosOffset);

        // updates projectiles
        for(BirdSpit spit : activeSpits){
            spit.update(deltaTime);
        }

        // manage spits that exit the screen (could have been put into the playscreen)
        for(BirdSpit spit: activeSpits){
            if(spit.isOutOfScreen() || spit.isCollided()){
                spitPool.free(spit);
                activeSpits.removeValue(spit, true);
            }
        }


        // for shrinking / growing the bird
        if (shrinking == true && BirdWidth != ShrunkBirdWidth && currentState == State.IDLE){
            setBirdSize((int) ShrunkBirdWidth, (int) ShrunkBirdHeight);
            isShrunk = true;
            switchAnimations(State.IDLE);
        }

        if (growing == true && BirdWidth != OriginalBirdWidth && currentState == State.IDLE){
            setBirdSize((int) OriginalBirdWidth, (int) OriginalBirdHeight);
            isShrunk = false;
            switchAnimations(State.IDLE);
        }

//        Gdx.app.log("Position " + String.valueOf(this.getClass()), "\nPosition: " + this.hitboxBoundsOffset.x + " , " + this.hitboxBoundsOffset.y);
    }


    // detects hits from enemies
    public void updateHitDetection(List<Enemy> enemies, HUD hud){

        // checks if the player hit an enemy
        for (int i = 0; i < enemies.size(); i++){
            Enemy currEnemy = enemies.get(i);
            hitDetection(currEnemy, hud);
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

    public void switchAnimations(State playerState){
        switch (playerState) {
            case IDLE:
                if(shrinking || growing){
                    currentAnimation = shrunkResizeTransitionAnimation;
                }
                else {
                    if (isShrunk) {
                        currentAnimation = shrunkIdleAnimation;
                    } else {
                        currentAnimation = idleAnimation;
                    }
                }
                break;
            case SHOOT:
                if (isShrunk) {
                    currentAnimation = shrunkShootAnimation;
                } else {
                    currentAnimation = shootAnimation;
                }
                break;
            case DAMAGED:
                if (isShrunk) {
                    currentAnimation = shrunkDamageAnimation;
                } else {
                    currentAnimation = damageAnimation;
                }
                break;
            case DEAD:
                currentAnimation = deadAnimation;
                break;
        }
    }



    public void shoot() {
        if(isShrunk == false || (currentTime - lastTimeShot) > rateOfFireTime) {
            lastTimeShot = currentTime;
            previousState = currentState;
            currentState = State.SHOOT;
            if (previousState != State.SHOOT && previousState == State.IDLE) { // to prevent overlapping of animations

                // plays sound (Nikko: How to pause sound in the middle when the game is paused?)
                spitSound.play(volume);

                switchAnimations(State.SHOOT);

                // create spit
                BirdSpit item = spitPool.obtain();
                item.init(this.position.x + (int) (BirdWidth / 1.5), this.position.y + (int) (BirdWidth / 3.8)); //Nikko: change to this when the image has been adjusted
                activeSpits.add(item);
                Gdx.app.log("Spits Left:", String.valueOf(spitPool.getFree()));
            }
        }
    }


    public void deadBird(HUD hud){
        previousState = currentState;
        currentState = State.DEAD;

        switchAnimations(State.DEAD);

        deadSoundSad.play(volume);

        // set the screen to game over screen
        dead = true;

        isFlapping = false;
        flapSound.stop();

    }

    public void damageBird(HUD hud){
        if(!this.isInvincible && currentState != State.DEAD){ // bird can only get damaged when it isn't invincible
            previousState = currentState;
            currentState = State.DAMAGED;

            damageSoundNormal.play(volume);

            timeVar = System.currentTimeMillis(); // update time var to current time value every time the bird gets damaged
            setInvincible(true);
            switchAnimations(State.DAMAGED);

            //need to validate so that when updateHealthbar is updated
            //it doesnt look for a negative image since images goes from
            //0 to n numbers from the assests folder
            if(this.healthCount != 0) {
                if(isShrunk == false) {
                    this.healthCount--; //Nikko--turn this on when you are not debugging
                }
                else{
                    this.healthCount -= 2; // double damage when the bird is shrunk
                }
            }
            if(this.healthCount < 0){
                this.healthCount = 0;
            }

            if(this.healthCount == 0){
                this.deadBird(hud);
                currentState = State.DEAD;
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

        // resize hitbox
        if (shrinking){
            hitbox.resize((int) (hitbox.width / 2), (int) (hitbox.height / 2));
        }
        else if (growing){
            hitbox.resize((int) (hitbox.width * 2), (int) (hitbox.height * 2));
        }

        BirdWidth = width;
        BirdHeight = height;
    }

    // set the update action to growing
    public void growBird(){
        if(growing == false && BirdWidth != OriginalBirdWidth) {
            shrinking = false;
            growing = true;
            speedOffset = 1;
        }
    }

    // set the update action to shrinking
    public void shrinkBird(){
        if(shrinking == false && BirdWidth != ShrunkBirdWidth) {
            shrinking = true;
            growing = false;
            speedOffset = 1.15f;
        }
    }

    //this method changes the volume from the static integer from SettingsScreen
    //the changes occur whenever the user clicks the right and left buttons of volume
    //from the SettingsScreen and later on from MiniSettingScreen on PlayScreen
    public void setVolume(){
        //this if statement captures the changes on the volume from the settings
        if(SettingsScreen.volumeChanges == 1)
            volume = 0.1f;
        else if(SettingsScreen.volumeChanges == 2)
            volume = 0.2f;
        else if(SettingsScreen.volumeChanges == 3)
            volume = 0.3f;
        else if(SettingsScreen.volumeChanges == 4)
            volume = 0.4f;
        else if(SettingsScreen.volumeChanges == 5)
            volume = 0.5f;
        else if(SettingsScreen.volumeChanges == 6)
            volume = 0.6f;
        else if(SettingsScreen.volumeChanges == 7)
            volume = 0.7f;
        else if(SettingsScreen.volumeChanges == 8)
            volume = 0.8f;
        else if(SettingsScreen.volumeChanges == 9)
            volume = 0.9f;
        else if(SettingsScreen.volumeChanges == 10)
            volume = 1.0f;
    }
    // debugs the hitboxes of anything related to the bird
    public void debugHitbox(){
        //--DEBUG--// Note: debugging hitboxes has to occur after it has rendered
        this.hitbox.debugHitbox();
        for(BirdSpit spit:activeSpits){
            spit.getHitbox().debugHitbox();
        }
    }

    // manages all hit detection related to the bird character (Nikko: Change Bird -> List<Enemy> once it starts working)
    public void hitDetection(Enemy enemy, HUD hud){
        if(enemy.hitbox != null) {
            if (this.hitbox.isHit(enemy.hitbox) || enemy.hitbox.isHit(this.hitbox)) {
//            Gdx.app.log("PLAYER->ENEMY", "HIT");
                this.damageBird(hud);
            }

            if(enemy instanceof MechaBird){
                Array<MechaLaser> activeShots = ((MechaBird)(enemy)).activeShots;
                for (MechaLaser laser : activeShots) {
                    if (this.hitbox.isHit(laser.hitbox) || laser.hitbox.isHit(this.hitbox)) {
//                          Gdx.app.log("PLAYER->ENEMY", "HIT");
                        laser.setCollision(true);
                        this.damageBird(hud);

                    }
                }
            }

            // bird projectile and enemy collision
            for (int i = 0; i < activeSpits.size; i++){
                BirdSpit spit = activeSpits.get(i);
                if(spit.hitbox.isHit(enemy.hitbox) || enemy.hitbox.isHit(spit.hitbox)){
                    spit.setCollision(true);
                    spitParticles.createParticle(spit.getPosition().x + spit.getProjectileWidth(),spit.getPosition().y + spit.getProjectileHeight()/2);
                }
            }
        }
    }


    public void startFlap() {
        flapSound.loop();
        isFlapping = true;
    }


    @Override
    public void dispose() {
        currentAnimation.dispose();
        deadAnimation.dispose();
        idleAnimation.dispose();
        shootAnimation.dispose();
        damageAnimation.dispose();
        spitSound.dispose();
        flapSound.dispose();
        deadSound.dispose();
        deadSoundSad.dispose();
        damageSoundNormal.dispose();
        damageSoundLoud.dispose();
    }


}

package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Enemy;
import com.procode.game.tools.Hitbox;

import java.util.ArrayList;
import java.util.List;

public class MechaBird extends Enemy {

    // mecha bird heavily relies on the super class enemy that deals with the position, hitbox,
    // width/height, speed, state and animations played

    // holds all of the attacks for the bird to do, one after another
    private List<Integer> attackPattern; // list of what attacks to use. In this list the value compares to which attack to use [0 = dash, 1 = spin, 2 = shoot]
    private int currAttackInList; // which attack to use in the attack pattern
    public Vector2 currDestination; // if the mecha bird needs to reach a destination, this is
    private int maxAttacksPerEnemy; // the maximum amount of attacks a single enemy can have in their attack pattern

    // we want to pause an action before doing the next one
    public float timeActionPaused; // the current time an action is paused
    public float pausedDuration; // the amount of time the action will be paused for

    private Camera gameCamera;

    // the amount of spits an enemy can take while dashing before being destroyed
    private int spitHits; // current amount of hits by spit
    private int maxSpitHits = 4; // 4 hits will destroy the enemy
    private List<BirdSpit> playerSpitsAlreadyHit; // number of spits that have already hit for dash

    // when doing spinning attack, we want to keep track of the angle, radius and if the spin finished, as well as the original
    // enemy position before spinning
    private int angle;
    private int radius;
    private boolean spinFinished;
    private Vector2 originalPos;

    // sounds
    private float volume;
    private Sound deadSound;

    // stuff for the shoot
    private Vector2 playerPos; // needs the players position so the enemy can follow the player
    private float currTimeEnteredShoot; // current time the bird shot
    private float shootDuration; // the amount ot time the enemy will shoot for before entering next attack
    private boolean shootPerframe; // needs a toggle to know if a laser was already shot this frame
    public final Array<MechaLaser> activeShots = new Array<MechaLaser>(); // active spits is defined as in the screen and hasn't made contact with anything yet
    public final Pool<MechaLaser> shootPool;

    // amount of total hits before destruction
    private int originalHits; // original amount of hits
    private int totalCurrHits; // current hits before destruction
    public boolean isHit;

    // initializing the mecha bird
    public MechaBird(int mechaBWidth, int mechaBHeight, float speed, final Camera gameCamera){

        // super class of enemy that spawns outside of the screen view
        super(mechaBWidth, mechaBHeight, speed); // sets everything, position wise also makes the current stae idle
        pausedDuration = 1.5f; // will pause actions for about 1.5 seconds after each state/ attack transfer
        timeActionPaused = 0;
        spitHits = maxSpitHits;
        maxAttacksPerEnemy = 4; // 4 hits to destroy enemy in dashing state

        originalHits = 20; // 45 total hits before destroying the enemy
        totalCurrHits = originalHits;
        this.isHit = false;

        // stuff for hitbox
        this.gameCamera = gameCamera;
        playerSpitsAlreadyHit = new ArrayList<BirdSpit>();

        // stuff for attacks
        angle = 0;
        spinFinished = false;
        radius = 0;
        originalPos = new Vector2();
        playerPos = new Vector2();

        //shoot stuff
        currTimeEnteredShoot = 0;
        shootDuration = 5f; // shoot at player for about 5 seconds
        shootPerframe = false;
        shootPool = new Pool<MechaLaser>(){ // spit pool (16 by default)
            @Override
            protected MechaLaser newObject() {
                return new MechaLaser(gameCamera);
            }
        };

        // create all the animations
        // note we do not need super for variables because they are protected, just thought
        // itd be easier to read if we did so
        super.idleEnemy = new Animation();
        super.idleEnemy.setAnimation("mecha bird animations//mecha bird idle ", super.enemyWidth, super.enemyHeight, 1, 3, .5f, true);

        // the mecha bird will not be able to be damaged but will die upon collision with the bird
        super.damagedEnemy = null;
//        super.damagedEnemy = new Animation();
//        super.damagedEnemy.setAnimation("mecha bird animations//damaged//mecha bird damaged ", super.enemyWidth, super.enemyHeight, 1, 5, .50f, false);

        super.deadEnemy = new Animation();
        super.deadEnemy.setAnimation( "mecha bird animations//mecha bird dead ", (int) (super.enemyWidth * 1.5), (int) (super.enemyHeight * 1.5), 2, 4, .4f, false);

        currDestination = new Vector2();

        // add the attacking animations to the mecha bird
        // dashing attack animation
        Animation mechaBirdDashCharge = new Animation(); // happens when mecha bird charging up for the dash
        mechaBirdDashCharge.setAnimation("mecha bird animations//mecha bird dash ", super.enemyWidth, super.enemyHeight, 1, 6, .75f, false);
        Animation mechaBirdDashing = new Animation(); // happens when the mecha bird is charging at the bird
        mechaBirdDashing.setAnimation("mecha bird animations//mecha bird dash ", super.enemyWidth, super.enemyHeight, 7, 8, .25f, true);

        //spinning attack animation
        Animation mechaBirdSpinCharge = new Animation();
        mechaBirdSpinCharge.setAnimation("mecha bird animations//mecha bird spin ", super.enemyWidth, super.enemyHeight, 1, 6, .65f, false);
        Animation mechaBirdSpinning = new Animation();
        mechaBirdSpinning.setAnimation("mecha bird animations//mecha bird spin ", super.enemyWidth, super.enemyHeight, 4, 6, .15f, true);
        Animation mechaBirdSpinStop = new Animation();
        mechaBirdSpinStop.setAnimation("mecha bird animations//mecha bird spin ", super.enemyWidth, super.enemyHeight, 4, 8, .40f, false);

        // shooting animations
        Animation mechaBirdShoot = new Animation();
        mechaBirdShoot.setAnimation("mecha bird animations//mecha bird shoot ", super.enemyWidth, super.enemyHeight, 1, 5, .75f, true);

        // damaged animations
        Animation mechaBirdDamaged = new Animation();
        mechaBirdDamaged.setAnimation("mecha bird animations//damaged//mecha bird damaged ", super.enemyWidth, super.enemyHeight, 1, 8, .50f, false);

        // add all of the animations to the arraylist from the super class
        super.setAttackAnimation(mechaBirdDashCharge);
        super.setAttackAnimation(mechaBirdDashing);
        super.setAttackAnimation(mechaBirdSpinCharge);
        super.setAttackAnimation(mechaBirdSpinning);
        super.setAttackAnimation(mechaBirdSpinStop);
        super.setAttackAnimation(mechaBirdShoot);

        // set the attacking patterns first
        attackPattern = new ArrayList<Integer>();
        setAttackPattern();

        // start the attacking patterns then once it leaves the screen, or touches the bird, destroy it. (in update func)
        // remaining done in the update function
        setEnemyInitialPosition();

        // set the hitbox with its offset
        super.hitboxPosOffset = new Vector2(super.enemyWidth / 6, super.enemyHeight / 8 );
        super.hitboxBoundsOffset = new Vector2((int) (super.enemyWidth/ 1.5), ((int)(super.enemyHeight) - (super.enemyHeight/3)));
        super.hitbox = new Hitbox(new Vector2((int)(super.position.x + super.hitboxPosOffset.x), (int)(super.position.y + super.hitboxPosOffset.y)), (int) super.hitboxBoundsOffset.x, (int) super.hitboxBoundsOffset.y, gameCamera);

        // sounds
        deadSound = SuperBirdGame.manager.get("audio/sound/mecha_dead.wav", Sound.class);

        // now do the following: move the mecha bird until it reaches the screen visually, (is on the screen)
        // idle animation will play until this position is reached.
        setDestination();
    }


    public State getCurrentState(){return super.currentState;}

    // randomly chooses the attacks to do
    // will pick out random attacks until a point where the mecha bird does a dash
    public void setAttackPattern(){

        // if the attack is an ending attack, or it reaches the attack limit per attack pattern, stop adding new attacks
        // will add new random attacks to the attack pattern, always ends with a dashing attack
        boolean endingAttack = false;
        while (!endingAttack){
            int randomAttack = (int) (Math.random() * (3)); // a random attack that will be selected (0 = dash, 1 = spin, 2 = shoot)

            if (attackPattern.size() == maxAttacksPerEnemy - 1){
                attackPattern.add(0);
                endingAttack = true;
            }
            else {

                // 0 = dash, 1 = spin, 2 = shoot (based on animations listed)
                if (randomAttack == 0) {
                    endingAttack = true;
                }
                attackPattern.add(randomAttack);
            }
        }

        // start off at index 0 for attack pattern
        currAttackInList = 0;
    }



    // sets the destination, depending on the current scenario.
    // if the bird is in the idle state, the destination is a random area near the right side of the screen in view of player
    // if its an attack state, the destination is dependent of the attack used
    //  attackPattern[currAttackInList] = 0 (aka. dash), destination is left off screen, past the player
    //  attackPattern[currAttackInList] = 1 (aka. spin), destination is a circular motion around a radius and angle, back to the original position
    //  attackPattern[currAttackInList] = 2 (aka. shoot), destination is same y axis as the player, while staying in the same x coordinate
    public void setDestination(){
        if (super.currentState == State.IDLE) {

            // if the current state is idle, we simply need the destination to be on screen,
            // so the destination is the same y axis and a position in the screen
            // (will be randomized somewhat to allow for diversity)
            currDestination.y = (int) super.position.y;
            currDestination.x = (int) (SuperBirdGame.GAME_WIDTH - ((Math.random() * super.enemyWidth) + super.enemyWidth));
        }
        else if (super.currentState == State.ATTACK){

                // will search for the type of attack, if dash, to the end of the screen,
                // if spin, we use a function that will determine the next destination
                // if shoot, then move up and down for a set period of time
                if (attackPattern.get(currAttackInList) == 0){ // dash
                    currDestination.y = (int) super.position.y;
                    currDestination.x = (int) - (super.enemyWidth * 3);
                }

                // will spin in a circle
                else if (attackPattern.get(currAttackInList) == 1){ // spin

                    // if the current angle is radians 0, the original position is the current one,
                    // the radius has already been set when transferring to this state
                    if(angle == 0){
                        originalPos.x = super.position.x;
                        originalPos.y = super.position.y;
                    }

                    // the coordinates are based off of unit circle calculations
                    float radOffsetX = (float) (radius *  Math.cos(angle * Math.PI / 12));
                    float radOffsetY = (float) (radius * Math.sin(angle * Math.PI / 12));
                    currDestination.x = (int) ((originalPos.x - radius) + (radOffsetX));
                    currDestination.y = (int) ((originalPos.y) + (radOffsetY));

                    // angle needs to increment by 1 at a time and once reaches 24 as its new destination (went in a complete circle),
                    // reset to 0
                    if (angle < 25){
                        angle += 1;
                    }
                    else{
                        angle = 0;
                        spinFinished = true;
                    }
                }

                // will shoot following player y axis
                else if (attackPattern.get(currAttackInList) == 2){ // shoot

                    // current position is just the player's y position, while staying in the same x coordinate
                    currDestination.x = super.position.x;
                    currDestination.y = playerPos.y;
                }
        }
    }



    // updates the frames, state, position and actions depending on the current state/attack
    // need to pass in the player hitbox to see if there is a collision
    // and pass in the players active spits to see if there is a collision between the spits
    public void updateMechaBird(float deltaTime, Hitbox playerHitbox, Array<BirdSpit> playerSpits){

        super.update(deltaTime); // updates the frames of the current animation

        // now update the position and state depending on current state and other factors
        // because of the fact that after each attack, it goes into an idle state for a couple of seconds,
        // we need to make sure the first attack already finished meaning if the index is greater than 0 (did first attack)
        // the original actions played when first being in the idle state will not repeat again
        if (super.currentState == State.IDLE && currAttackInList == 0) {

            // keep moving until the mecha bird has reached the destination (position in screen)
            if (Math.abs(super.position.x - currDestination.x) > super.enemySpeed) {
                updatePos();
            }

            // now pause for a period of time before entering the next state
            else {

                // pause before changing to a new state
                if (timeActionPaused == 0) {
                    timeActionPaused = deltaTime;
                    pausedDuration = (float) Math.random() + .2f;
                }

                // stop for next movement for about 1 - 2 seconds
                if (timeActionPaused + pausedDuration < deltaTime) {

                    // sets the next state and attack the mecha bird will use then set the destination for said attack
                    if (attackPattern.get(currAttackInList) == 0) { // dash
                        super.changeState(State.ATTACK, 0);
                    } else if (attackPattern.get(currAttackInList) == 1) { // spin
                        super.changeState(State.ATTACK, 2);
                    } else { // shoot
                        super.changeState(State.ATTACK, 5);
                    }
                    setDestination();
                }

            }
        }

        // will do different actions depending on the current attack being used
        // also if the current state is idle but the mecha bird already did the first attack, it is simply in idle
        // for a few seconds before entering the next attack so it should enter this as well
        else if (super.currentState == State.ATTACK || currAttackInList > 0) {

            // stop for next movement before next attack in list
            if (timeActionPaused + pausedDuration < deltaTime) {

                // will do attack based on which attack to use in the attackPattern.get(currAttackInList), 0 = dash, 1 = spin, 2 = shoot
                // dash
                if (attackPattern.get(currAttackInList) == 0) {

                    // make sure the state, attack and destination is already set, since it could be in an idle state
                    if ((super.currAttackState != 0 && super.currAttackState != 1)){
                        super.changeState(State.ATTACK, 0);
                        setDestination();
                    }

                    // resize hitbox to fit the attack since the enemy crouches during the dash
                    super.hitbox.resize((int) hitboxBoundsOffset.x, (int) (hitboxBoundsOffset.y / 2));

                    // need to first charge up until the animation is complete
                    // if the animation is complete then we dash, also increase speed
                    if (super.currAttackState == 0 && super.enemyAttacks.get(super.currAttackState).isAnimFinished() == true) {
                        super.enemyAttacks.get(super.currAttackState).replayLoop();
                        super.changeState(State.ATTACK, 1);
                        super.setEnemySpeed(super.enemySpeed * 5);
                    }

                    // now dash to the end or until you hit the bird or go offscreen or are destroyed by by spits
                    else if (super.currAttackState == 1) {

                        // loop through the array of active spits to see if there is a collision
                        // if so decrement the amount of hits by 1
                        // if it reaches 0 or less, then destroy the enemy
                        for(int i = 0; i < playerSpits.size; i++){
                            BirdSpit currSpit = playerSpits.get(i);
                            Hitbox currSpitBox = currSpit.hitbox;
                            if ((super.hitbox.isHit(currSpitBox) || currSpitBox.isHit(super.hitbox)) && !playerSpitsAlreadyHit.contains(currSpit) ){
                                playerSpitsAlreadyHit.add(currSpit);
                                spitHits -= 1;
                            }
                        }

                        // destroys the enemy when it is hit by 4 spits, is off screen or hit the player
                        if (Math.abs(super.position.x - currDestination.x) < super.enemySpeed || super.hitbox.isHit(playerHitbox) ||
                                playerHitbox.isHit(super.hitbox) || spitHits <= 0) {
                            super.changeState(State.DEAD, -1);
                            currAttackInList = 0;
                        }

                        // otherwise move
                        else {
                            updatePos();
                        }

                    }
                }

                // spin
                // for these cases, the mecha bird will follow a directed path
                // and move onto the next attack in the list
                else if (attackPattern.get(currAttackInList) == 1) {

                    // randomly set a radius every time for the mecha bird to circle around
                    // the radius is in front of the mecha bird so that it can spin towards the player
                    if(radius == 0){
                        radius = (int) (SuperBirdGame.GAME_WIDTH / 2.5 + (Math.random() * .75));
                    }

                    // make sure the state, attack and destination is already set, since it could be in an idle state
                    if ((super.currAttackState != 2 && super.currAttackState != 4 && super.currAttackState != 3)){
                        super.changeState(State.ATTACK, 2);
                        setDestination();
                    }

                    // charge up until the animation is complete, then start to spin in a circular motion
                    if (super.currAttackState == 2 && super.enemyAttacks.get(super.currAttackState).isAnimFinished() == true) {
                        super.changeState(State.ATTACK, 3);
                        super.setEnemySpeed((float) (super.enemySpeed * 1.5)); // needs to spin fast
                    }

                    // will spin until it reaches its destination and when it does,
                    // change to the stopping animation until it is done
                    else if (super.currAttackState == 3){

                        // while the spinning has not stopped, update the position until it reached
                        // the next destination of the circle and keep repeating until the
                        // mecha bird went in a complete circle
                        if (!spinFinished){
                            if(Math.abs(super.position.x - currDestination.x) > super.enemySpeed && Math.abs(super.position.y - currDestination.y) > super.enemySpeed) {
                                updatePos();
                            }
                            else{
                                setDestination();
                            }
                        }

                        // now reset the angle and toggles to enter the spinning stopped animation
                        else{
                            spinFinished = false;
                            angle = 0;
                            super.currAttackState = 0;
                            super.changeState(State.ATTACK, 4);
                            super.setEnemySpeed((float) (super.enemySpeed / 1.5)); // needs to spin fast
                        }
                    }

                    // once the spinning stopped, wait until the spinning animation stopped, then enter the next attack in the list
                    else if (super.currAttackState == 4){
                        if(super.enemyAttacks.get(currAttackState).isAnimFinished() == true){
                            super.enemyAttacks.get(2).replayLoop();
                            super.enemyAttacks.get(4).replayLoop();
                            super.currAttackState = -1; // needs to be -1 to make sure all other functions work
                            timeActionPaused = deltaTime;
                            pausedDuration = (float) Math.random() + .5f;
                            currAttackInList += 1;
                            radius = 0;
                            angle = 0;
                            spinFinished = false;
                            super.changeState(State.IDLE, -1);
                        }
                    }

                }

                // shoot
                // will follow the players y coordinate for a set time while shooting
                else if (attackPattern.get(currAttackInList) == 2) {

                    // updates the players position to follow
                    playerPos.x = playerHitbox.position.x;
                    playerPos.y = playerHitbox.position.y;

                    // make sure the state, attack and destination is already set, since it could be in an idle state
                    if ((super.currAttackState != 5)){
                        super.changeState(State.ATTACK, 5);
                        setDestination();
                    }

                    // set the time the enemy has recently shot also slow down the enemy so that the player can easily avoid the shots
                    if (currTimeEnteredShoot == 0){
                        currTimeEnteredShoot = deltaTime;
                        setEnemySpeed(super.enemySpeed / 2);
                    }

                    // shoots for set duration
                    if (currTimeEnteredShoot + shootDuration > deltaTime){

                        updatePos();
                        setDestination();

                        // shoots a shot once per delayed time
                        if(super.enemyAttacks.get(super.currAttackState).getCurrFrameIndex() == 3 && shootPerframe == false) {
                            shootPerframe = true;
                            MechaLaser item = shootPool.obtain();
                            item.init(this.position.x - (int) (super.enemyWidth / 4.75), this.position.y + (int) (super.enemyHeight / 1.9)); //Nikko: change to this when the image has been adjusted
                            activeShots.add(item);
                        }
                        else if (super.enemyAttacks.get(super.currAttackState).getCurrFrameIndex() != 3){
                            shootPerframe = false;
                        }
                    }

                    // move onto next attack
                    else{
                        super.currAttackState = -1;
                        shootPerframe = false;
                        currTimeEnteredShoot = 0;
                        timeActionPaused = deltaTime;
                        pausedDuration = (float) Math.random() + .5f;
                        currAttackInList += 1;
                        super.changeState(State.IDLE, -1);
                        setEnemySpeed(super.enemySpeed * 2);
                    }

                }
            }
        }

        // enemy is destroyed
        else if (super.currentState == State.DEAD) {

            // resize hitbox to a larger size for the explosion effect
            super.hitbox.resize((int) (hitboxBoundsOffset.x * 1.5), (int) (hitboxBoundsOffset.y * 1.5));

            // clear the spits that have already hit
            if (super.deadEnemy.isAnimFinished() == true) {
                playerSpitsAlreadyHit.clear();
                attackPattern.clear();
            }
        }

        // takes into account the current hits taken so far
        for (BirdSpit spits: playerSpits ) {
            Hitbox currSpitHitbox = spits.getHitbox();
            if (currSpitHitbox.isHit(super.hitbox) || super.hitbox.isHit(currSpitHitbox)) {
                //--TEST--//
                if(this.attackPattern.get(currAttackInList) != 1){ // doesn't take damage when it is spinning
                    switch(this.attackPattern.get(currAttackInList)){
                        case 0: // gets damaged in dash
                            totalCurrHits -= 4;
                            break;
                        case 2: // gets damaged in shoot
                            totalCurrHits -= 2;
                            break;
                    }

                }
                this.isHit = true;
                totalCurrHits -= 1;
            }
        }

        // if taken more than the limit, destroy the enemy
        if (totalCurrHits < 1 && super.currentState != State.DEAD){
            super.changeState(State.DEAD, -1);
        }

        // updates projectiles
        for(MechaLaser laser : activeShots){
            laser.update(deltaTime);
        }

        // manage spits that exit the screen
        for(MechaLaser laser: activeShots){
            if(laser.isOutOfScreen() || laser.isCollided()){
                shootPool.free(laser);
                activeShots.removeValue(laser, true);
            }
        }
    }



    // is called to update the position to the current destination
    // also updates the position of the hitbox
    public void updatePos(){

        // checks if the position is close enough to the destination to stop moving
        if (Math.abs(currDestination.x - super.position.x ) > super.enemySpeed / 2){
            if (currDestination.x > super.position.x){
                position.x += (int) super.enemySpeed;
            }
            else{
                position.x -= (int) super.enemySpeed;
            }
        }
        if (Math.abs(currDestination.y - super.position.y ) > super.enemySpeed / 2){
            if (currDestination.y > super.position.y){
                position.y += (int) super.enemySpeed;
            }
            else{
                position.y -= (int) super.enemySpeed;
            }
        }
        updateHitboxPos();
    }



    // updates the hitbox pos
    public void updateHitboxPos() {
        // update the hitbox position if it isnt null
        if (super.hitbox != null) {
            super.hitbox.update(new Vector2((int) (super.position.x + super.hitboxPosOffset.x), (int) (super.position.y + super.hitboxPosOffset.y)));
        }
    }



    // spawns the enemy at the start as if it was barely initialized, so it can be reused
    public void reSpawn(){

        // reinitialize all values
        timeActionPaused = 0;
        super.enemySpeed = super.originalSpeed;
        setEnemyInitialPosition();
        super.currAttackState = 0;
        super.changeState(State.IDLE, -1);
        setAttackPattern();
        setDestination();

        // make animations be able to play again if they are not looped
        deadEnemy.replayLoop();
        for (int i = 0; i < super.enemyAttacks.size(); i++) {
            enemyAttacks.get(i).replayLoop();
        }

        // update the hitbox pos by replacing it
        super.hitbox = new Hitbox(new Vector2((int)(super.position.x + super.hitboxPosOffset.x), (int)(super.position.y + super.hitboxPosOffset.y)), (int) super.hitboxBoundsOffset.x, (int) super.hitboxBoundsOffset.y, gameCamera);

        // continue reinitalizing all necessary values
        spitHits = maxSpitHits;
        currAttackInList = 0;
        totalCurrHits = originalHits;
        radius = 0;
        angle = 0;
        spinFinished = false;
        shootPerframe = false;
        currTimeEnteredShoot = 0;
    }



    //sets the enemy position
    public void setEnemyInitialPosition(){
        super.position.x = (int) (SuperBirdGame.GAME_WIDTH + ((Math.random() * 5) * enemyWidth) + enemyWidth * 4); // random position outside of player view to right
        super.position.y = (int) ((Math.random()) * (SuperBirdGame.GAME_HEIGHT - enemyHeight)); // random pos between top and bottom of screen
    }



    // gets the position
    public Vector2 getMechaPos(){
        return super.position;
    }



    // disposes all data
    public void dispose(){
        super.dispose();
        attackPattern.clear();
        currDestination = null;
    }
}

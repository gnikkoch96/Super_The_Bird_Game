package com.procode.game.sprites;

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

    // holds all of the attacks for the bird to do, one after another
    private List<Integer> attackPattern; // list of what attacks to use
    private int currAttackInList;
    public Vector2 currDestination; // if the mecha bird needs to reach a destination, this is
    private int maxAttacksPerEnemy;


    // we want to pause an action before doing the next one
    public float timeActionPaused;
    public float pausedDuration;
    private Camera gameCamera;
    private int spitHits;
    private int maxSpitHits = 4;

    // keep track of spits that have already hit
    private List<BirdSpit> playerSpitsAlreadyHit;

    // when doing spinning attack, we want to keep track of the angle
    private int angle;
    private int radius;
    private boolean spinFinished;
    private Vector2 originalPos;
    private Vector2 playerPos;

    // sounds
    private float volume;
    private Sound deadSound;
    
    // time stuff for when the enemy shoots
    private float currTimeEnteredShoot;
    private float shootDuration;
    private boolean shootPerframe; // needs a toggle to know if a laser was already shot this frame
    public final Array<MechaLaser> activeShots = new Array<MechaLaser>(); // active spits is defined as in the screen and hasn't made contact with anything yet
    public final Pool<MechaLaser> shootPool;

    // amount of hits before destruction
    private int originalHits;
    private int totalCurrHits;
    private List<BirdSpit> ignoredSpits;


    public MechaBird(int mechaBWidth, int mechaBHeight, float speed, final Camera gameCamera){

        // super class of enemy that spawns outside of the screen view
        super(mechaBWidth, mechaBHeight, speed);
        pausedDuration = 1.5f;
        timeActionPaused = 0;
        spitHits = maxSpitHits;
        maxAttacksPerEnemy = 4;
        ignoredSpits =  new ArrayList<BirdSpit>();

        originalHits = 45;
        totalCurrHits = originalHits;

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
        shootDuration = 5f;
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

        // set the hitbox
        super.hitboxPosOffset = new Vector2(super.enemyWidth / 6, super.enemyHeight / 8 );
        super.hitboxBoundsOffset = new Vector2((int) (super.enemyWidth/ 1.5), ((int)(super.enemyHeight) - (super.enemyHeight/3)));
        super.hitbox = new Hitbox(new Vector2((int)(super.position.x + super.hitboxPosOffset.x), (int)(super.position.y + super.hitboxPosOffset.y)), (int) super.hitboxBoundsOffset.x, (int) super.hitboxBoundsOffset.y, gameCamera);

        // sounds
        deadSound = SuperBirdGame.manager.get("audio/sound/mecha_dead.wav", Sound.class);

        // now do the following: move the mecha bird until it reaches the screen visually, (is on the screen)
        // idle animation will play until this position is reached.
        setDestination();
    }



    // randomly chooses the attacks to do
    // will pick out random attacks until a point where the mecha bird does a dash
    public void setAttackPattern(){
        // to test only, the only attack is the dash

        boolean endingAttack = false;

        while (!endingAttack){
            int randomAttack = (int) (Math.random() * (3)); // a random attack that will be selected

            if (attackPattern.size() == maxAttacksPerEnemy - 1){
                attackPattern.add(0);
                endingAttack = true;
            }
            else {

                // 0 = dash, 1 = spin 2 = shoot (based on animations listed)
                if (randomAttack == 0) {
                    endingAttack = true;
                }
                attackPattern.add(randomAttack);
            }
        }

        currAttackInList = 0;
    }



    // sets the destination, depending on the current scenario.
    // if the bird is in the idle state,
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

                else if (attackPattern.get(currAttackInList) == 1){ // spin

                    if(angle == 0){
                        originalPos.x = super.position.x;
                        originalPos.y = super.position.y;
                    }

                    float radOffsetX = (float) (radius *  Math.cos(angle * Math.PI / 12));
                    float radOffsetY = (float) (radius * Math.sin(angle * Math.PI / 12));
                    currDestination.x = (int) ((originalPos.x - radius) + (radOffsetX));
                    currDestination.y = (int) ((originalPos.y) + (radOffsetY));

                    // angle needs to increment by 1 at a time and once reaches 360 as its new destination,
                    // reset to 0
                    if (angle < 25){
                        angle += 1;
                    }
                    else{
                        angle = 0;
                        spinFinished = true;
                    }
                }

                else if (attackPattern.get(currAttackInList) == 2){ // shoot

                    // current position is just the player position
                    currDestination.x = super.position.x;
                    currDestination.y = playerPos.y;
                }
        }
    }



    // updates the frames, state and position depending on the situation
    // need to pass in the player hitbox to see if there is a collision
    public void updateMechaBird(float deltaTime, Hitbox playerHitbox, Array<BirdSpit> playerSpits){

        super.update(deltaTime); // updates the frames

        System.out.println("currList: " + attackPattern + " currDes: " + currDestination + " pos: " + position + " currInd: " + currAttackInList);
        // now update the position and state depending on current state and other factors
        if (super.currentState == State.IDLE && currAttackInList == 0) {

            // checks to see if the position of the mecha bird has reached the destination
            if (Math.abs(super.position.x - currDestination.x) > super.enemySpeed) {
                updatePos();
            } else {

                // pause before changing to a new state
                if (timeActionPaused == 0) {
                    timeActionPaused = deltaTime;
                    pausedDuration = (float) Math.random() + .2f;
                }

                // stop for next movement for about 1 - 2 seconds
                if (timeActionPaused + pausedDuration < deltaTime) {

                    // sets the next attack to a destination to move to
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
        else if (super.currentState == State.ATTACK || currAttackInList > 0) {

            // stop for next movement before next attack in list
            if (timeActionPaused + pausedDuration < deltaTime) {

                // will do attack based on the animation completion and the position it is in
                if (attackPattern.get(currAttackInList) == 0) {

                    // make sure its already set
                    if ((super.currAttackState != 0 && super.currAttackState != 1)){
                        super.changeState(State.ATTACK, 0);
                        setDestination();
                    }

                    // resize hitbox to fit the attack
                    super.hitbox.resize((int) hitboxBoundsOffset.x, (int) (hitboxBoundsOffset.y / 2));

                    // need to first charge up until the animation is complete
                    // if the animation is complete then dash, also increase speed
                    if (super.currAttackState == 0 && super.enemyAttacks.get(super.currAttackState).isAnimFinished() == true) {
                        super.enemyAttacks.get(super.currAttackState).replayLoop();
                        super.changeState(State.ATTACK, 1);
                        super.setEnemySpeed(super.enemySpeed * 5);
                    }

                    // now dash to the end or until you hit the bird
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

                        if (Math.abs(super.position.x - currDestination.x) < super.enemySpeed || super.hitbox.isHit(playerHitbox) ||
                                playerHitbox.isHit(super.hitbox) || spitHits <= 0) {
                            super.changeState(State.DEAD, -1);
                            currAttackInList = 0;
                        }
                        else {
                            updatePos();
                        }

                    }
                }
                // for these cases, the mecha bird will follow a directed path
                // and move onto the next attack in the list
                else if (attackPattern.get(currAttackInList) == 1) {

                    // randomly set a radius every time
                    if(radius == 0){
                        radius = (int) (SuperBirdGame.GAME_WIDTH / 2.5 + (Math.random() * .75));
                    }

                    // make sure the attack state is set at least once
                    if ((super.currAttackState != 2 && super.currAttackState != 4 && super.currAttackState != 3)){
                        super.changeState(State.ATTACK, 2);
                        setDestination();
                    }

                    // then charge up until the animation is complete
                    if (super.currAttackState == 2 && super.enemyAttacks.get(super.currAttackState).isAnimFinished() == true) {
                        super.changeState(State.ATTACK, 3);
                        super.setEnemySpeed((float) (super.enemySpeed * 1.5)); // needs to spin fast
                    }

                    // will spin until it reaches its destination and when it does,
                    // change to the stopping animation until it is done
                    else if (super.currAttackState == 3){
                        if (!spinFinished){
                            if(Math.abs(super.position.x - currDestination.x) > super.enemySpeed && Math.abs(super.position.y - currDestination.y) > super.enemySpeed) {
                                updatePos();
                            }
                            else{
                                setDestination();
                            }
                        }
                        else{
                            spinFinished = false;
                            angle = 0;
                            super.currAttackState = 0;
                            super.changeState(State.ATTACK, 4);
                            super.setEnemySpeed((float) (super.enemySpeed / 1.5)); // needs to spin fast
                        }
                    }

                    // once the spinning stopped, wait for the next action in the list
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
                else if (attackPattern.get(currAttackInList) == 2) {

                    // updates the players position
                    playerPos.x = playerHitbox.position.x;
                    playerPos.y = playerHitbox.position.y;

                    // make sure the attack state is set at least once
                    if ((super.currAttackState != 5)){
                        super.changeState(State.ATTACK, 5);
                        setDestination();
                    }

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
        else if (super.currentState == State.DEAD) { // mecha bird dies
            deadSound.play();

            // resize hitbox to fit the attack
            super.hitbox.resize((int) (hitboxBoundsOffset.x * 1.5), (int) (hitboxBoundsOffset.y * 1.5));

            if (super.deadEnemy.isAnimFinished() == true) {
                playerSpitsAlreadyHit.clear();
                attackPattern.clear();
            }
        }

        // takes into account the current hits taken so far
        for (BirdSpit spits: playerSpits ){
            Hitbox currSpitHitbox = spits.getHitbox();
            if (currSpitHitbox.isHit(super.hitbox) || super.hitbox.isHit(currSpitHitbox)){
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
            if(laser.isOutOfScreen()){
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



    // spawns the enemy at the start
    // if the enemy has been disposed, aka: the enemy has dies, it will be placed offScreen again
    // needs to be in an update method to allow for time reset
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
        super.position.x = (int) (SuperBirdGame.GAME_WIDTH + ((Math.random() * 5) * enemyWidth) + enemyWidth * 4);
        super.position.y = (int) ((Math.random()) * (SuperBirdGame.GAME_HEIGHT - enemyHeight));
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

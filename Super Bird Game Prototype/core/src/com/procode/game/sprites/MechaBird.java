package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Enemy;

import java.util.ArrayList;
import java.util.List;

public class MechaBird extends Enemy {

    // holds all of the attacks for the bird to do, one after another
    private List<Integer> attackPattern; // list of what attacks to use
    private int currAttackInList;
    public Vector2 currDestination; // if the mecha bird needs to reach a destination, this is
    private Vector2 finalDestination; // used for cases where the mecha bird has a current destination to reach and another one after that

    // we want to pause an action before doing the next one
    public float timeActionPaused;
    public float pausedDuration;



    public MechaBird(int mechaBWidth, int mechaBHeight, float speed){

        // super class of enemy that spawns outside of the screen view
        super(mechaBWidth, mechaBHeight, speed);
        pausedDuration = 1.5f;
        timeActionPaused = 0;

        // create all the animations
        // note we do not need super for variables because they are protected, just thought
        // itd be easier to read if we did so
        super.idleEnemy = new Animation();
        super.idleEnemy.setAnimation("mecha bird animations//mecha bird idle ", super.enemyWidth, super.enemyHeight, 1, 3, .5f, true);

        // the mecha bird will not be able to be damaged but will die upon collision with the bird
        super.damagedEnemy = null;

        super.deadEnemy = new Animation();
        super.deadEnemy.setAnimation( "mecha bird animations//mecha bird dead ", super.enemyWidth, super.enemyHeight, 1, 4, .5f, false);

        currDestination = new Vector2();
        finalDestination = new Vector2();

        // add the attacking animations to the mecha bird
        // dashing attack animation
        Animation mechaBirdDashCharge = new Animation(); // happens when mecha bird charging up for the dash
        mechaBirdDashCharge.setAnimation("mecha bird animations//mecha bird dash ", super.enemyWidth, super.enemyHeight, 1, 6, .75f, false);
        Animation mechaBirdDashing = new Animation(); // happens when the mecha bird is charging at the bird
        mechaBirdDashing.setAnimation("mecha bird animations//mecha bird dash ", super.enemyWidth, super.enemyHeight, 7, 8, .25f, true);

        //spinning attack animation
        Animation mechaBirdSpinCharge = new Animation();
        mechaBirdSpinCharge.setAnimation("mecha bird animations//mecha bird spin ", super.enemyWidth, super.enemyHeight, 1, 3, .15f, false);
        Animation mechaBirdSpinning = new Animation();
        mechaBirdSpinning.setAnimation("mecha bird animations//mecha bird spin ", super.enemyWidth, super.enemyHeight, 4, 6, .1f, true);
        Animation mechaBirdSpinStop = new Animation();
        mechaBirdSpinStop.setAnimation("mecha bird animations//mecha bird spin ", super.enemyWidth, super.enemyHeight, 4, 8, .15f, false);

        // shooting animations
        Animation mechaBirdShoot = new Animation();
        mechaBirdShoot.setAnimation("mecha bird animations//mecha bird shoot ", super.enemyWidth, super.enemyHeight, 1, 5, .25f, false);

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

        // now do the following: move the mecha bird until it reaches the screen visually, (is on the screen)
        // idle animation will play until this position is reached.
        setDestination();
    }



    // randomly chooses the attacks to do
    // will pick out random attacks until a point where the mecha bird does a dash
    public void setAttackPattern(){
        // to test only, the only attack is the dash
        attackPattern.add(0);

//      -----------------------make attack path logic--------------------
//        boolean endingAttack = false;
//
//        while (!endingAttack){
//            int randomAttack = (int) (Math.random() * (super.numOfAttacks)); // a random attack that will be selected
//
//            // 0 = dash, 1 = spin 2 = shoot (based on animations listed)
//            if (randomAttack == 0){
//                endingAttack = true;
//            }
//            attackPattern.add(randomAttack);
//        }

        currAttackInList = 0;
    }



    // sets the destination, depending on the current scenario.
    // if the bird is in the idle state,
    public void setDestination(){
        if (super.currentState == State.IDLE) {

            // if the current state is idle, we simply need the destination to be on screen,
            // so the destination is the same y axis and a position in the screen
            // (will be randomized somewhat to allow for diversity)
            currDestination.y = super.position.y;
            currDestination.x = (int) (SuperBirdGame.GAME_WIDTH - ((Math.random() * super.enemyWidth * 1.5) + super.enemyWidth));
        }
        else if (super.currentState == State.ATTACK){
                // will search for the type of attack, if dash, to the end of the screen,
                // if spin, we use a function that will determine the next destination
                // if shoot, then move up and down for a set period of time
                if (attackPattern.get(currAttackInList) == 0){ // dash
                    currDestination.y = super.position.y;
                    currDestination.x = - super.enemyWidth;
                }

                else if (attackPattern.get(currAttackInList) == 1){ // spin
                    // spin
                    //      -----------------------make attack path logic--------------------

                }

                else if (attackPattern.get(currAttackInList) == 2){ // shoot
                    //shoot
                    //      -----------------------make attack path logic--------------------

                }
        }
    }



    // updates the frames, state and position depending on the situation
    public void updateMechaBird(float deltaTime){
        super.update(deltaTime); // updates the frames

        // now update the position and state depending on current state and other factors
        if (super.currentState == State.IDLE) {

            // checks to see if the position of the mecha bird has reached the destination
            if (super.position.x > currDestination.x) {
                updatePos();
            } else {

                // pause before changing to a new state
                if(timeActionPaused == 0) {
                    timeActionPaused = deltaTime;
                    pausedDuration = (float) Math.random() + .2f;
                }

                // stop for next movement for about 1 - 2 seconds
                if(timeActionPaused + pausedDuration < deltaTime) {

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
        else if (super.currentState == State.ATTACK) {

            // stop for next movement before next attack in list
            if(timeActionPaused + pausedDuration < deltaTime) {

                // will do attack based on the animation completion and the position it is in
                if (attackPattern.get(currAttackInList) == 0) {

                    // need to first charge up until the animation is complete
                    // if the animation is complete then dash, also increase speed
                    if (super.currAttackState == 0 && super.enemyAttacks.get(currAttackState).isAnimFinished() == true) {
                        super.changeState(State.ATTACK, 1);
                        super.setEnemySpeed(super.enemySpeed * 5);
                    }

                    // now dash to the end or until you hit the bird
                    else if (super.currAttackState == 1) {

                        if (super.position.x <= currDestination.x) {
                            super.changeState(State.DEAD, -1);
                        } else {
                            updatePos();
                        }

                    }
                }
                // for these cases, the mecha bird will follow a directed path
                // and move onto the next attack in the list
                else if (attackPattern.get(currAttackInList) == 1) {
                    //      -----------------------make attack path logic--------------------
                } else {
                    //      -----------------------make attack path logic--------------------

                }
            }
        }
        else if (super.currentState == State.DEAD){
                if (super.deadEnemy.isAnimFinished() == true){
                    attackPattern.clear();
                }
        }
    }



    // is called to update the position to the current destination
    public void updatePos(){

        // checks if the position is close enough to the destination to stop moving
        if (Math.abs(currDestination.x - super.position.x ) > .5){
            if (currDestination.x > super.position.x){
                position.x += super.enemySpeed;
            }
            else{
                position.x -= super.enemySpeed;
            }
        }
        if (Math.abs(currDestination.y - super.position.y ) > .5){
            if (currDestination.y > super.position.y){
                position.y += super.enemySpeed;
            }
            else{
                position.y -= super.enemySpeed;
            }
        }
    }



    // get the current image of the MechaBird
    public Texture getMechaBirdImage(){
        return super.getEnemyImage();
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
        for (int i = 0; i < super.enemyAttacks.size(); i++){
            enemyAttacks.get(i).replayLoop();
        }
    }



    //sets the enemy position
    public void setEnemyInitialPosition(){
        super.position.x = (int) (SuperBirdGame.GAME_WIDTH + ((Math.random() * 3) * enemyWidth));
        super.position.y = (int) ((Math.random()) * (SuperBirdGame.GAME_HEIGHT - enemyHeight * 2));
    }



    public Vector2 getMechaPos(){
        return super.position;
    }



    // disposes all data
    public void dispose(){
        super.dispose();
        attackPattern.clear();
        currDestination = null;
        finalDestination = null;
    }
}

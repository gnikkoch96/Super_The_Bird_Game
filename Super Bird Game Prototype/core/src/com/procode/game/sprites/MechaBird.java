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
    private Vector2 currDestination; // if the mecha bird needs to reach a destination, this is
    private Vector2 finalDestination; // used for cases where the mecha bird has a current destination to reach and another one after that
    public boolean isDisposed ; // turns true when the enemy is completely destroyed



    public MechaBird(int mechaBWidth, int mechaBHeight, float speed){

        // super class of enemy that spawns outside of the screen view
        super(mechaBWidth, mechaBHeight, speed);
        isDisposed = false;

        // create all the animations
        // note we do not need super for variables because they are protected, just thought
        // itd be easier to read if we did so
        super.idleEnemy = new Animation();
        super.idleEnemy.setAnimation("mecha bird animations//mecha bird idle ", super.enemyWidth, super.enemyHeight, 1, 3, .2f, true);

        // the mecha bird will not be able to be damaged but will die upon collision with the bird
        super.damagedEnemy = null;

        super.deadEnemy = new Animation();
        super.deadEnemy.setAnimation( "mecha bird animations//mecha bird dead ", super.enemyWidth, super.enemyHeight, 1, 4, .25f, false);

        currDestination = new Vector2();
        finalDestination = new Vector2();

        // add the attacking animations to the mecha bird
        // dashing attack animation
        Animation mechaBirdDashCharge = new Animation(); // happens when mecha bird charging up for the dash
        mechaBirdDashCharge.setAnimation("mecha bird animations//mecha bird dash ", super.enemyWidth, super.enemyHeight, 1, 6, .25f, false);
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

        // now do the following: move the mecha bird until it reaches the screen visually, (is on the screen)
        // idle animation will play until this position is reached.
        setDestination();

        // start the attacking patterns then once it leaves the screen, or touches the bird, destroy it. (in update func)
        // remaining done in the update function
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
        switch(super.currentState){
            case IDLE:

                // if the current state is idle, we simply need the destination to be on screen,
                // so the destination is the same y axis and a position in the screen
                // (will be randomized somewhat to allow for diversity)
                currDestination.y = super.position.y;
                currDestination.x = (int) (SuperBirdGame.ANDROID_WIDTH - ((Math.random() * super.enemyWidth) + super.enemyWidth));

            case ATTACK:
                // will search for the type of attack, if dash, to the end of the screen,
                // if spin, we use a function that will determine the next destination
                // if shoot, then move up and down for a set period of time
                if (attackPattern.get(currAttackInList) == 0){ // dash
                    currDestination.y = super.getEnemyPosition().y;
                    currDestination.x = - super.enemyWidth;
                }

                else if (attackPattern.get(currAttackInList) == 1){ // spin
                    // spin
                    //      -----------------------make attack path logic--------------------
                    break;
                }

                else if (attackPattern.get(currAttackInList) == 2){ // shoot
                    //shoot
                    //      -----------------------make attack path logic--------------------
                    break;
                }
        }
    }



    // updates the frames, state and position depending on the situation
    public void updateMechaBird(float deltaTime){
        super.update(deltaTime); // updates the frames

        // now update the position and state depending on current state and other factors
        switch(super.currentState){
            case IDLE:

                // checks to see if the position of the mecha bird has reached the destination
                if (super.position.x > currDestination.x){
                    //updatePos();
                    setDestination();
                }
                else{

                    // sets the next attack to a destination to move to
                    switch (attackPattern.get(0)){
                        case 0: // dash
                            super.changeState(State.ATTACK, 0);
                        case 1: //spin
                            super.changeState(State.ATTACK, 2);
                        case 2: //shoot
                            super.changeState(State.ATTACK, 5);
                    }
                }
            case ATTACK:

                // will do attack based on the animation completion and the position it is in
                if (currAttackInList != attackPattern.size()){
                    switch (currAttackInList){
                        case 0: // dash

                            // need to first charge up until the animation is complete
                            // if the animation is complete then dash, also increase speed
                            if (super.currAttackState == 0 && super.enemyAttacks.get(currAttackState).animationEnded == true){
                                super.changeState(State.ATTACK, 1);
                                super.setEnemySpeed(super.getEnemySpeed() * 2);
                            }

                            // now dash to the end or until you hit the bird
                            else if (super.currAttackState == 1){

                                if (super.position.x < currDestination.x){
                                    super.changeState(State.DEAD, -1);
                                }
                                else{
                                    //updatePos();
                                }

                            }

                        // for these cases, the mecha bird will follow a directed path
                        // and move onto the next attack in the list
                        case 1: //spin
                            //      -----------------------make attack path logic--------------------
                            break;
                        case 2: //shoot
                            //      -----------------------make attack path logic--------------------
                            break;
                    }
                }

            // if the animation is finished, destroy it
            case DEAD:
                if (super.deadEnemy.animationEnded == true){
                    isDisposed = true;
                }
        }
    }



    // is called to update the position to the current destination
    /*public void updatePos(){

        // checks if the position is close enough to the destination to stop moving
        if (Math.abs(currDestination.x - super.position.x ) > super.enemySpeed * 1.5){
            if (currDestination.x > super.position.x){
                position.x += super.enemySpeed;
            }
            else{
                position.x -= super.enemySpeed;
            }
        }
        if (Math.abs(currDestination.y - super.position.y ) > super.enemySpeed * 1.5){
            if (currDestination.y > super.position.y){
                position.y += super.enemySpeed;
            }
            else{
                position.y -= super.enemySpeed;
            }
        }
    }*/



    // get the current image of the MechaBird
    public Texture getMechaBirdImage(){
        return super.getEnemyImage();
    }



    // spawns the enemy at the start
    // if the enemy has been disposed, aka: the enemy has dies, it will be placed offScreen again
    public void spawn(){

        isDisposed = false;
        setEnemyInitialPosition();
        super.changeState(State.IDLE, -1);
    }



    //sets the enemy position
    public void setEnemyInitialPosition(){
        position.x = (int) (SuperBirdGame.ANDROID_WIDTH + ((Math.random() * 3) * enemyWidth));
        position.y = (int) (((Math.random() * 5) * SuperBirdGame.ANDROID_HEIGHT / 2) + 1);
    }



    public Vector2 getMechaPos(){
        return super.position;
    }



    // disposes all data
    public void dispose(){
        super.dispose();
        attackPattern.clear();
    }
}

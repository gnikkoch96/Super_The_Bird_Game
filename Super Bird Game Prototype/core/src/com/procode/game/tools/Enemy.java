package com.procode.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;

import java.util.ArrayList;
import java.util.List;

public class Enemy implements Disposable {


    public enum State {IDLE, ATTACK, DAMAGE, DEAD} // the states an enemy can be in
    protected List<Animation> enemyAttacks; // a list of attack animations the enemy will use (will cycle through them based on currAttackState as index)
    protected int numOfAttacks; // the total number of attacks an enemy has
    protected Animation idleEnemy;
    protected Animation damagedEnemy;
    protected Animation deadEnemy;
    protected Vector2 position;
    protected int enemyWidth;
    protected int enemyHeight;
    protected boolean isDead;

    protected State currentState; // the current state of the enemy
    protected int currAttackState; // what position in the enemyAttacks to use

    // how fast the enemy goes across the screen
    protected float enemySpeed; // the current speed
    protected float originalSpeed; // the original speed the enemy was initialized as

    // hitbox stuff
    public Hitbox hitbox;
    protected Vector2 hitboxPosOffset; // the position offset
    protected Vector2 hitboxBoundsOffset; // the hitbox bounds (box width/height) offset


    // constructor for the enemy class
    public Enemy(int width, int height, float speed){

        enemyWidth = width;
        enemyHeight = height;
        enemySpeed = speed;
        originalSpeed = speed;
        numOfAttacks = 0;
        currAttackState = 0;

        //sets an empty position, must be initialized in the child class
        position = new Vector2();

        currentState = State.IDLE;
        enemyAttacks = new ArrayList<Animation>();
    }



    // adds another attacking animation to the current list
    public void setAttackAnimation(Animation attackingAnim){
        enemyAttacks.add(attackingAnim);
        numOfAttacks += 1;
    }



    // updates the enemy animation
    public void update(float deltaTime){

        // depending on which state the enemy is in, we will update the specified frame of the current states animation
        // if it is an attacking state, it will be the specified attack in the enemyAttacks list that will be updated
        switch(currentState){
            case IDLE:
                idleEnemy.updateFrame(deltaTime);
                break;
            case ATTACK:
                enemyAttacks.get(currAttackState).updateFrame(deltaTime);
                break;
            case DAMAGE:

                break;
            case DEAD:
                deadEnemy.updateFrame(deltaTime);
                break;
        }
    }



    // gets the current image of the bird
    // depending on which state the enemy is in, we will return the current image of the state
    // if it is an attacking state, it will be the specified attack in the enemyAttacks list that will be returned
    public Texture getEnemyImage(){
        switch(currentState){
            case IDLE:
                return idleEnemy.getCurrImg();
            case ATTACK:
                return enemyAttacks.get(currAttackState).getCurrImg();
            case DAMAGE:
                return damagedEnemy.getCurrImg();
            case DEAD:
                return deadEnemy.getCurrImg();
        }
        return null; // should not return null
    }



    // what state to change to (idle, attack, damaged or dead)
    // if there exists an attack, place the index of the attack in the enemyAttacks list else place a -1
    public void changeState(State enemyState, int enemyAttackIndex){
        this.currentState = enemyState;
        if (enemyState == State.ATTACK){
            this.currAttackState = enemyAttackIndex;
        }
    }



    // gets the current state of the enemy
    public State getState(){
        return currentState;
    }



    //gets the position of the enemy
    public Vector2 getEnemyPosition(){
        return this.position;
    }



    // gets the speed of the enemy
    public float getEnemySpeed(){
        return enemySpeed;
    }



    // sets a new speed for the enemy
    public void setEnemySpeed(float speed){
        enemySpeed = speed;
    }



    //gets the width and height of the enemy
    public Vector2 getEnemySize(){
        return new Vector2(enemyWidth, enemyHeight);
    }



    @Override
    public void dispose() {
        enemyAttacks.clear();
        idleEnemy.dispose();
        damagedEnemy.dispose();
        deadEnemy.dispose();
        position = null;
        enemyWidth = 0;
        enemyHeight = 0;
    }
}

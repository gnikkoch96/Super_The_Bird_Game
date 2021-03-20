package com.procode.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;

import java.util.ArrayList;
import java.util.List;

public class Enemy implements Disposable {

    public enum State {IDLE, ATTACK, DAMAGE, DEAD}
    protected List<Animation> enemyAttacks;
    protected int numOfAttacks;
    protected Animation idleEnemy;
    protected Animation damagedEnemy;
    protected Animation deadEnemy;
    protected Vector2 position;
    protected int enemyWidth;
    protected int enemyHeight;

    protected State currentState; // the current state of the enemy
    protected int currAttackState = 0; // what position in the attack list to use

    // how fast the enemy goes across the screen
    protected float enemySpeed;



    // constructor for the enemy class when want a random position off screen
    public Enemy(int width, int height, float speed){

        enemyWidth = width;
        enemyHeight = height;
        enemySpeed = speed;
        numOfAttacks = 0;

        //sets a random position off screen for x and random position y
        position = new Vector2();

        currentState = State.IDLE;
        enemyAttacks = new ArrayList<Animation>();
    }



    // adds another attacking animation
    public void setAttackAnimation(Animation attackingAnim){
        enemyAttacks.add(attackingAnim);
        numOfAttacks += 1;
    }



    // updates the enemy animation
    public void update(float deltaTime){
        switch(currentState){
            case IDLE:
                idleEnemy.updateFrame(deltaTime);
            case ATTACK:
                enemyAttacks.get(currAttackState).updateFrame(deltaTime);
            case DEAD:
                deadEnemy.updateFrame(deltaTime);
        }
    }



    // gets the current image of the bird
    public Texture getEnemyImage(){
        switch(currentState){
            case IDLE:
                return idleEnemy.getCurrImg();
            case ATTACK:
                return enemyAttacks.get(currAttackState).getCurrImg();
            case DEAD:
                return deadEnemy.getCurrImg();
        }
        return null;
    }



    // what state to change to (idle, attack, damaged or dead)
    // if there exists an attack, it will also place it, otherwise it should be -1
    public void changeState(State enemyState, int enemyAttack){
        currentState = enemyState;
        if (currentState == State.ATTACK){
            currAttackState = enemyAttack;
        }
    }



    // gets the current state of the enemy
    public State getState(){
        return currentState;
    }



    //gets the position of the bird
    public Vector2 getEnemyPosition(){
        return this.position;
    }



    public float getEnemySpeed(){
        return enemySpeed;
    }



    public void setEnemySpeed(float speed){
        enemySpeed = speed;
    }



    //gets the width and height of the bird
    public Vector2 getEnemySize(){
        return new Vector2(enemyWidth, enemyHeight);
    }



    // returns true when an enemy is on screen
    // and has not passed the bird
    public boolean onScreen(){
        if (((position.x + enemyWidth) > 0) &&
                ((position.y + enemyHeight) < SuperBirdGame.ANDROID_HEIGHT) &&
                ((position.y + enemyHeight) > 0)){
            return true;
        }
        return false;
    }



    @Override
    public void dispose() {
        enemyAttacks = null;
        idleEnemy = null;
        damagedEnemy = null;
        deadEnemy = null;
        position = null;
        enemyWidth = 0;
        enemyHeight = 0;
    }
}

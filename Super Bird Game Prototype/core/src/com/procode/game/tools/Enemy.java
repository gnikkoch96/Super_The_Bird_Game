package com.procode.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;

public class Enemy implements Disposable {

    protected Vector2 position;
    protected int enemyWidth;
    protected int enemyHeight;

    protected Animation enemyAnimation;

    // how fast the enemy goes across the screen
    protected float enemySpeed;

    // constructor for the enemy class when given a set position
    public Enemy(int x, int y, int width, int height, float speed){
        position.x = x;
        position.y = y;
        enemyWidth = width;
        enemyHeight = height;
        enemySpeed = speed;
        enemyAnimation = new Animation();
    }

    // constructor for the enemy class when want a random position
    public Enemy(int width, int height, float speed){

        enemyWidth = width;
        enemyHeight = height;
        enemySpeed = speed;
        enemyAnimation = new Animation();

        //sets a random position off screen for x and random position y
        position.y = (int) (Math.random() * SuperBirdGame.ANDROID_HEIGHT);
        position.x = (int) (SuperBirdGame.ANDROID_WIDTH + ((Math.random() * 5) * enemyWidth));
    }

    // updates the enemy animation and position every frame
    public void update(float deltaTime){
        enemyAnimation.updateFrame(deltaTime);
    }

    public void setEnemyAnimation(String animation, int width, int height, int startingFrame, int endingFrame, float animationSec){
        enemyAnimation.setAnimation(animation, width, height, startingFrame, endingFrame, animationSec);
    }

    // gets the current image of the bird
    public Texture getEnemyImage(){
        return enemyAnimation.getCurrImg();
    }

    //gets the position of the bird
    public Vector2 getEnemyPosition(){
        return this.position;
    }

    //sets the enemy position
    public void setEnemyPosition(int newX, int newY){
        position.x = newX;
        position.y = newY;
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
    public boolean isOnScreen(){
        if (((position.x + enemyWidth) < SuperBirdGame.ANDROID_WIDTH) &&
                ((position.x + enemyWidth) > 0) &&
                ((position.y + enemyHeight) < SuperBirdGame.ANDROID_HEIGHT) &&
                ((position.y + enemyHeight) > 0)){
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        enemyAnimation.dispose();
    }
}

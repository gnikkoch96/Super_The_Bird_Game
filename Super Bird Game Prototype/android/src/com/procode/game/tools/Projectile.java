package com.procode.game.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Hitbox;

public class Projectile implements Pool.Poolable {
    //--Note: You have to create the projectileImage/animation in the specific projectile class and set it to static so that we can save memory usage

    protected int projectileWidth;
    protected int projectileHeight;
    protected float velocity; // only apply on x-axis

    // collision detection
    public Hitbox hitbox;
    protected Vector2 position;
    protected boolean alive;  // checks to see if the bullet is still on screen or hasn't hit an object
    protected boolean collided; // used to play the finishing animation of the spit (Nikko: Might remove this as we can just use the isHit() from Hitbox.class)

    public void setCollision(boolean val){
        this.collided = val;
    }
    public boolean isCollided(){return this.collided;}

    // getters and setters
    public int getProjectileWidth(){return this.projectileWidth;}
    public int getProjectileHeight(){return this.projectileHeight;}
    public Vector2 getPosition() {
        return this.position;
    }
    public boolean isAlive() {
        return this.alive;
    }

    public boolean isOutOfScreen(){
        if(this.position.x >= SuperBirdGame.GAME_WIDTH)
            return true;
        else
            return false;
    }

    public Hitbox getHitbox(){ return this.hitbox;}

    /**
     * Callback method when the object is freed. It is automatically called by Pool.free()
     * Must reset every meaningful field of this bullet.
     */
    @Override
    public void reset() {
        this.alive = false;
        this.collided = false;
    }

}

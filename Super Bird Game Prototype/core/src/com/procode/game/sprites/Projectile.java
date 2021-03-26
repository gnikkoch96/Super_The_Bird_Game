package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Hitbox;

public class Projectile implements Pool.Poolable {
    protected int projectileWidth;
    protected int projectileHeight;
    protected float velocity; // only apply on x-axis

    // collision detection
    protected Hitbox hitbox;
    protected Vector2 position;
    protected boolean alive;  // checks to see if the bullet is still on screen or hasn't hit an object
    protected boolean collided; // used to play the finishing animation of the spit (Nikko: Might remove this as we can just use the isHit() from Hitbox.class)

    // single lined methods
    public void madeContact(){
        collided = true;
    }

    // getters and setters
    public Vector2 getPosition() {
        return this.position;
    }
    public boolean isAlive() {
        return this.alive;
    }

    public boolean isOutOfScreen(){
        if(this.position.x >= SuperBirdGame.ANDROID_WIDTH)
            return true;
        else
            return false;
    }

    /**
     * Callback method when the object is freed. It is automatically called by Pool.free()
     * Must reset every meaningful field of this bullet.
     */
    @Override
    public void reset() {
        this.position.set(0,0);
        this.alive = false;
    }

}

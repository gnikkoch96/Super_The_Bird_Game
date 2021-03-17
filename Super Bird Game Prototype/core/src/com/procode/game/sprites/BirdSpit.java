package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Hitbox;

public class BirdSpit implements Pool.Poolable {
    // static variables
    private static int spitHeight = 200;
    private static int spitWidth = 200;

    // collision detection stuff
    private Hitbox hitbox;
    private Vector2 position;
    private float velocity; // only apply on x-axis
    private boolean alive; // checks to see if the bullet is still on screen or hasn't hit an object

    // animation related
    private Texture spitImage; // this will be the image that floats until a contact has been made
    private Animation collisionAnimation; // the animation will be when the spit animation is done since we want the spit to stay in its first form until a contact is made
    private boolean collided; // used to play the finishing animation of the spit

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

    public BirdSpit(){
        position = new Vector2(); // x and y should be the bird mouth's location
        velocity = 0.1f; //--Nikko: change speed if necessary
        collided = false;

        //--Nikko: To save memory from creating a new texture and animation for each new spit
        if(spitImage != null) spitImage = new Texture("bird animations//spit projectile 1.png");
        if(collisionAnimation != null){
            collisionAnimation = new Animation();
            collisionAnimation.setAnimation("bird animations//idle bird ", 100, 100, 2, 3, .25f, true);
        }

        hitbox = new Hitbox(this.position, this.spitWidth, this.spitHeight);

    }

    public void init(float x, float y){ // sets the spit
        this.position.set(x, y);
        this.alive = true;
    }

    public void update(float dt){
        // update spit position
        this.position.x += velocity;

        if(isOutOfScreen())
            alive = false;

//        if(collided) { // play the ending of spit animation once it has been set to destroyed
//            if(spitAnimationFinished.getCurrFrameIndex() <= 2)
//                spitAnimationFinished.updateFrame(dt);
//            else {
//                collided = false;
//                destroyed = true;
//                spitAnimationFinished.dispose(); //--Nikko: Not sure how this will work, will need to test it out--//
//            }
//        }else{ // play the spit texture
//
//        }
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(spitImage, this.position.x, this.position.y);
        batch.end();
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

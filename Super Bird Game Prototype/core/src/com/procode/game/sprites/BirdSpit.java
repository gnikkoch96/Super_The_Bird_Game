package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.procode.game.screens.PlayScreen;
import com.procode.game.tools.Animation;

public class BirdSpit extends Sprite {
    // static variables
    private static int spitHeight;
    private static int spitWidth;

    // collision detection stuff
    private Rectangle spitBounds;

    // animation related
    private Texture spitImage; // this will be the image that floats until a contact has been made
    private Animation spitAnimationFinished; // the animation will be when the spit animation is done since we want the spit to stay in its first form until a contact is made
    private boolean destroyed;// used to destroy
    private boolean collided; // used to play the finishing animation of the spit

    private float stateTime;

    public BirdSpit(PlayScreen screen, float x, float y){
        collided = false;
        destroyed = false;
        spitImage = new Texture("bird animations// spit projectile 1");
        spitAnimationFinished.setAnimation("bird animations//idle bird ", 100, 100, 2, 3, .25f, true);

    }

    public void madeContact(){
        collided = true;
    }

    public void update(float dt){
        stateTime += dt;

        if(collided) { // play the ending of spit animation once it has been set to destroyed
            if(spitAnimationFinished.getCurrFrameIndex() <= 2)
                spitAnimationFinished.updateFrame(dt);
            else {
                collided = false;
                destroyed = true;
                spitAnimationFinished.dispose(); //--Nikko: Not sure how this will work, will need to test it out--//
            }
        }else{ // play the spit texture

        }
    }

    public boolean isDestroyed(){
        return this.destroyed;
    }
}

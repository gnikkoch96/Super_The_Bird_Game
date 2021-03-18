package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Hitbox;
import com.procode.game.tools.ImageFunctions;

public class BirdSpit extends Projectile {
    // animations
    private static Texture projectileImage; // this will be the image that floats until a contact has been made
    private static Animation collisionAnimation; // the animation will be when the spit animation is done since we want the spit to stay in its first form until a contact is made

    public BirdSpit(){
        //--Nikko: The width and height can be changed
        this.projectileWidth = 100;
        this.projectileHeight = 80;

        this.position = new Vector2(); // x and y are initialized in the init()
        this.velocity = 20; //--Nikko: try changing speed
        this.collided = false;
        this.alive = false;

        //--Nikko: To save memory from creating a new texture and animation for each new spit
        if(BirdSpit.projectileImage == null) BirdSpit.projectileImage = ImageFunctions.resize("bird animations//spit projectile 1.png", this.projectileWidth, this.projectileHeight);
        if(BirdSpit.collisionAnimation == null){
            BirdSpit.collisionAnimation = new Animation();
            BirdSpit.collisionAnimation.setAnimation("bird animations//spit projectile ", 100, 100, 2, 3, .25f, true);
        }

        hitbox = new Hitbox(this.position, this.projectileWidth, this.projectileHeight);
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
        // doesn't need to begin or end batch as this code will be called while the spritebatch of PlayScreen.class already started the begin()
        batch.draw(projectileImage, this.position.x + (Bird.getBirdWidth()/2), this.position.y + (Bird.getBirdHeight()/3));
    }


}

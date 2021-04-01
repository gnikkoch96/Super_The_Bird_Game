package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Hitbox;
import com.procode.game.tools.ImageFunctions;

public class BirdSpit extends Projectile implements Disposable {
    // animations
    private static Texture projectileImage; // this will be the image that floats until a contact has been made
    private static Animation collisionAnimation; // the animation will be when the spit animation is done since we want the spit to stay in its first form until a contact is made

    public BirdSpit(){
        //--Nikko: The width and height can be changed
        this.projectileWidth = 100;
        this.projectileHeight = 30;

        this.position = new Vector2(); // x and y are initialized in the init()
        this.velocity = 20; //--Nikko: try changing speed
        this.collided = false;
        this.alive = false;

        //--Nikko: To save memory from creating a new texture and animation for each new spit
        if(BirdSpit.projectileImage == null) BirdSpit.projectileImage = ImageFunctions.resize("bird animations//spit projectile 1.png", this.projectileWidth, this.projectileHeight);
        if(BirdSpit.collisionAnimation == null){
            BirdSpit.collisionAnimation = new Animation();
            BirdSpit.collisionAnimation.setAnimation("bird animations//spit projectile ", 100, 100, 2, 3, .25f, false);
        }

        this.hitbox = new Hitbox(this.position, this.projectileWidth, this.projectileHeight);
    }

    // sets the spit initial values
    public void init(float x, float y){
        this.position.set(x, y);
        this.alive = true;
    }

    public void update(float dt) {
        //--NIKKO: placed it here because I didn't want it to keep calculating the position and hitbox after the spit leaves the screen
        if (isOutOfScreen()) { // removes the spit if it exits the screen
            this.reset();
        } else {
            if (this.isCollided()) { // collision detected
                collisionAnimation.updateFrame(dt);
            } else {
                // update spit position
                this.position.x += this.velocity;

                // update hitbox location
                hitbox.update(this.position);

                if (isOutOfScreen()) // removes the spit if it exits the screen
                    this.alive = false;
            }
        }

        Gdx.app.log("Hitbox " + String.valueOf(this.getClass()), "\nbotleft: (" + this.getHitbox().botleft.x + ", " + this.getHitbox().botleft.y + ")\n"
                + "botright: (" + this.getHitbox().botright.x + ", " + this.getHitbox().botright.y + ")\n"
                + "topleft: (" + this.getHitbox().topleft.x + ", " + this.getHitbox().topleft.y + ")\n"
                + "topright: (" + this.getHitbox().topright.x + ", " + this.getHitbox().topright.y + ")\n");

    }

    public void render(SpriteBatch batch){
        //--Nikko: doesn't need to begin or end batch as this code will be called while the spritebatch of PlayScreen.class already started the begin()

        if(!collided){
//            Gdx.app.log("Hitbox (BotLeft)", "( " + Float.toString(this.getHitbox().botleft.x) + ", " + Float.toString(this.getHitbox().position.y) + ")");
            batch.draw(projectileImage, this.position.x, this.position.y);
//            Gdx.app.log("Spit Location", "( " + Float.toString(this.position.x) + ", " + Float.toString(this.position.y) + ")");
//            batch.draw(projectileImage, this.position.x, this.position.y - this.projectileHeight/2); //Nikko: switch to this when images have been adjusted
        }else if(!collided){
            batch.draw(collisionAnimation.getCurrImg(), this.position.x, this.position.y);
        }
//        if(this.collided){
//            batch.draw(collisionAnimation.getCurrImg(), this.position.x, this.position.y);
//        }else if(!collided){
//            batch.draw(projectileImage, this.position.x + (Bird.getBirdWidth()/2), this.position.y + (Bird.getBirdHeight()/3));
//        }
    }


    @Override
    public void dispose() {
        projectileImage.dispose();
        collisionAnimation.dispose();
    }
}

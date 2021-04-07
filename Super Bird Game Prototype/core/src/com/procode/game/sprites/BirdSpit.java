package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Hitbox;
import com.procode.game.tools.ImageFunctions;
import com.procode.game.tools.Projectile;

public class BirdSpit extends Projectile implements Disposable {
    // animations
    private static Texture projectileImage; // this will be the image that floats until a contact has been made
    private Animation collisionAnimation; // the animation will be when the spit animation is done since we want the spit to stay in its first form until a contact is made
    public static ParticleEffect collisionParticle;

    public BirdSpit(Camera gameCamera){
        //--Nikko: The width and height can be changed
        this.projectileWidth = SuperBirdGame.GAME_WIDTH / 15;
        this.projectileHeight = SuperBirdGame.GAME_HEIGHT / 35;

        this.position = new Vector2(); // x and y are initialized in the init()
        this.velocity = 20; //--Nikko: try changing speed
        this.collided = false;
        this.alive = false;

        //--Nikko: To save memory from creating a new texture and animation for each new spit
        if(BirdSpit.projectileImage == null) BirdSpit.projectileImage = ImageFunctions.resize("bird animations//spit projectile 1.png", this.projectileWidth, this.projectileHeight);
//        if(BirdSpit.collisionAnimation == null){
//            BirdSpit.collisionAnimation = new Animation();
//            BirdSpit.collisionAnimation.setAnimation("bird animations//spit projectile ", 100, 100, 2, 3, 1f, false);
//        }

        if(BirdSpit.collisionParticle == null){
            collisionParticle = new ParticleEffect();
            collisionParticle.load(Gdx.files.internal("effects/liquid.p"), Gdx.files.internal("bird animations"));


        }

        this.hitbox = new Hitbox(this.position, this.projectileWidth, this.projectileHeight, gameCamera);
        collisionAnimation = new Animation();
        collisionAnimation.setAnimation("bird animations//spit projectile ", 100, 100, 1, 3, 1f, false);

        deltaTime = 0;
    }

    public Animation getCollisionAnimation() {
        return collisionAnimation;
    }

    // sets the spit initial values
    public void init(float x, float y){
        this.position.set(x, y);
        this.alive = true;
    }

    //--TEST--/
    private float deltaTime;
    public void update(float dt) {
        deltaTime = dt;
        if (isOutOfScreen()) { // removes the spit if it exits the screen
            this.alive = false;
        }else {
            Gdx.app.log("Collision Status:", String.valueOf(this.isCollided()));

            //--TEST--//
            if (this.isCollided()) { // collision detected
//                collisionAnimation.updateFrame(dt);
//                collisionParticle.update(dt);
            }else {
                // update spit position
                this.position.x += this.velocity;

                // update hitbox location
                hitbox.update(this.position);


            }
        }

//        Gdx.app.log("Hitbox " + String.valueOf(this.getClass()), "\nbotleft: (" + this.getHitbox().botleft.x + ", " + this.getHitbox().botleft.y + ")\n"
//                + "botright: (" + this.getHitbox().botright.x + ", " + this.getHitbox().botright.y + ")\n"
//                + "topleft: (" + this.getHitbox().topleft.x + ", " + this.getHitbox().topleft.y + ")\n"
//                + "topright: (" + this.getHitbox().topright.x + ", " + this.getHitbox().topright.y + ")\n");

    }

    public void render(SpriteBatch batch){
        //--Nikko: doesn't need to begin or end batch as this code will be called while the spritebatch of PlayScreen.class already started the begin()


        if(this.isCollided()){ // collided with an object
//            batch.draw(collisionAnimation.getCurrImg(), this.position.x, this.position.y);
            collisionParticle.setPosition(this.position.x, this.position.y);
            collisionParticle.start();

        }else{
            batch.draw(projectileImage, this.position.x, this.position.y);
        }


    }


    @Override
    public void dispose() {
        projectileImage.dispose();
        collisionAnimation.dispose();
    }
}

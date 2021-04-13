package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Hitbox;
import com.procode.game.tools.ImageFunctions;
import com.procode.game.tools.Projectile;

public class MechaLaser extends Projectile implements Disposable {

    // animations
    private static Texture projectileImage; // this will be the image that floats until a contact has been made

    public MechaLaser(Camera gameCamera){
        //--Nikko: The width and height can be changed
        this.projectileWidth = SuperBirdGame.GAME_WIDTH / 10;
        this.projectileHeight = SuperBirdGame.GAME_HEIGHT / 25;
        this.position = new Vector2(); // x and y are initialized in the init()
        this.velocity = -20; //--Nikko: try changing speed (to fix the bird catching up to spit, make this equation include the speed of the bird)
        this.collided = false;
        this.alive = false;

        //--Nikko: To save memory from creating a new texture and animation for each new spit
        if(MechaLaser.projectileImage == null) MechaLaser.projectileImage = ImageFunctions.resize("mecha bird animations//mecha bird projectile 1.png", this.projectileWidth, this.projectileHeight);

        this.hitbox = new Hitbox(this.position, this.projectileWidth, this.projectileHeight, gameCamera);
    }

    // sets the spit initial values
    public void init(float x, float y){
        this.position.set(x, y);
        this.alive = true;
    }

    public void update(float dt) {
        if (isOutOfScreen()) { // removes the spit if it exits the screen
            this.alive = false;
        }else {
            if (!this.isCollided()) {
                // update spit position
                this.position.x += this.velocity;

                // update hitbox location
                hitbox.update(this.position);
            }
        }
    }

    public void render(SpriteBatch batch){
        //--Nikko: doesn't need to begin or end batch as this code will be called while the spritebatch of PlayScreen.class already started the begin()
        if(!this.isCollided()){ // collided with an object
            batch.draw(projectileImage, this.position.x, this.position.y);
        }
    }


    @Override
    public void dispose() {
        projectileImage.dispose();

    }
}

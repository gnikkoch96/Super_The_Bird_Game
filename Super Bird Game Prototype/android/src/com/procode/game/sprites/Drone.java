package com.procode.game.sprites;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.Animation;
import com.procode.game.tools.Enemy;
import com.procode.game.tools.Hitbox;

import java.util.ArrayList;
import java.util.List;

public class Drone extends Enemy {

    public Vector2 currDestination;
    private boolean hasBeenSet;
    private boolean hasBeenHit;

    private Camera gameCamera;
    public float explosionMultiplier;
    private boolean hasExploded;
    private Vector2 playerPos;

    private int spitHits; // current amount of hits by spit
    private int maxSpitHits; // 3 hits will destroy the enemy
    private List<BirdSpit> playerSpitsAlreadyHit; // number of spits that have already hit



    // initializing the mecha bird
    public Drone(int mechaBWidth, int mechaBHeight, float speed, final Camera gameCamera) {
        super(mechaBWidth, mechaBHeight, speed);

        this.gameCamera = gameCamera;
        this.isHit = false;
        this.pointValue = 50; // 50 points

        currDestination = new Vector2();
        hasBeenHit = false;
        hasBeenSet = false;
        hasExploded = false;
        maxSpitHits = 5;
        spitHits = maxSpitHits;
        playerSpitsAlreadyHit = new ArrayList<BirdSpit>();
        explosionMultiplier = 1.5f;
        playerPos = new Vector2();

        // animations
        super.idleEnemy = new Animation();
        super.idleEnemy.setAnimation("drone animations//drone dash ", super.enemyWidth, super.enemyHeight, 1, 3, .15f, true);
        super.deadEnemy = new Animation();
        super.deadEnemy.setAnimation("drone animations//drone explode ", super.enemyWidth, super.enemyHeight, 1, 4, .25f, false);



        // set initial positions
        setEnemyInitialPosition();

        // set the hitbox with its offset
        super.hitboxPosOffset = new Vector2(super.enemyWidth / 10, super.enemyHeight / 8 );
        super.hitboxBoundsOffset = new Vector2((int) (super.enemyWidth/ 1.2), ((int)(super.enemyHeight) - (super.enemyHeight/3)));
        super.hitbox = new Hitbox(new Vector2((int)(super.position.x + super.hitboxPosOffset.x), (int)(super.position.y + super.hitboxPosOffset.y)), (int) super.hitboxBoundsOffset.x, (int) super.hitboxBoundsOffset.y, gameCamera);

        setDestination();
    }

    public boolean getIsHit(){return this.hasBeenHit;}

    public void setDestination(){

        // if was hit, will go to do a large explode in a random position on the screen
        if(hasBeenHit){
            currDestination.y = playerPos.y; // random pos between top and bottom of screen
            currDestination.x = playerPos.x + (super.enemyWidth * explosionMultiplier /2);
        }

        else {

            // will just go to the destination until
            currDestination.y = (int) super.position.y;
            currDestination.x = (-1 * (3 * super.enemyWidth));
        }
    }


    // will update the frames and everything else
    public void updateDrone(float deltaTime, Array<BirdSpit> playerSpits, Hitbox playerHitbox, HUD hud){
        super.update(deltaTime); // updates the frames of the current animation

        // checks to see if the player was hit at least once
        // loop through the array of active spits to see if there is a collision
        // if so decrement the amount of hits by 1
        // if it reaches 0 or less, then destroy the enemy
        for(int i = 0; i < playerSpits.size; i++){
            BirdSpit currSpit = playerSpits.get(i);
            Hitbox currSpitBox = currSpit.hitbox;
            if ((super.hitbox.isHit(currSpitBox) || currSpitBox.isHit(super.hitbox)) && !playerSpitsAlreadyHit.contains(currSpit) ){
                playerSpitsAlreadyHit.add(currSpit);
                spitHits -= 1;
            }
        }

        // while idle, the drone will move to the end of the screen and exlpode,
        // if it is hit at least once, it will go to a random position on the screen and the
        // explosion multiplier will increase by 2, making a huge explosion, also will increase the speed by 2
        if (super.currentState == State.IDLE){
            if (spitHits < maxSpitHits){
                hasBeenHit = true;

                // needs to enter this only once
                if(!hasBeenSet) {
                    playerPos.x = playerHitbox.position.x;
                    playerPos.y = playerHitbox.position.y;
                    setDestination();
                    hasBeenSet = true;
                    super.setEnemySpeed((int) (super.enemySpeed * 3.5));
                }
            }

            // if the player has reached the destination, dead state and if was already hit
            // explosion multiplier will increase by 2, making a huge explosion,
            // unless it is hit 3 times before that, which will result in it being destroyed
            if (spitHits <= 0){
                super.changeState(State.DEAD, -1);
            }
            else{

                // will keep on moving until the destination is reached, and if was hit before will make a
                // big explosion
                if (Math.abs(super.position.x - currDestination.x) < super.enemySpeed && Math.abs(super.position.y - currDestination.y) < super.enemySpeed){
                    if(hasBeenHit){
                        explosionMultiplier = 2.75f;
                    }
                    else {
                        explosionMultiplier = 1.5f;
                    }
                    super.changeState(State.DEAD, -1);
                    this.isDead = true;

                }
                else{
                    updatePos();
                }
            }
        }

        else if ( super.currentState == State.DEAD){
            if(!hasExploded && hasBeenHit) {
                // resize hitbox to a larger size for the explosion effect
                if(spitHits == 0) {
                    hud.updatePoints(pointValue); // only points added when player kills the drone
                    super.hitbox.resize((int) (hitboxBoundsOffset.x * explosionMultiplier * .25), (int) (hitboxBoundsOffset.y * explosionMultiplier * .3));
                }
                else {
                    super.hitbox.resize((int) (hitboxBoundsOffset.x * explosionMultiplier * .35), (int) (hitboxBoundsOffset.y * explosionMultiplier * .35));
                }
                super.enemyWidth = (int) (super.enemyWidth * explosionMultiplier);
                super.enemyHeight = (int) (super.enemyHeight * explosionMultiplier);
                super.position.x = super.position.x - (super.enemyWidth / 2);
                super.position.y = super.position.y - (super.enemyHeight/ 2);
                updateHitboxPos();
                hasExploded = true;
            }

            // clear the spits that have already hit
            if (super.deadEnemy.isAnimFinished() == true) {
                playerSpitsAlreadyHit.clear();
            }
        }
    }



    // updates the hitbox pos
    public void updateHitboxPos() {
        // update the hitbox position if it isnt null
        if (super.hitbox != null) {
            super.hitbox.update(new Vector2((int) (super.position.x + super.hitboxPosOffset.x), (int) (super.position.y + super.hitboxPosOffset.y)));
        }
    }



    // is called to update the position to the current destination
    // also updates the position of the hitbox
    public void updatePos(){

        // checks if the position is close enough to the destination to stop moving
        if (Math.abs(currDestination.x - super.position.x ) > super.enemySpeed / 2){
            if (currDestination.x > super.position.x){
                position.x += (int) super.enemySpeed;
            }
            else{
                position.x -= (int) super.enemySpeed;
            }
        }

        if (Math.abs(currDestination.y - super.position.y ) > super.enemySpeed / 2){
            if (currDestination.y > super.position.y){
                position.y += (int) super.enemySpeed;
            }
            else{
                position.y -= (int) super.enemySpeed;
            }
        }
        updateHitboxPos();
    }



    //sets the enemy position
    public void setEnemyInitialPosition(){
        super.position.x = (int) (SuperBirdGame.GAME_WIDTH + ((Math.random() * 5) * enemyWidth) + enemyWidth * 4); // random position outside of player view to right
        super.position.y = (int) ((Math.random()) * (SuperBirdGame.GAME_HEIGHT - enemyHeight)); // random pos between top and bottom of screen
    }

    public void respawn() {

        hasBeenHit = false;
        hasBeenSet = false;
        hasExploded = false;
        spitHits = maxSpitHits;

        super.enemyHeight = super.enemyOriginalHeight;
        super.enemyWidth = super.enemyOriginalWidth;
        explosionMultiplier = 1.5f;

        super.deadEnemy.replayLoop();
        super.setEnemySpeed(super.originalSpeed);
        super.changeState(State.IDLE, -1);

        setEnemyInitialPosition();
        setDestination();

        // update the hitbox pos by replacing it
        super.hitbox = new Hitbox(new Vector2((int)(super.position.x + super.hitboxPosOffset.x), (int)(super.position.y + super.hitboxPosOffset.y)), (int) super.hitboxBoundsOffset.x, (int) super.hitboxBoundsOffset.y, gameCamera);
    }
}

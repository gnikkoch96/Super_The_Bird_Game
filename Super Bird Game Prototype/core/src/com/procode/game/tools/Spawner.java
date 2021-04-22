package com.procode.game.tools;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Array;
import com.procode.game.SuperBirdGame;
import com.procode.game.sprites.Bird;
import com.procode.game.sprites.BirdSpit;
import com.procode.game.sprites.Drone;
import com.procode.game.sprites.MechaBird;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Spawner {

    // this will have 2 arrays, one with enabled enemies and one with disabled ones
    // we will swap enemies onto and off of the list as needed
    public List<Enemy> activeEnemies;
    private List<Enemy> inactiveEnemies;

    // variables that control how many enemies on the screen,
    // rate of spawning and enemy speed
    private int maxEnemies;
    private int minEnemies;

    private float enemyMaxSpeed;
    private float enemyMinSpeed;
    private float lastEnemySpawnTime; // the time the most recent enemy spawned
    private float newSpawnPerS; // the percentage for a new enemy spawning per second (needs to be between 0 and 1)
    private float currSpawnPerS; // the current spawn percentage (increases over time when a new enemy isn't spawned)
    private float spawnFrequency;
    private Camera gamecam;

    private float lastTimeIncreaseDifficulty;
    private boolean changeEnemyAttacks;
    private float addedDifficultyTime = 25;



    // will initialize variables and also initialize enemies depending on the maxEnemies,
    // will initialize that many enemies per enemy variant
    public Spawner(int maxEnemies, int minEnemies, float enemyMaxSpeed, float enemyMinSpeed, float newSpawnPerS, float spawnFrequency, Camera gameCam){
        this.maxEnemies = maxEnemies;
        this.minEnemies = minEnemies;
        this.enemyMaxSpeed = enemyMaxSpeed;
        this.enemyMinSpeed = enemyMinSpeed;
        this.newSpawnPerS = newSpawnPerS;
        this.currSpawnPerS = newSpawnPerS;
        this.lastEnemySpawnTime = 0;
        this.spawnFrequency = spawnFrequency;
        activeEnemies = new ArrayList<Enemy>();
        inactiveEnemies = new ArrayList<Enemy>();
        this.gamecam = gameCam;
        lastTimeIncreaseDifficulty = 0;
        changeEnemyAttacks = true;

        // initialize all variants of enemies based off of the max into the inactive enemies
        // initialize mechaBirds
        int mechaBirdWidth = SuperBirdGame.GAME_WIDTH / 5;
        int mechaBirdHeight = SuperBirdGame.GAME_HEIGHT / 5;
        for (int i = 0;  i < maxEnemies; i++){

            float randSpeed = (float) (Math.random() * enemyMaxSpeed);
            if(randSpeed < enemyMinSpeed){
                randSpeed = enemyMinSpeed;
            }
            MechaBird mecha = new MechaBird(mechaBirdWidth, mechaBirdHeight, (int) randSpeed, gameCam);

            inactiveEnemies.add(mecha);
        }

        // now do the same for drones
        int droneWidth = SuperBirdGame.GAME_WIDTH / 6;
        int droneHeight = SuperBirdGame.GAME_HEIGHT / 7;
        for (int i = 0;  i < maxEnemies; i++){

            float randSpeed = (float) (Math.random() * enemyMaxSpeed / 2);
            if(randSpeed < enemyMinSpeed / 2){
                randSpeed = (float) (enemyMinSpeed / 2);
            }
            Drone drone = new Drone(droneWidth, droneHeight, (int) randSpeed, gameCam);

            inactiveEnemies.add(drone);
        }


        // now shuffle the list for randomness and add the min amount of enemies onto the active list
        Collections.shuffle(inactiveEnemies);
        int iterator = 0;
        while (iterator < minEnemies){

            // get a random position in the inactive list and add it to the active
            int randListPos = (int) Math.random() * inactiveEnemies.size();
            activeEnemies.add(inactiveEnemies.remove(randListPos));
            iterator += 1;
        }
    }



    // updates the enemies on the active list
    // if they die, their state is Dead, then we must remove them to the inactive list
    // also we must control how the enemies are spawned onto the screen by adding to the active list
    public void updateSpawner(float dt, Hitbox playerHitbox, Array<BirdSpit> playerSpit){

        // first update the enemies in the active list
        for (int i = 0; i < activeEnemies.size(); i++){

            // mecha bird update
            if (activeEnemies.get(i) instanceof MechaBird){
                ((MechaBird) activeEnemies.get(i)).updateMechaBird(dt, playerHitbox, playerSpit);
            }
            // remaining cases for updating for a drone or other enemy type
            else if(activeEnemies.get(i) instanceof Drone){
                ((Drone) activeEnemies.get(i)).updateDrone(dt, playerSpit);
            }
        }

        // now delete any enemy that has died by both respawn the enemy and send it to the inactive list
        for (int i = 0; i < activeEnemies.size(); i++){

            if (activeEnemies.get(i).getState() == Enemy.State.DEAD && activeEnemies.get(i).deadEnemy.isAnimFinished() == true){

                // make inactive
                inactiveEnemies.add(activeEnemies.remove(i));
            }

        }

        // if there exists less active enemies than the minimum, make a new one
        while (activeEnemies.size() < minEnemies){

            // get a random position in the inactive list and add it to the active
            int randListPos = (int) Math.random() * inactiveEnemies.size();
            if (inactiveEnemies.get(randListPos) instanceof MechaBird) {
                ((MechaBird) inactiveEnemies.get(randListPos)).reSpawn();
                activeEnemies.add(inactiveEnemies.remove(randListPos));
            }

            // respawn drones
            else if (inactiveEnemies.get(randListPos) instanceof Drone) {
                ((Drone) inactiveEnemies.get(randListPos)).respawn();
                activeEnemies.add(inactiveEnemies.remove(randListPos));
            }
        }

        // finally, random chance of spawning a new enemy if the number is less than the max
        if (dt - lastEnemySpawnTime > spawnFrequency && activeEnemies.size() < maxEnemies && dt > 10){

            int randomSpawn = (int) Math.random();
            currSpawnPerS *= 1.25;

            // if a new enemy spawns, reset the time counter, reset the spawn rate and spawn a new enemy
            if (randomSpawn <  currSpawnPerS){
                lastEnemySpawnTime = dt;
                currSpawnPerS = newSpawnPerS;

                // get a random position in the inactive list and add it to the active
                int randListPos = (int) Math.random() * inactiveEnemies.size();
                if (inactiveEnemies.get(randListPos) instanceof MechaBird) {
                    ((MechaBird) inactiveEnemies.get(randListPos)).reSpawn();
                    activeEnemies.add(inactiveEnemies.remove(randListPos));
                }

                // now for drones
                else if (inactiveEnemies.get(randListPos) instanceof Drone) {
                    ((Drone) inactiveEnemies.get(randListPos)).respawn();
                    activeEnemies.add(inactiveEnemies.remove(randListPos));
                }

            }
        }

        // every 15 seconds will either make more enemies or increase # of attacks
        if(dt - lastTimeIncreaseDifficulty > addedDifficultyTime) {
            lastTimeIncreaseDifficulty = dt;

            // if time is greater than 25 seconds, will either increase # of enemies or # of attacks per enemy
            // increase # of attacks for mecha birds
            if (changeEnemyAttacks) {
                changeEnemyAttacks = false;

                for (int i = 0; i < inactiveEnemies.size(); i++) {

                    // only applies to the mecha bird
                    if (inactiveEnemies.get(i) instanceof MechaBird) {
                        int currNumOfAttacks = ((MechaBird) inactiveEnemies.get(i)).maxAttacksPerEnemy;
                        ((MechaBird) inactiveEnemies.get(i)).resetMaxAttacks(currNumOfAttacks + 1);
                    }
                }
                for (int i = 0; i < activeEnemies.size(); i++) {

                    // only applies to the mecha bird
                    if (activeEnemies.get(i) instanceof MechaBird) {
                        int currNumOfAttacks = ((MechaBird) activeEnemies.get(i)).maxAttacksPerEnemy;
                        ((MechaBird) activeEnemies.get(i)).resetMaxAttacks(currNumOfAttacks + 1);
                    }
                }
            }

            // increase # of enemies
            else {
                changeEnemyAttacks = true;
                System.out.println(dt + ": increased min num of enemies to : " + (minEnemies + 1));
                setMinEnemies(minEnemies + 1);
            }
        }
    }



    // resets the minimum amount of ememies
    public void setMinEnemies(int minEnemies){
        if(minEnemies > maxEnemies){
            setMaxEnemies(minEnemies);
            this.minEnemies = minEnemies;
        }
        else {
            this.minEnemies = minEnemies;
        }
    }



    // the new maximum enemies (must be larger than the current one)
    public void setMaxEnemies(int maxEnemies){
        if(this.maxEnemies < maxEnemies){

            // now add more enemies of each type to match the current max

            // initialize all variants of enemies based off of the max into the inactive enemies
            // initialize mechaBirds
            int currMax = maxEnemies - this.maxEnemies;
            this.maxEnemies = maxEnemies;
            int mechaBirdWidth = SuperBirdGame.GAME_WIDTH / 5;
            int mechaBirdHeight = SuperBirdGame.GAME_HEIGHT / 5;
            for (int i = 0;  i < currMax; i++){

                float randSpeed = (float) (Math.random() * enemyMaxSpeed);
                if(randSpeed < enemyMinSpeed){
                    randSpeed = enemyMinSpeed;
                }
                MechaBird mecha = new MechaBird(mechaBirdWidth, mechaBirdHeight, (int) randSpeed, gamecam);

                inactiveEnemies.add(mecha);
            }

            // now do the same for drones
            int droneWidth = SuperBirdGame.GAME_WIDTH / 6;
            int droneHeight = SuperBirdGame.GAME_HEIGHT / 7;
            for (int i = 0;  i < currMax; i++){

                float randSpeed = (float) (Math.random() * enemyMaxSpeed / 2);
                if(randSpeed < enemyMinSpeed / 2){
                    randSpeed = (float) (enemyMinSpeed / 2);
                }
                Drone drone = new Drone(droneWidth, droneHeight, (int) randSpeed, gamecam);

                inactiveEnemies.add(drone);
            }
        }
        else {
            this.maxEnemies = maxEnemies;
        }
    }


}

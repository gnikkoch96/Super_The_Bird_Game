package com.procode.game.tools;


import com.procode.game.SuperBirdGame;
import com.procode.game.sprites.MechaBird;

import java.util.ArrayList;
import java.util.List;

public class EnemySpawner {

    private int maxEnemiesOnScreen;
    private enum ENEMY_TYPES {MECHA_BIRD, DRONE};
    private int NUM_OF_ENEMY_TYPES = 2;
    public List<Enemy> enemies; // is public to allow for looping through and placing the enemies on screen

    private float lastTimeUpdated = 0;
    private float spawnFrequency; // amount of time passed before a new enemy is spawned if limit isnt reached



    public EnemySpawner(int maxEnemiesOnScreen, float spawnFrequency){
        this.maxEnemiesOnScreen = maxEnemiesOnScreen;
        this.spawnFrequency = spawnFrequency;
        enemies = new ArrayList<Enemy>();
    }



    // deals with placing new random enemies on screen at a time,
    // deleting enemies when any one of them has been removed
    public void updateSpawner(float deltaTime){


        if (lastTimeUpdated + spawnFrequency < deltaTime){

            // probability of an enemy spawning
            double randSpawn = Math.random();
            double spawnProb = .3;

            // spawns a random enemy
            if (randSpawn <= spawnProb && enemies.size() < maxEnemiesOnScreen){
                int randomEnemy = (int) (Math.random()  * NUM_OF_ENEMY_TYPES);

                // spawns a mecha bird
                if (randomEnemy == 0){

                    int mechaBirdWidth = SuperBirdGame.ANDROID_WIDTH / 5;
                    int mechaBirdHeight = SuperBirdGame.ANDROID_HEIGHT / 5;
                    float mechaBirdSpeed = SuperBirdGame.ANDROID_HEIGHT / 60;
                    MechaBird mecha = new MechaBird(mechaBirdWidth, mechaBirdHeight, mechaBirdSpeed);
                    mecha.setEnemyInitialPosition();
                    enemies.add(mecha);
                }

                // spawns a drone
                else {
                    // -------------------spawn a drone ----------------------
                }
            }
        }

        // updates enemy animations
        if (enemies.size() > 0){
            for (int i =0; i < enemies.size(); i++){
                if (enemies.get(i) instanceof MechaBird){
                    ((MechaBird) enemies.get(i)).updateMechaBird(deltaTime);
                }
                // should be an else if for drone types
            }
        }

        // now delete any enemies that have been disposed
        int iterator = 0;
        if (enemies.size() > 0){
            while (iterator < enemies.size()){
                if (enemies.get(iterator) instanceof MechaBird){
                    if (((MechaBird) enemies.get(iterator)).isDisposed == true){
                        enemies.remove(iterator);
                    }
                    else{
                        iterator ++;
                    }
                }

                // need one for drone too
            }
        }
    }
}

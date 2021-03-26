package com.procode.game.tools;

import com.procode.game.SuperBirdGame;
import com.procode.game.sprites.MechaBird;

import java.util.List;

public class Spawner {

    // this will have 2 arrays, one with enabled enemies and one with disabled ones
    // we will swap enemies onto and off of the list as needed
    public List<Enemy> activeEnemies;
    private List<Enemy> inactiveEnemies;

    // variables that control how many enemies on the screen,
    // rate of spawning and enemy speed
    private int maxEnemies;
    private int minEnemies;

    private int maxSimultaneousSpawns; // maximum amount of enemies that can spawn at once

    private float spawnRate;
    private float enemyAverageSpeed;



    // will initialize variables and also initialize enemies depending on the maxEnemies,
    // will initialize that many enemies per enemy variant
    public Spawner(int maxEnemies, int minEnemies, int maxSimultaneousSpawns, float spawnRate, float enemyAverageSpeed){
        this.maxEnemies = maxEnemies;
        this.minEnemies = minEnemies;
        this.maxSimultaneousSpawns = maxSimultaneousSpawns;
        this.spawnRate = spawnRate;
        this.enemyAverageSpeed = enemyAverageSpeed;

        // initialize all variants of enemies based off of the max into the inactive enemies
        // initialize mechaBirds
        int mechaBirdWidth = SuperBirdGame.ANDROID_WIDTH / 5;
        int mechaBirdHeight = SuperBirdGame.ANDROID_HEIGHT / 5;
        float mechaBirdSpeed = SuperBirdGame.ANDROID_HEIGHT / 70;
        for (int i = 0;  i < maxEnemies; i++){

            float randSpeed = (float) (Math.random() * enemyAverageSpeed);
            if(randSpeed < mechaBirdSpeed){
                randSpeed = mechaBirdSpeed;
            }
            MechaBird mecha = new MechaBird(mechaBirdWidth, mechaBirdHeight, randSpeed);

            inactiveEnemies.add(mecha);
        }

        // now do the same for drones


        // now add the min amount of enemies onto the active list
        int iterator = 0;
        while (iterator < minEnemies){

            // get a random position in the
            int randListPos = (int) Math.random() * inactiveEnemies.size();
            activeEnemies.add(inactiveEnemies.remove(randListPos));
        }
    }



    // updates the enemies on the active list
    // if they die, their state is Dead, then we must remove them to the inactive list
    // also we must control how the enemies are spawned onto the screen by adding to the active list
    public void updateSpawner(float dt){

    }
}

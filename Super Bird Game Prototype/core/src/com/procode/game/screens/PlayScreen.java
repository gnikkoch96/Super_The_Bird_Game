package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.sprites.Background;
import com.procode.game.sprites.Bird;
import com.procode.game.sprites.MechaBird;
import com.procode.game.tools.Enemy;
import com.procode.game.sprites.BirdSpit;
import com.procode.game.tools.Gamepad;
import com.procode.game.tools.ImageFunctions;
import com.procode.game.tools.Spawner;


//This class will handle the Play State logic of the game
public class PlayScreen implements Screen {
    private static final int OFFSET = 80; // offset of the camera in respect to the bird
    private SuperBirdGame game;
    private HUD hud;
    private Texture background;
    private float currTime;
    private int state;

    private Vector2 birdMovement;
    private Gamepad gamepad;

    private final int GAME_PLAY =0; // play the game
    private final int GAME_PAUSE = 1; // pause the game

    // sprites
    public static Bird player;
    public static Bird enemy;
    private Background bg;
    private int moveHills_x, moveMountain_x, moveClouds_x, enemySpeed;
    //private MechaBird enemyBird;
    private Spawner enemySpawner;

    public Array<BirdSpit> activeSpits;


    public PlayScreen(SuperBirdGame game){
        //Initializing Properties
        this.game = game;
        System.out.println("width " + SuperBirdGame.GAME_WIDTH);
        hud = new HUD(game);
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        currTime = 0;
        bg = new Background(); //this is the background class that contains all textures of images for the background


        //set the starting point of the background objects
        moveHills_x = game.GAME_WIDTH;
        moveMountain_x = game.GAME_WIDTH;
        moveClouds_x = game.GAME_WIDTH;
        //start the state with game
        state = GAME_PLAY;
        //Creating Sprites
        int birdWidth = SuperBirdGame.GAME_WIDTH /5;
        int birdHeight = SuperBirdGame.GAME_HEIGHT /5;
        player = new Bird(SuperBirdGame.GAME_WIDTH /7, SuperBirdGame.GAME_HEIGHT /2, birdWidth, birdHeight);
        enemy = new Bird(SuperBirdGame.GAME_WIDTH /2, SuperBirdGame.GAME_HEIGHT /2, birdWidth,birdHeight);


        //Setting Properties
        game.camera.setToOrtho(false, SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);

        gamepad = new Gamepad(game);
        activeSpits = player.getActiveSpits();

//        // testing only -----------------------------------------------
//        int mechaBirdWidth = SuperBirdGame.ANDROID_WIDTH / 5;
//        int mechaBirdHeight = SuperBirdGame.ANDROID_HEIGHT / 5;
//        float mechaBirdSpeed = SuperBirdGame.ANDROID_HEIGHT / 70;
//        enemyBird = new MechaBird(mechaBirdWidth, mechaBirdHeight, mechaBirdSpeed);

        int minEnemies = 2; // easy = 2 hard = 5
        int maxEnemies = 20; // easy = 5 hard = 15
        float enemyMaxSpeed =  SuperBirdGame.GAME_HEIGHT / 40;
        float enemyMinSpeed = SuperBirdGame.GAME_HEIGHT / 80;
        float spawnPerSec = 1f; // easy = .003f hard = 1f
        float spawnFrequency = 0f; // easy = 2.5f hard = 0
        enemySpawner = new Spawner(maxEnemies, minEnemies, enemyMaxSpeed, enemyMinSpeed, spawnPerSec, spawnFrequency);
    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.damageBird(hud);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            player.shoot();
        }
    }

    public void update(float dt){
        handleInput(dt);

        state = HUD.state;
        player.update(dt);
        enemy.update(dt);

        player.hitDetection(enemy, hud);
        player.hitbox.isHit(enemy.hitbox);


        // bird movement
        birdMovement = hud.gamepad.getButtonInputs();
        //Gdx.app.log("Bird Movement", "(" + String.valueOf(birdMovement.x) + " , " + String.valueOf(birdMovement.y) + ")");
        player.movePosition(birdMovement.x, birdMovement.y);
        setBackgroundMovement();
        //gameCam.position.x = player.getPosition().x + OFFSET;           //Update Camera Position in relative to bird

        /*if(hud.getShootStateBtn() == true) {
           // System.out.println("Player Shoot");
            player.shoot();
        }*/

        //testing only-----------------------------------------------
//        enemyBird.updateMechaBird(dt);
//
//        if(enemyBird.getState() == Enemy.State.DEAD){
//            enemyBird.reSpawn();
//        }

        enemySpawner.updateSpawner(dt);
    }



    public void setBackgroundMovement(){
        if(moveHills_x > -(game.GAME_WIDTH /4))
            moveHills_x -= 3;
        else
            moveHills_x = game.GAME_WIDTH;

        if(moveClouds_x > -(game.GAME_WIDTH /2))
            moveClouds_x -= 3;
        else
            moveClouds_x = game.GAME_WIDTH;

        if(moveMountain_x > -(game.GAME_WIDTH))
            moveMountain_x -= 3;
        else
            moveMountain_x = game.GAME_WIDTH;
    }

    @Override
    public void show() {

    }

    public void play(float delta){
        currTime += delta;
        update(currTime);
        //game.batch.draw(background, 0, 0);

        HUD.settingScreen.setContainerVisible(false);

        game.batch.draw(bg.getBackgroundSky(),0,0);
        game.batch.draw(bg.getBackground_hills(),moveHills_x,0);
        game.batch.draw(bg.getBackgroundMountains(),moveMountain_x,0);
        game.batch.draw(bg.getBackgroundClouds(),moveClouds_x,0);

        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);
        game.batch.draw(enemy.getBirdImage(),enemy.getPosition().x, enemy.getPosition().y);

        //testing only---------------------------------------
//        System.out.println("currPos: " + enemyBird.getEnemyPosition() + "   currDestination: " + enemyBird.currDestination + "   currentState: " + enemyBird.getState() + "   currentSpeed: " + enemyBird.getEnemySpeed());
//        game.batch.draw(enemyBird.getMechaBirdImage(), enemyBird.getEnemyPosition().x, enemyBird.getEnemyPosition().y);

        for (int i = 0; i < enemySpawner.activeEnemies.size(); i++){
            Texture currEnemyImg = enemySpawner.activeEnemies.get(i).getEnemyImage();
            Vector2 enemyPos = enemySpawner.activeEnemies.get(i).getEnemyPosition();

            game.batch.draw(currEnemyImg,enemyPos.x, enemyPos.y);
        }
    }


    @Override
    public void render(float delta) {
        // empties the Screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // main render
        game.batch.setProjectionMatrix(game.camera.combined);      //--Mess with this by comparing with and without this line of code--//
        game.batch.begin();
        game.batch.draw(background, 0, 0);

        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);

        switch(state){ // pauses or resumes the game
            case GAME_PLAY:
                play(delta);
                break;
            case GAME_PAUSE:
                pause();
                break;
        }

        game.batch.draw(enemy.getBirdImage(), enemy.getPosition().x, enemy.getPosition().y);

        // render projectiles
        for(BirdSpit spits: activeSpits){
            spits.render(game.batch);
        }

        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);
        //player.renderBullets(game.batch);
        game.batch.end();

        //--DEBUGGING--//
//        enemy.hitbox.debugHitbox();
//        player.debugHitbox();

        //add buttons to screen
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        game.camera.position.set(game.GAME_WIDTH/2, game.GAME_HEIGHT/2, 0);
    }

    @Override
    public void pause() {
        //game.batch.draw(background, 0, 0);
        game.batch.draw(bg.getBackgroundSky(),0,0);
        game.batch.draw(bg.getBackground_hills(),moveHills_x,0);
        game.batch.draw(bg.getBackgroundMountains(),moveMountain_x,0);
        game.batch.draw(bg.getBackgroundClouds(),moveClouds_x,0);
        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);

        HUD.settingScreen.setContainerVisible(true);

        state = HUD.state;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose(){
        hud.dispose();
        background.dispose();
        player.dispose();
    }
}
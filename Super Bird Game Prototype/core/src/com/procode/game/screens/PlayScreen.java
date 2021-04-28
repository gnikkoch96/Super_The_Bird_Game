package com.procode.game.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.BaseScene;
import com.procode.game.scenes.HUD;
import com.procode.game.sprites.Background;
import com.procode.game.sprites.Bird;
import com.procode.game.sprites.Drone;
import com.procode.game.sprites.MechaBird;
import com.procode.game.sprites.MechaLaser;
import com.procode.game.tools.Enemy;
import com.procode.game.sprites.BirdSpit;
import com.procode.game.tools.Gamepad;
import com.procode.game.tools.ImageFunctions;
import com.procode.game.tools.ParticleEffectComponent;
import com.procode.game.tools.Spawner;


//This class will handle the Play State logic of the game
public class PlayScreen extends BaseScene implements Screen {
    private static final int OFFSET = 80; // offset of the camera in respect to the bird
    private SuperBirdGame game;
    private HUD hud;
    private Texture background;
    private float currTime;
    private int state;

    private Vector2 birdMovement;
    private Gamepad gamepad;

    private final int GAME_PLAY = 0; // play the game
    private final int GAME_PAUSE = 1; // pause the game
    private final int GAME_QUIT = 4; // quit the game

    // sprites
    public static Bird player;
    private Background bg;
    private int moveHills_x, moveMountain_x, moveClouds_x, enemySpeed;
    //private MechaBird enemyBird;
    private Spawner enemySpawner;
    public static boolean rapidFireSpit = false;

    public Array<BirdSpit> activeSpits;
    public Array<ParticleEffectComponent> activeParticles;

    // music
    private Music backgroundMusic;

    public static String changeBackground = "orange";

    // game over screen stuff
    private float timePlayerDied;

    public PlayScreen(SuperBirdGame game){
        super(game);

        //Initializing Properties
        this.game = game;
        System.out.println("width " + SuperBirdGame.GAME_WIDTH);
        hud = new HUD(game);
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        currTime = 0;
        timePlayerDied = -1;
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
        player = new Bird(SuperBirdGame.GAME_WIDTH /7, SuperBirdGame.GAME_HEIGHT /2, birdWidth, birdHeight, hud.stage.getCamera());


        //Setting Properties
        game.camera.setToOrtho(false, SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);

        gamepad = new Gamepad(game);
        activeSpits = player.getActiveSpits();

//        // testing only -----------------------------------------------
//        int mechaBirdWidth = SuperBirdGame.ANDROID_WIDTH / 5;
//        int mechaBirdHeight = SuperBirdGame.ANDROID_HEIGHT / 5;
//        float mechaBirdSpeed = SuperBirdGame.ANDROID_HEIGHT / 70;
//        enemyBird = new MechaBird(mechaBirdWidth, mechaBirdHeight, mechaBirdSpeed);

        int minEnemies = 1; // easy = 2 hard = 5
        int maxEnemies = 15; // max should always be 15
        float enemyMaxSpeed =  SuperBirdGame.GAME_HEIGHT / 40; // desired max speed = game height / 40, hard = /10
        float enemyMinSpeed = SuperBirdGame.GAME_HEIGHT / 80; // desired min speed = game height / 80, hard = /40
        float spawnPerSec = .001f; // easy = .001f hard = 1f
        float spawnFrequency = 5.5f; // easy = 3.5f hard = 0
        enemySpawner = new Spawner(maxEnemies, minEnemies, enemyMaxSpeed, enemyMinSpeed, spawnPerSec, spawnFrequency, hud.stage.getCamera(), hud);

                // background music stuff
        backgroundMusic = SuperBirdGame.manager.get("audio/music/music.mp3", Music.class);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.setLooping(true); // loops music
//        backgroundMusic.play();
    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.damageBird(hud);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.Z) || Gdx.input.isKeyPressed(Input.Keys.Z) ||
                Gdx.input.isKeyJustPressed(Input.Keys.E) || Gdx.input.isKeyPressed(Input.Keys.E) ||
                Gdx.input.isKeyJustPressed(Input.Keys.O) || Gdx.input.isKeyPressed(Input.Keys.O)){
            player.shoot();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            if(player.isShrunk){
                player.growBird();
            }
            else{
                player.shrinkBird();
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.W)){
            player.movePosition(0,gamepad.touchSensitivity);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.A)){
            player.movePosition(-gamepad.touchSensitivity,0);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.S)){
            player.movePosition(0, -gamepad.touchSensitivity);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.D)){
            player.movePosition(gamepad.touchSensitivity,0);
        }


    }

    public void update(float dt){

        state = HUD.state;

        if(!player.isDead()){
            handleInput(dt);
            player.update(dt);
            player.updateHitDetection(enemySpawner.activeEnemies, hud);
            if(rapidFireSpit) {
                player.shoot();
            }

            // bird movement
            birdMovement = hud.gamepad.getButtonInputs();
            player.movePosition(birdMovement.x, birdMovement.y);
            setBackgroundMovement();

        }else{

            //disable movement for 1 second to show the bird turning into food animation
            // then gameover screen
            player.update(dt);
            setBackgroundMovement();

            if (timePlayerDied == -1){
                timePlayerDied = dt;
            }
            if (dt - timePlayerDied > 1) {
                game.setScreen(new GameOverScreen());
            }
        }

        enemySpawner.updateSpawner(dt, player.hitbox, player.getActiveSpits());
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
        hud.settingScreen.volumeButtons();
    }

    public void play(float delta){
        currTime += delta;
        update(currTime);

        if(!player.isFlapping)
            player.startFlap();

        //game.batch.draw(background, 0, 0);

        hud.settingScreen.setContainerVisible(false);

       // game.batch.draw(bg.getBackgroundSky(),0,0);
        game.batch.draw(bg.getBackground_hills(),moveHills_x,0);
        game.batch.draw(bg.getBackgroundMountains(),moveMountain_x,0);
        game.batch.draw(bg.getBackgroundClouds(),moveClouds_x,0);

//        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);

        hud.gamepad.upArrow.setVisible(true);
        hud.gamepad.downArrow.setVisible(true);
        hud.gamepad.leftArrow.setVisible(true);
        hud.gamepad.rightArrow.setVisible(true);
        hud.gamepad.shootButton.setVisible(true);
        hud.gamepad.resizeButton.setVisible(true);
        hud.pauseBtn.setVisible(true);

        state = HUD.state;

        for (int i = 0; i < enemySpawner.activeEnemies.size(); i++){
            Enemy currEnemy = enemySpawner.activeEnemies.get(i);

            Texture currEnemyImg = enemySpawner.activeEnemies.get(i).getEnemyImage();
            Vector2 enemyPos = enemySpawner.activeEnemies.get(i).getEnemyPosition();


            if (currEnemy instanceof MechaBird) { // render the lasers
                Array<MechaLaser> activeShots = ((MechaBird)(currEnemy)).activeShots;
                for (MechaLaser laser : activeShots) {
                    laser.render(game.batch);
                }

                if(((MechaBird) currEnemy).getIsHit()){ // blinking effect when the mecha bird takes damage
                    //make the sprite transparent by using a for loop
                    Sprite sprite = new Sprite(currEnemyImg);
                    sprite.setPosition(enemyPos.x, enemyPos.y);
                    sprite.setColor(Color.BLUE);
                    sprite.draw(game.batch);
                    ((MechaBird)currEnemy).setIsHit(false);
                }else{
                    game.batch.draw(currEnemyImg,enemyPos.x, enemyPos.y);
                }
            }

            if(currEnemy instanceof Drone){
                if(((Drone) currEnemy).getIsHit() && (currEnemy.getState()) != Enemy.State.DEAD){
                    Sprite sprite = new Sprite(currEnemyImg);
                    sprite.setPosition(enemyPos.x, enemyPos.y);
                    sprite.setColor(Color.RED);
                    sprite.draw(game.batch);
                }else {
                    game.batch.draw(currEnemyImg, enemyPos.x, enemyPos.y);
                }
            }



        }

        // render particles if any
        player.getSpitParticles().update(delta); // has to be placed in render for it to work (Nikko: Not sure why)
        player.getSpitParticles().renderParticles(game.batch);
        game.batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // related to particles

    }


    @Override
    public void render(float delta) {
        // empties the Screen
        super.render(delta);
        if(changeBackground.equals("orange"))
            Gdx.gl.glClearColor(255/255f, 127/255f, 39/255f,1);
        else if(changeBackground.equals("blue"))
            Gdx.gl.glClearColor(0/255f, 255/255f, 255/255f,1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // main render
        game.batch.setProjectionMatrix(game.camera.combined);      //--Mess with this by comparing with and without this line of code--//
        game.batch.begin();

        switch(state){ // pauses or resumes the game
            case GAME_PLAY:
                play(delta);
                break;
            case GAME_PAUSE:
                pause();
                break;
        }

        // render projectiles
        for(BirdSpit spits: activeSpits){
            spits.render(game.batch);
        }

        // render player
        if(player.isInvincible){
            Sprite sprite = new Sprite(player.getBirdImage());
            sprite.setPosition(player.getPosition().x, player.getPosition().y);
            sprite.draw(game.batch, (+5f * (float)Math.sin(0) + .5f));
        }else{
            game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);
        }

        game.batch.end();

        //--DEBUGGING--//
        player.debugHitbox();
        for (int i = 0; i < enemySpawner.activeEnemies.size(); i++){
            if(enemySpawner.activeEnemies.get(i).hitbox != null) { // this is because hitboxes are deleted and replaced with new ones
                enemySpawner.activeEnemies.get(i).hitbox.debugHitbox();

                if(enemySpawner.activeEnemies.get(i) instanceof MechaBird){ // debugs laser hitboxes
                    for(MechaLaser laser: ((MechaBird) enemySpawner.activeEnemies.get(i)).activeShots){
                        laser.hitbox.debugHitbox();
                    }
                }
            }
        }

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
//        game.batch.draw(background, 0, 0);
//        game.batch.draw(bg.getBackgroundSky(),0,0);
//        game.batch.draw(bg.getBackground_hills(),moveHills_x,0);
//        game.batch.draw(bg.getBackgroundMountains(),moveMountain_x,0);
//        game.batch.draw(bg.getBackgroundClouds(),moveClouds_x,0);
//        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);

        this.hud.settingScreen.setContainerVisible(true);

        //activate the buttons
        hud.settingScreen.Buttons();

        //this will set the screen into the setting screen
        //1 is the id number for setting screen view on play screen
        if(hud.settingScreen.state == 1) {
            hud.settingScreen.setSettingsContainerVisible(true);
        }
        else
            hud.settingScreen.setSettingsContainerVisible(false);


        hud.gamepad.upArrow.setVisible(false);
        hud.gamepad.downArrow.setVisible(false);
        hud.gamepad.leftArrow.setVisible(false);
        hud.gamepad.rightArrow.setVisible(false);
        hud.gamepad.shootButton.setVisible(false);
        hud.gamepad.resizeButton.setVisible(false);
        hud.pauseBtn.setVisible(false);

        //Nikko: I am trying to figure out a way to make this work
//        hud.gamepad.upArrow.setTouchable(Touchable.disabled);
//        hud.gamepad.downArrow.setTouchable(Touchable.disabled);
//        hud.gamepad.leftArrow.setTouchable(Touchable.disabled);
//        hud.gamepad.rightArrow.setTouchable(Touchable.disabled);
//        hud.gamepad.shootButton.setTouchable(Touchable.disabled);
//
//        hud.gamepad.upArrow.setTouchable(Touchable.enabled);
//        hud.gamepad.downArrow.setTouchable(Touchable.enabled);
//        hud.gamepad.leftArrow.setTouchable(Touchable.enabled);
//        hud.gamepad.rightArrow.setTouchable(Touchable.enabled);
//        hud.gamepad.shootButton.setTouchable(Touchable.enabled);

        state = HUD.state;

        //set the screen into homescreen
        if(state == GAME_QUIT){
            hud.setState(GAME_QUIT);
            game.setScreen(new HomeScreen(game));
        }


    }

    @Override
    public void resume() {
        hud.setState(GAME_PAUSE);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose(){
        hud.dispose();
        background.dispose();
        player.dispose();
        backgroundMusic.dispose();
    }
}
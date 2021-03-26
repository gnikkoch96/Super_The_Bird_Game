package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.procode.game.sprites.BirdSpit;
import com.procode.game.sprites.EnemyDummy;
import com.procode.game.tools.Gamepad;
import com.procode.game.tools.Hitbox;
import com.procode.game.tools.ImageFunctions;


//This class will handle the Play State logic of the game
public class PlayScreen implements Screen {
    private static final int OFFSET = 80; // offset of the camera in respect to the bird
    private SuperBirdGame game;
    private HUD hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Texture background;
    private float currTime;
    private Gamepad gamepad;
    private int state;

    private final int GAME_PLAY =0; // play the game
    private final int GAME_PAUSE = 1; // pause the game
    //Sprites
    public static Bird player;
    public static Bird enemy;
    private Background bg;
    private int moveHills_x, moveMountain_x, moveClouds_x, enemySpeed;

    private Sound collisionSound;

    public PlayScreen(SuperBirdGame game){
        //Initializing Properties
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_WIDTH, gameCam);
        System.out.println("width " + SuperBirdGame.ANDROID_WIDTH);
        hud = new HUD(game);
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);
        currTime = 0;

        //this is the background class that contains all textures of images for the background
        bg = new Background();

        //set the starting point of the background objects
        moveHills_x = game.ANDROID_WIDTH;
        moveMountain_x = game.ANDROID_WIDTH;
        moveClouds_x = game.ANDROID_WIDTH;
        //start the state with game
        state = GAME_PLAY;
        //Creating Sprites
        int birdWidth = SuperBirdGame.ANDROID_WIDTH/5;
        int birdHeight = SuperBirdGame.ANDROID_HEIGHT /5;
        player = new Bird(SuperBirdGame.ANDROID_WIDTH/7, SuperBirdGame.ANDROID_HEIGHT/2, birdWidth, birdHeight);
        enemy = new Bird(SuperBirdGame.ANDROID_WIDTH/2, SuperBirdGame.ANDROID_HEIGHT/2,birdWidth,birdHeight);

        collisionSound = SuperBirdGame.manager.get("audio/sound/spitCollision.mp3", Sound.class);

        //Setting Properties
        gameCam.setToOrtho(false, SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);
        gamepad = new Gamepad(game);

        activeSpits = player.getActiveSpits();


    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.damageBird(hud);
        }
    }

    public void update(float dt){
        handleInput(dt);

        state = HUD.state;
        player.update(dt);
        enemy.update(dt);
        if(player.hitbox.isHit(enemy.hitbox)){
            Gdx.app.log("PLAYER->ENEMY", "HIT");
            player.damageBird(hud);
        }
        hitDetection(dt);

        // bird movement
        Vector2 birdMovement = hud.gamepad.getButtonInputs();
        player.movePosition(birdMovement.x, birdMovement.y);
        setBackgroundMovement();
        gameCam.position.x = player.getPosition().x + OFFSET;           //Update Camera Position in relative to bird


        /*if(hud.getShootStateBtn() == true) {
           // System.out.println("Player Shoot");
            player.shoot();
        }*/

    }

    public Array<BirdSpit> activeSpits;
    public void hitDetection(float dt){
//        Array<BirdSpit> activeSpits = player.getActiveSpits();
        for(BirdSpit spit: activeSpits){
            // check if it hits an enemy
            if(spit.getHitbox().isHit(enemy.hitbox) && (enemy.getInvincible() == false) && spit.isAlive()){ //--Nikko: Buggy
                Gdx.app.log("SPIT->ENEMY", "HIT");
                enemy.damageBird(hud);
                spit.setCollision(true);
            }
        }
    }

    public void setBackgroundMovement(){
        if(moveHills_x > -(game.ANDROID_WIDTH/4))
            moveHills_x -= 3;
        else
            moveHills_x = game.ANDROID_WIDTH;

        if(moveClouds_x > -(game.ANDROID_WIDTH/2))
            moveClouds_x -= 3;
        else
            moveClouds_x = game.ANDROID_WIDTH;

        if(moveMountain_x > -(game.ANDROID_WIDTH))
            moveMountain_x -= 3;
        else
            moveMountain_x = game.ANDROID_WIDTH;
    }

    @Override
    public void show() {

    }

    public void play(float delta){
        currTime += delta;
        update(currTime);
        //game.batch.draw(background, 0, 0);

        game.batch.draw(bg.getBackgroundSky(),0,0);
        game.batch.draw(bg.getBackground_hills(),moveHills_x,0);
        game.batch.draw(bg.getBackgroundMountains(),moveMountain_x,0);
        game.batch.draw(bg.getBackgroundClouds(),moveClouds_x,0);

        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);
        game.batch.draw(enemy.getBirdImage(),enemy.getPosition().x, enemy.getPosition().y);

    }

    @Override
    public void render(float delta) {
        // empties the Screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // main render
        game.batch.setProjectionMatrix(gameCam.combined);      //--Mess with this by comparing with and without this line of code--//
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        switch(state){ // pauses or resumes the game
            case GAME_PLAY:
                play(delta);
                break;
            case GAME_PAUSE:
                pause();
                break;
        } // pause logic

        game.batch.draw(enemy.getBirdImage(), enemy.getPosition().x, enemy.getPosition().y);
        player.renderBullets(game.batch);
        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //add buttons to screen
        hud.stage.draw();

        //--DEBUG--// Note: debugging hitboxes has to occur after it has rendered
//        player.hitbox.debugHitbox();
//        enemy.hitbox.debugHitbox();
//        for(BirdSpit spit:activeSpits){
//            spit.getHitbox().debugHitbox();
//        }
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        //game.batch.draw(background, 0, 0);
        game.batch.draw(bg.getBackgroundSky(),0,0);
        game.batch.draw(bg.getBackground_hills(),moveHills_x,0);
        game.batch.draw(bg.getBackgroundMountains(),moveMountain_x,0);
        game.batch.draw(bg.getBackgroundClouds(),moveClouds_x,0);
        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);
        state = HUD.state;
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        hud.dispose();
        background.dispose();
        player.dispose();
    }
}

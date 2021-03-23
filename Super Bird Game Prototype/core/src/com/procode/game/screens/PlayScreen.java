package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.sprites.Bird;
import com.procode.game.tools.Gamepad;
import com.procode.game.tools.ImageFunctions;

//This class will handle the Play State logic of the game
public class PlayScreen implements Screen {
    private static final int OFFSET = 80; // offset of the camera in respect to the bird
    private SuperBirdGame game;
    private HUD hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private World world;  //--Not sure what to do with this yet--//
    private Texture background;
    private float currTime;
    private Gamepad gamepad;

    //Sprites
    public static Bird player;
<<<<<<< Updated upstream
=======
    private Background bg;
    private int moveHills_x, moveMountain_x, moveClouds_x;
    //private EnemySpawner spawner;
    private MechaBird enemyBird;
>>>>>>> Stashed changes

    public PlayScreen(SuperBirdGame game){
        //Initializing Properties
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_WIDTH, gameCam);
        System.out.println("width " + SuperBirdGame.ANDROID_WIDTH);
        hud = new HUD(game);
        world = new World(new Vector2(0,0), true); // vector2 represents the gravity values
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);
        currTime = 0;

        //Creating Sprites
        int birdWidth = SuperBirdGame.ANDROID_WIDTH/5;
        int birdHeight = SuperBirdGame.ANDROID_HEIGHT /5;
        player = new Bird(SuperBirdGame.ANDROID_WIDTH/7, SuperBirdGame.ANDROID_HEIGHT/2, birdWidth, birdHeight);

        //Setting Properties
        gameCam.setToOrtho(false, SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);

        gamepad = new Gamepad(game);
<<<<<<< Updated upstream
=======

        int maxEnemies = 1;
        float spawnFrequency = 1.5f;
        //spawner = new EnemySpawner(maxEnemies, spawnFrequency);

        // testing only -----------------------------------------------
        int mechaBirdWidth = SuperBirdGame.ANDROID_WIDTH / 5;
        int mechaBirdHeight = SuperBirdGame.ANDROID_HEIGHT / 5;
        float mechaBirdSpeed = SuperBirdGame.ANDROID_HEIGHT / 80;
        enemyBird = new MechaBird(mechaBirdWidth, mechaBirdHeight, mechaBirdSpeed);
>>>>>>> Stashed changes
    }

    public World getWorld(){return this.world;}

    public void handleInput(float dt){
        //--TEST--//
        if(Gdx.input.isTouched()){
//            player.damagedBird(hud);
//            player.shoot();
        }

    }

    public void update(float dt){
        // bird movement
        Vector2 birdMovement = hud.gamepad.getButtonInputs();
        player.movePosition(birdMovement.x, birdMovement.y);
<<<<<<< Updated upstream

        gameCam.position.x = player.getPosition().x + OFFSET;           //Update Camera Position in relative to bird
        player.update(dt);                                              //Updates the Animation Frame
=======
        setBackgroundMovement();
        gameCam.position.x = player.getPosition().x + OFFSET;           //Update Camera Position in relative to bird
                                                      //Updates the Animation Frame

        // updates the spawner
        //spawner.updateSpawner(dt);

        /*if(hud.getShootStateBtn() == true) {
           // System.out.println("Player Shoot");
            player.shoot();
        }*/

        //testing only-----------------------------------------------
        enemyBird.updateMechaBird(dt);

        if(enemyBird.isDisposed == true){
            enemyBird.reSpawn();
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
>>>>>>> Stashed changes
    }

    @Override
    public void show() {

    }

<<<<<<< Updated upstream
=======
    public void play(float delta){
        currTime += delta;
        update(currTime);
        //game.batch.draw(background, 0, 0);
        game.batch.draw(bg.getBackgroundSky(),0,0);
        game.batch.draw(bg.getBackground_hills(),moveHills_x,0);
        game.batch.draw(bg.getBackgroundMountains(),moveMountain_x,0);
        game.batch.draw(bg.getBackgroundClouds(),moveClouds_x,0);
        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);

        // draws all enemies on screen
        // first only test the mechabird

//        if (spawner.enemies.size() > 0) {
//            for (int i = 0; i < spawner.enemies.size(); i++) {
//
//                game.batch.draw(((MechaBird)spawner.enemies.get(i)).getMechaBirdImage(), ((MechaBird)spawner.enemies.get(i)).getMechaPos().x, ((MechaBird)spawner.enemies.get(i)).getMechaPos().y);
//            }
//        }

        //testing only---------------------------------------
        System.out.println("currPos: " + enemyBird.getEnemyPosition() + "   currDestination: " + enemyBird.currDestination + "   currentState: " + enemyBird.getState());
        game.batch.draw(enemyBird.getMechaBirdImage(), enemyBird.getEnemyPosition().x, enemyBird.getEnemyPosition().y);
    }

>>>>>>> Stashed changes
    @Override
    public void render(float delta) {
        currTime += delta;
        update(currTime);

        // empties the Screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // main render
        game.batch.setProjectionMatrix(gameCam.combined);      //--Mess with this by comparing with and without this line of code--//
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.draw(player.getBirdImage(), player.getPosition().x, player.getPosition().y);
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        //add buttons to screen
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

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
        world.dispose();
        background.dispose();
        player.dispose();
    }
}

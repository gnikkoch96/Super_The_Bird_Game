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

    //Sprites
    public static Bird player;

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
        player = new Bird(300, 500, birdWidth, birdHeight);

        //Setting Properties
        gameCam.setToOrtho(false, SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);


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
        handleInput(dt);

//        // FOR TESTING PURPOSES ONLY
//        // MUST SUBTRACT SCREEN HEIGHT WITH Y BECAUSE LIBGDX INVERTS Y-AXIS
//        if(Gdx.input.isTouched()) {
//            int fingerX = Gdx.input.getX();
//            int fingerY = Gdx.input.getY();
//
//            // the playerBirdSize is to make sure the bird is centered wherever its clicked
//            float newX = fingerX - ((player.getBirdSize().x) / 3);
//            float newY = game.ANDROID_HEIGHT - (fingerY + ((player.getBirdSize().y)/ 2));
//            player.setPosition(newX, newY);
//        }

        gameCam.position.x = player.getPosition().x + OFFSET; // updates camera position in relative to bird
        player.update(dt);
    }

    @Override
    public void show() {

    }

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

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
import com.procode.game.sprites.Background;
import com.procode.game.sprites.Bird;
import com.procode.game.tools.ImageFunctions;

//This class will handle the Play State logic of the game
public class PlayScreen implements Screen {
    private static final int OFFSET = 80;                               //Offset of the camera in respect to the bird
    private SuperBirdGame game;
    private HUD hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private World world;  //--Not sure what to do with this yet--//
    private Texture background;
    private Background bg;
    private float currTime;

    //Sprites
    private Bird player;

    public PlayScreen(SuperBirdGame game){
        //Initializing Properties
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(game.ANDROID_WIDTH, game.ANDROID_WIDTH, gameCam);
        System.out.println("width " + game.ANDROID_WIDTH);
        hud = new HUD(game);
        world = new World(new Vector2(0,0), true);                           //--The Vector Represents Gravity Value--//
        background = ImageFunctions.resize("background stuff/bg.png", game.ANDROID_WIDTH, game.ANDROID_HEIGHT);
        currTime = 0;

        bg = new Background();
        //Creating Sprites
        int birdWidth = game.ANDROID_WIDTH/5;
        int birdHeight = game.ANDROID_HEIGHT/5;
        player = new Bird(50, 100, birdWidth, birdHeight);

        //Setting Properties
        gameCam.setToOrtho(false, game.ANDROID_WIDTH, game.ANDROID_HEIGHT);


    }

    public void handleInput(float dt){

    }


    public void update(float dt){
        // FOR TESTING PURPOSES ONLY
        // MUST SUBTRACT SCREEN HEIGHT WITH Y BECAUSE LIBGDX INVERTS Y-AXIS
        if(Gdx.input.isTouched()) {
            int fingerX = Gdx.input.getX();
            int fingerY = Gdx.input.getY();

            // the playerBirdSize is to make sure the bird is centered wherever its clicked
            float newX = fingerX - ((player.getBirdSize().x) / 3);
            float newY = game.ANDROID_HEIGHT - (fingerY + ((player.getBirdSize().y)/ 2));
            player.setPosition(newX, newY);
        }

        gameCam.position.x = player.getPosition().x + OFFSET;           //Update Camera Position in relative to bird
        player.update(dt);                                              //Updates the Animation Frame
    }

    public World getWorld(){return this.world;}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        currTime += delta;
        update(currTime);

        //Empties the Screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Main Render Activities
        game.batch.setProjectionMatrix(gameCam.combined);      //--Mess with this by comparing with and without this line of code--//
        game.batch.begin();
        game.batch.draw(bg.getBackgroundSky(), 0, 0);
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

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
    private static final int OFFSET = 80;                               //Offset of the camera in respect to the bird
    private SuperBirdGame game;
    private HUD hud;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private World world;  //--Not sure what to do with this yet--//
    private Texture background;

    //Sprites
    private Bird player;

    public PlayScreen(SuperBirdGame game){
        //Initializing Properties
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SuperBirdGame.DESKTOP_WIDTH, SuperBirdGame.DESKTOP_WIDTH, gameCam);
        hud = new HUD(game.batch);
        world = new World(new Vector2(0,0), true);                           //--The Vector Represents Gravity Value--//
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.DESKTOP_WIDTH, SuperBirdGame.DESKTOP_HEIGHT);

        //Creating Sprites
        player = new Bird(50, 100);

        //Setting Properties
        gameCam.setToOrtho(false, SuperBirdGame.DESKTOP_WIDTH, SuperBirdGame.DESKTOP_HEIGHT);


    }

    public void handleInput(float dt){

    }


    public void update(float dt){
        gameCam.position.x = player.getPosition().x + OFFSET;           //Update Camera Position in relative to bird
        player.update(dt);                                              //Updates the Animation Frame
    }

    public World getWorld(){return this.world;}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        //Empties the Screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Main Render Activities
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

    }
}
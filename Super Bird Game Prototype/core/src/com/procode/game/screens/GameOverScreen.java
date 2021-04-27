package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.Animation;
import com.procode.game.tools.ImageFunctions;

public class GameOverScreen implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Texture background;
    private TextButton.TextButtonStyle style_button;
    private Skin skin;
    private TextButton scoreVal, btnRestartGame, btnExitGame;
    private Container<Table> tableContainer;
    private Table table, buttonTable;
    private Animation gameOverAnimation;
    private float deltaTime;
    private int score;

    public GameOverScreen(SuperBirdGame g, int currentScore){
        game = g;
        this.score = currentScore;
        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("comic-ui.json"));

        // background and animation
        deltaTime = 0;
        background = ImageFunctions.resize("game over animation/game over screen 10.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        gameOverAnimation = new Animation();
        gameOverAnimation.setAnimation("game over animation/game over screen ", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT, 1, 10, 1.5f, false);

        // font and button stuff
        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 100;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;
    }

    //this method will design and alight the buttons in the table and table container
    public void buttons(){

        btnRestartGame = new TextButton("Restart Game", style_button);

        btnExitGame = new TextButton("Exit To Home", style_button);

        scoreVal = new TextButton("Score: " + new Integer(this.score).toString(), style_button);

        //=================Table Containers and Tables =================================

        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.7f;
        float ch = sh * 0.8f;

        tableContainer = new Container<Table>();
        tableContainer.setSize(cw,ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch));


        table = new Table(skin);
        table.add(btnRestartGame).width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6).padTop(50);
        table.add(btnExitGame).width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6).padTop(50);
        table.row().colspan(3).expandX().fillX();
        table.add(scoreVal).fillX().width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6).padTop(50);
        table.row().colspan(3).expandX().fillX();
        table.row().colspan(3).expandX().fillX();

        buttonTable = new Table(skin);

        table.add(buttonTable).colspan(3);
        table.bottom();

        buttonTable.row().bottom();

        tableContainer.setActor(table);
        tableContainer.bottom();
        //==============================================================================

        buttonListeners();

        stage.addActor(tableContainer);
    }

    //this method contains all the Clicklistener for the buttons
    public void buttonListeners(){


        //set the listener for SignUp button
        btnExitGame.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //btnLoginClicked();
                gameOverAnimation.replayLoop();
                game.setScreen(new HomeScreen(game));

            }
        });


        //set the listener for edit account button
        btnRestartGame.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //btnLoginClicked();
                gameOverAnimation.replayLoop();
                game.setScreen(new LoadingScreen(game));
            }
        });
    }

    @Override
    public void show() {
        if(gameOverAnimation.isAnimFinished()) {
            buttons();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deltaTime += delta;

        if(!gameOverAnimation.isAnimFinished()) {
            game.batch.begin();
            gameOverAnimation.updateFrame(deltaTime);
            game.batch.draw(gameOverAnimation.getCurrImg(), 0,0);
            game.batch.end();
        }
        else{
            game.batch.begin();
            game.batch.draw(background, 0,0);
            game.batch.end();
            show();
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        game.camera.position.set(game.GAME_WIDTH/2, game.GAME_HEIGHT/2, 0);
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

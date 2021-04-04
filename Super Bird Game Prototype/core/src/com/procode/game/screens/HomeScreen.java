package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.sprites.Bird;
import com.procode.game.tools.ImageFunctions;

public class HomeScreen implements Screen {
    private SuperBirdGame game;
    private Stage stage;
    private Skin skin;
    private Texture background;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Button btnPlay, btnScoreboard, btnOption, btnExit;
    private ImageButton leftArrow, rightArrow;
    private Bird bird;
    private float currTime;
    private int fly;
    private boolean moveUp;

    public int buttonSize; // because image is a circle only need the radius so size is a single variable

    public HomeScreen(SuperBirdGame g) {
        game = g;
        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);

        Skin skin = new Skin(Gdx.files.internal("comic-ui.json"));
        Container<Table> tableContainer = new Container<Table>();
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);

        buttonSize = game.GAME_HEIGHT / 5;


        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;

        font = fontGenerator.generateFont(fontParameter);

        currTime = 0;
        fly =0;
        moveUp = true;

        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.7f;
        float ch = sh * 0.5f;

        tableContainer.setSize(cw, ch);
        tableContainer.setPosition((sw - cw) / 2.0f, (sh - ch) / 2.0f);

        Table table = new Table(skin);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;

        Label topLabel = new Label("Choose Option Below", labelStyle);

        topLabel.setAlignment(Align.center);



        TextButton.TextButtonStyle style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 90;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        btnPlay = new TextButton("Play", style_button);
        btnScoreboard = new TextButton("ScoreBoard", style_button);
        btnOption = new TextButton("Option", style_button);
        btnExit = new TextButton("Exit", style_button);

        btnPlay.setSize(400, 400);
        //btnPlay.setPosition(game.ANDROID_WIDTH/2,game.ANDROID_HEIGHT/2);
        btnPlay.setVisible(true);

        btnScoreboard.setSize(400,400);
        btnOption.setSize(400,400);
        btnExit.setSize(400,400);

        Table buttonTable = new Table(skin);
        Table buttonTable2 = new Table(skin);

        table.row().colspan(3).expandX().fillX();
        table.add(topLabel).fillX();
        table.row().expandX().fillX();
        table.add(buttonTable).colspan(3);
        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnPlay).height(cw/7.0f);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnScoreboard).height(cw/7.0f);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnOption).height(cw/7.0f);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnExit).height(cw/7.0f);

        tableContainer.setActor(table);
        stage.addActor(tableContainer);

        int birdWidth = game.GAME_WIDTH /5;
        int birdHeight = game.GAME_HEIGHT /5;
        bird = new Bird(50, 100, birdWidth, birdHeight);


        btnPlay.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoadingScreen(game));
            }
        });


        btnScoreboard.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Scoreboard(game));
            }
        });

        btnOption.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingsScreen(game));
            }
        });
    }

    @Override
    public void show() {

    }

    public void update(float dt){
        // bird movement
        bird.update(dt);   //Updates the Animation Frame

        if(fly != game.GAME_HEIGHT && moveUp == true){
            fly += 5;
            if(fly == game.GAME_HEIGHT)
            moveUp =false;
        }else{
            fly -= 5;
            if(fly == 0)
            moveUp = true;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255/255f, 127/255f, 39/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        currTime += delta;
        update(currTime);

        game.batch.begin();
        //render the stage and draw it
        game.batch.draw(background, 0, 0);
        game.batch.draw(bird.getBirdImage(), bird.getPosition().x, fly);
        game.batch.end();

        //validateAccount();
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
        skin.dispose();
        background.dispose();
        game.dispose();
        stage.dispose();
        bird.dispose();
    }
}
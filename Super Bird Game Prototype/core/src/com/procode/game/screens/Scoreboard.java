package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

import java.util.ArrayList;

public class Scoreboard implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private Skin skin;
    private Texture background,scoreboard_bg;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Button btnLocalScore, btnGlobalScore;
    private ArrayList<Label> users;
    private TextButton.TextButtonStyle style_button;
    private Container<Table> tableLocalScoreContainer, tableGlobalContainer;
    private Table tableLocalScore, tableGlobalScore, buttonLocalScoreTable, buttonGlobalScoreTable;
    private float sw, sh, cw, ch;
    private TextureRegionDrawable textureRegionDrawableBg;

    public Scoreboard(SuperBirdGame g) {
        game = g;
        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        //atlas = new TextureAtlas("font-export.fnt");
        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        scoreboard_bg = ImageFunctions.resize("background stuff/scoreboard.png", SuperBirdGame.GAME_WIDTH,SuperBirdGame.GAME_HEIGHT);
        Skin skin = new Skin(Gdx.files.internal("comic-ui.json"));
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);

        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;

        font = fontGenerator.generateFont(fontParameter);


        sw = SuperBirdGame.GAME_WIDTH;
        sh = SuperBirdGame.GAME_HEIGHT;

        cw = sw * 0.7f;
        ch = sh * 0.6f;

        tableLocalScoreContainer = new Container<Table>();
        tableGlobalContainer = new Container<Table>();

        //this where I set the sizes and position of both containers
        tableLocalScoreContainer.setSize(cw,ch);
        tableLocalScoreContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);

        tableGlobalContainer.setSize(cw,ch);
        tableGlobalContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);



        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 90;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;




        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.WHITE);
        bgPixmap.fill();
        textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        tableLocalScore = new Table(skin);
        buttonLocalScoreTable = new Table(skin);

        tableGlobalScore = new Table(skin);
        buttonGlobalScoreTable = new Table(skin);

        btnGlobalScore = new Button(skin);
        btnLocalScore = new Button(skin);

    }

    @Override
    public void show(){

        LocalScores();
        GlobalScores();

        //set the table container into the stage
        tableLocalScoreContainer.setActor(tableLocalScore);
        tableGlobalContainer.setActor(tableGlobalScore);
        stage.addActor(tableLocalScoreContainer);
        stage.addActor(tableGlobalContainer);

        tableGlobalContainer.setVisible(false);

        buttons();
    }

    public void buttons(){

        btnLocalScore.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!tableGlobalContainer.isVisible())
                {
                    tableLocalScoreContainer.setVisible(false);
                    tableGlobalContainer.setVisible(true);
                }
                return true;
            }
        });

        btnGlobalScore.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                System.out.println("clicked0");
                if(!tableLocalScoreContainer.isVisible())
                {
                    System.out.println("clicked1");
                    tableGlobalContainer.setVisible(false);
                    tableLocalScoreContainer.setVisible(true);

                }
                return true;
            }
        });
    }

    //this method consist of the local score tables
    public void LocalScores(){
        Table scrollTable = new Table(skin);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;

        String nameSpace = "                ";


        Label LocalScoreLabel = new Label("Local Scores", labelStyle);
        LocalScoreLabel.setAlignment(Align.center);

        Label topLabel = new Label("Username:" + nameSpace + "Scores:", labelStyle);
        topLabel.setAlignment(Align.center);

        scrollTable.row().colspan(3).expandX().fillX();
        scrollTable.add(LocalScoreLabel).fillX();
        scrollTable.row().colspan(3).expandX().fillX();
        scrollTable.add(topLabel).fillX();

        for(int i =0; i < 20; i++){
            Label user = new Label(i + ". Robin" + i + nameSpace + "        300", labelStyle);
            scrollTable.row().colspan(3).expandX().fillX();
            scrollTable.add(user);
        }

        ScrollPane scroll = new ScrollPane(scrollTable, skin.get(ScrollPane.ScrollPaneStyle.class));
        //scroll.setTransform(true);
        scroll.setPosition((sw-cw)/2.0f, (sh-ch));
        scroll.setSize(sw,ch);

        TextButton btnBack = new TextButton("Back", style_button);
        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HomeScreen(game));
            }
        });

        buttonLocalScoreTable.row().fillX().expandX();
        buttonLocalScoreTable.add(btnBack).fillX().width(cw/6.0f).height(cw/11.0f);

        scrollTable.setBackground(textureRegionDrawableBg);

        tableLocalScore.row().colspan(3).expandX().fillX();
        tableLocalScore.add(scroll);
        tableLocalScore.row().colspan(3).expandX().fillX();
        tableLocalScore.add(buttonLocalScoreTable).colspan(3);


        btnLocalScore = new TextButton("Global Scores", style_button);

        buttonLocalScoreTable.add(btnLocalScore).fillX().width(cw/2.0f).height(cw/11.0f);

    }

    public void GlobalScores(){
        Table scrollTable = new Table(skin);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;

        String nameSpace = "               ";

        Label GlobalScoreLabel = new Label("Global Scores", labelStyle);
        GlobalScoreLabel.setAlignment(Align.center);

        Label topLabel = new Label("Username:" + nameSpace + "Scores:", labelStyle);
        topLabel.setAlignment(Align.center);

        scrollTable.row().colspan(3).expandX().fillX();
        scrollTable.add(GlobalScoreLabel).fillX();
        scrollTable.row().colspan(3).expandX().fillX();
        scrollTable.add(topLabel).fillX();

        for(int i =0; i < 20; i++){
            Label user = new Label(i + ". Jason" + i + nameSpace + "        600", labelStyle);
            scrollTable.row().colspan(3).expandX().fillX();
            scrollTable.add(user);
        }

        ScrollPane scroll = new ScrollPane(scrollTable, skin.get(ScrollPane.ScrollPaneStyle.class));
        //scroll.setTransform(true);
        scroll.setPosition((sw-cw)/2.0f, (sh-ch));
        scroll.setSize(sw,ch);

        TextButton btnBack = new TextButton("Back", style_button);
        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HomeScreen(game));
            }
        });

        buttonGlobalScoreTable.row().fillX().expandX();
        buttonGlobalScoreTable.add(btnBack).fillX().width(cw/6.0f).height(cw/11.0f);

        scrollTable.setBackground(textureRegionDrawableBg);

        tableGlobalScore.row().colspan(3).expandX().fillX();
        tableGlobalScore.add(scroll);
        tableGlobalScore.row().colspan(3).expandX().fillX();
        tableGlobalScore.add(buttonGlobalScoreTable).colspan(3);


        btnGlobalScore = new TextButton("Local Scores", style_button);

        buttonGlobalScoreTable.add(btnGlobalScore).fillX().width(cw/2.0f).height(cw/11.0f);


    }
    @Override
    public void render(float delta) {

        //buttons();
        Gdx.gl.glClearColor(0,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(scoreboard_bg,0,0);
        game.batch.end();

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

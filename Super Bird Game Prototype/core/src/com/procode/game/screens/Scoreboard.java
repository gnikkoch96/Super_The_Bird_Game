package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

import java.util.ArrayList;

public class Scoreboard implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Texture background,scoreboard_bg;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Button btnContinue;
    private ArrayList<Label> users;

    public Scoreboard(SuperBirdGame g) {
        game = g;
        viewport = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        //atlas = new TextureAtlas("font-export.fnt");
        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);
        scoreboard_bg = ImageFunctions.resize("background stuff/scoreboard.png", SuperBirdGame.ANDROID_WIDTH,SuperBirdGame.ANDROID_HEIGHT);
        Skin skin = new Skin(Gdx.files.internal("comic-ui.json"));
        Container<Table> tableContainer = new Container<Table>();
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);

        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;

        font = fontGenerator.generateFont(fontParameter);


        float sw = SuperBirdGame.ANDROID_WIDTH;
        float sh = SuperBirdGame.ANDROID_HEIGHT;

        float cw = sw * 0.7f;
        float ch = sh * 0.6f;

        tableContainer.setSize(cw,ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);

        Table table = new Table(skin);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;

        String nameSpace = "                     ";


        Label topLabel = new Label("Username:" + nameSpace + "Score:", labelStyle);
        topLabel.setAlignment(Align.center);

        table.row().colspan(3).expandX().fillX();
        table.add(topLabel).fillX();
        for(int i =0; i < 20; i++){
            Label user = new Label("Robin" + i + nameSpace + "        600", labelStyle);
            table.row().colspan(3).expandX().fillX();
            table.add(user);
        }

        TextButton.TextButtonStyle style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 90;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        btnContinue = new TextButton("Back", style_button);

        btnContinue.setSize(200,200);
        btnContinue.setVisible(true);

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.WHITE);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        Table buttonTable = new Table(skin);

        btnContinue.setPosition((sw-cw)/2.0f, sh - 250);
        btnContinue.setSize(400,200);


        table.row().expandX().fillX();
        tableContainer.setBackground(textureRegionDrawableBg);
        tableContainer.setActor(table);

        ScrollPane scroll = new ScrollPane(tableContainer, skin.get(ScrollPane.ScrollPaneStyle.class));
        //scroll.setTransform(true);
        scroll.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);
        scroll.setSize(cw,ch);
        stage.addActor(scroll);
        stage.addActor(btnContinue);

        btnContinue.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HomeScreen(game));
            }
        });

    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {
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

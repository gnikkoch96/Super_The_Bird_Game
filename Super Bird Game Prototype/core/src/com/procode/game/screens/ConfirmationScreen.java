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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

import java.util.HashMap;


public class ConfirmationScreen implements Screen {
    private SuperBirdGame game;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Texture background;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Button btnContinue;

    public ConfirmationScreen(SuperBirdGame g, HashMap<String,String> map){
        game = g;
        viewport = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        //atlas = new TextureAtlas("font-export.fnt");
        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);


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
        float ch = sh * 0.5f;

        tableContainer.setSize(cw,ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);

        Table table = new Table(skin);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;

        Label topLabel = new Label("Congratulations your account has been verified", labelStyle);

        topLabel.setAlignment(Align.center);
        Label name = new Label("Name: " + map.remove("name"),labelStyle);
        name.setAlignment(Align.center);
        Label username = new Label("Username: " + map.remove("username"), labelStyle);
        username.setAlignment(Align.center);
        Label password = new Label("Password: " + map.remove("password"), labelStyle);
        password.setAlignment(Align.center);
        Label email = new Label("Email: " + map.remove("email"), labelStyle);
        email.setAlignment(Align.center);

        TextButton.TextButtonStyle style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 90;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        btnContinue = new TextButton("Back", style_button);

        btnContinue.setSize(200,200);
        btnContinue.setVisible(true);

        Table buttonTable = new Table(skin);

        table.row().colspan(3).expandX().fillX();
        table.add(topLabel).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(name).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(username).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(password).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(email).fillX();
        table.row().expandX().fillX();
        table.add(buttonTable).colspan(3);
        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnContinue).width(cw/3.0f);


        tableContainer.setActor(table);
        stage.addActor(tableContainer);

        btnContinue.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoginScreen(game));
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        //render the stage and draw it
        game.batch.draw(background, 0, 0);
        game.batch.end();

        //validateAccount();
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
        skin.dispose();
        background.dispose();
        game.dispose();
        stage.dispose();
    }
}

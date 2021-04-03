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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

public class SettingScreen implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Texture background;

    public SettingScreen(SuperBirdGame g){
        game = g;
        viewport = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("comic-ui.json"));
        Container<Table> tableContainer = new Container<Table>();
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);

        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 120;

        font = fontGenerator.generateFont(fontParameter);


        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();

        float cw = sw * 0.7f;
        float ch = sh * 0.5f;

        tableContainer.setSize(cw,ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);

        Table table = new Table(skin);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;

        Label topLabel = new Label("Username:            Highest Scores:           Time:", labelStyle);
        topLabel.setAlignment(Align.center);
        Label name = new Label("1. Max            300",labelStyle);
        //name.setAlignment(Align.center);
        Label username = new Label("Username: " , labelStyle);
        username.setAlignment(Align.center);
        Label password = new Label("Password: " , labelStyle);
        password.setAlignment(Align.center);
        Label email = new Label("Email: " , labelStyle);
        email.setAlignment(Align.center);
        Label num = new Label("Num: ", labelStyle);
        num.setAlignment(Align.center);
        Label num1 = new Label("Num1: ", labelStyle);
        num.setAlignment(Align.center);
        Label num2 = new Label("Num2: ", labelStyle);
        num.setAlignment(Align.center);
        TextButton.TextButtonStyle style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 90;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        Slider slider = new Slider(0,100,1,false,skin);
        slider.setSize(viewport.getScreenWidth()/2,viewport.getScreenHeight()/2);
        slider.setPosition(viewport.getScreenWidth()/4,(viewport.getScreenHeight()/4) - 500);


        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.WHITE);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        Table buttonTable = new Table(skin);
        table.setBackground(textureRegionDrawableBg);
        table.setSize(500,500);
        table.row().colspan(3).expandX().fillX();
        table.add(topLabel);
        table.row().colspan(3).expandX().fillX();
        table.add(name).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(username).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(password);

        table.add(slider).expand(false,false);
        table.row().colspan(3).expandX().fillX();
        table.add(num1).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(num2).fillX();




        tableContainer.setActor(table);



        ScrollPane scroll = new ScrollPane(tableContainer, skin.get(ScrollPane.ScrollPaneStyle.class));
        scroll.setTransform(true);
        scroll.setPosition(0,viewport.getScreenHeight()/2);
        scroll.setSize(viewport.getScreenWidth(),viewport.getScreenHeight()/2);
        stage.addActor(tableContainer);
        stage.addActor(scroll);
        stage.addActor(slider);

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
        //skin.dispose();
       // background.dispose();
        game.dispose();
        stage.dispose();
    }
}

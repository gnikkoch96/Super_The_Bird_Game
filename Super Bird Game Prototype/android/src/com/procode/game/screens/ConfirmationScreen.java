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
import com.procode.game.User;
import com.procode.game.tools.ImageFunctions;

import java.util.HashMap;


public class ConfirmationScreen implements Screen {
    private SuperBirdGame game;
    private Stage stage;
    private Skin skin;
    private Texture background;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Button btnContinue;
    private Label topLabel,nameLabel,usernameLabel,passwordLabel,emailLabel;
    private  Container<Table> tableContainer;
    private String fullName, userName, password, email;
    private final float sw = SuperBirdGame.GAME_WIDTH;
    private final float sh = SuperBirdGame.GAME_HEIGHT;

    private final float cw = sw * 0.7f;
    private final float ch = sh * 0.5f;

    public ConfirmationScreen(SuperBirdGame g, HashMap<String,String> map){
        game = g;
        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        //atlas = new TextureAtlas("font-export.fnt");
        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);


        tableContainer = new Container<Table>();
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);

        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;

        font = fontGenerator.generateFont(fontParameter);
        tableContainer.setSize(cw,ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);

        fullName = map.remove("name");
        password = map.remove("password");
        email = map.remove("email");
        userName = map.remove("username");



    }

    @Override
    public void show() {

        accountConfirmed();
        upDateDatabase();
        stage.addActor(tableContainer);
    }

    //this method will prompt to the screen that the account is successfully created
    private void accountConfirmed(){
        Table table = new Table(skin);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;

        topLabel = new Label("Congratulations account has been verified", labelStyle);
        topLabel.setAlignment(Align.center);
        nameLabel = new Label("Name: " + fullName,labelStyle);
        nameLabel.setAlignment(Align.center);
        usernameLabel = new Label("Username: " + userName, labelStyle);
        usernameLabel.setAlignment(Align.center);
        passwordLabel = new Label("Password: " + password, labelStyle);
        passwordLabel.setAlignment(Align.center);
        emailLabel = new Label("Email: " + email, labelStyle);
        emailLabel.setAlignment(Align.center);

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
        table.add(nameLabel).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(usernameLabel).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(passwordLabel).fillX();
        table.row().colspan(3).expandX().fillX();
        table.add(emailLabel).fillX();
        table.row().expandX().fillX();
        table.add(buttonTable).colspan(3);
        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnContinue).width(cw/3.0f);

        btnContinue.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoginScreen(game));
            }
        });

        tableContainer.setActor(table);
    }

    //this method will update the database with new information from the user input
    private void upDateDatabase(){
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        user.InsertDataToDatabase();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255/255f, 127/255f, 39/255f,1);
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
    }
}

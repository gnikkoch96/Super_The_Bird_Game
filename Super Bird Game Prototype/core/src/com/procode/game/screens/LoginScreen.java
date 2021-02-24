package com.procode.game.screens;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



import com.procode.game.SuperBirdGame;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;


public class LoginScreen extends ApplicationAdapter implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private TextButton btnLogin;
    private TextField txtUserName;
    private TextureAtlas atlas;
    private TextField txtPassword;
    private Table table;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public LoginScreen(SuperBirdGame g){
        game = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        //atlas = new TextureAtlas("font-export.fnt");
        Skin skin = new Skin(Gdx.files.internal("comic-ui.json"));
        //Skin skin = new Skin(atlas);
        //Skin skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        //create a text button and send it to skin


        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 80;
        font = fontGenerator.generateFont(fontParameter);
        //this is for the user enter username
        TextField.TextFieldStyle ts = new TextField.TextFieldStyle();
        ts = skin.get(TextField.TextFieldStyle.class);
        ts.font = font;
        txtUserName = new TextField("", ts);
        //font.getData().setScale(1.0f);

        txtUserName.setSize(800,100);




        //txtUserName.getStyle().font.getData().scale(3);
        //txtUserName.getStyle().font.getData().setScale(8.0f);
        //.getFont("default-font").setScale(0.33f, 0.33f);
        txtUserName.setPosition(game.ANDROID_WIDTH/2 - 500,(game.ANDROID_HEIGHT/2) - 50);

        //txtUserName.set

        //add the user name in the stage
        stage.addActor(txtUserName);

        txtPassword = new TextField("", ts);
        txtPassword.setPasswordCharacter('*');
        txtPassword.setPosition(game.ANDROID_WIDTH/2 - 505,(game.ANDROID_HEIGHT/2) - 150);
        txtPassword.setPasswordMode(true);
        txtPassword.setSize(800,100);

        //then send the password into the stage
        stage.addActor(txtPassword);

        TextButton btnLogin = new TextButton("Log In", skin);
        font = new BitmapFont(Gdx.files.internal("font-export.fnt"), false);
        font.getData().setScale(2,2);
        skin.add("fonts", font);
        //set the position and size of the button
        btnLogin.setPosition(game.ANDROID_WIDTH/2 - 505,(game.ANDROID_HEIGHT/2) - 250);
        btnLogin.setSize(200,100);

        //set the listener for log in button
        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnLoginClicked();
            }
        });

        //then draw it on the screen by adding it to stage
        stage.addActor(btnLogin);
    }

    public void btnLoginClicked(){
        //just to see the user name in the console
        //System.out.println(txtUserName.getText());
        System.out.println(txtPassword.getText());
        game.setScreen(new PlayScreen(game));
    }
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render the stage and draw it
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height){

    }

    public void show(){

    }

    public void pause(){

    }

    public void resume(){

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}

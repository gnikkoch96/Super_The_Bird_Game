package com.procode.game.screens;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    private TextField txtPassword;
    private BitmapFont font;

    public LoginScreen(SuperBirdGame g){
        game = g;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("glassy-ui.json"));
        //Skin skin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        //create a text button and send it to skin
        TextButton btnLogin = new TextButton("Log In", skin);
        //set the position and size of the button
        btnLogin.setPosition(300,130);
        btnLogin.setSize(200,100);

        //set the listener for log in button
        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnLoginClicked();
            }
        });


        //this is for the user enter username
        txtUserName = new TextField("", skin.get(TextField.TextFieldStyle.class));
        //font.getData().setScale(1.0f);
        txtUserName.getStyle().font.getData().setScale(4.0f,4.0f);
                //.getFont("default-font").setScale(0.33f, 0.33f);
        txtUserName.setPosition(game.ANDROID_WIDTH/2 - 500,(game.ANDROID_HEIGHT/2) - 100);
        txtUserName.setSize(800,160);
        //txtUserName.set

        //add the user name in the stage
        stage.addActor(txtUserName);

        txtPassword = new TextField("", skin);
        txtPassword.setPasswordCharacter('*');
        txtPassword.setPosition(300,250);
        txtPassword.setPasswordMode(true);
        txtPassword.setSize(300,40);

        //then send the password into the stage
        stage.addActor(txtPassword);

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

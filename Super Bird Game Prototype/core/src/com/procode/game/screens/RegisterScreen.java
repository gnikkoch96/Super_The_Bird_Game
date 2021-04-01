package com.procode.game.screens;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

import java.util.HashMap;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;


public class RegisterScreen extends ApplicationAdapter implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private TextButton btnRegisterUser, btnBack, btnNext;
    private TextField userName, password, fullName, email;
    private TextureAtlas atlas;
    private Table table;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Label txtUserName, txtPassword, message, txtFullName, txtEmail;
    private Texture background;
    private TextField.TextFieldStyle txtFieldStyle;
    private Label.LabelStyle labelStyle;
    private TextButton.TextButtonStyle style_button;
    private Skin skin;
    private String username_input, user_full_name, user_email, user_password;


    public RegisterScreen(SuperBirdGame g){
        game = g;

        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        //atlas = new TextureAtlas("font-export.fnt");
        skin = new Skin(Gdx.files.internal("comic-ui.json"));

        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 130;

        font = fontGenerator.generateFont(fontParameter);

        //creates a style for the font
        txtFieldStyle = new TextField.TextFieldStyle();
        txtFieldStyle = skin.get(TextField.TextFieldStyle.class);
        txtFieldStyle.font = font;

        //=======Instantiate style for label======================
        labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;

        //===============================================
        style_button = new TextButton.TextButtonStyle();


    }

    public void userFullName(){
        txtFullName = new Label("Enter Full Name",labelStyle);
        txtFullName.setPosition(game.GAME_WIDTH /2 - 500,(game.GAME_HEIGHT /2));
        txtFullName.setSize(300,300);
        txtFullName.setVisible(false);

        fullName = new TextField("", txtFieldStyle);
        fullName.setPosition(game.GAME_WIDTH /2 - (game.GAME_HEIGHT /2),(game.GAME_HEIGHT /2) - 200);
        fullName.setSize(game.GAME_HEIGHT,game.GAME_HEIGHT /6);
        fullName.setVisible(false);

        stage.addActor(fullName);
        stage.addActor(txtFullName);
    }

    public void userEmail(){
        txtEmail = new Label("Enter Email",labelStyle);
        txtEmail.setPosition(game.GAME_WIDTH /2 - 500,(game.GAME_HEIGHT /2));
        txtEmail.setSize(300,300);
        txtEmail.setVisible(false);

        email = new TextField("", txtFieldStyle);
        email.setPosition(game.GAME_WIDTH /2 - (game.GAME_HEIGHT /2),(game.GAME_HEIGHT /2) - 200);
        email.setSize(game.GAME_HEIGHT,game.GAME_HEIGHT /6);
        email.setVisible(false);

        stage.addActor(email);
        stage.addActor(txtEmail);
    }

    public void enterUserName(){
        //=========This is the label for user name============
        txtUserName = new Label("Create UserName",labelStyle);
        txtUserName.setPosition(game.GAME_WIDTH /2 - 500,(game.GAME_HEIGHT /2));
        txtUserName.setSize(300,300);

        //=========Text field for the user name================
        userName = new TextField("", txtFieldStyle);
        userName.setSize(game.GAME_HEIGHT,game.GAME_HEIGHT /6);
        userName.setPosition(game.GAME_WIDTH /2 - (game.GAME_HEIGHT /2),(game.GAME_HEIGHT /2) - 200);

        System.out.println(username_input);

        stage.addActor(txtUserName);
        //set the actor UserName
        stage.addActor(userName);
    }

    public boolean validateAccount(){
        FreeTypeFontGenerator fg = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fp.size = 80;
        BitmapFont f = new BitmapFont();
        f = fontGenerator.generateFont(fp);
        Label.LabelStyle ls = new Label.LabelStyle();
        ls = skin.get(Label.LabelStyle.class);
        ls.font = f;
        if(username_input.length() >= 0 && username_input.length() < 6){
            if(message == null) {
                message = new Label("Username should be atleast 6 characters", ls);
                message.setPosition(game.GAME_WIDTH / 2 - (game.GAME_HEIGHT / 2), (game.GAME_HEIGHT / 2) - 400);
                message.setSize(300, 300);
                message.setVisible(true);
                stage.addActor(message);
            }
            return false;
        }else if(password.isVisible() == true && password.getText().length() == 0){


            return false;
        }else if(fullName.isVisible() == true && fullName.getText().length() == 0){

            return false;
        }else if(email.isVisible() == true && email.getText().length() == 0){

            return false;
        }
        return true;
    }


    public void userPassword(){
        txtPassword = new Label("Create Password",labelStyle);
        txtPassword.setPosition(game.GAME_WIDTH /2 - 500,(game.GAME_HEIGHT /2));
        txtPassword.setSize(300,300);
        txtPassword.setVisible(false);

        //=========Text field for the user password =============
        password = new TextField("", txtFieldStyle);
        password.setPasswordCharacter('*');
        password.setPosition(game.GAME_WIDTH /2 - (game.GAME_HEIGHT /2),(game.GAME_HEIGHT /2) - 200);
        password.setPasswordMode(true);
        password.setSize(game.GAME_HEIGHT,game.GAME_HEIGHT /6);
        password.setVisible(false);


        //then send the password into the stage
        stage.addActor(txtPassword);
        stage.addActor(password);
    }

    //this method contains all the buttons such as Login and SignUp
    public void buttons(){

        //setting style for the button and resizing the font
        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 90;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        btnBack = new TextButton("Back", style_button);
        btnBack.setPosition(game.GAME_WIDTH /2 - (game.GAME_HEIGHT /2),(game.GAME_HEIGHT /2) - 500);
        btnBack.setSize(400,200);
        btnBack.setVisible(true);

        //=================Next button =====================
        String text ="Next";

        if(email.isVisible() == true)
            text = "Register";

        btnNext = new TextButton(text, style_button);;
        //set the position and size of the button
        btnNext.setPosition((game.GAME_WIDTH /2) + 300,(game.GAME_HEIGHT /2) - 500);
        btnNext.setSize(400,200);
        btnNext.setVisible(true);


        btnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnNextClicked();
            }
        });

        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked();
            }
        });



        stage.addActor(btnBack);
        stage.addActor(btnNext);
    }

    public void btnNextClicked(){
        //just to see the user name in the console
        username_input = userName.getText();
        System.out.println("Clicked");
        if(validateAccount() == true && password.isVisible() == false
         && fullName.isVisible() == false && email.isVisible() == false) {
            txtUserName.setVisible(false);
            userName.setVisible(false);
            txtPassword.setVisible(true);
            password.setVisible(true);
            btnBack.setVisible(true);
            if(message != null)
                message.setVisible(false);
            //game.setScreen(new PlayScreen(game));
            System.out.println(userName.getText());
        }else if(validateAccount() == true && password.isVisible() == true){
            txtPassword.setVisible(false);
            password.setVisible(false);
            fullName.setVisible(true);
            txtFullName.setVisible(true);
            btnBack.setVisible(true);


        }else if(validateAccount() == true && fullName.isVisible() == true){
            txtFullName.setVisible(false);
            fullName.setVisible(false);
            txtEmail.setVisible(true);
            email.setVisible(true);


        }else if(validateAccount() == true && email.isVisible() == true){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("name", fullName.getText());
            map.put("username", userName.getText());
            map.put("password", password.getText());
            map.put("email", email.getText());
            game.setScreen(new ConfirmationScreen(game, map));
        }
        //game.setScreen(new PlayScreen(game));
    }

    public void btnBackClicked(){
        if(userName.isVisible() == true)
            game.setScreen(new LoginScreen(game));
        if(password.isVisible() == true){
            password.setVisible(false);
            txtPassword.setVisible(false);
            userName.setVisible(true);
            txtUserName.setVisible(true);
        }
        if(fullName.isVisible() == true){
            fullName.setVisible(false);
            txtFullName.setVisible(false);
            txtPassword.setVisible(true);
            password.setVisible(true);
        }
        if(email.isVisible() == true){
            email.setVisible(false);
            txtEmail.setVisible(false);
            fullName.setVisible(true);
            txtFullName.setVisible(true);
        }


    }



    //this method is from the library of libdx from Application Adapter
    //It runs automatically just like the render.
    public void show(){

        enterUserName();
        userPassword();
        userFullName();
        userEmail();

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        buttons();
        game.batch.begin();
        //render the stage and draw it
        game.batch.draw(background, 0, 0);
        game.batch.end();

        //validateAccount();
        stage.act(delta);
        stage.draw();




        //System.out.println("legnth " + username_input);

    }

    public void resize(int width, int height){
        game.viewport.update(width, height);
        game.camera.position.set(game.GAME_WIDTH/2, game.GAME_HEIGHT/2, 0);
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
        fontGenerator.dispose();
        background.dispose();
        game.dispose();
        stage.dispose();
        atlas.dispose();
        font.dispose();
        skin.dispose();
    }

    public String getFullName(){
        return fullName.getText();
    }

}
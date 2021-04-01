package com.procode.game.screens;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.procode.game.sprites.Background;
import com.procode.game.tools.ImageFunctions;


public class LoginScreen extends ApplicationAdapter implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private TextButton btnLogin, btnSignUp;
    private TextField userName;
    private TextureAtlas atlas;
    private TextField password;
    private Table table;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Label txtUserName, txtPassword;
    private Texture background;
    private TextField.TextFieldStyle txtFieldStyle;
    private Label.LabelStyle labelStyle;
    private TextButton.TextButtonStyle style_button;
    private Skin skin;
    private Background bg;
    private int moveHills_x, moveMountain_x, moveClouds_x, getMoveClouds_y;

    public LoginScreen(SuperBirdGame g){
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
        //fontParameter.spaceY = 100;
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

        //=======Background stuff ===================
        bg = new Background();

        moveHills_x = game.GAME_WIDTH;
        moveMountain_x = game.GAME_WIDTH - 50;
        moveClouds_x = game.GAME_WIDTH;
    }


    public void userName(){
        //=========This is the label for user name============
        txtUserName = new Label("Enter Username",labelStyle);
        txtUserName.setPosition(game.GAME_WIDTH /2 - 500,(game.GAME_HEIGHT /2) + 150 );
        txtUserName.setSize(300,300);

        //=========Text field for the user name================
        userName = new TextField("", txtFieldStyle);
        userName.setSize(game.GAME_HEIGHT,game.GAME_HEIGHT /6);
        userName.setPosition(game.GAME_WIDTH /2 - (game.GAME_HEIGHT /2),(game.GAME_HEIGHT /2));


        stage.addActor(txtUserName);
        //set the actor UserName
        stage.addActor(userName);
    }

    public void userPassword(){
        txtPassword = new Label("Enter Password",labelStyle);
        txtPassword.setPosition(game.GAME_WIDTH /2 - 500,(game.GAME_HEIGHT /2) - 200 );
        txtPassword.setSize(300,300);

        //=========Text field for the user password =============
        password = new TextField("", txtFieldStyle);
        password.setPasswordCharacter('*');
        password.setPosition(game.GAME_WIDTH /2 - (game.GAME_HEIGHT /2),(game.GAME_HEIGHT /2) - 350);
        password.setPasswordMode(true);
        password.setSize(game.GAME_HEIGHT,game.GAME_HEIGHT /6);

        //then send the password into the stage
        stage.addActor(txtPassword);
        stage.addActor(password);
    }

    //this method contains all the buttons such as Login and SignUp
    public void buttons(){
        //setting style for the button and resizing the font
        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 100;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        //==================Button Log In =====================
        btnLogin = new TextButton("Log In", style_button);
        skin.add("fonts", font);
        //set the position and size of the button
        btnLogin.setPosition(game.GAME_WIDTH /2 - (game.GAME_HEIGHT /2),(game.GAME_HEIGHT /2) - 550);
        btnLogin.setSize(400,200);

        //set the listener for log in button
        btnLogin.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnLoginClicked();
            }
        });
        //=================Button Sign Up =====================

        btnSignUp = new TextButton("Sign Up", style_button);;
        //set the position and size of the button
        btnSignUp.setPosition((game.GAME_WIDTH /2) + 300,(game.GAME_HEIGHT /2) - 550);
        btnSignUp.setSize(400,200);

        //set the listener for SignUp button
        btnSignUp.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnSignUpClicked();
            }
        });

        //=====================================================

        stage.addActor(btnLogin);
        stage.addActor(btnSignUp);
    }

    public void btnLoginClicked(){
        //just to see the user name in the console
        System.out.println(password.getText());
        game.setScreen(new HomeScreen(game));
    }

    public void btnSignUpClicked(){
        game.setScreen(new RegisterScreen(game));

    }

    //this method is from the library of libdx from Application Adapter
    //It runs automatically just like the render.
    public void show(){
        userName();
        userPassword();
        buttons();
    }

    public void upDate(){
        if(moveHills_x > -(game.GAME_WIDTH /4))
            moveHills_x -= 5;
        else
            moveHills_x = game.GAME_WIDTH;

        if(moveClouds_x > -(game.GAME_WIDTH /2))
            moveClouds_x -= 5;
        else
            moveClouds_x = game.GAME_WIDTH;

        if(moveMountain_x > -(game.GAME_WIDTH))
            moveMountain_x -= 5;
        else
            moveMountain_x = game.GAME_WIDTH;

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        upDate();
        game.batch.begin();
        //render the stage and draw it
        //game.batch.draw(background, 0, 0);
        game.batch.draw(bg.getBackgroundSky(),0,0);
        game.batch.draw(bg.getBackground_hills(),moveHills_x,0);
        game.batch.draw(bg.getBackgroundMountains(),moveMountain_x,0);
        game.batch.draw(bg.getBackgroundClouds(),moveClouds_x,0);
        game.batch.end();
        stage.act(delta);
        stage.draw();

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

}
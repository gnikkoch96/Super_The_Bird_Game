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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
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
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Label txtUserName, txtPassword, message, txtFullName, txtEmail;
    private SpriteBatch batch;
    private Viewport viewport;
    private Texture background;
    private TextField.TextFieldStyle txtFieldStyle;
    private Label.LabelStyle labelStyle;
    private TextButton.TextButtonStyle style_button;
    private Skin skin;
    private String username_input, user_full_name, user_email, user_password;
    private Container<Table> tableContainer0, tableContainer1,tableContainer2,tableContainer3;
    private Table table, buttonTable, userNameTable, emailTable, fullNameTable, passwordTable;


    public RegisterScreen(SuperBirdGame g){
        game = g;

        viewport = new FitViewport(SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        //atlas = new TextureAtlas("font-export.fnt");
        skin = new Skin(Gdx.files.internal("comic-ui.json"));

        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.ANDROID_WIDTH, SuperBirdGame.ANDROID_HEIGHT);
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

        //========Setting style for the buttons ===================
        style_button = new TextButton.TextButtonStyle();
        //setting style for the button and resizing the font
        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 90;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        //========Setting the sizes of the containers ===================
        float sw = SuperBirdGame.ANDROID_WIDTH;
        float sh = SuperBirdGame.ANDROID_HEIGHT;

        float cw = sw * 0.7f;
        float ch = sh * 0.5f;


        /*for(int i =0; i<4;i++) {
            tableContainer[i] = new Container<Table>();
            tableContainer[i].setSize(sw, sh);
            tableContainer[i].setPosition(0,0);
        }*/
        //tableContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);

        tableContainer0 = new Container<Table>();
        tableContainer0.setSize(sw, sh);
        tableContainer0.setPosition(0,0);

        tableContainer1 = new Container<Table>();
        tableContainer1.setSize(sw, sh);
        tableContainer1.setPosition(0,0);

        tableContainer2 = new Container<Table>();
        tableContainer2.setSize(sw, sh);
        tableContainer2.setPosition(0,0);

        tableContainer3 = new Container<Table>();
        tableContainer3.setSize(sw, sh);
        tableContainer3.setPosition(0,0);





        table = new Table(skin);
        table.setSize(sw,sh);

        //instantiating all tables
        userNameTable = new Table(skin);
        emailTable = new Table(skin);
        passwordTable = new Table(skin);
        fullNameTable = new Table(skin);
        buttonTable = new Table(skin);




    }

    public void userFullName(){
        txtFullName = new Label("Enter Full Name",labelStyle);
        txtFullName.setAlignment(Align.center);
        fullName = new TextField("", txtFieldStyle);


        TextButton btnNext = new TextButton("Next", style_button);
        TextButton btnBack = new TextButton("Back", style_button);



        btnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //go to password
                btnNextClicked("Email");
            }
        });

        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked();
            }
        });


        Table buttonTable = new Table(skin);
        fullNameTable.add(txtFullName).fillX().width((float)game.ANDROID_WIDTH/2);
        fullNameTable.row().colspan(3).expandX().fillX();
        fullNameTable.add(fullName).fillX().width((float)game.ANDROID_WIDTH/2).height((float)game.ANDROID_HEIGHT/6);


        fullNameTable.row().colspan(3).expandX().fillX();
        fullNameTable.add(buttonTable);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnBack).width(((float)game.ANDROID_WIDTH)/6.0f).height(((float)game.ANDROID_HEIGHT*0.5f)/3.0f).spaceRight((float)game.ANDROID_WIDTH/6);
        buttonTable.add(btnNext).width(((float)game.ANDROID_WIDTH)/6.0f).height(((float)game.ANDROID_HEIGHT*0.5f)/3.0f).expand();

        //adding the table into the container
     //   tableContainer.setActor(table);
        tableContainer2.setActor(fullNameTable);
        tableContainer2.setVisible(false);
    }

    public void userEmail(){
        txtEmail = new Label("Enter Email",labelStyle);
        txtEmail.setAlignment(Align.center);
        email = new TextField("", txtFieldStyle);


        TextButton btnNext = new TextButton("Next", style_button);
        TextButton btnBack = new TextButton("Back", style_button);



        btnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //go to password
                btnNextClicked("Register");
            }
        });

        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked();
            }
        });


        Table buttonTable = new Table(skin);

        //Adding the label and the text field in the table
        emailTable.row().colspan(3).expandX().fillX();
        emailTable.add(txtEmail).fillX().width((float)game.ANDROID_WIDTH/2);
        emailTable.row().colspan(3).expandX().fillX();
        emailTable.add(email).fillX().width((float)game.ANDROID_WIDTH/2).height((float)game.ANDROID_HEIGHT/6);

        emailTable.row().colspan(3).expandX().fillX();
        emailTable.add(buttonTable);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnBack).width(((float)game.ANDROID_WIDTH)/6.0f).height(((float)game.ANDROID_HEIGHT*0.5f)/3.0f).spaceRight((float)game.ANDROID_WIDTH/6);
        buttonTable.add(btnNext).width(((float)game.ANDROID_WIDTH)/6.0f).height(((float)game.ANDROID_HEIGHT*0.5f)/3.0f).expand();

        //adding the table into the container
      //  tableContainer.setActor(table);
        tableContainer3.setActor(emailTable);
        tableContainer3.setVisible(false);
    }

    public void enterUserName(){
        //=========This is the label for user name============
        txtUserName = new Label("Create UserName",labelStyle);
        //txtUserName.setPosition(game.ANDROID_WIDTH/2 - 500,(game.ANDROID_HEIGHT/2));
        //txtUserName.setSize(300,300);
        txtUserName.setAlignment(Align.center);

        //=========Text field for the user name================
        userName = new TextField("", txtFieldStyle);
        userName.setSize(game.ANDROID_HEIGHT,game.ANDROID_HEIGHT/6);
        userName.setPosition(game.ANDROID_WIDTH/2 - (game.ANDROID_HEIGHT/2),(game.ANDROID_HEIGHT/2) - 200);

        System.out.println(username_input);

       // stage.addActor(txtUserName);
        //set the actor UserName
       // stage.addActor(userName);

        //Adding the label and the text field in the table
        //table.row().colspan(3).expandX().fillX();
       // table.row().colspan(3).expandX().fillX();
        userNameTable.row().colspan(3).expandX().fillX();
        userNameTable.add(txtUserName).fillX().width((float)game.ANDROID_WIDTH/2);
        userNameTable.row().colspan(3).expandX().fillX();
        userNameTable.add(userName).fillX().width((float)game.ANDROID_WIDTH/2).height((float)game.ANDROID_HEIGHT/6);

        //adding the table into the container
        //tableContainer.setActor(table);


        //=================Next button =====================
        String text ="Next";

        //if(email.isVisible() == true && emailTable.isVisible() == true)
        //    text = "Register";

        TextButton btnNext = new TextButton(text, style_button);
        TextButton btnBack = new TextButton("Back", style_button);
        //set the position and size of the button
        btnNext.setPosition((game.ANDROID_WIDTH/2) + 300,(game.ANDROID_HEIGHT/2) - 500);
        btnNext.setSize(400,200);
        btnNext.setVisible(true);


        btnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //go to password
                btnNextClicked("Password");
            }
        });

        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked();
            }
        });


        Table buttonTable = new Table(skin);

        userNameTable.row().colspan(3).expandX().fillX();
        userNameTable.add(buttonTable);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnBack).width(((float)game.ANDROID_WIDTH)/6.0f).height(((float)game.ANDROID_HEIGHT*0.5f)/3.0f).spaceRight((float)game.ANDROID_WIDTH/6);
        buttonTable.add(btnNext).width(((float)game.ANDROID_WIDTH)/6.0f).height(((float)game.ANDROID_HEIGHT*0.5f)/3.0f).expand();



        tableContainer0.setActor(userNameTable);
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
                message.setPosition(game.ANDROID_WIDTH / 2 - (game.ANDROID_HEIGHT / 2), (game.ANDROID_HEIGHT / 2) - 400);
                message.setSize(300, 300);
                message.setVisible(true);
                stage.addActor(message);
            }
            return false;
        }else if(password.isVisible() == true && password.getText().length() == 0){


            return false;
        }/*else if(fullName.isVisible() == true && fullName.getText().length() == 0){

            return false;
        }else if(email.isVisible() == true && email.getText().length() == 0){

            return false;
        }*/
        return true;
    }


    public void userPassword(){
        txtPassword = new Label("Create Password",labelStyle);
        //txtPassword.setPosition(game.ANDROID_WIDTH/2 - 500,(game.ANDROID_HEIGHT/2));
        //txtPassword.setSize(300,300);
        txtPassword.setAlignment(Align.center);
      //  txtPassword.setVisible(false);

        //=========Text field for the user password =============
        password = new TextField("", txtFieldStyle);
        password.setPasswordCharacter('*');
        password.setPosition(game.ANDROID_WIDTH/2 - (game.ANDROID_HEIGHT/2),(game.ANDROID_HEIGHT/2) - 200);
        password.setPasswordMode(true);
        password.setSize(game.ANDROID_HEIGHT,game.ANDROID_HEIGHT/6);
      //  password.setVisible(false);


        //Adding the label and the text field in the table
        passwordTable.row().colspan(3).expandX().fillX();
        passwordTable.add(txtPassword).fillX().width((float)game.ANDROID_WIDTH/2);
        passwordTable.row().colspan(3).expandX().fillX();
        passwordTable.add(password).fillX().width((float)game.ANDROID_WIDTH/2).height((float)game.ANDROID_HEIGHT/6);
        //then send the password into the stage
       // stage.addActor(txtPassword);
       // stage.addActor(password);
        //adding the table into the container
      //  tableContainer.setActor(table);

        //=================Next button =====================

        TextButton btnNext = new TextButton("Next", style_button);
        TextButton btnBack = new TextButton("Back", style_button);



        btnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set the screen to prompt full name
                btnNextClicked("FullName");
            }
        });

        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked();
            }
        });

        Table buttonTable = new Table(skin);

        passwordTable.row().colspan(3).expandX().fillX();
        passwordTable.add(buttonTable);



        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(btnBack).width(((float)game.ANDROID_WIDTH)/6.0f).height(((float)game.ANDROID_HEIGHT*0.5f)/3.0f).spaceRight((float)game.ANDROID_WIDTH/6);
        buttonTable.add(btnNext).width(((float)game.ANDROID_WIDTH)/6.0f).height(((float)game.ANDROID_HEIGHT*0.5f)/3.0f).expand();

        tableContainer1.setVisible(false);
        tableContainer1.setActor(passwordTable);
    }



    //this method will change the state of the screen.
    public void btnNextClicked(String state){

        if(state.equals("Password")){
            tableContainer0.setVisible(false);
            tableContainer1.setVisible(true);
        }else if(state.equals("FullName")){
            tableContainer1.setVisible(false);
            tableContainer2.setVisible(true);
        }else if(state.equals("Email")){
            tableContainer2.setVisible(false);
            tableContainer3.setVisible(true);
        }else if(state.equals("Register")){
            game.setScreen(new ConfirmationScreen(game, new HashMap<String, String>()));
        }

    }

    public void btnNextClicked(){
        //just to see the user name in the console

        username_input = userName.getText();

        System.out.println("Clicked");
        //if(validateAccount() == true && password.isVisible() == false
       // && fullName.isVisible() == false && email.isVisible() == false) {

        tableContainer0.setVisible(false);
        tableContainer1.setVisible(true);

        if(validateAccount() == true && tableContainer1.isVisible() == false) {
            tableContainer0.setVisible(false);
            tableContainer1.setVisible(true);

         //   userNameTable.setVisible(false);
         //   txtUserName.setVisible(false);
           // userName.setVisible(false);
          //  passwordTable.setVisible(true);
           // txtPassword.setVisible(true);
          //  password.setVisible(true);
          //  btnBack.setVisible(true);
           // if (message != null)
          //      message.setVisible(false);
            //game.setScreen(new PlayScreen(game));

            //}else if(validateAccount() == true && password.isVisible() == true){
        }else if(validateAccount() == true && passwordTable.isVisible() == true) {
            passwordTable.setVisible(false);
            txtPassword.setVisible(false);
            password.setVisible(false);
            fullNameTable.setVisible(true);
            fullName.setVisible(true);
            txtFullName.setVisible(true);
            btnBack.setVisible(true);


            //}else if(validateAccount() == true && fullName.isVisible() == true){
        }else if(validateAccount() == true && fullNameTable.isVisible() == true){
            fullNameTable.setVisible(false);
            txtFullName.setVisible(false);
            fullName.setVisible(false);
            emailTable.setVisible(true);
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
        if(userName.isVisible() == true && userNameTable.isVisible())
            game.setScreen(new LoginScreen(game));
        if(password.isVisible() == true && passwordTable.isVisible() == true){
            passwordTable.setVisible(false);
            password.setVisible(false);
            txtPassword.setVisible(false);
            userNameTable.setVisible(true);
            userName.setVisible(true);
            txtUserName.setVisible(true);
        }
        if(fullName.isVisible() == true && fullNameTable.isVisible()){
            fullNameTable.setVisible(false);
            fullName.setVisible(false);
            txtFullName.setVisible(false);
            passwordTable.setVisible(false);
            txtPassword.setVisible(true);
            password.setVisible(true);
        }
        if(email.isVisible() == true && emailTable.isVisible()){
            emailTable.setVisible(false);
            email.setVisible(false);
            txtEmail.setVisible(false);
            fullNameTable.setVisible(true);
            fullName.setVisible(true);
            txtFullName.setVisible(true);
        }


    }



    //this method is from the library of libdx from Application Adapter
    //It runs automatically just like the render.
    public void show(){

        //buttons();

        enterUserName();
        userPassword();
        userFullName();
        userEmail();

        //


        //set the stage and the table container
       // for(int i=0; i < 5; i++)
        stage.addActor(tableContainer0);
        stage.addActor(tableContainer1);
        stage.addActor(tableContainer2);
        stage.addActor(tableContainer3);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //buttons();
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
        batch.dispose();
        skin.dispose();
    }

    public String getFullName(){
        return fullName.getText();
    }

}
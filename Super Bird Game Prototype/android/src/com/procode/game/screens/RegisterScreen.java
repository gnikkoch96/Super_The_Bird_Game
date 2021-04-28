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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.Database;
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
    private TextButton ubtnBack, pbtnBack, ebtnBack, fbtnBack;
    private TextButton ubtnNext, pbtnNext, ebtnNext, fbtnNext;
    private TextField userName, password, fullName, email;
    private TextureAtlas atlas;
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
    private Container<Table> tableContainer0, tableContainer1,tableContainer2,tableContainer3, accountImageContainer;
    private Table table, buttonTable, userNameTable, emailTable, fullNameTable, passwordTable, accountImageTable;
    private Container<Table> accountEmailImageContainer;
    private Table accountEmailImageTable;


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

        //========Setting style for the buttons ===================
        style_button = new TextButton.TextButtonStyle();
        //setting style for the button and resizing the font
        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 90;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        //========Setting the sizes of the containers ===================
        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.7f;
        float ch = sh * 0.5f;

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

        accountImageContainer = new Container<Table>();
        accountImageContainer.setSize(sw,sh);
        accountImageContainer.setPosition(0,0);

        accountEmailImageContainer = new Container<Table>();
        accountEmailImageContainer.setSize(sw,sh);
        accountEmailImageContainer.setPosition(0,0);

        table = new Table(skin);
        table.setSize(sw,sh);

        //instantiating all tables
        userNameTable = new Table(skin);
        emailTable = new Table(skin);
        passwordTable = new Table(skin);
        fullNameTable = new Table(skin);
        buttonTable = new Table(skin);
        accountImageTable = new Table(skin);
        accountEmailImageTable = new Table(skin);

    }

    public void accountExist(){
        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.7f;
        float ch = sh * 0.5f;
        Image accountexist = new Image(new Texture("background stuff//accnt_exist.png"));
        Image accountEmailExist = new Image(new Texture("background stuff//accnt_email_exist.png"));

        accountImageTable.row().colspan(3).expandX().fillX();
        accountImageTable.add(accountexist).fillX().width(cw /2.0f).height(ch /2.0f).padBottom(sh/1.5f);

        accountEmailImageTable.row().colspan(3).expandX().fillX();
        accountEmailImageTable.add(accountEmailExist).fillX().width(cw /2.0f).height(ch /2.0f).padBottom(sh/1.5f);

        accountImageContainer.setActor(accountImageTable);
        accountEmailImageContainer.setActor(accountEmailImageTable);
        accountImageContainer.setVisible(false);
        accountEmailImageContainer.setVisible(false);
    }

    public void btnclickListeners(){

        fbtnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //go to password
                btnNextClicked("FullName");
            }
        });

        fbtnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked("FullName");
            }
        });



        ebtnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked("Email");
            }
        });

        ubtnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Database database = new Database();
                database.checkDatabase(userName.getText(), "random");
                if(Database.usernameStatus.equals("DNE") ){
                    btnNextClicked("Username");
                }else if(Database.usernameStatus.equals("Exist")){
                    System.out.println("The User Already Exist");
                    accountImageContainer.setVisible(true);
                }

                return true;
            }
        });


        pbtnNext.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //set the screen to prompt full name
                btnNextClicked("Password");
            }
        });

        pbtnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked("Password");
            }
        });

    }

    public void userFullName(){
        txtFullName = new Label("Enter Full Name",labelStyle);
        txtFullName.setAlignment(Align.center);
        fullName = new TextField("", txtFieldStyle);


        fbtnNext = new TextButton("Next", style_button);
        fbtnBack = new TextButton("Back", style_button);
        Table buttonTable = new Table(skin);
        fullNameTable.add(txtFullName).fillX().width((float)game.GAME_WIDTH/2);
        fullNameTable.row().colspan(3).expandX().fillX();
        fullNameTable.add(fullName).fillX().width((float)game.GAME_WIDTH/2).height((float)game.GAME_HEIGHT/6);


        fullNameTable.row().colspan(3).expandX().fillX();
        fullNameTable.add(buttonTable);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(fbtnBack).width(((float)game.GAME_WIDTH)/6.0f).height(((float)game.GAME_HEIGHT*0.5f)/3.0f).spaceRight((float)game.GAME_WIDTH/6);
        buttonTable.add(fbtnNext).width(((float)game.GAME_WIDTH)/6.0f).height(((float)game.GAME_HEIGHT*0.5f)/3.0f).expand();

        tableContainer2.setActor(fullNameTable);
        tableContainer2.setVisible(false);
    }

    public void userEmail(){
        txtEmail = new Label("Enter Email",labelStyle);
        txtEmail.setAlignment(Align.center);
        email = new TextField("", txtFieldStyle);


        ebtnNext = new TextButton("Next", style_button);
        ebtnBack = new TextButton("Back", style_button);


        ebtnNext.addListener(new ClickListener(){

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Database database = new Database();

                database.checkEmail(email.getText());
                //go to password

                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //go to password
            }
        });


        Table buttonTable = new Table(skin);

        //Adding the label and the text field in the table
        emailTable.row().colspan(3).expandX().fillX();
        emailTable.add(txtEmail).fillX().width((float)game.GAME_WIDTH/2);
        emailTable.row().colspan(3).expandX().fillX();
        emailTable.add(email).fillX().width((float)game.GAME_WIDTH/2).height((float)game.GAME_HEIGHT/6);

        emailTable.row().colspan(3).expandX().fillX();
        emailTable.add(buttonTable);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(ebtnBack).width(((float)game.GAME_WIDTH)/6.0f).height(((float)game.GAME_HEIGHT*0.5f)/3.0f).spaceRight((float)game.GAME_WIDTH/6);
        buttonTable.add(ebtnNext).width(((float)game.GAME_WIDTH)/6.0f).height(((float)game.GAME_HEIGHT*0.5f)/3.0f).expand();

        //adding the table into the container
        tableContainer3.setActor(emailTable);
        tableContainer3.setVisible(false);
    }

    public void setUpNewScreen(){
        if(Database.emailStatus.equals("DNE"))
            btnNextClicked("Email");
        else if(Database.emailStatus.equals("Exist"))
            accountEmailImageContainer.setVisible(true);
    }

    public void enterUserName(){
        //=========This is the label for user name============
        txtUserName = new Label("Create UserName",labelStyle);
        txtUserName.setAlignment(Align.center);

        //=========Text field for the user name================
        userName = new TextField("", txtFieldStyle);


        System.out.println(username_input);

        userNameTable.row().colspan(3).expandX().fillX();
        userNameTable.add(txtUserName).fillX().width((float)game.GAME_WIDTH/2);
        userNameTable.row().colspan(3).expandX().fillX();
        userNameTable.add(userName).fillX().width((float)game.GAME_WIDTH/2).height((float)game.GAME_HEIGHT/6);


        ubtnNext = new TextButton("Next", style_button);
        ubtnBack = new TextButton("Back", style_button);

        ubtnNext.setVisible(true);
       ubtnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                btnBackClicked("Username");
            }
        });


        Table buttonTable = new Table(skin);

        userNameTable.row().colspan(3).expandX().fillX();
        userNameTable.add(buttonTable);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(ubtnBack).width(((float)game.GAME_WIDTH)/6.0f).height(((float)game.GAME_HEIGHT*0.5f)/3.0f).spaceRight((float)game.GAME_WIDTH/6);
        buttonTable.add(ubtnNext).width(((float)game.GAME_WIDTH)/6.0f).height(((float)game.GAME_HEIGHT*0.5f)/3.0f).expand();



        tableContainer0.setActor(userNameTable);
    }



    public void userPassword(){
        txtPassword = new Label("Create Password",labelStyle);
        txtPassword.setAlignment(Align.center);

        //=========Text field for the user password =============
        password = new TextField("", txtFieldStyle);
        password.setPasswordCharacter('*');
        password.setPasswordMode(true);

        //Adding the label and the text field in the table
        passwordTable.row().colspan(3).expandX().fillX();
        passwordTable.add(txtPassword).fillX().width((float)game.GAME_WIDTH/2);
        passwordTable.row().colspan(3).expandX().fillX();
        passwordTable.add(password).fillX().width((float)game.GAME_WIDTH/2).height((float)game.GAME_HEIGHT/6);

        //=================Next button =====================

        pbtnNext = new TextButton("Next", style_button);
        pbtnBack = new TextButton("Back", style_button);



        Table buttonTable = new Table(skin);

        passwordTable.row().colspan(3).expandX().fillX();
        passwordTable.add(buttonTable);

        buttonTable.pad(16);
        buttonTable.row().fillX().expandX();
        buttonTable.add(pbtnBack).width(((float)game.GAME_WIDTH)/6.0f).height(((float)game.GAME_HEIGHT*0.5f)/3.0f).spaceRight((float)game.GAME_WIDTH/6);
        buttonTable.add(pbtnNext).width(((float)game.GAME_WIDTH)/6.0f).height(((float)game.GAME_HEIGHT*0.5f)/3.0f).expand();

        tableContainer1.setVisible(false);
        tableContainer1.setActor(passwordTable);
    }


    //this method will return it back from previous state
    public void btnBackClicked(String state){
        if(state.equals("Username")){
            game.setScreen(new LoginScreen(game));
        }else if(state.equals("Password")){
            tableContainer1.setVisible(false);
            tableContainer0.setVisible(true);
        }else if(state.equals("FullName")){
            tableContainer2.setVisible(false);
            tableContainer1.setVisible(true);
        }else if(state.equals("Email")){
            tableContainer3.setVisible(false);
            tableContainer2.setVisible(true);
        }
    }


    //this method will change the state of the screen.
    public void btnNextClicked(String state){

        if(state.equals("Username")){
            tableContainer0.setVisible(false);
            tableContainer1.setVisible(true);
        }else if(state.equals("Password")){
            accountImageContainer.setVisible(false);
            tableContainer1.setVisible(false);
            tableContainer2.setVisible(true);
        }else if(state.equals("FullName")){
            tableContainer2.setVisible(false);
            tableContainer3.setVisible(true);
        }else if(state.equals("Email")){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("name", fullName.getText());
            map.put("username", userName.getText());
            map.put("password", password.getText());
            map.put("email", email.getText());
            game.setScreen(new ConfirmationScreen(game, map));
        }

    }



    //this method is from the library of libdx from Application Adapter
    //It runs automatically just like the render.
    public void show(){

        enterUserName();
        userPassword();
        userFullName();
        userEmail();
        accountExist();

        //set the stage and the table container
       // for(int i=0; i < 5; i++)
        stage.addActor(tableContainer0);
        stage.addActor(tableContainer1);
        stage.addActor(tableContainer2);
        stage.addActor(tableContainer3);
        stage.addActor(accountImageContainer);
        stage.addActor(accountEmailImageContainer);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(255/255f, 127/255f, 39/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //buttons();
        setUpNewScreen();
        btnclickListeners();
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
package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.procode.game.Database;
import com.procode.game.SuperBirdGame;
import com.procode.game.User;
import com.procode.game.tools.ImageFunctions;

public class EditAccountScreen implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private Skin skin;
    private Container<Table> tableContainer;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private TextButton.TextButtonStyle style_button;
    private Label txtUserName, txtEmail, txtFullName, incorrectInfoLabel;
    private Table table;
    private TextField userName, fullName, email;
    private TextField.TextFieldStyle txtFieldStyle;
    private Label.LabelStyle labelStyle;
    private TextButton btnSave, btnBack;
    private String label;
    private Texture emailTaken, usernameTaken, username_email_taken;

    public EditAccountScreen(SuperBirdGame g){

        this.game = g;

        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        //=================Table Containers and Tables =================================

        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.7f;
        float ch = sh * 0.8f;

        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 100;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        //creates a style for the font
        txtFieldStyle = new TextField.TextFieldStyle();
        txtFieldStyle = skin.get(TextField.TextFieldStyle.class);
        txtFieldStyle.font = font;

        //=======Instantiate style for label======================
        labelStyle = new Label.LabelStyle();
        labelStyle = skin.get(Label.LabelStyle.class);
        labelStyle.font = font;
        table = new Table(skin);


        emailTaken = ImageFunctions.resize("background stuff/emailTaken.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        usernameTaken = ImageFunctions.resize("background stuff/usernameTaken.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        username_email_taken = ImageFunctions.resize("background stuff/username_email_taken.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        
        
        tableContainer = new Container<Table>();
        tableContainer.setSize(sw,sh);
        //tableContainer.setPosition((sw-cw)/2.0f, (sh-ch));
        tableContainer.setPosition(0,0);



    }


    public void showLabels(){

        incorrectInfoLabel = new Label(label,labelStyle);
        txtUserName.setAlignment(Align.center);
        table.row().colspan(3).expandX().fillX();
        table.add(incorrectInfoLabel).colspan(3);

        incorrectInfoLabel.setVisible(false);
        tableContainer.setActor(table);
    }

    public void buttons(){

        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.6f;
        float ch = sh * 0.9f;
        Table buttonTable = new Table(skin);

        table.row().colspan(3).expandX().fillX();
        table.add(buttonTable).colspan(3);

        btnBack = new TextButton("Back", style_button);
        btnSave = new TextButton("Save", style_button);

        buttonTable.row().fillX().expandX();
        buttonTable.add(btnBack).width(cw/4.0f).height(ch / 8.0f);
        buttonTable.add(btnSave).width(cw/4.0f).height(ch / 8.0f);

    }

    public void buttonClickListeners(){
        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingsScreen(game));

            }
        });

        btnSave.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                validateUserName(userName.getText());
                validateEmail(email.getText());

            }
        });
    }

    private void upDateDatabase(){

    }

    private void validateUserName(String username){
        Database database = new Database();
        database.checkUserName(username);
    }

    private void validateEmail(String email){
        Database database = new Database();
        database.checkEmail(email);
    }

    public void changeUserName(){
        //=========This is the label for user name============
        txtUserName = new Label("Edit Username",labelStyle);
        txtUserName.setAlignment(Align.center);

        //=========Text field for the user name================
        userName = new TextField("", txtFieldStyle);

        //Adding the label and the text field in the table
        table.row().colspan(3).expandX().fillX();
        table.add(txtUserName).fillX().width((float)game.GAME_WIDTH/2);
        table.row().colspan(3).expandX().fillX();
        table.add(userName).fillX().width((float)game.GAME_WIDTH/2).height((float)game.GAME_HEIGHT/8);

        //adding the table into the container
        tableContainer.setActor(table);
    }

    public void changeFullName(){
    //=========This is the label for user name============
        txtFullName = new Label("Edit Full Name",labelStyle);
        txtFullName.setAlignment(Align.center);

        //=========Text field for the user name================
        fullName = new TextField("", txtFieldStyle);

        //Adding the label and the text field in the table
        table.row().colspan(3).expandX().fillX();
        table.add(txtFullName).fillX().width((float)game.GAME_WIDTH/2);
        table.row().colspan(3).expandX().fillX();
        table.add(fullName).fillX().width((float)game.GAME_WIDTH/2).height((float)game.GAME_HEIGHT/8);

        //adding the table into the container
        tableContainer.setActor(table);
    }

    public void changeEmail(){
        //=========This is the label for user name============
        txtEmail = new Label("Edit Email",labelStyle);
        txtEmail.setAlignment(Align.center);

        //=========Text field for the user name================
        email = new TextField("", txtFieldStyle);

        //Adding the label and the text field in the table
        table.row().colspan(3).expandX().fillX();
        table.add(txtEmail).fillX().width((float)game.GAME_WIDTH/2);
        table.row().colspan(3).expandX().fillX();
        table.add(email).fillX().width((float)game.GAME_WIDTH/2).height((float)game.GAME_HEIGHT/8);

        //adding the table into the container
        tableContainer.setActor(table);

    }

    public void setUpNewScreen(){
        if(Database.usernameStatus.equals("DNE") && Database.emailStatus.equals("DNE")) {
            Database.usernameStatus = "NULL";
            Database.emailStatus = "NULL";

            Database database = new Database();
            database.upDateUsername(userName.getText());

            if(!(fullName.getText().length() == 0) || !(fullName.getText().charAt(0) == ' ')){
                database.upDateFullName(fullName.getText());
            }

            database.upDateEmail(email.getText());
            game.setScreen(new SettingsScreen(game));
        }

    }

    @Override
    public void show() {
        changeUserName();
        changeFullName();
        changeEmail();
        buttons();
        buttonClickListeners();
        //adding the table into the container
        tableContainer.setActor(table);
        //set the table container with all the labels, textfields and buttons
        stage.addActor(tableContainer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        setUpNewScreen();
        game.batch.begin();
        if(Database.usernameStatus.equals("Exist") && Database.emailStatus.equals("Exist"))
            game.batch.draw(username_email_taken,0,0);
        else if(Database.emailStatus.equals("Exist"))
            game.batch.draw(emailTaken,0,0);
        else if(Database.usernameStatus.equals("Exist"))
            game.batch.draw(usernameTaken,0,0);

        game.batch.end();

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
        fontGenerator.dispose();
        game.dispose();
        stage.dispose();
        font.dispose();
        skin.dispose();
    }
}
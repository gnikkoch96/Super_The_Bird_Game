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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.tools.ImageFunctions;

public class SettingsScreen implements Screen {

    private SuperBirdGame game;
    private Stage stage;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Texture background, volumeImage;
    private TextButton.TextButtonStyle style_button;
    private Skin skin;
    private TextButton btnEditAccount, btnChangePassword, btnBack;

    public SettingsScreen(SuperBirdGame g){
        game = g;
        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        Container<Table> tableContainer = new Container<Table>();
        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);

        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 120;

        font = fontGenerator.generateFont(fontParameter);

        volumeImage = ImageFunctions.resize("screen icons//volume.png", SuperBirdGame.GAME_WIDTH /2, SuperBirdGame.GAME_HEIGHT /4);
    }

    public void buttons(){
        //setting style for the button and resizing the font
        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 100;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        //==================Button Edit Account =====================
        btnEditAccount = new TextButton("Edit Account", style_button);
        skin.add("fonts", font);
        //set the position and size of the button
        btnEditAccount.setPosition((game.GAME_WIDTH /2) - 500,(game.GAME_HEIGHT /2) + 300);
        btnEditAccount.setSize(1000,350);

        //set the listener for log in button
        btnEditAccount.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //btnLoginClicked();
            }
        });
        //=================Button End Edit Account =====================
        //=================Button Change Password =====================

        btnChangePassword = new TextButton("Change Password", style_button);;
        //set the position and size of the button
        btnChangePassword.setPosition((game.GAME_WIDTH /2) - 500,(game.GAME_HEIGHT /2) - 150);
        btnChangePassword.setSize(1000,350);

        //set the listener for SignUp button
        btnChangePassword.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               // btnSignUpClicked();
            }
        });

        //==================Button End Change Password=================================
        //==================Button Back ==============================================
        btnBack = new TextButton("Back", style_button);
        skin.add("fonts", font);
        //set the position and size of the button
        btnBack.setPosition(game.GAME_WIDTH - (game.GAME_WIDTH - 100),(game.GAME_HEIGHT) - 300);
        btnBack.setSize(400,200);

        //set the listener for log in button
        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //btnLoginClicked();
                game.setScreen(new HomeScreen(game));
            }
        });
        //==================End of Button back =========================================



        stage.addActor(btnEditAccount);
        stage.addActor(btnChangePassword);
        stage.addActor(btnBack);


    }

    @Override
    public void show() {
        buttons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,1,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //buttons();

        game.batch.begin();
        game.batch.draw(volumeImage,game.GAME_WIDTH - (game.GAME_WIDTH - 700),100);
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

    }
}

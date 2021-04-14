package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
    private Container<Table> tableContainer;
    private Table table, buttonTable;
    private Image volumes, volumeLabel;
    private ImageButton leftVolumeBtn, rightVolumeBtn;
    private SpriteDrawable currentVolume,volume0, volume1,volume2, volume3,volume4, volume5,volume6,volume7, volume8,volume9;
    public static int volumeChanges = 5;

    public SettingsScreen(SuperBirdGame g){
        game = g;
        stage = new Stage(game.viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("comic-ui.json"));

        background = ImageFunctions.resize("background stuff/bg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);
        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        style_button = skin.get(TextButton.TextButtonStyle.class);
        fontParameter.size = 100;
        font = fontGenerator.generateFont(fontParameter);
        style_button.font = font;

        //this is for the volume image label
        volumeLabel = new Image(new Texture("screen icons//volumeLabel.png"));

        volumes = new Image();


        //instantiate the volume images
        volumeImages();
        setVolume();

        leftVolumeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//left_volumebtn.png"))));
        rightVolumeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//right_volumebtn.png"))));
    }

    public void setVolume(){

        if(volumeChanges == 1)
            currentVolume = volume0;
        else if(volumeChanges == 2)
            currentVolume = volume1;
        else if(volumeChanges == 3)
            currentVolume = volume2;
        else if(volumeChanges == 4)
            currentVolume = volume3;
        else if(volumeChanges == 5)
            currentVolume = volume4;
        else if(volumeChanges == 6)
            currentVolume = volume5;
        else if(volumeChanges == 7)
            currentVolume = volume6;
        else if(volumeChanges == 8)
            currentVolume = volume7;
        else if(volumeChanges == 9)
            currentVolume = volume8;
        else if(volumeChanges == 10)
            currentVolume = volume9;

        volumes.setDrawable(currentVolume);
    }

    public void volumeImages(){
        volume0 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume0.png")));
        volume1 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume1.png")));
        volume2 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume2.png")));
        volume3 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume3.png")));
        volume4 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume4.png")));
        volume5 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume5.png")));
        volume6 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume6.png")));
        volume7 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume7.png")));
        volume8 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume8.png")));
        volume9 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume9.png")));
    }

    //this method will design and alight the buttons in the table and table container
    public void buttons(){

        btnEditAccount = new TextButton("Edit Account", style_button);

        btnChangePassword = new TextButton("Change Password", style_button);

        btnBack = new TextButton("Back", style_button);

        //=================Table Containers and Tables =================================

        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.7f;
        float ch = sh * 0.8f;

        tableContainer = new Container<Table>();
        tableContainer.setSize(cw,ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch));


        table = new Table(skin);
        table.row().colspan(3).expandX().fillX();
        table.add(btnBack).fillX().width((float)game.GAME_WIDTH /6).height((float)game.GAME_HEIGHT /6).padRight(cw).padTop(150);
        table.row().colspan(3).expandX().fillX();
        table.add(btnEditAccount).fillX().width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6);
        table.row().colspan(3).expandX().fillX();
        table.add(btnChangePassword).fillX().width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6).padTop(50);
        table.row().colspan(3).expandX().fillX();
        table.add(volumeLabel).fillX().width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /15).padTop(50);
        table.row().colspan(3).expandX().fillX();
        //table.add(volumes).fillX().width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6).padTop(50);

        buttonTable = new Table(skin);

        table.add(buttonTable).colspan(3);

        buttonTable.row().fillX().expandX();
        buttonTable.add(leftVolumeBtn).height((float) game.GAME_HEIGHT / 6);//.width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6).padTop(50).padLeft(500);
        buttonTable.add(volumes).width((float) game.GAME_WIDTH / 2).height((float) game.GAME_HEIGHT / 6);
        buttonTable.add(rightVolumeBtn).height((float) game.GAME_HEIGHT / 6);//.width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6).padTop(50).padRight(500);


        tableContainer.setActor(table);
        //==============================================================================



        buttonListeners();


        stage.addActor(tableContainer);


    }

    //this method contains all the Clicklistener for the buttons
    public void buttonListeners(){


        //set the listener back to the home screen
        btnBack.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //btnLoginClicked();
                game.setScreen(new HomeScreen(game));

            }
        });


        //set the listener for SignUp button
        btnChangePassword.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // btnSignUpClicked();
                game.setScreen(new ChangePasswordScreen(game));
            }
        });


        //set the listener for edit account button
        btnEditAccount.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //btnLoginClicked();
                game.setScreen(new EditAccountScreen(game));
            }
        });

        //change the volume in increasing order
        rightVolumeBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(volumeChanges != 10)
                    volumeChanges += 1;

                System.out.println("volumeChange = " + volumeChanges);
                setVolume();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });


        leftVolumeBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(volumeChanges != 1)
                    volumeChanges -= 1;
                System.out.println("volumeChange = " + volumeChanges);
                setVolume();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

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
        //game.batch.draw(volumeImage,game.GAME_WIDTH - (game.GAME_WIDTH - 700),100);
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

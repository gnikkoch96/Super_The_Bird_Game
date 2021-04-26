package com.procode.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.ImageFunctions;
import com.procode.game.tools.Volume;

import javax.swing.plaf.nimbus.State;

public class MiniSettingScreen{

    private SuperBirdGame game;
    private Stage stage;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Skin skin;
    private BitmapFont font;
    private Container<Table> tableContainer, settingsContainer;
    private Table table, buttonTable;
    private Texture background;
    private SpriteBatch batch;
    public ImageButton playbtn, quitbtn, settingsbtn, savebtn, leftVolumeBtn, rightVolumeBtn, orangeBackground, blueBackground;
    public static int state = 0;
    private Volume volume;


    public MiniSettingScreen(Stage s){
        stage = s;

        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        font = new BitmapFont();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Cartoon 2 US.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 130;
        font = fontGenerator.generateFont(fontParameter);

        background = ImageFunctions.resize("background stuff/woodbg.png", SuperBirdGame.GAME_WIDTH, SuperBirdGame.GAME_HEIGHT);

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.WHITE);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        //========Setting the sizes of the containers ===================
        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.5f;
        float ch = sh * 0.8f;

        batch = new SpriteBatch();

        volume = new Volume();

        settingsContainer = new Container<Table>();
        settingsContainer.setSize(cw,ch);
        settingsContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);


        tableContainer = new Container<Table>();
        tableContainer.setSize(cw,ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);


        table = new Table(skin);
        buttonTable = new Table(skin);


        playbtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//play button.png"))));
        quitbtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//quit button.png"))));
        settingsbtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//option button.png"))));
        savebtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//savebtn.png"))));
        orangeBackground = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("background stuff//orange_bg.png"))));
        blueBackground = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("background stuff//bluebg.png"))));

        //buttons pressed
        Texture playPressed = new Texture("screen icons//play buttonsmashed.png");
        playbtn.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(playPressed));
        Texture quitPressed = new Texture("screen icons//broken quit button.png");
        quitbtn.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(quitPressed));
        Texture settingsPressed = new Texture("screen icons//settings buttonsmashed.png");
        settingsbtn.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(settingsPressed));
        Texture orangeBackgroundPressed = new Texture("background stuff//orange_bg_clicked.png");
        orangeBackground.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(orangeBackgroundPressed));
        Texture blueBackgroundPressed = new Texture("background stuff//bluebg_clicked.png");
        blueBackground.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(blueBackgroundPressed));


        table.row().colspan(3).expandX().fillX();
        table.add(playbtn).fillX().width((float)game.GAME_WIDTH /4).height((float)game.GAME_HEIGHT /6);
        table.row().colspan(3).expandX().fillX();
        table.add(playbtn).fillX().width((float)game.GAME_WIDTH /4).height((float)game.GAME_HEIGHT /6);
        table.row().colspan(3).expandX().fillX();
        table.add(settingsbtn).fillX().width((float)game.GAME_WIDTH /4).height((float)game.GAME_HEIGHT /6);
        table.row().colspan(3).expandX().fillX();
        table.add(quitbtn).fillX().width((float)game.GAME_WIDTH /4).height((float)game.GAME_HEIGHT /6);


       //tableContainer.setBackground(textureRegionDrawableBg);
       // tableContainer.setBackground(textureRegionDrawableBg);
        tableContainer.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("background stuff/menu.png"))));


        tableContainer.setActor(table);

    }

    public void Buttons(){
        playbtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //btnLoginClicked();
                Gdx.app.log("Pressed: " , "Play");
                HUD.state = 0;
                Gdx.app.log("Hud.state", String.valueOf(HUD.state));
            }
        });

        settingsbtn.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(SettingsScreen.volumeChanges != 10){

                }
                    //volumeChanges += 1;
                state = 1;
                System.out.println("settings");

            }


        });

        quitbtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //this will switch the state in to GAME_QUIT which will exit the game back to homescreen
                HUD.state = 4;
            }
        });

        savebtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                state = 0;
            }
        });


    }

    //this volume button needs to be separated from the other buttons due to its nature
    public void volumeButtons(){
        rightVolumeBtn.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(SettingsScreen.volumeChanges != 10) {
                    SettingsScreen.volumeChanges += 1;
                    volume.setVolume(SettingsScreen.volumeChanges);
                }
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

        });


        leftVolumeBtn.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(SettingsScreen.volumeChanges != 1) {
                    SettingsScreen.volumeChanges -= 1;
                    volume.setVolume(SettingsScreen.volumeChanges);
                }
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

        });


        //these buttons would be separated soon
        blueBackground.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                PlayScreen.changeBackground = "blue";

                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

        });


        orangeBackground.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                PlayScreen.changeBackground = "orange";
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

        });
    }

    public void setContainerVisible(boolean e){
        tableContainer.setVisible(e);
    }

    public void setSettingsContainerVisible(boolean e){settingsContainer.setVisible(e); }

    public Container<Table> getActor(){
        return tableContainer;
    }

    //this method will return a table container of volume and change background
    public Container<Table> getSettingsActor(){
        settings();
        return settingsContainer;
    }

    public Stage getStage(){
        stage.addActor(tableContainer);
        return stage;
    }


    //=================This is container for the Settings in the PlayScreen ========================

    public void settings(){
        float sw = SuperBirdGame.GAME_WIDTH;
        float sh = SuperBirdGame.GAME_HEIGHT;

        float cw = sw * 0.5f;
        float ch = sh * 0.8f;

        Table table = new Table(skin);
        Table buttonTable = new Table(skin);


        leftVolumeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//left_volumebtn.png"))));
        rightVolumeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//right_volumebtn.png"))));

        //this is for the volume image label
        Image volumeLabel = new Image(new Texture("screen icons//volumeLabel.png"));

        //set the initial volume from the setting screen
        volume.setVolume(SettingsScreen.volumeChanges);
        table.row().colspan(3).expandX().fillX();
        table.add(volumeLabel).fillX().width(cw /4.0f).height(ch /9.0f);
        table.row().colspan(3).expandX().fillX();
        table.add(volumeLabel).fillX().width(cw /4.0f).height(ch /9.0f);
        table.row().colspan(3).expandX().fillX();
        table.add(buttonTable).colspan(3);
        table.row().colspan(3).expandX().fillX();
        table.add(savebtn).fillX().width(cw /4).height(ch /6).padTop(35);



        buttonTable.row().fillX().expandX();
        buttonTable.add(leftVolumeBtn).width(cw/4.0f).height(ch / 8.0f);//.padLeft(ch/12.0f);//.width((float)game.GAME_WIDTH /2).height((float)game.GAME_HEIGHT /6).padTop(50).padLeft(500);
        buttonTable.add(volume.getVolume()).width(cw/4.0f).height(ch / 6.0f);
        buttonTable.add(rightVolumeBtn).width(cw/4.0f).height(ch / 8.0f);//.padRight(ch/12.0f);
        buttonTable.row().fillX().expandX();
        buttonTable.add(blueBackground).width(cw/4.0f).height(ch / 8.0f);
        buttonTable.add(orangeBackground).width(cw/4.0f).height(ch / 8.0f);//.padRight(ch/12.0f);
        buttonTable.row().fillX().expandX();
        //System.out.println("volumchanges = " + SettingsScreen.volumeChanges);
        settingsContainer.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("background stuff/settingsScreen.png"))));

        settingsContainer.setActor(table);
    }
    //=========================End of Container ====================================================


}

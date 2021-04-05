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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.ImageFunctions;

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
    public ImageButton playbtn, quitbtn, settingsbtn, savebtn;
    public static int state = 0;


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

        //buttons pressed
        Texture playPressed = new Texture("screen icons//play buttonsmashed.png");
        playbtn.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(playPressed));
        Texture quitPressed = new Texture("screen icons//broken quit button.png");
        quitbtn.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(quitPressed));
        Texture settingsPressed = new Texture("screen icons//settings buttonsmashed.png");
        settingsbtn.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(settingsPressed));

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
                HUD.state = 0;
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

        table.add(savebtn).fillX().width((float)game.GAME_WIDTH /4).height((float)game.GAME_HEIGHT /6);
        table.row().colspan(3).expandX().fillX();

        settingsContainer.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("background stuff/menu.png"))));

        settingsContainer.setActor(table);
    }
    //=========================End of Container ====================================================


}

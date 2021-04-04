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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.tools.ImageFunctions;

public class MiniSettingScreen{

    private SuperBirdGame game;
    private Stage stage;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private Skin skin;
    private BitmapFont font;
    private Container<Table> tableContainer;
    private Table table, buttonTable;
    private Texture background;
    private SpriteBatch batch;
    public ImageButton playbtn, quitbtn, settingsbtn;


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

        tableContainer = new Container<Table>();
        tableContainer.setSize(cw,ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch)/2.0f);


        table = new Table(skin);
        buttonTable = new Table(skin);


        playbtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//playbtn.png"))));
        quitbtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//quitbtn.png"))));
        settingsbtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//settingsbtn.png"))));


        playbtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //btnLoginClicked();
                HUD.state = 0;
            }
        });

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
    }

    public void setContainerVisible(boolean e){
        tableContainer.setVisible(e);
    }

    public Container<Table> getActor(){
        return tableContainer;
    }

    public Stage getStage(){
        stage.addActor(tableContainer);
        return stage;
    }





}

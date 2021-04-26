package com.procode.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.procode.game.SuperBirdGame;

//this class is used if you want a specific screen to handle the navigation buttons such as back, home, and menu
//(Nikko: not sure why it isn't able to read home and menu as of yet, but back works fine)
public class BaseScene extends ScreenAdapter{
    protected SuperBirdGame game;
    private boolean keyHandled;

    public BaseScene(SuperBirdGame game){
        this.game = game;
        this.keyHandled = false;
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
        Gdx.input.setCatchKey(Input.Keys.HOME, true);
        Gdx.input.setCatchKey(Input.Keys.MENU, true);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        Gdx.app.log("BACK", String.valueOf(Gdx.input.isKeyJustPressed(Input.Keys.BACK)));
        Gdx.app.log("HOME", String.valueOf(Gdx.input.isKeyJustPressed(Input.Keys.HOME)));
        Gdx.app.log("MENU", String.valueOf(Gdx.input.isKeyJustPressed(Input.Keys.MENU)));
        if(Gdx.input.isKeyPressed(Input.Keys.BACK) ||
                Gdx.input.isKeyJustPressed(Input.Keys.HOME) ||
                    Gdx.input.isKeyJustPressed(Input.Keys.MENU)){

            if(keyHandled){
                return;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
                handleBackPress();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.HOME)){
                handleHomePress();
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.MENU)){
                handleMenuPress();
            }

            keyHandled = true;
        }else{
            keyHandled = false;
        }

    }

    protected void handleBackPress(){
        Gdx.app.log("Pressed: ", "BACK");
    }

    protected void handleHomePress(){
        Gdx.app.log("Pressed: ", "HOME");
    }

    protected void handleMenuPress(){
        Gdx.app.log("Pressed:", "MENU");
    }


}

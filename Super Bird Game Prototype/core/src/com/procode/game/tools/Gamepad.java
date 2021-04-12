package com.procode.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.procode.game.SuperBirdGame;
import com.procode.game.scenes.HUD;
import com.procode.game.screens.PlayScreen;

public class Gamepad {
    private float x; //values for position to add to the bird
    private float y;
    public float touchSensitivity; // the amount the bird moves per second the button is held

    public int buttonSize; // because image is a circle only need the radius so size is a single variable
    public ImageButton upArrow; // need to be images to add on click listeners does not work well with textures
    public ImageButton downArrow;
    public ImageButton leftArrow;
    public ImageButton rightArrow;
    public ImageButton shootButton;



    //initializes
    public Gamepad(SuperBirdGame game) {
        x = 0;
        y = 0;
        touchSensitivity = game.GAME_HEIGHT / 60;
        buttonSize = game.GAME_HEIGHT / 10;

        upArrow = ImageFunctions.resizeImageButton("screen icons//up button.png", buttonSize, buttonSize);
        downArrow = ImageFunctions.resizeImageButton("screen icons//down button.png", buttonSize, buttonSize);
        leftArrow = ImageFunctions.resizeImageButton("screen icons//left button.png", buttonSize, buttonSize);
        rightArrow = ImageFunctions.resizeImageButton("screen icons//right button.png", buttonSize, buttonSize);

        // pressed icons
        Texture upButtonPressed = ImageFunctions.resize("screen icons//pressed up button.png", buttonSize, buttonSize);
        upArrow.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(upButtonPressed));
        Texture downButtonPressed = ImageFunctions.resize("screen icons//pressed down button.png", buttonSize, buttonSize);
        downArrow.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(downButtonPressed));
        Texture leftButtonPressed = ImageFunctions.resize("screen icons//pressed left button.png", buttonSize, buttonSize);
        leftArrow.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(leftButtonPressed));
        Texture rightButtonPressed = ImageFunctions.resize("screen icons//pressed right button.png", buttonSize, buttonSize);
        rightArrow.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(rightButtonPressed));

        shootButton = ImageFunctions.resizeImageButton("screen icons//shoot button.png", (int)(buttonSize * 1.5), (int)(buttonSize * 1.5));
        Texture shootButtonPressed = ImageFunctions.resize("screen icons//pressed shoot button.png", buttonSize, buttonSize);
        shootButton.getStyle().imageDown =  new TextureRegionDrawable(new TextureRegion(shootButtonPressed));





        // add listeners
        upArrow.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float upMovement = touchSensitivity;
                buttonPressed(upMovement, true);
//                System.out.println("UP");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               buttonReleased(true);
            }
        });

        downArrow.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float downMovement = touchSensitivity * -1;
                buttonPressed(downMovement, true);
//                System.out.println("DOWN");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonReleased(true);
            }
        });

        leftArrow.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float leftMovement = touchSensitivity * -1;
                buttonPressed(leftMovement, false);
//                System.out.println("LEFT");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonReleased(false);
            }
        });

        rightArrow.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float rightMovement = touchSensitivity;
                buttonPressed(rightMovement, false);
//                System.out.println("RIGHT");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonReleased(false);
            }
        });

        shootButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(HUD.state == 0)
                    if(PlayScreen.rapidFireSpit == false) {
                        PlayScreen.rapidFireSpit = true;
                    }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PlayScreen.rapidFireSpit = false;
            }
        });

        //set where the buttons will be placed on screen
        downArrow.setPosition(buttonSize,0);
        leftArrow.setPosition(0,buttonSize);
        rightArrow.setPosition(buttonSize * 2, buttonSize);
        upArrow.setPosition(buttonSize, buttonSize * 2);
        shootButton.setPosition(SuperBirdGame.GAME_WIDTH - buttonSize * 3, (int)(buttonSize/1.5));
    }

    // adds or subtracts the amount depending on the axis
    private void buttonPressed(float movement, boolean isYAxis){
        if (isYAxis){
            y = movement;
        }
        else{
            x = movement;
        }

        System.out.println("button pressed");
    }

    // called when button is released
    public void buttonReleased(boolean isYAxis){
        if (isYAxis){
            y = 0;
        }
        else{
            x = 0;
        }
    }

    public Vector2 getButtonInputs() {
        return new Vector2(x, y);
    }

    public void dispose(){
        x = 0;
        y = 0;
        buttonSize = 0;
        touchSensitivity = 0;
        upArrow = null;
        downArrow = null;
        rightArrow = null;
        leftArrow = null;
        shootButton = null;
    }

}

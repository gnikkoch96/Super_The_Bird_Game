package com.procode.game.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.procode.game.SuperBirdGame;
import com.procode.game.screens.PlayScreen;

public class Gamepad {
    private float x; //values for position to add to the bird
    private float y;
    private float touchSensitivity; // the amount the bird moves per second the button is held

    public int buttonSize; // because image is a circle only need the radius so size is a single variable
    public ImageButton upArrow; // need to be images to add on click listeners does not work well with textures
    public ImageButton downArrow;
    public ImageButton leftArrow;
    public ImageButton rightArrow;
    public ImageButton shootButton;
    public boolean shoot = false;



    //initializes
    public Gamepad(SuperBirdGame game) {
        x = 0;
        y = 0;
        touchSensitivity = game.ANDROID_HEIGHT / 60;
        buttonSize = game.ANDROID_HEIGHT / 10;

        upArrow = ImageFunctions.resizeImageButton("screen icons//up button.png", buttonSize, buttonSize);
        downArrow = ImageFunctions.resizeImageButton("screen icons//down button.png", buttonSize, buttonSize);
        leftArrow = ImageFunctions.resizeImageButton("screen icons//left button.png", buttonSize, buttonSize);
        rightArrow = ImageFunctions.resizeImageButton("screen icons//right button.png", buttonSize, buttonSize);

        shootButton = ImageFunctions.resizeImageButton("screen icons//shoot button.png", (int)(buttonSize * 1.5), (int)(buttonSize * 1.5));

        // add listeners
        upArrow.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float upMovement = touchSensitivity;
                buttonPressed(upMovement, true);
                System.out.println("UP");
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
                System.out.println("DOWN");
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
                System.out.println("LEFT");
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
                System.out.println("RIGHT");
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
               PlayScreen.player.shoot();

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                shoot = false;
            }
        });

        //set where the buttons will be placed on screen
        downArrow.setPosition(buttonSize,0);
        leftArrow.setPosition(0,buttonSize);
        rightArrow.setPosition(buttonSize * 2, buttonSize);
        upArrow.setPosition(buttonSize, buttonSize * 2);
        shootButton.setPosition(SuperBirdGame.ANDROID_WIDTH - buttonSize * 3, (int)(buttonSize/1.5));
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

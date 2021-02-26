package com.procode.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.procode.game.SuperBirdGame;
import com.procode.game.screens.PlayScreen;

import java.util.ArrayList;

public class Gamepad{
    private float x; //values for position to add to the bird
    private float y;
    private float touchSensitivity; // the amount the bird moves per second the button is held

    private int buttonSize; // because image is a circle only need the radius so size is a single variable
    public Image upArrow; // need to be images to add on click listeners does not work well with textures
    public Image downArrow;
    public Image leftArrow;
    public Image rightArrow;

    public Gamepad(SuperBirdGame game) {
        x = 0;
        y = 0;
        touchSensitivity = .05f;
        buttonSize = game.ANDROID_HEIGHT / 10;

        upArrow = new Image(ImageFunctions.resize("screen icons//up button.png", buttonSize, buttonSize));
        downArrow = new Image(ImageFunctions.resize("screen icons//down button.png", buttonSize, buttonSize));
        leftArrow = new Image(ImageFunctions.resize("screen icons//left button.png", buttonSize, buttonSize));
        rightArrow = new Image(ImageFunctions.resize("screen icons//right button.png", buttonSize, buttonSize));

        // add listeners
        upArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float upMovement = y;
                buttonPressed(upMovement, true);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               buttonReleaed(true);
            }
        });

        downArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float downMovement = y * -1;
                buttonPressed(downMovement, true);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonReleaed(true);
            }
        });

        leftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float leftMovement = x * -1;
                buttonPressed(leftMovement, false);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonReleaed(false);
            }
        });

        rightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float rightMovement = x;
                buttonPressed(rightMovement, false);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonReleaed(false);
            }
        });
    }

    //overrides render to draw the buttons onto the screen
    public ArrayList<Image> getButtons(){
        return null;
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
    public void buttonReleaed(boolean isYAxis){
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
}

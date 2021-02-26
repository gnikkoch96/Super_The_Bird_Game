package com.procode.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.procode.game.SuperBirdGame;
import com.procode.game.screens.PlayScreen;

public class Gamepad extends PlayScreen {
    private float x; //values for position to add to the bird
    private float y;
    private float touchSensitivity; // the amount the bird moves per second the button is held

    private int buttonSize; // because image is a circle only need the radius so size is a single variable
    public Texture upArrow;
    public Texture downArrow;
    public Texture leftArrow;
    public Texture rightArrow;

    public Gamepad(SuperBirdGame game)
    {
        super(game); // do we need to instanciate the game again? will it not duplicate the game?
        x = 0;
        y = 0;
        touchSensitivity = .05f;
        buttonSize = game.ANDROID_HEIGHT / 10;
        upArrow = ImageFunctions.resize("screen icons//up button.png", buttonSize, buttonSize);
        downArrow = ImageFunctions.resize("screen icons//down button.png", buttonSize, buttonSize);
        rightArrow = ImageFunctions.resize("screen icons//right button.png", buttonSize, buttonSize);
        leftArrow = ImageFunctions.resize("screen icons//left button.png", buttonSize, buttonSize);

        // add listeners to buttons

    }


    public void update(){

    }

    @Override
    public void render(float delta) {
        //super.render(delta); //commented out render because I do not know if it will draw objects on screen twice

    }

    // adds or subtracts the amount depending on the axis
    private void buttonPressed(float movement, boolean isYAxis){
        if (isYAxis){
            y = movement;
        }
        else{
            x = movement;
        }
    }

    public Vector2 getButtonInputs() {
        return new Vector2(x, y);
    }
}

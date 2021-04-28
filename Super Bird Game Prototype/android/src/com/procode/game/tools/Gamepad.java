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
    public ImageButton upArrow, downArrow, leftArrow, rightArrow; // need to be images to add on click listeners does not work well with textures
    public ImageButton upLeft, upRight, downLeft, downRight;
    public ImageButton shootButton, resizeButton;
    private Texture shrinkImage, growImage;
    public boolean shoot = false;

    public ClickListener clickListener;

    //initializes
    public Gamepad(SuperBirdGame game) {
        x = 0;
        y = 0;
        touchSensitivity = game.GAME_HEIGHT / 60;
        buttonSize = game.GAME_HEIGHT / 8;

        upArrow = ImageFunctions.resizeImageButton("screen icons//up button.png", buttonSize, buttonSize);
        downArrow = ImageFunctions.resizeImageButton("screen icons//down button.png", buttonSize, buttonSize);
        leftArrow = ImageFunctions.resizeImageButton("screen icons//left button.png", buttonSize, buttonSize);
        rightArrow = ImageFunctions.resizeImageButton("screen icons//right button.png", buttonSize, buttonSize);
        upLeft = ImageFunctions.resizeImageButton("screen icons//blank png.png", buttonSize, buttonSize);
        upRight = ImageFunctions.resizeImageButton("screen icons//blank png.png", buttonSize, buttonSize);
        downLeft = ImageFunctions.resizeImageButton("screen icons//blank png.png", buttonSize, buttonSize);
        downRight = ImageFunctions.resizeImageButton("screen icons//blank png.png", buttonSize, buttonSize);

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

        resizeButton = ImageFunctions.resizeImageButton("screen icons//shrink button.png", (int)(buttonSize * 1.5), (int)(buttonSize * 1.5));
        shrinkImage = ImageFunctions.resize("screen icons//shrink button.png", (int) (buttonSize * 1.5), (int) (buttonSize * 1.5));
        growImage = ImageFunctions.resize("screen icons//grow button.png", (int) (buttonSize * 1.5), (int) (buttonSize * 1.5));

        //set where the buttons will be placed on screen
        downArrow.setPosition(buttonSize,0);
        leftArrow.setPosition(0,buttonSize);
        rightArrow.setPosition(buttonSize * 2, buttonSize);
        upArrow.setPosition(buttonSize, buttonSize * 2);

        upLeft.setPosition(0, buttonSize * 2);
        upRight.setPosition(buttonSize * 2, buttonSize * 2);
        downLeft.setPosition(0, 0);
        downRight.setPosition(buttonSize * 2, 0);

        shootButton.setPosition(SuperBirdGame.GAME_WIDTH - buttonSize * 3, (int)(buttonSize/1.5));
        resizeButton.setPosition(SuperBirdGame.GAME_WIDTH - buttonSize * 3,(int)(buttonSize * 2.5));

        shootButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(HUD.state == 0)
                    if(PlayScreen.rapidFireSpit == false)
                        PlayScreen.rapidFireSpit = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                shoot = false;
                PlayScreen.rapidFireSpit = false;
            }
        });

        resizeButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(HUD.state == 0)

                    // if the player hasnt alreayd shrunk yet, then it will be.
                    // also reset the image to grow
                    if(PlayScreen.player.isShrunk == false){
                        PlayScreen.player.shrinkBird();
                        resizeButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(growImage));
                    }

                    // else grow the player
                    // and reset the image to shrink
                    else{
                        PlayScreen.player.growBird();
                        resizeButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(shrinkImage));
                    }
                return true;
            }
        });

        clickListener = new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                logic(x, y);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buttonReleased();
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                logic(x, y);
            }

            int previous = 0;

            private void logic(float x, float y) {
                // 1
                if( (x > upArrow.getX() && x < upArrow.getX() + buttonSize) && (y > upArrow.getY() && y < upArrow.getY() + buttonSize) ) {
                    System.out.println("Up");
                    if (previous != 1) {
                        buttonReleased();
                        previous = 1;
                    }
                    float upMovement = touchSensitivity;
                    buttonPressed(upMovement, true);
                }

                // 2
                if( (x > downArrow.getX() && x < downArrow.getX() + buttonSize) && (y > downArrow.getY() && y < downArrow.getY() + buttonSize) ) {
                    System.out.println("Down");
                    if(previous != 2) {
                        buttonReleased();
                        previous = 2;
                    }
                    float downMovement = touchSensitivity * -1;
                    buttonPressed(downMovement, true);
                }

                // 3
                if( (x > leftArrow.getX() && x < leftArrow.getX() + buttonSize) && (y > leftArrow.getY() && y < leftArrow.getY() + buttonSize) ) {
                    System.out.println("Left");
                    if (previous != 3) {
                        buttonReleased();
                        previous = 3;
                    }
                    float leftMovement = touchSensitivity * -1;
                    buttonPressed(leftMovement, false);
                }

                // 4
                if( (x > rightArrow.getX() && x < rightArrow.getX() + buttonSize) && (y > rightArrow.getY() && y < rightArrow.getY() + buttonSize) ) {
                    System.out.println("Right");
                    if (previous != 4) {
                        buttonReleased();
                        previous = 4;
                    }
                    float rightMovement = touchSensitivity;
                    buttonPressed(rightMovement, false);
                }

                // 5
                if( (x > upLeft.getX() && x < upLeft.getX() + buttonSize) && (y > upLeft.getY() && y < upLeft.getY() + buttonSize) ) {
                    System.out.println("UP LEFT");
                    if (previous != 5) {
                        buttonReleased();
                        previous = 5;
                    }
                    float upMovement = touchSensitivity;
                    float leftMovement = touchSensitivity * -1;
                    buttonPressed(upMovement, leftMovement);
                }

                // 6
                if( (x > upRight.getX() && x < upRight.getX() + buttonSize) && (y > upRight.getY() && y < upRight.getY() + buttonSize) ) {
                    System.out.println("UP RIGHT");
                    if (previous != 6) {
                        buttonReleased();
                        previous = 6;
                    }
                    float upMovement = touchSensitivity;
                    float rightMovement = touchSensitivity;
                    buttonPressed(upMovement, rightMovement);
                }

                // 7
                if( (x > downLeft.getX() && x < downLeft.getX() + buttonSize) && (y > downLeft.getY() && y < downLeft.getY() + buttonSize) ) {
                    System.out.println("DOWN LEFT");
                    if (previous != 7) {
                        buttonReleased();
                        previous = 7;
                    }
                    float downMovement = touchSensitivity * -1;
                    float leftMovement = touchSensitivity * -1;
                    buttonPressed(downMovement, leftMovement);
                }

                // 8
                if( (x > downRight.getX() && x < downRight.getX() + buttonSize) && (y > downRight.getY() && y < downRight.getY() + buttonSize) ) {
                    System.out.println("DOWN RIGHT");
                    if (previous != 8) {
                        buttonReleased();
                        previous = 8;
                    }
                    float downMovement = touchSensitivity * -1;
                    float rightMovement = touchSensitivity;
                    buttonPressed(downMovement, rightMovement);
                }
            }
        }; // ClickListener end
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

    private void buttonPressed(float movey, float movex) {
        y = movey;
        x = movex;
    }

    // called when button is buttonReleasedd
    public void buttonReleased(){
        x = 0;
        y = 0;
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
